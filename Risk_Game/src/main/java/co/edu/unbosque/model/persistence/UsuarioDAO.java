package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.Usuario;
import co.edu.unbosque.model.UsuarioDTO;
import co.edu.unbosque.util.MyDoubleLinkedList;
import co.edu.unbosque.util.DNode;

/**
 * DAO (Data Access Object) para la gestión de {@link Usuario}.
 *
 * <p>
 * Implementa las operaciones CRUD definidas por {@link CRUDOperation} para
 * UsuarioDTO/Usuario. Mantiene en memoria una lista doblemente enlazada de
 * usuarios y proporciona persistencia tanto en formato texto como serializado.
 * </p>
 *
 * <p>
 * Archivos usados:
 * <ul>
 * <li>TEXT_FILE_NAME: "usuarios.csv" (texto legible)</li>
 * <li>SERIAL_FILE_NAME: "usuarios.dat" (serializado)</li>
 * </ul>
 * </p>
 *
 * @author Mariana Pineda
 * @since 1.0
 */
public class UsuarioDAO implements CRUDOperation<UsuarioDTO, Usuario> {

	/** Lista interna de usuarios en memoria */
	private MyDoubleLinkedList<Usuario> usuarios;

	/** Nombre del archivo de texto para persistencia legible */
	private final String TEXT_FILE_NAME = "usuarios.csv";

	/** Nombre del archivo serializado para persistencia binaria */
	private final String SERIAL_FILE_NAME = "usuarios.dat";

	/**
	 * Constructor por defecto que inicializa la lista de usuarios vacía.
	 */
	public UsuarioDAO() {
		usuarios = new MyDoubleLinkedList<>();
	}

