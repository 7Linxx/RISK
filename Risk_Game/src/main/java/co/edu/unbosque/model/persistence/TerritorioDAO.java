package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.Territorio;
import co.edu.unbosque.model.TerritorioDTO;
import co.edu.unbosque.util.DNode;
import co.edu.unbosque.util.MyDoubleLinkedList;

/**
 * DAO (Data Access Object) para la gestión de {@link Territorio}.
 *
 * <p>
 * Implementa las operaciones CRUD definidas en {@link CRUDOperation} para
 * TerritorioDTO/Territorio. Mantiene en memoria una lista doblemente enlazada
 * de territorios y proporciona persistencia tanto en formato texto como
 * serializado.
 * </p>
 *
 * <p>
 * Archivos usados:
 * <ul>
 * <li>TEXT_FILE_NAME: "territorios.txt" (texto legible)</li>
 * <li>SERIAL_FILE_NAME: "territorios.dat" (serializado)</li>
 * </ul>
 * </p>
 * 
 * @author Mariana Pineda
 * @since 1.0
 */
public class TerritorioDAO implements CRUDOperation<TerritorioDTO, Territorio> {

	/** Lista interna de territorios en memoria */
	private MyDoubleLinkedList<Territorio> territorios;

	/** Archivo de texto para persistencia legible */
	private final String TEXT_FILE_NAME = "territorios.txt";

	/** Archivo serializado para persistencia binaria */
	private final String SERIAL_FILE_NAME = "territorios.dat";

	/**
	 * Constructor por defecto que inicializa la lista de territorios vacía.
	 */
	public TerritorioDAO() {
		territorios = new MyDoubleLinkedList<>();
	}

	/**
	 * Constructor que permite inyectar una lista de territorios (útil para
	 * pruebas).
	 *
	 * @param territorios lista de territorios a usar internamente
	 */
	public TerritorioDAO(MyDoubleLinkedList<Territorio> territorios) {
		this.territorios = territorios;
	}

	/**
	 * Devuelve la lista interna de territorios.
	 *
	 * @return MyDoubleLinkedList de territorios
	 */
	public MyDoubleLinkedList<Territorio> getTerritorios() {
		return territorios;
	}

	/**
	 * Establece la lista interna de territorios.
	 *
	 * @param territorios nueva lista de territorios
	 */
	public void setTerritorios(MyDoubleLinkedList<Territorio> territorios) {
		this.territorios = territorios;
	}

	// ================================================================
	// CRUD
	// ================================================================

	/**
	 * Crea un territorio a partir de su DTO si no existe otro con el mismo nombre.
	 * Persiste inmediatamente los cambios.
	 *
	 * @param data DTO con los datos del territorio
	 * @return true si el territorio fue creado, false si ya existía
	 */
	@Override
	public boolean crear(TerritorioDTO data) {
		if (findByNombre(data.getNombre()) == null) {
			territorios.add(DataMapper.territorioDTOtoTerritorio(data));
			escribirArchivo();
			escribirArchivoSerializado();
			return true;
		}
		return false;
	}

	/**
	 * Elimina un territorio buscándolo por nombre.
	 *
	 * @param nombre nombre del territorio a eliminar
	 * @return true si se eliminó, false si no se encontró
	 */
	@Override
	public boolean eliminarPorNombre(String nombre) {
		return eliminarRec(territorios.getHead(), nombre);
	}

	private boolean eliminarRec(DNode<Territorio> nodo, String nombre) {
		if (nodo == null)
			return false;

		if (nodo.getInfo().getNombre().equalsIgnoreCase(nombre)) {
			territorios.extract();
			escribirArchivo();
			escribirArchivoSerializado();
			return true;
		}
		return eliminarRec(nodo.getNext(), nombre);
	}

	/**
	 * Devuelve una representación enumerada de todos los territorios.
	 *
	 * @return cadena con todos los territorios
	 */
	@Override
	public String mostrarTodo() {
		return mostrarRecursivo(territorios.getHead(), 1, "");
	}

	private String mostrarRecursivo(DNode<Territorio> nodo, int num, String acc) {
		if (nodo == null)
			return acc;

		acc += "#" + num + " " + nodo.getInfo().getNombre() + "\n";
		return mostrarRecursivo(nodo.getNext(), num + 1, acc);
	}

