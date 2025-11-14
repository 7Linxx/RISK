package co.edu.unbosque.backRisk.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entidad JPA que representa un jugador en la base de datos.
 * <p>
 * Mapeada a la tabla "jugadores", contiene referencia al usuario asociado,
 * nombre, color, los territorios que posee y las tropas disponibles.
 * </p>
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
@Entity
@Table(name = "jugadores")
public class Jugador {

	/**
	 * Identificador único del jugador (clave primaria).
	 */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

	/**
	 * Usuario asociado al jugador. Relación ManyToOne con la entidad User.
	 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	/**
	 * Nombre del jugador.
	 */
	private String name;

	/**
	 * Color asignado al jugador (por ejemplo para representar en el tablero).
	 */
	private String color;

	/**
	 * Lista de territorios que pertenecen al jugador.
	 * <p>
	 * Relación OneToMany con la entidad Territorio.
	 * </p>
	 */
	@OneToMany
	private List<Territorio> territoriosPertenecientes;

	/**
	 * Cantidad de tropas disponibles del jugador.
	 */
	private int tropasDisponibles;

	/**
	 * Constructor por defecto.
	 */
	public Jugador() {
		// Constructor vacío para frameworks/serialización
	}

	/**
	 * Constructor que inicializa los campos principales de la entidad.
	 * 
	 * @param user                      Usuario asociado
	 * @param nombre                    Nombre del jugador
	 * @param color                     Color del jugador
	 * @param territoriosPertenecientes Lista de territorios que posee
	 * @param tropasDisponibles         Tropas disponibles
	 */
	public Jugador(User user, String nombre, String color, List<Territorio> territoriosPertenecientes,
			int tropasDisponibles) {
		super();
		this.user = user;
		this.name = nombre;
		this.color = color;
		this.territoriosPertenecientes = territoriosPertenecientes;
		this.tropasDisponibles = tropasDisponibles;
	}

	/**
	 * Obtiene el usuario asociado al jugador.
	 * 
	 * @return user asociado
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Establece el usuario asociado al jugador.
	 * 
	 * @param user usuario a asignar
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Obtiene el nombre del jugador.
	 * 
	 * @return nombre del jugador
	 */
	public String getNombre() {
		return name;
	}

	/**
	 * Establece el nombre del jugador.
	 * 
	 * @param nombre nombre a asignar
	 */
	public void setNombre(String nombre) {
		this.name = nombre;
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
	 * @param color color a asignar
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Obtiene la lista de territorios que pertenecen al jugador.
	 * 
	 * @return lista de territorios
	 */
	public List<Territorio> getTerritoriosPertenecientes() {
		return territoriosPertenecientes;
	}

	/**
	 * Establece la lista de territorios que pertenecen al jugador.
	 * 
	 * @param territoriosPertenecientes lista de territorios a asignar
	 */
	public void setTerritoriosPertenecientes(List<Territorio> territoriosPertenecientes) {
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
	 * @param tropasDisponibles cantidad de tropas a asignar
	 */
	public void setTropasDisponibles(int tropasDisponibles) {
		this.tropasDisponibles = tropasDisponibles;
	}

}