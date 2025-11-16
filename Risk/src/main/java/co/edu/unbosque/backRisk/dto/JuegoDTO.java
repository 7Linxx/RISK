package co.edu.unbosque.backRisk.dto;

import java.util.Objects;

public class JuegoDTO {

	private Long id;
	private String hashCode;
	private int fase;
	private int turnoJugador;
	private String detalles;

	public JuegoDTO() {
		// TODO Auto-generated constructor stub
	}

	public JuegoDTO(Long id, String hashCode, int fase, int turnoJugador, String detalles) {
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
