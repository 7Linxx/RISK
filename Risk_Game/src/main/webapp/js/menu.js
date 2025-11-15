(function () {
  'use strict';

  console.log('✓ menu.js iniciado');

  function getEl(id) {
    if (!id) return null;
    var el = document.getElementById(id);
    if (el) return el;
    try {
      var simple = id.indexOf(':') !== -1 ? id.split(':').pop() : id;
      return document.querySelector('[id$="' + simple.replace(/"/g, '\\"') + '"]');
    } catch (e) {
      return document.querySelector('[id$="' + id + '"]');
    }
  }

  function refs() {
    return {
      gearBtn: getEl('gearBtn'),
      settingsMenu: getEl('settingsMenu'),
      gameModePanel: getEl('game-mode-panel'),
      loginPanel: getEl('flogin'),
      playerSpinner: getEl('playerSpinner'),
      btnJugar: getEl('btn-jugar')
    };
  }

  function showMenu(elRefs) {
    if (!elRefs || !elRefs.settingsMenu) return;
    elRefs.settingsMenu.classList.add('visible');
    elRefs.settingsMenu.style.display = 'block';
    if (elRefs.gearBtn) elRefs.gearBtn.setAttribute('aria-expanded', 'true');
  }

  function hideMenu(elRefs) {
    if (!elRefs || !elRefs.settingsMenu) return;
    elRefs.settingsMenu.classList.remove('visible');
    setTimeout(function() {
      elRefs.settingsMenu.style.display = 'none';
    }, 300);
    if (elRefs.gearBtn) elRefs.gearBtn.setAttribute('aria-expanded', 'false');
  }

  function toggleMenuInternal() {
    var r = refs();
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

  document.addEventListener('click', function (ev) {
    var r = refs();
    if (!r.settingsMenu) return;
    if (!r.settingsMenu.classList.contains('visible')) return;
    var target = ev.target;
    if (r.gearBtn && (target === r.gearBtn || r.gearBtn.contains(target))) return;
    if (r.settingsMenu.contains(target)) return;
    hideMenu(r);
  }, false);

  document.addEventListener('keyup', function (ev) {
    if (ev.key === 'Escape' || ev.key === 'Esc') {
      var r = refs();
      if (!r.settingsMenu) return;
      hideMenu(r);
    }
  }, false);

  function showGameModesInternal() {
    try {
      var r = refs();
      console.log('showGameModes llamado', r);

      if (r.settingsMenu && r.settingsMenu.classList.contains('visible')) {
        hideMenu(r);
      }

      if (r.btnJugar) {
        r.btnJugar.style.display = 'none';
      }

      if (r.gameModePanel) {
        r.gameModePanel.style.display = 'block';

        if (r.loginPanel) {
          r.loginPanel.style.display = 'none';
        }

        setTimeout(function() {
          var spinnerInput = document.querySelector('[id$="playerSpinner"] input');
          if (spinnerInput && typeof spinnerInput.focus === 'function') {
            try { spinnerInput.focus(); } catch (e) { console.log('Focus error:', e); }
          }
        }, 150);
      } else {
        console.warn('showGameModes: no existe el elemento #game-mode-panel en el DOM');
      }
    } catch (err) {
      console.error('Error en showGameModesInternal:', err);
    }
  }

  function backToMainInternal() {
    try {
      var r = refs();
      console.log('backToMain llamado');

      if (r.gameModePanel) {
        r.gameModePanel.style.display = 'none';
      }
      if (r.loginPanel) {
        r.loginPanel.style.display = 'none';
      }

      if (r.btnJugar) {
        r.btnJugar.style.display = 'block';
      }

      setTimeout(function() {
        if (r.btnJugar && typeof r.btnJugar.focus === 'function') {
          try { r.btnJugar.focus(); } catch (e) { }
        }
      }, 100);
    } catch (err) {
      console.error('Error en backToMainInternal:', err);
    }
  }
  function backToPlayerInternal() {
    try {
      var r = refs();
      console.log('backToMain llamado');

      if (r.gameModePanel) {
        r.gameModePanel.style.display = 'block';
      }
      if (r.loginPanel) {
        r.loginPanel.style.display = 'none';
      }

      if (r.btnJugar) {
        r.btnJugar.style.display = 'none';
      }

      setTimeout(function() {
        if (r.btnJugar && typeof r.btnJugar.focus === 'function') {
          try { r.btnJugar.focus(); } catch (e) { }
        }
      }, 100);
    } catch (err) {
      console.error('Error en backToPlayerInternal:', err);
    }
  }

  function showLoginInternal() {
      try {
        var r = refs();
        console.log('showLogin llamado', r);

        // Obtener el valor del spinner
        var numPlayers = 2; // Valor por defecto
        var spinnerInput = document.querySelector('[id$="playerSpinner"] input');
        if (spinnerInput) {
          numPlayers = parseInt(spinnerInput.value) || 2;
          console.log('Número de jugadores seleccionado:', numPlayers);
        }

        // Ocultar todos los recuadros de jugadores primero
        for (var i = 1; i <= 6; i++) {
          var jugador = document.getElementById('jugador' + i);
          if (jugador) {
            jugador.style.display = 'none';
          }
        }

        // Mostrar solo los recuadros necesarios según numPlayers
        console.log('Mostrando recuadros del 1 al ' + numPlayers);
        for (var i = 1; i <= numPlayers; i++) {
          var jugador = document.getElementById('jugador' + i);
          if (jugador) {
            jugador.style.display = 'block';
            console.log('Mostrando jugador' + i);
          } else {
            console.warn('No se encontró el elemento jugador' + i);
          }
        }

        // Mostrar el panel de login
        if (r.loginPanel) {
          r.loginPanel.style.display = 'block';

          setTimeout(function() {
            var firstInput = r.loginPanel.querySelector('input:not([readonly])');
            if (firstInput && typeof firstInput.focus === 'function') {
              try { firstInput.focus(); } catch (e) { }
            }
          }, 150);
        } else {
          console.warn('showLogin: no existe #flogin en el DOM');
        }

        // Ocultar otros paneles
        if (r.gameModePanel) {
          r.gameModePanel.style.display = 'none';
        }

        if (r.btnJugar) {
          r.btnJugar.style.display = 'none';
        }

      } catch (err) {
        console.error('Error en showLoginInternal:', err);
      }
    }

  function closeLoginInternal() {
    try {
      var r = refs();
      console.log('closeLogin llamado');

      if (r.loginPanel) {
        r.loginPanel.style.display = 'none';
      }

      if (r.gameModePanel) {
        r.gameModePanel.style.display = 'block';
      }
    } catch (err) {
      console.error('Error en closeLoginInternal:', err);
    }
  }

  // Exponer funciones globalmente
  window.showGameModes = function () {
    try { showGameModesInternal(); } catch (e) { console.error('Error en showGameModes:', e); }
  };

  window.backToMainPanel = function () {
    try { backToMainInternal(); } catch (e) { console.error('Error en backToMainPanel:', e); }
  };
  window.backToPlayerPanel = function () {
    try { backToPlayerInternal(); } catch (e) { console.error('Error en backToMainPanel:', e); }
  };

  window.showLogin = function () {
    try { showLoginInternal(); } catch (e) { console.error('Error en showLogin:', e); }
  };

  window.closeLogin = function () {
    try { closeLoginInternal(); } catch (e) { console.error('Error en closeLogin:', e); }
  };

  console.log('✓ menu.js cargado correctamente - Funciones disponibles:', 
    'toggleMenu, showGameModes, backToMainPanel, showLogin, closeLogin');

})();