	/**
	 * Constructor que permite inyectar una lista de usuarios (útil para pruebas).
	 *
	 * @param usuarios lista de usuarios a usar internamente
	 */
	public UsuarioDAO(MyDoubleLinkedList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * Devuelve la lista interna de usuarios.
	 *
	 * @return lista de usuarios
	 */
	public MyDoubleLinkedList<Usuario> getUsuarios() {
		return usuarios;
	}

	/**
	 * Establece la lista interna de usuarios.
	 *
	 * @param usuarios nueva lista de usuarios
	 */
	public void setUsuarios(MyDoubleLinkedList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	// ===========================================================
	// CRUD
	// ===========================================================

	/**
	 * Crea un nuevo usuario a partir de su DTO si no existe otro con el mismo
	 * username. Persiste los cambios en archivos de texto y serializados.
	 *
	 * @param nuevoDato DTO con los datos del usuario a crear
	 * @return true si se creó correctamente, false si ya existía un usuario con ese
	 *         username
	 */
	@Override
	public boolean crear(UsuarioDTO nuevoDato) {
		if (findByNombre(nuevoDato.getUsername()) == null) {
			usuarios.insert(DataMapper.usuarioDTOtoUsuario(nuevoDato));
			escribirArchivo();
			escribirArchivoSerializado();
			return true;
		}
		return false;
	}

	/**
	 * Elimina un usuario buscándolo por username.
	 *
	 * @param nombre username del usuario a eliminar
	 * @return true si se eliminó; false si no se encontró
	 */
	@Override
	public boolean eliminarPorNombre(String nombre) {
		return eliminarRec(usuarios.getHead(), nombre);
	}

	private boolean eliminarRec(DNode<Usuario> nodo, String nombre) {
		if (nodo == null)
			return false;

		Usuario u = nodo.getInfo();

		if (u.getUsername().equalsIgnoreCase(nombre)) {
			usuarios.extract();
			escribirArchivo();
			escribirArchivoSerializado();
			return true;
		}

		return eliminarRec(nodo.getNext(), nombre);
	}

	/**
	 * Devuelve una cadena con todos los usuarios en formato enumerado.
	 *
	 * @return representación textual de todos los usuarios
	 */
	@Override
	public String mostrarTodo() {
		return mostrarRec(usuarios.getHead(), 1, "");
	}

	private String mostrarRec(DNode<Usuario> nodo, int n, String acum) {
		if (nodo == null)
			return acum;

		Usuario u = nodo.getInfo();

		acum += "#" + n + " " + u.getUsername() + " | correo: " + u.getCorreo() + "\n";
		return mostrarRec(nodo.getNext(), n + 1, acum);
	}

	/**
	 * Devuelve todos los usuarios como una lista de DTOs.
	 *
	 * @return MyDoubleLinkedList con UsuarioDTO
	 */
	@Override
	public MyDoubleLinkedList<UsuarioDTO> getAll() {
		MyDoubleLinkedList<UsuarioDTO> lista = new MyDoubleLinkedList<>();
		getAllRec(usuarios.getHead(), lista);
		return lista;
	}

	private void getAllRec(DNode<Usuario> nodo, MyDoubleLinkedList<UsuarioDTO> lista) {
		if (nodo == null)
			return;

		lista.insert(DataMapper.usuarioToUsuarioDTO(nodo.getInfo()));
		getAllRec(nodo.getNext(), lista);
	}

	/**
	 * Busca un usuario por su username (case-insensitive).
	 *
	 * @param nombre username a buscar
	 * @return Usuario encontrado o null si no existe
	 */
	@Override
	public Usuario findByNombre(String nombre) {
		return buscarNombreRec(usuarios.getHead(), nombre);
	}

	private Usuario buscarNombreRec(DNode<Usuario> nodo, String nombre) {
		if (nodo == null)
			return null;

		if (nodo.getInfo().getUsername().equalsIgnoreCase(nombre))
			return nodo.getInfo();

		return buscarNombreRec(nodo.getNext(), nombre);
	}

	/**
	 * Busca un DTO de usuario por su id.
	 *
	 * @param id identificador numérico del usuario
	 * @return UsuarioDTO correspondiente o null si no existe
	 */
	@Override
	public UsuarioDTO buscarPorId(int id) {
		return buscarPorIdRec(usuarios.getHead(), id);
	}

	private UsuarioDTO buscarPorIdRec(DNode<Usuario> nodo, int id) {
		if (nodo == null)
			return null;

		if (nodo.getInfo().getId() != null && nodo.getInfo().getId() == id)
			return DataMapper.usuarioToUsuarioDTO(nodo.getInfo());

		return buscarPorIdRec(nodo.getNext(), id);
	}

	// ===========================================================
	// ARCHIVOS
	// ===========================================================

	/**
	 * Escribe la representación textual (CSV/semicolon) de los usuarios en el
	 * archivo TEXT_FILE_NAME.
	 */
	@Override
	public void escribirArchivo() {
		StringBuilder sb = new StringBuilder();
		escribirRec(usuarios.getHead(), sb);
		FileManager.escribirEnArchivoTexto(TEXT_FILE_NAME, sb.toString());
	}

	private void escribirRec(DNode<Usuario> nodo, StringBuilder sb) {
		if (nodo == null)
			return;

		Usuario u = nodo.getInfo();

		sb.append(u.getId()).append(";").append(u.getUsername()).append(";").append(u.getCorreo()).append(";")
				.append(u.getContrasenia()).append("\n");

		escribirRec(nodo.getNext(), sb);
	}

	/**
	 * Carga usuarios desde el archivo de texto (TEXT_FILE_NAME) y los inserta en la
	 * lista. Si el contenido no tiene el formato esperado, las líneas malformadas
	 * se ignoran.
	 */
	@Override
	public void cargarDesdeArchivo() {
		String contenido = FileManager.leerArchivoTexto(TEXT_FILE_NAME);
		if (contenido == null || contenido.isBlank())
			return;

		String[] lineas = contenido.split("\n");
		cargarRec(lineas, 0);
	}

	private void cargarRec(String[] lineas, int pos) {
		if (pos >= lineas.length)
			return;

		String[] datos = lineas[pos].split(";");
		if (datos.length < 4) {
			cargarRec(lineas, pos + 1);
			return;
		}

		Long id = datos[0].equals("null") ? null : Long.parseLong(datos[0]);
		String username = datos[1];
		String correo = datos[2];
		String contrasenia = datos[3];

		Usuario nuevo = new Usuario(id, username, correo, contrasenia, null);

		usuarios.insert(nuevo);

		cargarRec(lineas, pos + 1);
	}

	/**
	 * Escribe la estructura completa de usuarios en formato serializado.
	 */
	@Override
	public void escribirArchivoSerializado() {
		FileManager.escribirArchivoSerializado(SERIAL_FILE_NAME, usuarios);
	}

	/**
	 * Lee la estructura serializada desde disco y la asigna a la lista interna si
	 * existe.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void leerArchivoSerializado() {
		Object data = FileManager.leerArchivoSerializado(SERIAL_FILE_NAME);

		if (data != null)
			usuarios = (MyDoubleLinkedList<Usuario>) data;
	}
}