
var websocket = new WebSocket("ws://localhost:8080/spielendpoint");

websocket.onmessage = function(nachricht) {
	ausgeben(nachricht);
};

function senden() {
	var eingabefeld = document.getElementById("eingabefeld");
	var nachricht = eingabefeld.value;
	websocket.send("schueler: " + nachricht);
}

function ausgeben(nachricht) {
	var ausgabefeld = document.getElementById("ausgabefeld");
	ausgabefeld.innerHTML = nachricht;
}