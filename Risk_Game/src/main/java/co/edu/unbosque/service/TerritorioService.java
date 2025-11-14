package co.edu.unbosque.service;

import co.edu.unbosque.model.TerritorioDTO;
import co.edu.unbosque.model.persistence.DataMapper;
import co.edu.unbosque.model.persistence.TerritorioDAO;

/**
 * Servicio encargado de la lógica de negocio para la entidad Territorio.
 * Encapsula el acceso al {@link TerritorioDAO} y expone operaciones básicas de
 * creación, eliminación, búsqueda, listado y persistencia.
 * 
 * <p>
 * Al inicializarse, el servicio carga el DAO y lee cualquier archivo
 * serializado existente para restaurar el estado.
 * </p>
 * 
 * @author Mariana Pineda
 * @since 1.0
 */
public class TerritorioService {

	/** DAO utilizado para persistencia y consultas de territorios */
	private TerritorioDAO territorioDAO;

	/**
	 * Constructor que crea e inicializa el DAO, cargando estado serializado si está
	 * disponible.
	 */
	public TerritorioService() {
		territorioDAO = new TerritorioDAO();
		territorioDAO.leerArchivoSerializado();
	}

	/**
	 * Crea un territorio a partir de su DTO delegando en el DAO.
	 *
	 * @param dto DTO con los datos del territorio
	 * @return true si el territorio fue creado correctamente; false en caso
	 *         contrario
	 */
	public boolean crearTerritorio(TerritorioDTO dto) {
		return territorioDAO.crear(dto);
	}

	/**
	 * Elimina un territorio identificado por su nombre.
	 *
	 * @param nombre nombre del territorio a eliminar
	 * @return true si el territorio fue eliminado; false si no se encontró
	 */
	public boolean eliminarTerritorio(String nombre) {
		return territorioDAO.eliminarPorNombre(nombre);
	}

	/**
	 * Obtiene una representación textual de todos los territorios (delegando en el
	 * DAO).
	 *
	 * @return cadena con la información de todos los territorios
	 */
	public String mostrarTerritorios() {
		return territorioDAO.mostrarTodo();
	}

	/**
	 * Busca un territorio por nombre y devuelve su DTO correspondiente.
	 *
	 * @param nombre nombre del territorio a buscar
	 * @return {@link TerritorioDTO} si se encuentra; null si no existe
	 */
	public TerritorioDTO buscarTerritorio(String nombre) {
		var t = territorioDAO.findByNombre(nombre);
		return t == null ? null : DataMapper.territorioToTerritorioDTO(t);
	}

	/**
	 * Persiste el estado actual de los territorios tanto en formato de texto como
	 * en formato serializado.
	 */
	public void guardar() {
		territorioDAO.escribirArchivo();
		territorioDAO.escribirArchivoSerializado();
	}

	/**
	 * Devuelve el DAO subyacente (útil para orquestadores que necesiten acceso
	 * directo al DAO, como GameService).
	 *
	 * @return instancia de {@link TerritorioDAO} utilizada por este servicio
	 */
	public TerritorioDAO getDAO() {
		return territorioDAO;
	}
}