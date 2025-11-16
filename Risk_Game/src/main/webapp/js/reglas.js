(function () {
  'use strict';

  console.log('✓ reglas.js iniciado');

  // Variables globales
  let currentPage = 0;
  let totalPages = 0;
  let isAnimating = false;

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
      settingsMenu: getEl('settingsMenu'),
      book: getEl('book'),
      prevBtn: getEl('prevBtn'),
      nextBtn: getEl('nextBtn'),
      currentPageDisplay: getEl('currentPageDisplay'),
      totalPagesDisplay: getEl('totalPagesDisplay')
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

  // Inicialización del libro
  function initBook() {
    const pages = document.querySelectorAll('.page');
    totalPages = Math.floor(pages.length / 2); // Páginas visibles (pares de hojas)
    
    console.log('Total de páginas del libro:', totalPages);
    
    // Mostrar solo las dos primeras páginas al inicio
    pages.forEach((page, index) => {
      if (index < 2) {
        page.classList.remove('hidden');
      } else {
        page.classList.add('hidden');
      }
    });

    updatePageDisplay();
    updateNavigationButtons();
  }

  // Actualizar display de página actual
  function updatePageDisplay() {
    const r = refs();
    if (r.currentPageDisplay) {
      r.currentPageDisplay.textContent = currentPage + 1;
    }
    if (r.totalPagesDisplay) {
      r.totalPagesDisplay.textContent = totalPages;
    }
  }

  // Actualizar estado de botones de navegación
  function updateNavigationButtons() {
    const r = refs();
    
    if (r.prevBtn) {
      if (currentPage === 0) {
        r.prevBtn.disabled = true;
        r.prevBtn.style.opacity = '0.3';
      } else {
        r.prevBtn.disabled = false;
        r.prevBtn.style.opacity = '1';
      }
    }

    if (r.nextBtn) {
      if (currentPage >= totalPages - 1) {
        r.nextBtn.disabled = true;
        r.nextBtn.style.opacity = '0.3';
      } else {
        r.nextBtn.disabled = false;
        r.nextBtn.style.opacity = '1';
      }
    }
  }

  // Función para cambiar de página
  function turnToPage(pageNumber) {
    if (isAnimating) return;
    if (pageNumber < 0 || pageNumber >= totalPages) return;
    if (pageNumber === currentPage) return;

    isAnimating = true;
    const pages = document.querySelectorAll('.page');
    const isGoingForward = pageNumber > currentPage;

    console.log(`Cambiando de página ${currentPage} a ${pageNumber}`);

    // Ocultar todas las páginas excepto las que vamos a mostrar
    pages.forEach((page, index) => {
      const targetLeftPage = pageNumber * 2;
      const targetRightPage = pageNumber * 2 + 1;
      
      if (index === targetLeftPage || index === targetRightPage) {
        page.classList.remove('hidden');
      }
    });

    // Animación de cambio de página
    setTimeout(() => {
      pages.forEach((page, index) => {
        const targetLeftPage = pageNumber * 2;
        const targetRightPage = pageNumber * 2 + 1;
        
        // Ocultar páginas que no corresponden a la página actual
        if (index !== targetLeftPage && index !== targetRightPage) {
          page.classList.add('hidden');
        }
      });

      currentPage = pageNumber;
      updatePageDisplay();
      updateNavigationButtons();

      setTimeout(() => {
        isAnimating = false;
      }, 100);
    }, 50);
  }

  // Navegar a la página anterior
  function prevPageInternal() {
    if (currentPage > 0) {
      turnToPage(currentPage - 1);
    }
  }

  // Navegar a la página siguiente
  function nextPageInternal() {
    if (currentPage < totalPages - 1) {
      turnToPage(currentPage + 1);
    }
  }

  // Ir a una página específica desde el índice
  function goToPageInternal(pageNumber) {
    const targetPage = Math.floor(pageNumber / 2);
    turnToPage(targetPage);
  }

  // Exponer funciones globalmente
  window.prevPage = function () {
    try {
      prevPageInternal();
    } catch (err) {
      console.error('Error en prevPage:', err);
    }
  };

  window.nextPage = function () {
    try {
      nextPageInternal();
    } catch (err) {
      console.error('Error en nextPage:', err);
    }
  };

  window.goToPage = function (pageNumber) {
    try {
      goToPageInternal(pageNumber);
    } catch (err) {
      console.error('Error en goToPage:', err);
    }
  };

  // Navegación con teclado
  document.addEventListener('keydown', (ev) => {
    if (isAnimating) return;
    
    switch (ev.key) {
      case 'ArrowLeft':
        ev.preventDefault();
        prevPageInternal();
        break;
      case 'ArrowRight':
        ev.preventDefault();
        nextPageInternal();
        break;
      case 'Home':
        ev.preventDefault();
        goToPageInternal(0);S
        break;
      case 'End':
        ev.preventDefault();
        goToPageInternal((totalPages - 1) * 2);
        break;
    }
  });

  // Inicializar cuando el DOM esté listo
  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', () => {
      setTimeout(initBook, 100);
    });
  } else {
    setTimeout(initBook, 100);
  }

  console.log('✓ reglas.js cargado correctamente');
  console.log('Funciones disponibles: toggleMenu, prevPage, nextPage, goToPage');
  console.log('Atajos de teclado: ← → (páginas), Home (inicio), End (final), ESC (cerrar menú)');

})();