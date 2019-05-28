
// http://lernen42.de/itam/websockets/websocket_faq.html

@ServerEndpoint("/spielendpoint")
public class SpielEndpoint {
	
	private List<Session> spieler = new ArrayList<>();
	public boolean gestartet = false;
	
	@OnMessage
	public String onMessage(String nachricht) {
		// Lehrer sendet start
		if (nachricht.equals("befehl: start")) {
			sendToAll("Jetzt gehts los!");
			gestartet = true;
		}
		// Lehrer sendet stop
		else if (nachricht.equals("befehl: stop")) {
			sendToAll("Und aus die Maus!");
			gestartet = false;
		}
		// Sende Nachrichten von Lehrern, hat immer sende Berechtigung
		else if (nachricht.startsWith("lehrer: ")){
			sendToAll(nachricht);
		}
		// Sende Nachrichten von Schülern nur wenn es gestartet ist
		else if (nachricht.startsWith("schueler: ")){
			if (gestartet) {
				sendToAll(nachricht);
			}
		}
		// Wenn was ganz anderes gekommen ist
		else {
			System.out.println("Die Nachricht '" + nachricht + "' ist nicht im richtigen Format");
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
	
	public void sendToAll(String nachricht) {
		for (int i = 0; i < spieler.size(); ++i) {
			Session session = spieler.get(i);
			try {
				session.getBasicRemote().sendText(nachricht);
			} catch(IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}