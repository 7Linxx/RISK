package co.edu.unbosque.model;

import co.edu.unbosque.util.MyDoubleLinkedList;

/**
 * Representa un jugador en el modelo del juego.
 *
 * <p>
 * Contiene los datos principales de un jugador: nombre, color, usuario
 * asociado, tropas disponibles y la lista de territorios que le pertenecen.
 * </p>
 *
 * @author Mariana Pineda
 * @since 1.0
 */
public class Jugador {

	/** Nombre del jugador */
	private String name;

	/** Color asignado al jugador */
	private String color;

	/** Usuario asociado al jugador (credenciales/meta información) */
	private Usuario user;

	/** Cantidad de tropas disponibles para repartir o mover */
	private int tropasDisponibles;

	/** Lista doblemente enlazada con los territorios que pertenecen al jugador */
	private MyDoubleLinkedList<Territorio> territoriosPertenecientes;

	private MyDoubleLinkedList<String> territorios;

	/**
	 * Constructor por defecto.
	 */
	public Jugador() {
		// Constructor vacío
	}

	/**
	 * Constructor con todos los atributos.
	 *
	 * @param name                      nombre del jugador
	 * @param color                     color del jugador
	 * @param user                      usuario asociado
	 * @param tropasDisponibles         tropas disponibles
	 * @param territoriosPertenecientes lista de territorios del jugador
	 */
	public Jugador(String name, String color, Usuario user, int tropasDisponibles,
			MyDoubleLinkedList<Territorio> territoriosPertenecientes) {
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
	 * Obtiene el número de tropas disponibles.
	 *
	 * @return tropas disponibles
	 */
	public int getTropasDisponibles() {
		return tropasDisponibles;
	}

	/**
	 * Establece el número de tropas disponibles.
	 *
	 * @param tropasDisponibles nuevas tropas disponibles
	 */
	public void setTropasDisponibles(int tropasDisponibles) {
		this.tropasDisponibles = tropasDisponibles;
	}

	/**
	 * Obtiene la lista de territorios pertenecientes al jugador.
	 *
	 * @return lista de territorios
	 */
	public MyDoubleLinkedList<Territorio> getTerritoriosPertenecientes() {
		return territoriosPertenecientes;
	}

	/**
	 * Establece la lista de territorios pertenecientes al jugador.
	 *
	 * @param territoriosPertenecientes nueva lista de territorios
	 */
	public void setTerritoriosPertenecientes(MyDoubleLinkedList<Territorio> territoriosPertenecientes) {
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