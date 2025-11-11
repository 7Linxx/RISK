package co.edu.unbosque.model;

import co.edu.unbosque.util.MyDoubleLinkedList;

public class Jugador {

	private String name;
	private String color;
	private Usuario user;
	private int tropasDisponibles;
	private MyDoubleLinkedList<Territorio> territoriosPertenecientes;

	public Jugador() {
		// TODO Auto-generated constructor stub
	}

	public Jugador(String name, String color, Usuario user, int tropasDisponibles,
			MyDoubleLinkedList<Territorio> territoriosPertenecientes) {
		super();
		this.name = name;
		this.color = color;
		this.user = user;
		this.tropasDisponibles = tropasDisponibles;
		this.territoriosPertenecientes = territoriosPertenecientes;
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

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public int getTropasDisponibles() {
		return tropasDisponibles;
	}

	public void setTropasDisponibles(int tropasDisponibles) {
		this.tropasDisponibles = tropasDisponibles;
	}

	public MyDoubleLinkedList<Territorio> getTerritoriosPertenecientes() {
		return territoriosPertenecientes;
	}

	public void setTerritoriosPertenecientes(MyDoubleLinkedList<Territorio> territoriosPertenecientes) {
		this.territoriosPertenecientes = territoriosPertenecientes;
	}
	
}
