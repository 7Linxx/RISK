package co.edu.unbosque.model;

import co.edu.unbosque.util.MyDoubleLinkedList;

/**
 * Representa un territorio dentro del modelo del juego.
 *
 * <p>
 * Un territorio contiene un nombre, la cantidad de tropas desplegadas, su dueño
 * (jugador) y una lista de territorios adyacentes que permiten modelar el mapa
 * del juego.
 * </p>
 *
 * @author Mariana Pineda
 * @since 1.0
 */
public class Territorio {

	/** Nombre identificador del territorio */
	private String nombre;

	/** Cantidad de tropas actualmente en el territorio */
	private int cantidadTropas;

	/** Jugador que posee actualmente el territorio */
	private Jugador duenio;

	/** Territorios adyacentes conectados a este territorio */
	private MyDoubleLinkedList<Territorio> territoriosAdyacentes;

	/** Constructor por defecto. */
	public Territorio() {
		// Constructor vacío
	}

	/**
	 * Constructor completo.
	 *
	 * @param nombre                nombre del territorio
	 * @param cantidadTropas        cantidad de tropas en el territorio
	 * @param duenio                jugador propietario del territorio
	 * @param territoriosAdyacentes lista de territorios adyacentes
	 */
	public Territorio(String nombre, int cantidadTropas, Jugador duenio,
			MyDoubleLinkedList<Territorio> territoriosAdyacentes) {
		super();
		this.nombre = nombre;
		this.cantidadTropas = cantidadTropas;
		this.duenio = duenio;
		this.territoriosAdyacentes = territoriosAdyacentes;
	}

	/**
	 * Obtiene el nombre del territorio.
	 *
	 * @return nombre del territorio
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del territorio.
	 *
	 * @param nombre nuevo nombre del territorio
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene la cantidad de tropas en el territorio.
	 *
	 * @return número de tropas
	 */
	public int getCantidadTropas() {
		return cantidadTropas;
	}

	/**
	 * Establece la cantidad de tropas en el territorio.
	 *
	 * @param cantidadTropas nueva cantidad de tropas
	 */
	public void setCantidadTropas(int cantidadTropas) {
		this.cantidadTropas = cantidadTropas;
	}

	/**
	 * Obtiene el jugador dueño del territorio.
	 *
	 * @return jugador propietario
	 */
	public Jugador getDuenio() {
		return duenio;
	}

	/**
	 * Establece el jugador dueño del territorio.
	 *
	 * @param duenio nuevo dueño del territorio
	 */
	public void setDuenio(Jugador duenio) {
		this.duenio = duenio;
	}

	/**
	 * Obtiene la lista de territorios adyacentes.
	 *
	 * @return lista de territorios adyacentes
	 */
	public MyDoubleLinkedList<Territorio> getTerritoriosAdyacentes() {
		return territoriosAdyacentes;
	}

	/**
	 * Establece la lista de territorios adyacentes.
	 *
	 * @param territoriosAdyacentes nueva lista de territorios adyacentes
	 */
	public void setTerritoriosAdyacentes(MyDoubleLinkedList<Territorio> territoriosAdyacentes) {
		this.territoriosAdyacentes = territoriosAdyacentes;
	}

}