// js/menu.js - versión simple y resistente para mostrar/ocultar el menú de configuración
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
      settingsMenu: getEl('settingsMenu')
    };
  }

  // Muestra / oculta
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

  // Exponer toggleMenu globalmente para que el onclick inline siga funcionando
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
    // si click dentro del botón o del menú no cerramos
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

  // Por si quieres probar desde la consola:
  // window.MenuDebug = { refs: refs, toggleMenu: window.toggleMenu, showMenu: function(){ showMenu(refs()); }, hideMenu: function(){ hideMenu(refs()); } };

  // Log pequeño que confirma que el script se cargó
  if (window.console && window.console.log) {
    console.log('menu.js cargado: toggleMenu disponible');
  }
})();