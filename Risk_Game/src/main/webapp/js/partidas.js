(function () {
  'use strict';

  console.log('✓ partidas.js iniciado');

  // Función para obtener elemento por ID con soporte JSF
  function getEl(id) {
    if (!id) return null;
    let el = document.getElementById(id);
    if (el) return el;
    try {
      const simple = id.indexOf(':') !== -1 ? id.split(':').pop() : id;
      return document.querySelector('[id$="' + simple.replace(/"/g, '\\"') + '"]');
    } catch (e) {
      return document.querySelector('[id$="' + id + '"]');
    }
  }

  // Referencias a elementos
  function refs() {
    return {
      gearBtn: getEl('gearBtn'),
      settingsMenu: getEl('settingsMenu')
    };
  }

  // Menú de configuración
  function showMenu(elRefs) {
    if (!elRefs || !elRefs.settingsMenu) return;
    elRefs.settingsMenu.classList.add('visible');
    elRefs.settingsMenu.style.display = 'block';
    if (elRefs.gearBtn) elRefs.gearBtn.setAttribute('aria-expanded', 'true');
  }

  function hideMenu(elRefs) {
    if (!elRefs || !elRefs.settingsMenu) return;
    elRefs.settingsMenu.classList.remove('visible');
    setTimeout(() => {
      elRefs.settingsMenu.style.display = 'none';
    }, 300);
    if (elRefs.gearBtn) elRefs.gearBtn.setAttribute('aria-expanded', 'false');
  }

  function toggleMenuInternal() {
    const r = refs();
    if (!r.settingsMenu) {
      console.warn('toggleMenu: no existe #settingsMenu en el DOM');
      return;
    }
    if (r.settingsMenu.classList.contains('visible')) {
      hideMenu(r);
    } else {
      showMenu(r);
    }
  }

  window.toggleMenu = function () {
    try {
      toggleMenuInternal();
    } catch (err) {
      console.error('Error en toggleMenu:', err);
    }
  };

  // Cerrar menú al hacer clic fuera
  document.addEventListener('click', (ev) => {
    const r = refs();
    if (!r.settingsMenu) return;
    if (!r.settingsMenu.classList.contains('visible')) return;
    const target = ev.target;
    if (r.gearBtn && (target === r.gearBtn || r.gearBtn.contains(target))) return;
    if (r.settingsMenu.contains(target)) return;
    hideMenu(r);
  }, false);

  // Cerrar menú con ESC
  document.addEventListener('keyup', (ev) => {
    if (ev.key === 'Escape' || ev.key === 'Esc') {
      const r = refs();
      if (!r.settingsMenu) return;
      hideMenu(r);
    }
  }, false);

  // Función para cargar una partida
  function cargarPartidaInternal(partidaId) {
    console.log('Cargando partida con ID:', partidaId);
    
    // Aquí puedes agregar lógica adicional antes de redirigir
    // Por ejemplo, mostrar un loader, validar datos, etc.
    
    // Redirigir a la página del juego con el ID de la partida
    window.location.href = 'game.xhtml?partidaId=' + partidaId;
  }

  // Exponer función globalmente
  window.cargarPartida = function (partidaId) {
    try {
      cargarPartidaInternal(partidaId);
    } catch (err) {
      console.error('Error al cargar partida:', err);
      alert('Error al cargar la partida. Por favor, intenta de nuevo.');
    }
  };

  // Función para formatear fechas (útil si necesitas procesamiento adicional)
  function formatearFecha(fecha) {
    const options = { 
      year: 'numeric', 
      month: 'long', 
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    };
    return new Date(fecha).toLocaleDateString('es-ES', options);
  }

  // Función para animar las tarjetas cuando se cargan
  function animarTarjetas() {
    const tarjetas = document.querySelectorAll('.partida-card');
    tarjetas.forEach((tarjeta, index) => {
      tarjeta.style.animationDelay = `${index * 0.1}s`;
    });
  }

  // Inicialización cuando el DOM esté listo
  function init() {
    console.log('Inicializando partidas.js');
    animarTarjetas();
    
    // Agregar efecto de hover mejorado a las tarjetas
    const tarjetas = document.querySelectorAll('.partida-card:not(.terminada)');
    tarjetas.forEach(tarjeta => {
      tarjeta.addEventListener('mouseenter', function() {
        this.style.transition = 'all 0.3s cubic-bezier(0.4, 0, 0.2, 1)';
      });
    });
  }

  // Ejecutar inicialización
  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', init);
  } else {
    init();
  }

  console.log('✓ partidas.js cargado correctamente');
  console.log('Funciones disponibles: toggleMenu, cargarPartida');

})();