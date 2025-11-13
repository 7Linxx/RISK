package co.edu.unbosque.model.persistence;

import java.util.ArrayList;
import java.util.Objects;
import co.edu.unbosque.model.Jugador;
import co.edu.unbosque.model.JugadorDTO;
import co.edu.unbosque.util.DNode;
import co.edu.unbosque.util.MyDoubleLinkedList;
import co.edu.unbosque.util.MyLinkedList;

public class JugadorDAO implements CRUDOperation<JugadorDTO, Jugador> {

	private MyDoubleLinkedList<Jugador> jugadores;
	private final String TEXT_FILE_NAME = "calles.csv";
	private final String SERIAL_FILE_NAME = "calles.dat";

	public JugadorDAO() {
		jugadores = new MyDoubleLinkedList<>();
	}

	public JugadorDAO(MyDoubleLinkedList<Jugador> jugadores) {
		super();
		this.jugadores = jugadores;
	}

	public MyDoubleLinkedList<Jugador> getJugadores() {
		return jugadores;
	}

	public void setJugadores(MyDoubleLinkedList<Jugador> jugadores) {
		this.jugadores = jugadores;
	}

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
		Jugador c = nodo.getInfo();
		if (c.getName().equalsIgnoreCase(nombre)) {
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
		acumulado += "#" + numero + " " + nodo.getInfo().toString() + "\n";
		return mostrarRecursivo(nodo.getNext(), numero + 1, acumulado);
	}

	@Override
	public MyDoubleLinkedList<JugadorDTO> getAll() {
		MyDoubleLinkedList<JugadorDTO> listaDTO = new MyDoubleLinkedList<>();
		getAllRecursivo(jugadores.getHead(), listaDTO);
		return listaDTO;
	}

	private void getAllRecursivo(DNode<Jugador> nodo, MyDoubleLinkedList<JugadorDTO> listaDTO) {
		if (nodo == null)
			return;
		listaDTO.add(DataMapper.jugadorToJugadorDTO(nodo.getInfo()));
		getAllRecursivo(nodo.getNext(), listaDTO);
	}

	@Override
	public Jugador findByNombre(String nombre) {
		return buscarNombreRec(jugadores.getHead(), nombre);
	}

	private Jugador buscarNombreRec(DNode<Jugador> n, String nombre) {
		if (n == null)
			return null;
		if (n.getInfo().getName().equalsIgnoreCase(nombre))
			return n.getInfo();
		return buscarNombreRec(n.getNext(), nombre);
	}

	@Override
	public JugadorDTO buscarPorId(int id) {
		JugadorDTO nuevo = null;
		return nuevo;
	}

	@Override
	public void escribirArchivo() {
		StringBuilder sb = new StringBuilder();
		escribirJugadorRecursivo(jugadores.getHead(), sb);
		FileManager.escribirEnArchivoTexto(TEXT_FILE_NAME, sb.toString());
	}

	private void escribirJugadorRecursivo(DNode<Jugador> nodoJugador, StringBuilder sb) {
		if (nodoJugador == null)
			return;

		Jugador c = nodoJugador.getInfo();
		sb.append(c.getName()).append("\n");
		sb.append(c.getColor()).append("\n");
		sb.append(c.getTropasDisponibles()).append("\n");
		sb.append(c.getUser()).append("\n");
		sb.append(0);
		sb.append("---\n"); // separador

		escribirJugadorRecursivo(nodoJugador.getNext(), sb);
	}

	@Override
	public void cargarDesdeArchivo() {
		String contenido = FileManager.leerArchivoTexto(TEXT_FILE_NAME);
		if (contenido == null || contenido.isBlank())
			return;

		String[] lineas = contenido.split("\n");
		cargarRecursivo(lineas, 0);
	}

	private void cargarRecursivo(String[] lineas, int pos) {
		if (pos >= lineas.length)
			return;

		String linea = lineas[pos];
		if (linea.equals("---") || linea.isBlank()) {
			cargarRecursivo(lineas, pos + 1);
			return;
		}

		String[] cabecera = linea.split(";");
		Long id = Long.parseLong(cabecera[0]);
		String nombre = cabecera[1];

		double distancia = Double.parseDouble(lineas[pos + 1]);
		Paradero a = stringToParadero(lineas[pos + 2]);
		Paradero b = stringToParadero(lineas[pos + 3]);

		Calle c = new Calle(nombre, distancia, a, b, true);
		listaCalles.insert(c);

		cargarRecursivo(lineas, pos + 5); // salta a la siguiente después del bloque
	}

	@Override
	public void escribirArchivoSerializado() {
		FileManager.escribirArchivoSerializado(SERIAL_FILE_NAME, listaCalles);
	}

	@Override
	public void leerArchivoSerializado() {
		MyDoubleLinkedList<Calle> data = (MyDoubleLinkedList<Calle>) FileManager
				.leerArchivoSerializado(SERIAL_FILE_NAME);
		if (data != null) {
			listaCalles = data;
		}
	}

}
