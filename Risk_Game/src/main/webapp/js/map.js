function addButtons() {
	const territories = ["Alaska", "Northwest_Territory", "Alberta", "Ontario", "Western_United_States", "Quebec", "Eastern_United_States", "Central_America", "Venezuela", "Peru", "Brazil", "Argentina", "Greenland", "Iceland", "Great_Britain", "Northern_Europe", "Scandinavia", "Western_Europe", "Southern_Europe", "Ukraine", "Ural", "Middle_East", "Afghanistan", "China", "India", "Siam", "Siberia", "Mongolia", "Irkutsk", "Yakursk", "Kamchatka", "Japan", "Indonesia", "New_Guinea", "Eastern_Australia", "Western_Australia", "North_Africa", "Congo", "Egypt", "East_Africa", "South_Africa", "Madagascar"]

	for (const territory of territories) {
		var elemento = document.getElementById(territory);
		var posicion = elemento.getBoundingClientRect();

		var nuevoElemento = document.getElementById("b" + territory);
		var posx;
		var posy;

		if (territory == "Alaska") {
			posx = posicion.left + (posicion.width / 2) + 'px';
			posy = posicion.top + (posicion.height / 2) - (10 * posicion.height / 100) + 'px';
		}
		else if (territory == "Northwest_Territory") {
			posx = posicion.left + (posicion.width / 2) + 'px';
			posy = posicion.top + (posicion.height / 2) + (23 * posicion.height / 100) + 'px';
		}
		else if (territory == "Central_America") {
			posx = posicion.left + (posicion.width / 2) + 'px';
			posy = posicion.top + (posicion.height / 2) - (10 * posicion.height / 100) + 'px';
		}
		else if (territory == "Venezuela") {
			posx = posicion.left + (posicion.width / 2) - (18 * posicion.width / 100) + 'px';
			posy = posicion.top + (posicion.height / 2) - (10 * posicion.height / 100) + 'px';
		}
		else if (territory == "Peru") {
			posx = posicion.left + (posicion.width / 2) + (10 * posicion.width / 100) + 'px';
			posy = posicion.top + (posicion.height / 2) + (10 * posicion.height / 100) + 'px';
		}
		else if (territory == "Brazil") {
			posx = posicion.left + (posicion.width / 2) + (12 * posicion.width / 100) + 'px';
			posy = posicion.top + (posicion.height / 2) - (12 * posicion.height / 100) + 'px';
		}
		else if (territory == "Argentina") {
			posx = posicion.left + (posicion.width / 2) - (20 * posicion.width / 100) + 'px';
			posy = posicion.top + (posicion.height / 2) + 'px';
		}
		else if (territory == "Greenland") {
			posx = posicion.left + (posicion.width / 2) + 'px';
			posy = posicion.top + (posicion.height / 2) - (5 * posicion.height / 100) + 'px';
		}
		else if (territory == "Scandinavia") {
			posx = posicion.left + (posicion.width / 2) + 'px';
			posy = posicion.top + (posicion.height / 2) - (15 * posicion.height / 100) + 'px';
		}
		else if (territory == "Western_Europe") {
			posx = posicion.left + (posicion.width / 2) + (12 * posicion.width / 100) + 'px';
			posy = posicion.top + (posicion.height / 2) - (12 * posicion.height / 100) + 'px';
		}
		else if (territory == "Southern_Europe") {
			posx = posicion.left + (posicion.width / 2) + 'px';
			posy = posicion.top + (posicion.height / 2) - (10 * posicion.height / 100) + 'px';
		}
		else if (territory == "East_Africa") {
			posx = posicion.left + (posicion.width / 2) + (10 * posicion.width / 100) + 'px';
			posy = posicion.top + (posicion.height / 2) - (10 * posicion.height / 100) + 'px';
		}
		else if (territory == "Eastern_Australia") {
			posx = posicion.left + (posicion.width / 2) + (15 * posicion.width / 100) + 'px';
			posy = posicion.top + (posicion.height / 2) + 'px';
		}
		else if (territory == "Japan") {
			posx = posicion.left + (posicion.width / 2) + (15 * posicion.width / 100) + 'px';
			posy = posicion.top + (posicion.height / 2) + 'px';
		}
		else if (territory == "Siam") {
			posx = posicion.left + (posicion.width / 2) + 'px';
			posy = posicion.top + (posicion.height / 2) - (12 * posicion.height / 100) + 'px';
		}
		else if (territory == "Kamchatka") {
			posx = posicion.left + (posicion.width / 2) + 'px';
			posy = posicion.top + (posicion.height / 2) - (30 * posicion.height / 100) + 'px';
		}
		else if (territory == "Ural") {
			posx = posicion.left + (posicion.width / 2) - (9 * posicion.width / 100) + 'px';
			posy = posicion.top + (posicion.height / 2) + 'px';
		}
		else if (territory == "Siberia") {
			posx = posicion.left + (posicion.width / 2) + (9 * posicion.width / 100) + 'px';
			posy = posicion.top + (posicion.height / 2) + 'px';
		}
		else {
			posx = posicion.left + (posicion.width / 2) + 'px';
			posy = posicion.top + (posicion.height / 2) + 'px';
		}
		nuevoElemento.style.position = 'absolute';
		nuevoElemento.style.left = posx;
		nuevoElemento.style.top = posy;
	}
}

function pathAction() {
	const territories = ["Alaska", "Northwest_Territory", "Alberta", "Ontario", "Western_United_States", "Quebec", "Eastern_United_States", "Central_America", "Venezuela", "Peru", "Brazil", "Argentina", "Greenland", "Iceland", "Great_Britain", "Northern_Europe", "Scandinavia", "Western_Europe", "Southern_Europe", "Ukraine", "Ural", "Middle_East", "Afghanistan", "China", "India", "Siam", "Siberia", "Mongolia", "Irkutsk", "Yakursk", "Kamchatka", "Japan", "Indonesia", "New_Guinea", "Eastern_Australia", "Western_Australia", "North_Africa", "Congo", "Egypt", "East_Africa", "South_Africa", "Madagascar"]

	for (const territory of territories) {
		document.getElementById(territory).addEventListener("click", function() {
			document.getElementById("b" + territory).click();
		});
	}
}
function replaceUnderscoreWithSpace() {
	var paragraphs = document.querySelectorAll('p');
	paragraphs.forEach(function(paragraph) {
		var text = paragraph.textContent;
		var newText = text.replace(/_/g, ' ')
		paragraph.textContent = newText;
	});
}

window.addEventListener("load", function() {
	addButtons();
	pathAction();
	replaceUnderscoreWithSpace();
	window.addEventListener("resize", function() {
		addButtons();
	});
});