package co.edu.unbosque.service;

import co.edu.unbosque.model.Jugador;
import co.edu.unbosque.model.Territorio;
import co.edu.unbosque.model.persistence.JugadorDAO;
import co.edu.unbosque.model.persistence.TerritorioDAO;

/**
 * Servicio de alto nivel que coordina operaciones del juego que requieren
 * acceso tanto a jugadores como a territorios.
 *
 * <p>
 * Este servicio actúa como orquestador entre los DAOs de Jugador y Territorio
 * para operaciones transaccionales sencillas como asignar territorios a
 * jugadores y persistir el estado completo del juego.
 * </p>
 *
 * @author Mariana Pineda
 * @since 1.0
 */
public class GameService {

	/** DAO responsable de la persistencia de jugadores */
	private JugadorDAO jugadorDAO;

	/** DAO responsable de la persistencia de territorios */
	private TerritorioDAO territorioDAO;

	/**
	 * Construye el servicio a partir de los servicios específicos de jugador y
	 * territorio. Se extraen los DAOs desde los servicios para realizar operaciones
	 * que involucren a ambas entidades.
	 *
	 * @param jugadorService    servicio de jugadores desde el cual se obtiene el
	 *                          DAO
	 * @param territorioService servicio de territorios desde el cual se obtiene el
	 *                          DAO
	 */
	public GameService(JugadorService jugadorService, TerritorioService territorioService) {
		this.jugadorDAO = jugadorService.getDAO();
		this.territorioDAO = territorioService.getDAO();
	}

	// ===========================================================
	// ASIGNAR TERRITORIO A JUGADOR
	// ===========================================================
	/**
	 * Asigna el territorio identificado por su nombre al jugador identificado por
	 * su nombre. La operación actualiza el dueño del territorio, añade el
	 * territorio a la lista de territorios del jugador y persiste ambos DAOs en sus
	 * respectivos archivos.
	 *
	 * @param territorio nombre del territorio a asignar
	 * @param jugador    nombre del jugador que recibirá el territorio
	 * @return true si la asignación fue exitosa; false si el territorio o el
	 *         jugador no existen
	 */
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
	/**
	 * Persiste el estado completo de jugadores y territorios usando las versiones
	 * serializadas de sus respectivos DAOs.
	 *
	 * <p>
	 * Este método delega en los DAOs la escritura de sus datos en formato
	 * serializado (por ejemplo para respaldos o restauración).
	 * </p>
	 */
	public void guardarTodo() {
		jugadorDAO.escribirArchivoSerializado();
		territorioDAO.escribirArchivoSerializado();
	}
}