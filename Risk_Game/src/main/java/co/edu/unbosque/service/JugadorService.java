package co.edu.unbosque.service;

import co.edu.unbosque.model.JugadorDTO;
import co.edu.unbosque.model.persistence.DataMapper;
import co.edu.unbosque.model.persistence.JugadorDAO;

/**
 * Servicio responsable de la lógica de negocio relacionada con la entidad
 * Jugador. Encapsula el acceso al {@link JugadorDAO} y proporciona operaciones
 * comunes (crear, eliminar, buscar, listar y persistir).
 * 
 * <p>
 * Al instanciarse, este servicio carga el DAO y lee el archivo serializado
 * correspondiente para inicializar el estado.
 * </p>
 * 
 * @author Mariana Pineda
 * @since 1.0
 */
public class JugadorService {

	/** DAO utilizado por el servicio para persistencia y consultas */
	private JugadorDAO jugadorDAO;

	/**
	 * Constructor que inicializa el DAO y carga datos serializados si existen.
	 */
	public JugadorService() {
		jugadorDAO = new JugadorDAO();
		jugadorDAO.leerArchivoSerializado();
	}

	/**
	 * Crea un jugador a partir de su DTO delegando en el DAO.
	 *
	 * @param dto DTO con los datos del jugador
	 * @return true si el DAO creó el jugador correctamente; false en caso contrario
	 */
	public boolean crearJugador(JugadorDTO dto) {
		return jugadorDAO.crear(dto);
	}

	/**
	 * Elimina un jugador identificado por su nombre.
	 *
	 * @param nombre nombre del jugador a eliminar
	 * @return true si el jugador fue eliminado; false si no fue encontrado
	 */
	public boolean eliminarJugador(String nombre) {
		return jugadorDAO.eliminarPorNombre(nombre);
	}

	/**
	 * Obtiene una representación textual de todos los jugadores (uso interno,
	 * delega en el DAO).
	 *
	 * @return cadena con la información de todos los jugadores
	 */
	public String mostrarJugadores() {
		return jugadorDAO.mostrarTodo();
	}

	/**
	 * Busca un jugador por nombre y devuelve su DTO correspondiente.
	 *
	 * @param nombre nombre del jugador a buscar
	 * @return {@link JugadorDTO} si se encuentra; null si no existe
	 */
	public JugadorDTO buscarJugador(String nombre) {
		var j = jugadorDAO.findByNombre(nombre);
		return j == null ? null : DataMapper.jugadorToJugadorDTO(j);
	}

	/**
	 * Persiste el estado actual de los jugadores tanto en formato de texto como en
	 * formato serializado.
	 */
	public void guardar() {
		jugadorDAO.escribirArchivo();
		jugadorDAO.escribirArchivoSerializado();
	}

	/**
	 * Devuelve el DAO subyacente (útil para orquestadores que necesiten acceso
	 * directo al DAO, como GameService).
	 *
	 * @return instancia de {@link JugadorDAO} utilizada por este servicio
	 */
	public JugadorDAO getDAO() {
		return jugadorDAO;
	}
}