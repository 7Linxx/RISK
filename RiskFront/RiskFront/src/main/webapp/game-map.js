/**
 * 
 */

// Placeholder para manejar eventos del mapa (clicks en territorios).
// Debes reemplazarlo cuando uses un SVG del mapa con territorios etiquetados por id.
var gameMap = (function(){
  function onMapClick(ev){
    // Obtener coordenadas, mostrar tooltip o detectar territorio por id si usas SVG
    var x = ev.clientX, y = ev.clientY;
    alert("Click en el mapa en x:"+x+" y:"+y+"\nIntegra aquí la lógica para seleccionar territorios (usar SVG id).");
  }

  return {
    onMapClick: onMapClick
  };
})();