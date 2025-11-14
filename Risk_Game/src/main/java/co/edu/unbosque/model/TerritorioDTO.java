package co.edu.unbosque.model;

import co.edu.unbosque.util.MyDoubleLinkedList;

/**
 * Data Transfer Object (DTO) para transferir información de un
 * {@link Territorio} entre capas de la aplicación.
 *
 * <p>
 * Contiene los mismos campos relevantes del modelo pero orientado a transporte
 * de datos (por ejemplo, entre servicio y controladores).
 * </p>
 *
 * @author Mariana Pineda
 * @since 1.0
 */
public class TerritorioDTO {

	/** Nombre del territorio */
	private String nombre;

	/** Cantidad de tropas en el territorio */
	private int cantidadTropas;

	/** Jugador dueño del territorio (puede ser null si no está asignado) */
	private Jugador duenio;

	/** Lista de territorios adyacentes (DTO) */
	private MyDoubleLinkedList<TerritorioDTO> territoriosAdyacentes;

	/** Constructor por defecto. */
	public TerritorioDTO() {
		// Constructor vacío
	}

	/**
	 * Constructor completo.
	 *
	 * @param nombre                nombre del territorio
	 * @param cantidadTropas        cantidad de tropas
	 * @param duenio                jugador dueño
	 * @param territoriosAdyacentes lista de territorios adyacentes (DTO)
	 */
	public TerritorioDTO(String nombre, int cantidadTropas, Jugador duenio,
			MyDoubleLinkedList<TerritorioDTO> territoriosAdyacentes) {
		super();
		this.nombre = nombre;
		this.cantidadTropas = cantidadTropas;
		this.duenio = duenio;
		this.territoriosAdyacentes = territoriosAdyacentes;
	}

	/**
	 * Obtiene el nombre del territorio.
	 *
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del territorio.
	 *
	 * @param nombre nuevo nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene la cantidad de tropas.
	 *
	 * @return cantidad de tropas
	 */
	public int getCantidadTropas() {
		return cantidadTropas;
	}

	/**
	 * Establece la cantidad de tropas.
	 *
	 * @param cantidadTropas nueva cantidad de tropas
	 */
	public void setCantidadTropas(int cantidadTropas) {
		this.cantidadTropas = cantidadTropas;
	}

	/**
	 * Obtiene el jugador dueño.
	 *
	 * @return dueño del territorio
	 */
	public Jugador getDuenio() {
		return duenio;
	}

	/**
	 * Establece el jugador dueño.
	 *
	 * @param duenio nuevo dueño
	 */
	public void setDuenio(Jugador duenio) {
		this.duenio = duenio;
	}

	/**
	 * Obtiene los territorios adyacentes (DTO).
	 *
	 * @return lista de territorios adyacentes (DTO)
	 */
	public MyDoubleLinkedList<TerritorioDTO> getTerritoriosAdyacentes() {
		return territoriosAdyacentes;
	}

	/**
	 * Establece la lista de territorios adyacentes (DTO).
	 *
	 * @param territoriosAdyacentes nueva lista de territorios adyacentes (DTO)
	 */
	public void setTerritoriosAdyacentes(MyDoubleLinkedList<TerritorioDTO> territoriosAdyacentes) {
		this.territoriosAdyacentes = territoriosAdyacentes;
	}

}