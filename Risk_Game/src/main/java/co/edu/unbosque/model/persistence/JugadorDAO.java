package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.Jugador;
import co.edu.unbosque.model.JugadorDTO;
import co.edu.unbosque.model.Usuario;
import co.edu.unbosque.util.DNode;
import co.edu.unbosque.util.MyDoubleLinkedList;

public class JugadorDAO implements CRUDOperation<JugadorDTO, Jugador> {

	private MyDoubleLinkedList<Jugador> jugadores;
	private final String TEXT_FILE_NAME = "jugadores.csv";
	private final String SERIAL_FILE_NAME = "jugadores.dat";

	public JugadorDAO() {
		jugadores = new MyDoubleLinkedList<>();
	}

	public JugadorDAO(MyDoubleLinkedList<Jugador> jugadores) {
		this.jugadores = jugadores;
	}

	public MyDoubleLinkedList<Jugador> getJugadores() {
		return jugadores;
	}

	public void setJugadores(MyDoubleLinkedList<Jugador> jugadores) {
		this.jugadores = jugadores;
	}

	// ===========================================================
	// CRUD
	// ===========================================================

	@Override
	public boolean crear(JugadorDTO nuevoDato) {
		if (findByNombre(nuevoDato.getName()) == null) {
			jugadores.insert(DataMapper.jugadorDTOtoJugador(nuevoDato));
			escribirArchivo();
			escribirArchivoSerializado();
			return true;
		}
		return false;
	}

	@Override
	public boolean eliminarPorNombre(String nombre) {
		return eliminarRec(jugadores.getHead(), nombre);
	}

	private boolean eliminarRec(DNode<Jugador> nodo, String nombre) {
		if (nodo == null)
			return false;

		Jugador j = nodo.getInfo();

		if (j.getName().equalsIgnoreCase(nombre)) {
			jugadores.extract();
			escribirArchivo();
			escribirArchivoSerializado();
			return true;
		}

		return eliminarRec(nodo.getNext(), nombre);
	}

	@Override
	public String mostrarTodo() {
		return mostrarRecursivo(jugadores.getHead(), 1, "");
	}

	private String mostrarRecursivo(DNode<Jugador> nodo, int numero, String acumulado) {
		if (nodo == null)
			return acumulado;

		acumulado += "#" + numero + " " + nodo.getInfo().getName() + "\n";
		return mostrarRecursivo(nodo.getNext(), numero + 1, acumulado);
	}

	@Override
	public MyDoubleLinkedList<JugadorDTO> getAll() {
		MyDoubleLinkedList<JugadorDTO> lista = new MyDoubleLinkedList<>();
		getAllRec(jugadores.getHead(), lista);
		return lista;
	}

	private void getAllRec(DNode<Jugador> nodo, MyDoubleLinkedList<JugadorDTO> lista) {
		if (nodo == null)
			return;
		lista.insert(DataMapper.jugadorToJugadorDTO(nodo.getInfo()));
		getAllRec(nodo.getNext(), lista);
	}

	@Override
	public Jugador findByNombre(String nombre) {
		return buscarNombreRec(jugadores.getHead(), nombre);
	}

	private Jugador buscarNombreRec(DNode<Jugador> nodo, String nombre) {
		if (nodo == null)
			return null;

		if (nodo.getInfo().getName().equalsIgnoreCase(nombre))
			return nodo.getInfo();

		return buscarNombreRec(nodo.getNext(), nombre);
	}

	@Override
	public JugadorDTO buscarPorId(int id) {
		// Jugador no tiene ID, queda sin implementación.
		return null;
	}

	// ===========================================================
	// ARCHIVOS
	// ===========================================================

	@Override
	public void escribirArchivo() {
		StringBuilder sb = new StringBuilder();
		escribirJugadorRec(jugadores.getHead(), sb);
		FileManager.escribirEnArchivoTexto(TEXT_FILE_NAME, sb.toString());
	}

	private void escribirJugadorRec(DNode<Jugador> nodo, StringBuilder sb) {
		if (nodo == null)
			return;

		Jugador j = nodo.getInfo();

		sb.append(j.getName()).append(";");
		sb.append(j.getColor()).append(";");
		sb.append(j.getTropasDisponibles()).append(";");
		sb.append(j.getUser().getUsername()).append("\n"); // Ajustar formateo según desees

		escribirJugadorRec(nodo.getNext(), sb);
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

		String nombre = datos[0];
		String color = datos[1];
		int tropas = Integer.parseInt(datos[2]);
		String username = datos[3];

		Usuario user = new Usuario(null, username, null, null, null);

		Jugador nuevo = new Jugador(nombre, color, user, tropas, new MyDoubleLinkedList<>());

		jugadores.insert(nuevo);

		cargarRec(lineas, pos + 1);
	}

	@Override
	public void escribirArchivoSerializado() {
		FileManager.escribirArchivoSerializado(SERIAL_FILE_NAME, jugadores);
	}

	@Override
	public void leerArchivoSerializado() {
		MyDoubleLinkedList<Jugador> data = (MyDoubleLinkedList<Jugador>) FileManager
				.leerArchivoSerializado(SERIAL_FILE_NAME);

		if (data != null)
			jugadores = data;
	}
}
