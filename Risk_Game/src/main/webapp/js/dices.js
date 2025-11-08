function randomDice(uno, dos, tres, cuatro, cinco, numdice, numedice) {
	var arra = [];
	arra[0] = uno;
	arra[1] = dos;
	arra[2] = tres;
	var arre = [];
	arre[0] = cuatro;
	arre[1] = cinco;
	for (var i = 0; i < numdice; i++) {
		var id = 'dicea' + i.toString();
		console.log(id);
		console.log(arra[i]);
		rollDice(id, arra[i]);
	}
	for (var i = 0; i < numedice; i++) {
		var id = 'dicee' + i.toString();
		console.log(id);
		console.log(arre[i]);
		rollDice(id, arre[i]);
	}
}
function disablePaths(){
	var paths= document.querySelectorAll("#layer1 path");
	paths.forEach(function(path){
		path.style.pointerEvents="none";
	});
}
function rollDice(id, value) {
	const dice = document.getElementById(id);
	dice.style.animation = 'rolling 4s';
	setTimeout(() => {
		switch (value) {
			case 1: dice.style.transform =
				'rotateX(0deg) rotateY(0deg)';
				break;
			case 6: dice.style.transform =
				'rotateX(180deg) rotateY(0deg)';
				break;
			case 2: dice.style.transform =
				'rotateX(-90deg) rotateY(0deg)';
				break;
			case 5: dice.style.transform =
				'rotateX(90deg) rotateY(0deg)';
				break;
			case 3: dice.style.transform =
				'rotateX(0deg) rotateY(90deg)';
				break;
			case 4: dice.style.transform =
				'rotateX(0deg) rotateY(-90deg)';
				break;
			default: break;
		}
		dice.style.animation = 'none';
	}, 4050);
}
function showWinner() {
	document.getElementById('win').style.opacity = '0';
	document.getElementById('win').style.fontSize = '15px';
	document.getElementById('win').style.textAlign = 'center';
	setTimeout(function(){
		document.getElementById('win').style.opacity = '1';
	},4060);
}