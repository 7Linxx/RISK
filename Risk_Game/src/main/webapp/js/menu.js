(function () {
  'use strict';

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
       playerCountPanel: getEl('player-count-panel'),
       userInfo: getEl('user-info'),
       loginPanel: getEl('login'),
       playerSpinner: getEl('playerSpinner'),
       btnJugar: getEl('btn-jugar')
     };
   }

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
           r.gameModePanel.setAttribute('aria-hidden', 'false');
           
       if (r.playerCountPanel) {
         r.playerCountPanel.style.display = 'none';
         r.playerCountPanel.setAttribute('aria-hidden', 'true');
       }
       if (r.userInfo) {
         r.userInfo.style.display = 'none';
         r.userInfo.setAttribute('aria-hidden', 'true');
       }
		           
       setTimeout(function() {
         var spinnerInput = document.querySelector('[id$="playerSpinner"] input');
         if (spinnerInput && typeof spinnerInput.focus === 'function') {
           try { spinnerInput.focus(); } catch (e) { /* ignore */ }
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
       
       if (r.btnJugar) {
         r.btnJugar.style.display = 'block';
       }
       
       setTimeout(function() {
         if (r.btnJugar && typeof r.btnJugar.focus === 'function') {
           try { r.btnJugar.focus(); } catch (e) { /* ignore */ }
         }
       }, 100);
     } catch (err) {
       console.error('Error en backToMainInternal:', err);
     }
   }

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

   function showLoginInternal() {
     try {
       var r = refs();
       console.log('showLogin llamado', r);
       
       if (r.gameModePanel) {
         r.gameModePanel.style.display = 'none';
         r.gameModePanel.setAttribute('aria-hidden', 'true');
       }
       if (r.playerCountPanel) {
         r.playerCountPanel.style.display = 'none';
         r.playerCountPanel.setAttribute('aria-hidden', 'true');
       }
       
       if (r.btnJugar) {
         r.btnJugar.style.display = 'none';
       }
       
       if (r.userInfo) {
         r.userInfo.style.display = 'block';
         r.userInfo.setAttribute('aria-hidden', 'false');
         
         setTimeout(function() {
           var firstInput = r.userInfo.querySelector('input:not([readonly])');
           if (firstInput && typeof firstInput.focus === 'function') {
             try { firstInput.focus(); } catch (e) { /* ignore */ }
           }
         }, 150);
       } else {
         console.warn('showLogin: no existe #user-info en el DOM');
       }
     } catch (err) {
       console.error('Error en showLoginInternal:', err);
     }
   }

   function closeLoginInternal() {
     try {
       var r = refs();
       console.log('closeLogin llamado');
       
       if (r.userInfo) {
         r.userInfo.style.display = 'none';
         r.userInfo.setAttribute('aria-hidden', 'true');
       }
       
       if (r.gameModePanel) {
         r.gameModePanel.style.display = 'block';
         r.gameModePanel.setAttribute('aria-hidden', 'false');
       }
     } catch (err) {
       console.error('Error en closeLoginInternal:', err);
     }
   }

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

   console.log('✓ menu.js cargado correctamente - Funciones disponibles:', 
     'toggleMenu, showGameModes, backToMainPanel, backToModePanel, showLogin, closeLogin');

 })();