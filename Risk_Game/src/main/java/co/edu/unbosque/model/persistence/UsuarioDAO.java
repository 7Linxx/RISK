package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.Usuario;
import co.edu.unbosque.model.UsuarioDTO;
import co.edu.unbosque.util.MyDoubleLinkedList;
import co.edu.unbosque.util.DNode;

public class UsuarioDAO implements CRUDOperation<UsuarioDTO, Usuario> {

	private MyDoubleLinkedList<Usuario> usuarios;
	private final String TEXT_FILE_NAME = "usuarios.csv";
	private final String SERIAL_FILE_NAME = "usuarios.dat";

	public UsuarioDAO() {
		usuarios = new MyDoubleLinkedList<>();
	}

	public UsuarioDAO(MyDoubleLinkedList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public MyDoubleLinkedList<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(MyDoubleLinkedList<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	// ===========================================================
	// CRUD
	// ===========================================================

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

	@Override
	public void escribirArchivoSerializado() {
		FileManager.escribirArchivoSerializado(SERIAL_FILE_NAME, usuarios);
	}

	@Override
	public void leerArchivoSerializado() {
		Object data = FileManager.leerArchivoSerializado(SERIAL_FILE_NAME);

		if (data != null)
			usuarios = (MyDoubleLinkedList<Usuario>) data;
	}
}
