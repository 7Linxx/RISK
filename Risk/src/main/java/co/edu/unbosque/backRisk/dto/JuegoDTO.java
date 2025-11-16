package co.edu.unbosque.backRisk.dto;

import java.util.Objects;

/**
 * Representa los datos de un juego dentro del sistema Risk. Esta clase actúa
 * como un Data Transfer Object (DTO) para transportar información básica
 * relacionada con el estado actual del juego.
 * 
 * <p>
 * Incluye identificador, código hash de la partida, fase de juego, el turno del
 * jugador actual y detalles adicionales del estado.
 * </p>
 * 
 * <p>
 * Se utiliza para comunicar información entre las capas de la aplicación, sin
 * exponer directamente las entidades del modelo.
 * </p>
 * 
 * @author Mariana
 * @version 2.0
 */
public class JuegoDTO {

	/** Identificador único del juego. */
	private Long id;

	/** Código hash asociado al juego para diferenciar partidas. */
	private String hashCode;

	/** Número de la fase actual del juego. */
	private int fase;

	/** Indica qué jugador tiene el turno en este momento. */
	private int turnoJugador;

	/** Información adicional sobre el estado del juego. */
	private String detalles;

	/**
	 * Constructor por defecto.
	 */
	public JuegoDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Construye un nuevo objeto {@code JuegoDTO} con los valores proporcionados.
	 * 
	 * @param id           identificador del juego
	 * @param hashCode     código hash único de la partida
	 * @param fase         fase actual del juego
	 * @param turnoJugador jugador al que le corresponde el turno
	 * @param detalles     información adicional o descriptiva
	 */
	public JuegoDTO(Long id, String hashCode, int fase, int turnoJugador, String detalles) {
		super();
		this.id = id;
		this.hashCode = hashCode;
		this.fase = fase;
		this.turnoJugador = turnoJugador;
		this.detalles = detalles;
	}

	/** @return el ID del juego. */
	public Long getId() {
		return id;
	}

	/** @param id nuevo identificador del juego. */
	public void setId(Long id) {
		this.id = id;
	}

	/** @return el código hash del juego. */
	public String getHashCode() {
		return hashCode;
	}

	/** @param hashCode nuevo código hash del juego. */
	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	/** @return la fase actual del juego. */
	public int getFase() {
		return fase;
	}

	/** @param fase nueva fase del juego. */
	public void setFase(int fase) {
		this.fase = fase;
	}

	/** @return el número del jugador que tiene el turno. */
	public int getTurnoJugador() {
		return turnoJugador;
	}

	/** @param turnoJugador nuevo turno del jugador. */
	public void setTurnoJugador(int turnoJugador) {
		this.turnoJugador = turnoJugador;
	}

	/** @return detalles del estado del juego. */
	public String getDetalles() {
		return detalles;
	}

	/** @param detalles nuevos detalles para el juego. */
	public void setDetalles(String detalles) {
		this.detalles = detalles;
	}

	@Override
	public int hashCode() {
		return Objects.hash(detalles, fase, hashCode, id, turnoJugador);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JuegoDTO other = (JuegoDTO) obj;
		return Objects.equals(detalles, other.detalles) && fase == other.fase
				&& Objects.equals(hashCode, other.hashCode) && Objects.equals(id, other.id)
				&& turnoJugador == other.turnoJugador;
	}

}
