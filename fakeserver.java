
// http://lernen42.de/itam/websockets/websocket_faq.html

@ServerEndpoint("/spielendpoint")
public class SpielEndpoint {
	
	private List<Session> spieler = new ArrayList<>();
	public boolean gestarted = false;
	
	@OnMessage
	public String onMessage(String nachricht) {
		// Lehrer sendet start
		if (nachricht.equals("lehrer: start")) {
			sendToAll("Jetzt gehts los!");
			gestarted = true;
		}
		// Lehrer sendet stop
		else if (nachricht.equals("lehrer: stop")) {
			sendToAll("Und aus die Maus!");
			gestarted = false;
		}
		// Sende Nachrichten von Lehrern, hat immer sende Berechtigung
		else if (nachricht.startingWith("lehrer: ")){
			sendToAll(nachricht);
		}
		// Sende Nachrichten von Schülern nur wenn es gestartet ist
		else if (nachricht.startingWith("schueler: ")){
			if (gestarted) {
				sendToAll(nachricht);
			}
		}
		return null;
	}
	
	@OnOpen
	public void onOpen(Session session) {
		spieler.add(session);
	}
	
	@OnClose
	public void onClose(Session session) {
		spieler.remove(session);
	}
	
	public void sendToAll(String message) {
		for (int i = 0; i < spieler.size(); ++i) {
			Session session = spieler.get(i);
			session.getBasicRemote().sendText(message);
		}
	}
}