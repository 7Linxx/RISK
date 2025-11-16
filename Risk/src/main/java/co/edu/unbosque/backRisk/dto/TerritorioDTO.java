package co.edu.unbosque.backRisk.dto;

import java.util.Objects;

public class TerritorioDTO {

	private Long id;
	private String name;
	private int cantidadTropas;
	private String color;
	private String hashCode;
	private int jugador;

	public TerritorioDTO() {
		// TODO Auto-generated constructor stub
	}

	public TerritorioDTO(Long id, String name, int cantidadTropas, String color, String hashCode, int jugador) {
		super();
		this.id = id;
		this.name = name;
		this.cantidadTropas = cantidadTropas;
		this.color = color;
		this.hashCode = hashCode;
		this.jugador = jugador;
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

	public int getCantidadTropas() {
		return cantidadTropas;
	}

	public void setCantidadTropas(int cantidadTropas) {
		this.cantidadTropas = cantidadTropas;
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

	public int getJugador() {
		return jugador;
	}

	public void setJugador(int jugador) {
		this.jugador = jugador;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cantidadTropas, color, hashCode, id, jugador, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TerritorioDTO other = (TerritorioDTO) obj;
		return cantidadTropas == other.cantidadTropas && Objects.equals(color, other.color)
				&& Objects.equals(hashCode, other.hashCode) && Objects.equals(id, other.id) && jugador == other.jugador
				&& Objects.equals(name, other.name);
	}

}