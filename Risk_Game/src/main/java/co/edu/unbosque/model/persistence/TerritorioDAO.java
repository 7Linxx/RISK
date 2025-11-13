package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.Territorio;
import co.edu.unbosque.model.TerritorioDTO;
import co.edu.unbosque.util.DNode;
import co.edu.unbosque.util.MyDoubleLinkedList;

public class TerritorioDAO implements CRUDOperation<TerritorioDTO, Territorio> {

	private MyDoubleLinkedList<Territorio> territorios;
	private final String TEXT_FILE_NAME = "territorios.txt";
	private final String SERIAL_FILE_NAME = "territorios.dat";

	public TerritorioDAO() {
		territorios = new MyDoubleLinkedList<>();
	}

	public TerritorioDAO(MyDoubleLinkedList<Territorio> territorios) {
		this.territorios = territorios;
	}

	public MyDoubleLinkedList<Territorio> getTerritorios() {
		return territorios;
	}

	public void setTerritorios(MyDoubleLinkedList<Territorio> territorios) {
		this.territorios = territorios;
	}

	// ================================================================
	// CRUD
	// ================================================================

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

	@Override
	public TerritorioDTO buscarPorId(int id) {
		if (id < 0 || id >= territorios.getSize())
			return null;

		return DataMapper.territorioToTerritorioDTO(territorios.get(id));
	}

	// ================================================================
	// Persistencia
	// ================================================================

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

	@Override
	public void escribirArchivoSerializado() {
		FileManager.escribirArchivoSerializado(SERIAL_FILE_NAME, territorios);
	}

	@Override
	public void leerArchivoSerializado() {
		MyDoubleLinkedList<Territorio> data = (MyDoubleLinkedList<Territorio>) FileManager
				.leerArchivoSerializado(SERIAL_FILE_NAME);

		if (data != null)
			territorios = data;
	}
}
