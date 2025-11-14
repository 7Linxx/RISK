package co.edu.unbosque.model;

import co.edu.unbosque.util.MyDoubleLinkedList;

/**
 * Data Transfer Object (DTO) para transferir información de un Jugador entre
 * capas de la aplicación.
 *
 * <p>
 * Contiene campos similares a {@link Jugador} pero pensado para transporte de
 * datos (por ejemplo entre la capa servicio y la capa controladora).
 * </p>
 *
 * @author Mariana Pineda
 * @since 1.0
 */
public class JugadorDTO {

	/** Nombre del jugador */
	private String name;

	/** Color asignado al jugador */
	private String color;

	/** Usuario asociado */
	private Usuario user;

	/** Tropas disponibles */
	private int tropasDisponibles;

	/** Territorios pertenecientes (DTOs de territorios) */
	private MyDoubleLinkedList<TerritorioDTO> territoriosPertenecientes;

	private MyDoubleLinkedList<String> territorios;

	/**
	 * Constructor por defecto.
	 */
	public JugadorDTO() {
		// Constructor vacío
	}

	/**
	 * Constructor con todos los atributos.
	 *
	 * @param name                      nombre del jugador
	 * @param color                     color del jugador
	 * @param user                      usuario asociado
	 * @param tropasDisponibles         tropas disponibles
	 * @param territoriosPertenecientes lista de territorios (DTO)
	 */
	public JugadorDTO(String name, String color, Usuario user, int tropasDisponibles,
			MyDoubleLinkedList<TerritorioDTO> territoriosPertenecientes) {
		super();
		this.name = name;
		this.color = color;
		this.user = user;
		this.tropasDisponibles = tropasDisponibles;
		this.territoriosPertenecientes = territoriosPertenecientes;
	}

	/**
	 * Obtiene el nombre del jugador.
	 *
	 * @return nombre del jugador
	 */
	public String getName() {
		return name;
	}

	/**
	 * Establece el nombre del jugador.
	 *
	 * @param name nuevo nombre
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Obtiene el color del jugador.
	 *
	 * @return color del jugador
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Establece el color del jugador.
	 *
	 * @param color nuevo color
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Obtiene el usuario asociado al jugador.
	 *
	 * @return usuario asociado
	 */
	public Usuario getUser() {
		return user;
	}

	/**
	 * Establece el usuario asociado al jugador.
	 *
	 * @param user nuevo usuario
	 */
	public void setUser(Usuario user) {
		this.user = user;
	}

	/**
	 * Obtiene las tropas disponibles.
	 *
	 * @return tropas disponibles
	 */
	public int getTropasDisponibles() {
		return tropasDisponibles;
	}

	/**
	 * Establece las tropas disponibles.
	 *
	 * @param tropasDisponibles nuevas tropas disponibles
	 */
	public void setTropasDisponibles(int tropasDisponibles) {
		this.tropasDisponibles = tropasDisponibles;
	}

	/**
	 * Obtiene la lista de territorios pertenecientes (DTO).
	 *
	 * @return lista de territorios DTO
	 */
	public MyDoubleLinkedList<TerritorioDTO> getTerritoriosPertenecientes() {
		return territoriosPertenecientes;
	}

	/**
	 * Establece la lista de territorios pertenecientes (DTO).
	 *
	 * @param territoriosPertenecientes nueva lista de territorios DTO
	 */
	public void setTerritoriosPertenecientes(MyDoubleLinkedList<TerritorioDTO> territoriosPertenecientes) {
		this.territoriosPertenecientes = territoriosPertenecientes;
	}

	public boolean containsTerritory(String territorio) {
		for (String aux : territorios) {
			if (aux.equals(territorio))
				return true;
		}
		return false;
	}
}