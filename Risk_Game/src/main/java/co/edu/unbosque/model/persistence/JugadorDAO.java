package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.Jugador;
import co.edu.unbosque.model.JugadorDTO;
import co.edu.unbosque.model.Usuario;
import co.edu.unbosque.util.DNode;
import co.edu.unbosque.util.MyDoubleLinkedList;

/**
 * DAO (Data Access Object) para la gestión de Jugador en almacenamiento local.
 *
 * <p>
 * Implementa las operaciones CRUD definidas por {@link CRUDOperation} para
 * JugadorDTO/Jugador. Internamente mantiene una lista doblemente enlazada de
 * objetos {@link Jugador} y ofrece métodos recursivos para búsqueda, carga y
 * persistencia.
 * </p>
 *
 * <p>
 * Los datos se persisten en dos ficheros:
 * <ul>
 * <li>TEXT_FILE_NAME: csv/text (jugadores.csv)</li>
 * <li>SERIAL_FILE_NAME: archivo serializado (jugadores.dat)</li>
 * </ul>
 * </p>
 *
 * @author Mariana Pineda
 * @since 1.0
 */
public class JugadorDAO implements CRUDOperation<JugadorDTO, Jugador> {

	/** Lista interna de jugadores en memoria */
	private MyDoubleLinkedList<Jugador> jugadores;

	/** Nombre del archivo de texto para persistencia legible */
	private final String TEXT_FILE_NAME = "jugadores.csv";

	/** Nombre del archivo serializado para persistencia binaria */
	private final String SERIAL_FILE_NAME = "jugadores.dat";

	/**
	 * Constructor por defecto que inicializa la lista de jugadores vacía.
	 */
	public JugadorDAO() {
		jugadores = new MyDoubleLinkedList<>();
	}

	/**
	 * Constructor que permite inyectar una lista de jugadores existente (para
	 * pruebas).
	 *
	 * @param jugadores lista de jugadores a usar internamente
	 */
	public JugadorDAO(MyDoubleLinkedList<Jugador> jugadores) {
		this.jugadores = jugadores;
	}

	/**
	 * Devuelve la lista interna de jugadores.
	 *
	 * @return lista de jugadores
	 */
	public MyDoubleLinkedList<Jugador> getJugadores() {
		return jugadores;
	}

	/**
	 * Establece la lista interna de jugadores.
	 *
	 * @param jugadores nueva lista de jugadores
	 */
	public void setJugadores(MyDoubleLinkedList<Jugador> jugadores) {
		this.jugadores = jugadores;
	}

	// ===========================================================
	// CRUD
	// ===========================================================

	/**
	 * Crea un nuevo jugador a partir de su DTO si no existe otro con el mismo
	 * nombre. Persiste los cambios en archivos de texto y serializados.
	 *
	 * @param nuevoDato DTO con los datos del jugador a crear
	 * @return true si se creó correctamente, false si ya existía un jugador con ese
	 *         nombre
	 */
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

	/**
	 * Elimina un jugador buscándolo por nombre.
	 *
	 * @param nombre nombre del jugador a eliminar
	 * @return true si se eliminó; false si no se encontró
	 */
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

	/**
	 * Devuelve una cadena con todos los jugadores en formato enumerado.
	 *
	 * @return representación textual de todos los jugadores
	 */
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

	/**
	 * Devuelve todos los jugadores como una lista de DTOs.
	 *
	 * @return MyDoubleLinkedList con JugadorDTO
	 */
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

	/**
	 * Busca un jugador por su nombre (case-insensitive).
	 *
	 * @param nombre nombre a buscar
	 * @return Jugador encontrado o null si no existe
	 */
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

	/**
	 * Búsqueda por id no implementada para Jugador (no se dispone de id en la
	 * entidad).
	 *
	 * @param id identificador solicitado
	 * @return siempre null (no aplica)
	 */
	@Override
	public JugadorDTO buscarPorId(int id) {
		// Jugador no tiene ID, queda sin implementación.
		return null;
	}

	// ===========================================================
	// ARCHIVOS
	// ===========================================================

	/**
	 * Escribe la representación textual (CSV/semicolons) de los jugadores en el
	 * archivo TEXT_FILE_NAME.
	 */
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

	/**
	 * Carga jugadores desde el archivo de texto (TEXT_FILE_NAME) y los inserta en
	 * la lista. Si el contenido no tiene el formato esperado, las líneas
	 * malformadas se ignoran.
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

		String nombre = datos[0];
		String color = datos[1];
		int tropas = Integer.parseInt(datos[2]);
		String username = datos[3];

		Usuario user = new Usuario(null, username, null, null, null);

		Jugador nuevo = new Jugador(nombre, color, user, tropas, new MyDoubleLinkedList<>());

		jugadores.insert(nuevo);

		cargarRec(lineas, pos + 1);
	}

	/**
	 * Escribe la estructura completa de jugadores en formato serializado.
	 */
	@Override
	public void escribirArchivoSerializado() {
		FileManager.escribirArchivoSerializado(SERIAL_FILE_NAME, jugadores);
	}

	/**
	 * Lee la estructura serializada desde disco y la asigna a la lista interna si
	 * existe.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void leerArchivoSerializado() {
		MyDoubleLinkedList<Jugador> data = (MyDoubleLinkedList<Jugador>) FileManager
				.leerArchivoSerializado(SERIAL_FILE_NAME);

		if (data != null)
			jugadores = data;
	}
}