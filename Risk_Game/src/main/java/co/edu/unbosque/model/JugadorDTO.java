package co.edu.unbosque.model;

import co.edu.unbosque.util.MyDoubleLinkedList;

public class JugadorDTO {

	private String name;
	private String color;
	private Usuario user;
	private int tropasDisponibles;
	private MyDoubleLinkedList<TerritorioDTO> territoriosPertenecientes;

	public JugadorDTO() {
		// TODO Auto-generated constructor stub
	}

	public JugadorDTO(String name, String color, Usuario user, int tropasDisponibles,
			MyDoubleLinkedList<TerritorioDTO> territoriosPertenecientes) {
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

	public MyDoubleLinkedList<TerritorioDTO> getTerritoriosPertenecientes() {
		return territoriosPertenecientes;
	}

	public void setTerritoriosPertenecientes(MyDoubleLinkedList<TerritorioDTO> territoriosPertenecientes) {
		this.territoriosPertenecientes = territoriosPertenecientes;
	}

}
