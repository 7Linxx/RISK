function showLogin() {
	closeNumberPlayers();
	document.getElementById('login').classList.add('open-login');
}

function closeLogin() {
	document.getElementById('login').classList.remove('open-login');
}
function showNumberPlayers(){
	document.getElementById('dnplayers').classList.add('open-login');
}
function closeNumberPlayers(){
	document.getElementById('dnplayers').classList.remove('open-login');
}
function showSelectHash(){
	document.getElementById('scode').classList.add('open-login');
}
function closeSelectHash(){
	document.getElementById('scode').classList.remove('open-login');
}
