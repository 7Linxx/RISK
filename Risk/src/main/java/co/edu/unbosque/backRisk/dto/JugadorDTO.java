package co.edu.unbosque.backRisk.dto;

import co.edu.unbosque.backRisk.model.Territorio;
import co.edu.unbosque.backRisk.model.User;
import co.edu.unbosque.backRisk.util.MyLinkedList;

/**
 * Data Transfer Object (DTO) que representa la información de un jugador en la
 * capa de transferencia de datos de la aplicación.
 * <p>
 * Contiene los datos básicos del jugador como identificador, usuario asociado,
 * nombre, color, los territorios que posee y las tropas disponibles.
 * </p>
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public class JugadorDTO {

	/**
	 * Identificador único del jugador.
	 */
	private Long id;

	/**
	 * DTO que contiene la información del usuario asociado al jugador.
	 */
	private UserDTO userDTO;

	/**
	 * Nombre del jugador.
	 */
	private String name;

	/**
	 * Color asignado al jugador (por ejemplo para representar en el tablero).
	 */
	private String color;

	/**
	 * Lista de territorios (DTO) que pertenecen al jugador.
	 */
	private MyLinkedList<TerritorioDTO> territoriosPertenecientes;

	/**
	 * Número de tropas que el jugador tiene disponibles para colocar/mover.
	 */
	private int tropasDisponibles;

	/**
	 * Constructor por defecto.
	 */
	public JugadorDTO() {
		// Constructor vacío para frameworks/serialización
	}

	/**
	 * Constructor que inicializa los campos principales del DTO.
	 * 
	 * @param userDTO                   DTO del usuario asociado al jugador
	 * @param nombre                    Nombre del jugador
	 * @param color                     Color asignado al jugador
	 * @param territoriosPertenecientes Lista de territorios que posee el jugador
	 * @param tropasDisponibles         Tropas disponibles del jugador
	 */
	public JugadorDTO(UserDTO userDTO, String nombre, String color,
			MyLinkedList<TerritorioDTO> territoriosPertenecientes, int tropasDisponibles) {
		super();
		this.userDTO = userDTO;
		this.name = nombre;
		this.color = color;
		this.territoriosPertenecientes = territoriosPertenecientes;
		this.tropasDisponibles = tropasDisponibles;
	}

	/**
	 * Obtiene el DTO del usuario asociado al jugador.
	 * 
	 * @return el UserDTO asociado
	 */
	public UserDTO getUserDTO() {
		return userDTO;
	}

	/**
	 * Establece el DTO del usuario asociado al jugador.
	 * 
	 * @param userDTO el UserDTO a asignar
	 */
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	/**
	 * Obtiene el nombre del jugador.
	 * 
	 * @return el nombre del jugador
	 */
	public String getNombre() {
		return name;
	}

	/**
	 * Establece el nombre del jugador.
	 * 
	 * @param nombre el nombre a asignar
	 */
	public void setNombre(String nombre) {
		this.name = nombre;
	}

	/**
	 * Obtiene el color asignado al jugador.
	 * 
	 * @return el color del jugador
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Establece el color asignado al jugador.
	 * 
	 * @param color el color a asignar
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Obtiene la lista de territorios (DTO) que pertenecen al jugador.
	 * 
	 * @return la lista de territorios pertenecientes
	 */
	public MyLinkedList<TerritorioDTO> getTerritoriosPertenecientes() {
		return territoriosPertenecientes;
	}

	/**
	 * Establece la lista de territorios (DTO) que pertenecen al jugador.
	 * 
	 * @param territoriosPertenecientes la lista de territorios a asignar
	 */
	public void setTerritoriosPertenecientes(MyLinkedList<TerritorioDTO> territoriosPertenecientes) {
		this.territoriosPertenecientes = territoriosPertenecientes;
	}

	/**
	 * Obtiene la cantidad de tropas disponibles del jugador.
	 * 
	 * @return tropas disponibles
	 */
	public int getTropasDisponibles() {
		return tropasDisponibles;
	}

	/**
	 * Establece la cantidad de tropas disponibles del jugador.
	 * 
	 * @param tropasDisponibles la cantidad de tropas a asignar
	 */
	public void setTropasDisponibles(int tropasDisponibles) {
		this.tropasDisponibles = tropasDisponibles;
	}

}