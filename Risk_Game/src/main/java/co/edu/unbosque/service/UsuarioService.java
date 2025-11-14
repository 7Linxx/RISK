package co.edu.unbosque.service;

import co.edu.unbosque.model.UsuarioDTO;
import co.edu.unbosque.model.persistence.UsuarioDAO;

/**
 * Servicio encargado de la lógica de negocio relacionada con la entidad
 * Usuario. Encapsula el acceso al {@link UsuarioDAO} y expone operaciones
 * comunes como crear, eliminar, buscar, listar y persistir usuarios.
 *
 * <p>
 * Al instanciarse, este servicio inicializa el DAO y trata de cargar un archivo
 * serializado para restaurar el estado previo.
 * </p>
 * 
 * @author Mariana Pineda
 * @since 1.0
 */
public class UsuarioService {

	/** DAO utilizado por el servicio para operaciones de persistencia y consulta */
	private UsuarioDAO usuarioDAO;

	/**
	 * Constructor que inicializa el DAO y carga datos serializados si existen.
	 */
	public UsuarioService() {
		usuarioDAO = new UsuarioDAO();
		usuarioDAO.leerArchivoSerializado();
	}

	/**
	 * Crea un usuario a partir de su DTO delegando en el DAO.
	 *
	 * @param dto DTO con los datos del usuario a crear
	 * @return true si la creación fue exitosa; false en caso contrario (por
	 *         ejemplo, si ya existe)
	 */
	public boolean crearUsuario(UsuarioDTO dto) {
		return usuarioDAO.crear(dto);
	}

	/**
	 * Elimina un usuario buscándolo por su nombre de usuario.
	 *
	 * @param username nombre de usuario del usuario a eliminar
	 * @return true si el usuario fue eliminado; false si no se encontró
	 */
	public boolean eliminarUsuario(String username) {
		return usuarioDAO.eliminarPorNombre(username);
	}

	/**
	 * Busca un usuario por su nombre de usuario y devuelve su DTO.
	 *
	 * <p>
	 * Primero localiza la entidad usando {@code findByNombre} y posteriormente
	 * obtiene el DTO por id usando {@code buscarPorId} en el DAO.
	 * </p>
	 *
	 * @param username nombre de usuario a buscar
	 * @return {@link UsuarioDTO} si se encuentra; null si no existe
	 */
	public UsuarioDTO buscarUsuario(String username) {
		var u = usuarioDAO.findByNombre(username);
		return u == null ? null : usuarioDAO.buscarPorId(u.getId().intValue());
	}

	/**
	 * Devuelve una representación textual de todos los usuarios.
	 *
	 * @return cadena con la información de todos los usuarios
	 */
	public String mostrarUsuarios() {
		return usuarioDAO.mostrarTodo();
	}

	/**
	 * Persiste el estado actual de los usuarios tanto en formato de texto como en
	 * formato serializado.
	 */
	public void guardar() {
		usuarioDAO.escribirArchivo();
		usuarioDAO.escribirArchivoSerializado();
	}

	/**
	 * Devuelve el DAO subyacente (útil para orquestadores o servicios que necesiten
	 * acceso directo al DAO).
	 *
	 * @return instancia de {@link UsuarioDAO} utilizada por este servicio
	 */
	public UsuarioDAO getDAO() {
		return usuarioDAO;
	}
}