
var websocket = new WebSocket("ws://localhost:8080/spielendpoint");

websocket.onmessage = function(nachricht) {
	ausgeben(nachricht);
};

function anschalten() {
	websocket.send("lehrer: start");
	alert("angeschaltet");
}

function ausschalten() {
	websocket.send("lehrer: stop");
	alert("ausgeschaltet");
}

function senden() {
	var eingabefeld = document.getElementById("eingabefeld");
	var nachricht = eingabefeld.value;
	websocket.send("lehrer: " + nachricht);
}

function befehlen() {
	var eingabefeld = document.getElementById("eingabefeld");
	var nachricht = eingabefeld.value;
	websocket.send("befehl: " + nachricht);
}