	/**
	 * Devuelve todos los territorios como DTOs en una lista doblemente enlazada.
	 *
	 * @return MyDoubleLinkedList con TerritorioDTO
	 */
	@Override
	public MyDoubleLinkedList<TerritorioDTO> getAll() {
		MyDoubleLinkedList<TerritorioDTO> listaDTO = new MyDoubleLinkedList<>();
		getAllRec(territorios.getHead(), listaDTO);
		return listaDTO;
	}

	private void getAllRec(DNode<Territorio> nodo, MyDoubleLinkedList<TerritorioDTO> lista) {
		if (nodo == null)
			return;

		lista.add(DataMapper.territorioToTerritorioDTO(nodo.getInfo()));
		getAllRec(nodo.getNext(), lista);
	}

	/**
	 * Busca un territorio por su nombre (case-insensitive).
	 *
	 * @param nombre nombre a buscar
	 * @return Territorio encontrado o null si no existe
	 */
	@Override
	public Territorio findByNombre(String nombre) {
		return buscarRec(territorios.getHead(), nombre);
	}

	private Territorio buscarRec(DNode<Territorio> nodo, String nombre) {
		if (nodo == null)
			return null;

		if (nodo.getInfo().getNombre().equalsIgnoreCase(nombre))
			return nodo.getInfo();

		return buscarRec(nodo.getNext(), nombre);
	}

	/**
	 * Busca un DTO por su índice (id conceptual) dentro de la lista.
	 *
	 * @param id índice posicional del territorio en la lista (0-based)
	 * @return TerritorioDTO correspondiente o null si el índice es inválido
	 */
	@Override
	public TerritorioDTO buscarPorId(int id) {
		if (id < 0 || id >= territorios.getSize())
			return null;

		return DataMapper.territorioToTerritorioDTO(territorios.get(id));
	}

	// ================================================================
	// Persistencia
	// ================================================================

	/**
	 * Escribe la representación textual de los territorios en TEXT_FILE_NAME.
	 */
	@Override
	public void escribirArchivo() {
		StringBuilder sb = new StringBuilder();
		escribirRec(territorios.getHead(), sb);
		FileManager.escribirEnArchivoTexto(TEXT_FILE_NAME, sb.toString());
	}

	private void escribirRec(DNode<Territorio> nodo, StringBuilder sb) {
		if (nodo == null)
			return;

		Territorio t = nodo.getInfo();
		sb.append(t.getNombre()).append(";").append(t.getCantidadTropas()).append(";")
				.append(t.getDuenio() == null ? "null" : t.getDuenio().getName()).append("\n");

		escribirRec(nodo.getNext(), sb);
	}

	/**
	 * Carga territorios desde el archivo de texto (TEXT_FILE_NAME). Las líneas
	 * malformadas se omiten y la lista interna se reconstituye.
	 */
	@Override
	public void cargarDesdeArchivo() {
		String contenido = FileManager.leerArchivoTexto(TEXT_FILE_NAME);
		if (contenido == null || contenido.isBlank())
			return;

		String[] lineas = contenido.split("\n");
		territorios = new MyDoubleLinkedList<>();
		cargarRec(lineas, 0);
	}

	private void cargarRec(String[] lineas, int pos) {
		if (pos >= lineas.length)
			return;

		String[] datos = lineas[pos].split(";");
		if (datos.length < 3) {
			cargarRec(lineas, pos + 1);
			return;
		}

		String nombre = datos[0];
		int tropas = Integer.parseInt(datos[1]);

		Territorio t = new Territorio(nombre, tropas, null, new MyDoubleLinkedList<>());

		territorios.add(t);
		cargarRec(lineas, pos + 1);
	}

	/**
	 * Escribe la estructura completa de territorios en formato serializado.
	 */
	@Override
	public void escribirArchivoSerializado() {
		FileManager.escribirArchivoSerializado(SERIAL_FILE_NAME, territorios);
	}

	/**
	 * Lee la estructura serializada desde disco y la asigna a la lista interna si
	 * existe.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void leerArchivoSerializado() {
		MyDoubleLinkedList<Territorio> data = (MyDoubleLinkedList<Territorio>) FileManager
				.leerArchivoSerializado(SERIAL_FILE_NAME);

		if (data != null)
			territorios = data;
	}
}