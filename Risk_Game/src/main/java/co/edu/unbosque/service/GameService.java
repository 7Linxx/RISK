package co.edu.unbosque.service;

import co.edu.unbosque.model.Jugador;
import co.edu.unbosque.model.Territorio;
import co.edu.unbosque.model.persistence.JugadorDAO;
import co.edu.unbosque.model.persistence.TerritorioDAO;

public class GameService {

	private JugadorDAO jugadorDAO;
	private TerritorioDAO territorioDAO;

	public GameService(JugadorService jugadorService, TerritorioService territorioService) {
		this.jugadorDAO = jugadorService.getDAO();
		this.territorioDAO = territorioService.getDAO();
	}

	// ===========================================================
	// ASIGNAR TERRITORIO A JUGADOR
	// ===========================================================
	public boolean asignarTerritorioAJugador(String territorio, String jugador) {

		Territorio t = territorioDAO.findByNombre(territorio);
		Jugador j = jugadorDAO.findByNombre(jugador);

		if (t == null || j == null) {
			return false;
		}

		t.setDuenio(j);
		j.getTerritoriosPertenecientes().insert(t);

		territorioDAO.escribirArchivo();
		jugadorDAO.escribirArchivo();

		return true;
    }

    // ===========================================================
    // GUARDAR TODO (equivalente a MapBean.guardarTodo)
    // ===========================================================
    public void guardarTodo() {
        jugadorDAO.escribirArchivoSerializado();
        territorioDAO.escribirArchivoSerializado();
    }
}
