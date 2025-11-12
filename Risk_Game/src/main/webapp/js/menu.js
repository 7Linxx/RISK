// js/menu.js - versión completa y funcional para manejo de menús y paneles
(function () {
  'use strict';

  // Helper: busca por id exacto o por sufijo (útil con ids JSF que contienen ':')
  function getEl(id) {
    if (!id) return null;
    var el = document.getElementById(id);
    if (el) return el;
    // fallback: buscar por sufijo del id (evita usar CSS.escape para compatibilidad)
    try {
      var simple = id.indexOf(':') !== -1 ? id.split(':').pop() : id;
      return document.querySelector('[id$="' + simple.replace(/"/g, '\\"') + '"]');
    } catch (e) {
      return document.querySelector('[id$="' + id + '"]');
    }
  }

  // Obtiene referencias (puede devolver null si aún no existe el DOM)
  function refs() {
    return {
      gearBtn: getEl('gearBtn'),
      settingsMenu: getEl('settingsMenu'),
      gameModePanel: getEl('game-mode-panel'),
      playerCountPanel: getEl('player-count-panel'),
      userInfo: getEl('user-info'),
      loginPanel: getEl('login'),
      playerSpinner: getEl('playerSpinner'),
      btnJugar: getEl('btn-jugar')
    };
  }

  // Muestra / oculta menú de configuración
  function showMenu(elRefs) {
    if (!elRefs || !elRefs.settingsMenu) return;
    elRefs.settingsMenu.classList.add('visible');
    if (elRefs.gearBtn) elRefs.gearBtn.setAttribute('aria-expanded', 'true');
  }
  
  function hideMenu(elRefs) {
    if (!elRefs || !elRefs.settingsMenu) return;
    elRefs.settingsMenu.classList.remove('visible');
    if (elRefs.gearBtn) elRefs.gearBtn.setAttribute('aria-expanded', 'false');
  }
  
  function toggleMenuInternal() {
    var r = refs();
    if (!r.settingsMenu) {
      console.warn('toggleMenu: no existe #settingsMenu en el DOM');
      return;
    }
    if (r.settingsMenu.classList.contains('visible')) hideMenu(r);
    else showMenu(r);
  }

  // Exponer toggleMenu globalmente
  window.toggleMenu = function () {
    try {
      toggleMenuInternal();
    } catch (err) {
      console.error('Error en toggleMenu:', err);
    }
  };

  // Añadimos listeners para cerrar con click fuera y con Escape
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

  // ============================================================
  // FUNCIONES PARA CONTROLAR LOS PANELES DE "JUGAR"
  // ============================================================
  
  // Mostrar panel de selección de número de jugadores
  function showGameModesInternal() {
    try {
      var r = refs();
      console.log('showGameModes llamado', r);
      
      // Cerrar menú de configuración si está abierto
      if (r.settingsMenu && r.settingsMenu.classList.contains('visible')) {
        hideMenu(r);
      }

      if (r.gameModePanel) {
        r.gameModePanel.style.display = 'block';
        r.gameModePanel.setAttribute('aria-hidden', 'false');
        
        // Ocultar otros paneles
        if (r.playerCountPanel) {
          r.playerCountPanel.style.display = 'none';
          r.playerCountPanel.setAttribute('aria-hidden', 'true');
        }
        if (r.userInfo) {
          r.userInfo.style.display = 'none';
          r.userInfo.setAttribute('aria-hidden', 'true');
        }
        
        // Intentar poner foco en el spinner
        setTimeout(function() {
          if (r.playerSpinner && typeof r.playerSpinner.focus === 'function') {
            try { r.playerSpinner.focus(); } catch (e) { /* ignore */ }
          } else {
            var ps = document.querySelector('[id$="playerSpinner"] input, [id$="playerSpinner"]');
            if (ps && typeof ps.focus === 'function') {
              try { ps.focus(); } catch (e) { /* ignore */ }
            }
          }
        }, 100);
      } else {
        console.warn('showGameModes: no existe el elemento #game-mode-panel en el DOM');
      }
    } catch (err) {
      console.error('Error en showGameModesInternal:', err);
    }
  }

  // Volver al panel principal (oculta todos los paneles)
  function backToMainInternal() {
    try {
      var r = refs();
      console.log('backToMain llamado');
      
      if (r.gameModePanel) {
        r.gameModePanel.style.display = 'none';
        r.gameModePanel.setAttribute('aria-hidden', 'true');
      }
      if (r.playerCountPanel) {
        r.playerCountPanel.style.display = 'none';
        r.playerCountPanel.setAttribute('aria-hidden', 'true');
      }
      if (r.userInfo) {
        r.userInfo.style.display = 'none';
        r.userInfo.setAttribute('aria-hidden', 'true');
      }
      
      // Regresar foco al botón JUGAR
      setTimeout(function() {
        if (r.btnJugar && typeof r.btnJugar.focus === 'function') {
          try { r.btnJugar.focus(); } catch (e) { /* ignore */ }
        } else {
          var playBtn = document.querySelector('[id$="btn-jugar"], .play-btn');
          if (playBtn && typeof playBtn.focus === 'function') {
            try { playBtn.focus(); } catch (e) { /* ignore */ }
          }
        }
      }, 100);
    } catch (err) {
      console.error('Error en backToMainInternal:', err);
    }
  }

  // Volver del panel de player-count al panel de game-mode
  function backToModeInternal() {
    try {
      var r = refs();
      console.log('backToMode llamado');
      
      if (r.playerCountPanel) {
        r.playerCountPanel.style.display = 'none';
        r.playerCountPanel.setAttribute('aria-hidden', 'true');
      }
      if (r.gameModePanel) {
        r.gameModePanel.style.display = 'block';
        r.gameModePanel.setAttribute('aria-hidden', 'false');
      }
    } catch (err) {
      console.error('Error en backToModeInternal:', err);
    }
  }

  // Mostrar el panel de login con los campos de jugadores
  function showLoginInternal() {
    try {
      var r = refs();
      console.log('showLogin llamado', r);
      
      // Ocultar paneles previos
      if (r.gameModePanel) {
        r.gameModePanel.style.display = 'none';
        r.gameModePanel.setAttribute('aria-hidden', 'true');
      }
      if (r.playerCountPanel) {
        r.playerCountPanel.style.display = 'none';
        r.playerCountPanel.setAttribute('aria-hidden', 'true');
      }
      
      // Mostrar panel de información de usuarios
      if (r.userInfo) {
        r.userInfo.style.display = 'block';
        r.userInfo.setAttribute('aria-hidden', 'false');
        
        // Poner foco en el primer campo visible
        setTimeout(function() {
          var firstInput = r.userInfo.querySelector('input:not([readonly]), textarea, [tabindex]');
          if (firstInput && typeof firstInput.focus === 'function') {
            try { firstInput.focus(); } catch (e) { /* ignore */ }
          }
        }, 100);
      } else {
        console.warn('showLogin: no existe #user-info en el DOM');
      }
    } catch (err) {
      console.error('Error en showLoginInternal:', err);
    }
  }

  // Cerrar panel de login
  function closeLoginInternal() {
    try {
      var r = refs();
      console.log('closeLogin llamado');
      
      if (r.userInfo) {
        r.userInfo.style.display = 'none';
        r.userInfo.setAttribute('aria-hidden', 'true');
      }
      
      // Volver al panel de selección de modo
      if (r.gameModePanel) {
        r.gameModePanel.style.display = 'block';
        r.gameModePanel.setAttribute('aria-hidden', 'false');
      }
    } catch (err) {
      console.error('Error en closeLoginInternal:', err);
    }
  }

  // ============================================================
  // EXPONER FUNCIONES GLOBALMENTE
  // ============================================================
  
  window.showGameModes = function () {
    try { showGameModesInternal(); } catch (e) { console.error('Error en showGameModes:', e); }
  };
  
  window.backToMainPanel = function () {
    try { backToMainInternal(); } catch (e) { console.error('Error en backToMainPanel:', e); }
  };
  
  window.backToModePanel = function () {
    try { backToModeInternal(); } catch (e) { console.error('Error en backToModePanel:', e); }
  };
  
  window.showLogin = function () {
    try { showLoginInternal(); } catch (e) { console.error('Error en showLogin:', e); }
  };
  
  window.closeLogin = function () {
    try { closeLoginInternal(); } catch (e) { console.error('Error en closeLogin:', e); }
  };

  // Confirmación de carga
  console.log('✓ menu.js cargado correctamente - Funciones disponibles:', 
    'toggleMenu, showGameModes, backToMainPanel, backToModePanel, showLogin, closeLogin');

})();