package co.edu.unbosque.backRisk.model;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa un jugador dentro del sistema.
 * 
 * <p>
 * La clase almacena información relevante para identificar y gestionar a cada
 * jugador, como su nombre, color asignado, correo electrónico, número de
 * jugador y tropas disponibles.
 * </p>
 *
 * <p>
 * Esta entidad está mapeada a la tabla {@code jugadores} en la base de datos.
 * Incluye métodos para acceder y modificar sus atributos, además de las
 * implementaciones de {@code equals} y {@code hashCode} para asegurar
 * comparaciones consistentes entre objetos.
 * </p>
 *
 * @author Mariana Pineda
 * 
 * @version 2.0
 */
@Entity
@Table(name = "jugadores")
public class Jugador {

	/**
	 * Identificador único del jugador. Se genera automáticamente.
	 */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

	/**
	 * Nombre del jugador.
	 */
	private String name;

	/**
	 * Color asignado al jugador dentro del juego.
	 */
	private String color;

	/**
	 * Código hash asociado al jugador.
	 */
	private String hashCode;

	/**
	 * Correo electrónico del jugador.
	 */
	private String email;

	/**
	 * Número de jugador.
	 */
	private int jugador;

	/**
	 * Cantidad de tropas disponibles para el jugador.
	 */
	private int tropasDisponibles;

	/**
	 * Constructor vacío requerido por frameworks de persistencia y serialización.
	 */
	public Jugador() {
		// Constructor vacío para frameworks/serialización
	}

	/**
	 * Constructor que inicializa todos los atributos del jugador.
	 *
	 * @param id                identificador único del jugador
	 * @param name              nombre del jugador
	 * @param color             color asignado al jugador
	 * @param hashCode          código hash asociado al jugador
	 * @param email             correo electrónico del jugador
	 * @param jugador           número de jugador
	 * @param tropasDisponibles cantidad de tropas disponibles
	 */
	public Jugador(Long id, String name, String color, String hashCode, String email, int jugador,
			int tropasDisponibles) {
		super();
		this.id = id;
		this.name = name;
		this.color = color;
		this.hashCode = hashCode;
		this.email = email;
		this.jugador = jugador;
		this.tropasDisponibles = tropasDisponibles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getJugador() {
		return jugador;
	}

	public void setJugador(int jugador) {
		this.jugador = jugador;
	}

	public int getTropasDisponibles() {
		return tropasDisponibles;
	}

	public void setTropasDisponibles(int tropasDisponibles) {
		this.tropasDisponibles = tropasDisponibles;
	}

	/**
	 * Genera un valor hash basado en los atributos del jugador.
	 *
	 * @return un valor hash representativo del estado del objeto
	 */
	@Override
	public int hashCode() {
		return Objects.hash(color, email, hashCode, id, jugador, name, tropasDisponibles);
	}

	/**
	 * Compara este jugador con otro objeto para determinar si son equivalentes.
	 *
	 * @param obj objeto a comparar
	 * @return {@code true} si ambos objetos representan el mismo jugador,
	 *         {@code false} en caso contrario
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jugador other = (Jugador) obj;
		return Objects.equals(color, other.color) && Objects.equals(email, other.email)
				&& Objects.equals(hashCode, other.hashCode) && Objects.equals(id, other.id) && jugador == other.jugador
				&& Objects.equals(name, other.name) && tropasDisponibles == other.tropasDisponibles;
	}

}