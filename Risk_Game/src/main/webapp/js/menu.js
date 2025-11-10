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
function showUsersInfo(){
	document.getElementById('user-info').classList.add('open-login');
}
function showGameModes() {
			document.getElementById('game-mode-panel').style.display = 'block';
			document.getElementById('btn-jugar').style.display = 'none';
			document.getElementById('player-count-panel').style.display = 'none';
		}
		function backToMainPanel() {
			document.getElementById('game-mode-panel').style.display = 'none';
			document.getElementById('btn-jugar').style.display = 'block';
			document.getElementById('player-count-panel').style.display = 'none';
		}
		function backToModePanel() {
			document.getElementById('game-mode-panel').style.display = 'block';
			document.getElementById('player-count-panel').style.display = 'none';
		}
		function selectMode(mode) {
			document.getElementById('playersForm:selectedMode').value = mode;
			document.getElementById('game-mode-panel').style.display = 'none';
			document.getElementById('player-count-panel').style.display = 'block';
			document.getElementById('player-count-title').textContent = "¿Cuántos jugadores van a jugar "
					+ mode + "?";
		}
		function openLoginForm() {
			document.getElementById('login').classList.add('open-login');
			document.getElementById('player-count-panel').style.display = 'none';
		}
		function closeLogin() {
			document.getElementById('login').classList.remove('open-login');
		}
		// Para mostrar el login modal tras num jugadores (AJAX callback)
		function playersFormSubmitCallback(data) {
			if (data.status === "success")
				openLoginForm();
		}