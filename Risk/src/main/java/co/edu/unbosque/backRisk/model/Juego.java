package co.edu.unbosque.backRisk.model;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa un juego dentro del sistema.
 * 
 * <p>
 * La clase contiene información relacionada con el estado del juego, incluyendo
 * la fase actual, el turno del jugador y detalles adicionales. Se encuentra
 * mapeada a la tabla {@code juegos} en la base de datos.
 * </p>
 *
 * <p>
 * Incluye métodos para acceder y modificar sus atributos, además de
 * implementaciones personalizadas de {@code equals} y {@code hashCode}.
 * </p>
 *
 * @author Mariana Pineda
 * @version 2.0
 */
@Entity
@Table(name = "juegos")
public class Juego {

	/**
	 * Identificador único del juego. Se genera automáticamente.
	 */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

	/**
	 * Código hash asociado al juego.
	 */
	private String hashCode;

	/**
	 * Fase actual del juego.
	 */
	private int fase;

	/**
	 * Número que indica el turno del jugador actual.
	 */
	private int turnoJugador;

	/**
	 * Detalles adicionales del juego.
	 */
	private String detalles;

	/**
	 * Constructor vacío requerido por JPA.
	 */
	public Juego() {
		// Constructor por defecto
	}

	/**
	 * Constructor principal que inicializa todos los atributos del juego.
	 *
	 * @param id           identificador único del juego
	 * @param hashCode     código hash asociado al juego
	 * @param fase         fase actual del juego
	 * @param turnoJugador turno del jugador
	 * @param detalles     detalles adicionales relacionados con el juego
	 */
	public Juego(Long id, String hashCode, int fase, int turnoJugador, String detalles) {
		super();
		this.id = id;
		this.hashCode = hashCode;
		this.fase = fase;
		this.turnoJugador = turnoJugador;
		this.detalles = detalles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	public int getFase() {
		return fase;
	}

	public void setFase(int fase) {
		this.fase = fase;
	}

	public int getTurnoJugador() {
		return turnoJugador;
	}

	public void setTurnoJugador(int turnoJugador) {
		this.turnoJugador = turnoJugador;
	}

	public String getDetalles() {
		return detalles;
	}

	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	/**
	 * Genera un valor hash basado en los atributos del juego.
	 *
	 * @return un valor hash representativo del estado del objeto
	 */
	@Override
	public int hashCode() {
		return Objects.hash(detalles, fase, hashCode, id, turnoJugador);
	}

	/**
	 * Compara este juego con otro objeto para determinar si son equivalentes.
	 *
	 * @param obj objeto a comparar
	 * @return {@code true} si ambos objetos representan el mismo juego,
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
		Juego other = (Juego) obj;
		return Objects.equals(detalles, other.detalles) && fase == other.fase
				&& Objects.equals(hashCode, other.hashCode) && Objects.equals(id, other.id)
				&& turnoJugador == other.turnoJugador;
	}

}
