package co.edu.unbosque.model;

import java.util.Objects;

import co.edu.unbosque.util.MyDoubleLinkedList;

public class Jugador {

	private String nombre;
	private String color;
	private String email;
	private MyDoubleLinkedList<String> territorios;

	public Jugador() {
		// TODO Auto-generated constructor stub
	}

	public Jugador(String nombre, String color, String email) {
		super();
		this.nombre = nombre;
		this.color = color;
		this.email = email;
	}

	public Jugador(String nombre, String color, MyDoubleLinkedList<String> territorios) {
		super();
		this.nombre = nombre;
		this.color = color;
		this.territorios = territorios;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public MyDoubleLinkedList<String> getTerritorios() {
		return territorios;
	}

	public void setTerritorios(MyDoubleLinkedList<String> territorios) {
		this.territorios = territorios;
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, nombre, territorios);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Jugador other = (Jugador) obj;
		return Objects.equals(color, other.color) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(territorios, other.territorios);
	}


	public boolean containsTerritory(String territorio) {
		for(String aux: territorios) {
			if(aux.equals(territorio))return true;
		}
		return false;
	}
	
	public void removeTerritory(String terrytorio) {
		for (int i = 0; i < territorios.getSize(); i++) {
			if(terrytorio.equals(territorios.get(i))) {
				territorios.remove(i);
				return;
			}
		}
	}
}
