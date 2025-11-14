package co.edu.unbosque.backRisk.dto;

import co.edu.unbosque.backRisk.model.Jugador;
import co.edu.unbosque.backRisk.model.Territorio;
import co.edu.unbosque.backRisk.util.MyLinkedList;

/**
 * Data Transfer Object (DTO) que representa la información de un territorio en
 * la capa de transferencia de datos de la aplicación.
 * <p>
 * Contiene identificador, nombre, dueño (Jugador), cantidad de tropas en el
 * territorio y la lista de territorios adyacentes.
 * </p>
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public class TerritorioDTO {

	/**
	 * Identificador único del territorio.
	 */
	private Long id;

	/**
	 * Nombre del territorio.
	 */
	private String name;

	/**
	 * Dueño actual del territorio.
	 */
	private Jugador duenio;

	/**
	 * Cantidad de tropas presentes en el territorio.
	 */
	private int cantidadTropas;

	/**
	 * Lista de territorios adyacentes a este territorio.
	 */
	private MyLinkedList<Territorio> territoriosAdyacentes;

	/**
	 * Constructor por defecto.
	 */
	public TerritorioDTO() {
		// Constructor vacío para frameworks/serialización
	}

	/**
	 * Constructor que inicializa los campos principales del DTO.
	 * 
	 * @param id                    Identificador del territorio
	 * @param nombre                Nombre del territorio
	 * @param duenio                Jugador dueño del territorio
	 * @param cantidadTropas        Cantidad de tropas en el territorio
	 * @param territoriosAdyacentes Lista de territorios adyacentes
	 */
	public TerritorioDTO(Long id, String nombre, Jugador duenio, int cantidadTropas,
			MyLinkedList<Territorio> territoriosAdyacentes) {
		super();
		this.id = id;
		this.name = nombre;
		this.duenio = duenio;
		this.cantidadTropas = cantidadTropas;
		this.territoriosAdyacentes = territoriosAdyacentes;
	}

	/**
	 * Obtiene el identificador del territorio.
	 * 
	 * @return id del territorio
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el identificador del territorio.
	 * 
	 * @param id Identificador a asignar
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del territorio.
	 * 
	 * @return nombre del territorio
	 */
	public String getNombre() {
		return name;
	}

	/**
	 * Establece el nombre del territorio.
	 * 
	 * @param nombre nombre a asignar
	 */
	public void setNombre(String nombre) {
		this.name = nombre;
	}

	/**
	 * Obtiene el dueño del territorio.
	 * 
	 * @return jugador dueño del territorio
	 */
	public Jugador getDuenio() {
		return duenio;
	}

	/**
	 * Establece el dueño del territorio.
	 * 
	 * @param duenio jugador a asignar como dueño
	 */
	public void setDuenio(Jugador duenio) {
		this.duenio = duenio;
	}

	/**
	 * Obtiene la cantidad de tropas en el territorio.
	 * 
	 * @return cantidad de tropas
	 */
	public int getCantidadTropas() {
		return cantidadTropas;
	}

	/**
	 * Establece la cantidad de tropas en el territorio.
	 * 
	 * @param cantidadTropas cantidad de tropas a asignar
	 */
	public void setCantidadTropas(int cantidadTropas) {
		this.cantidadTropas = cantidadTropas;
	}

	/**
	 * Obtiene la lista de territorios adyacentes a este territorio.
	 * 
	 * @return lista de territorios adyacentes
	 */
	public MyLinkedList<Territorio> getTerritoriosAdyacentes() {
		return territoriosAdyacentes;
	}

	/**
	 * Establece la lista de territorios adyacentes a este territorio.
	 * 
	 * @param territoriosAdyacentes lista de adyacentes a asignar
	 */
	public void setTerritoriosAdyacentes(MyLinkedList<Territorio> territoriosAdyacentes) {
		this.territoriosAdyacentes = territoriosAdyacentes;
	}

}