package co.edu.unbosque.model;

import java.util.Objects;

public class Territorio {

	private String nombre;
	private String color;
	private int numeroTropas;

	public Territorio() {
		// TODO Auto-generated constructor stub
	}

	public Territorio(String nombre, String color, int numeroTropas) {
		super();
		this.nombre = nombre;
		this.color = color;
		this.numeroTropas = numeroTropas;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getNumeroTropas() {
		return numeroTropas;
	}

	public void setNumeroTropas(int numeroTropas) {
		this.numeroTropas = numeroTropas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, nombre, numeroTropas);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Territorio other = (Territorio) obj;
		return Objects.equals(color, other.color) && Objects.equals(nombre, other.nombre)
				&& numeroTropas == other.numeroTropas;
	}

}
