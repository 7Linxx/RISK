package co.edu.unbosque.model;

import co.edu.unbosque.util.MyDoubleLinkedList;

public class TerritorioDTO {

	private String nombre;
	private int cantidadTropas;
	private Jugador duenio;
	private MyDoubleLinkedList<TerritorioDTO> territoriosAdyacentes;

	public TerritorioDTO() {
		// TODO Auto-generated constructor stub
	}

	public TerritorioDTO(String nombre, int cantidadTropas, Jugador duenio,
			MyDoubleLinkedList<TerritorioDTO> territoriosAdyacentes) {
		super();
		this.nombre = nombre;
		this.cantidadTropas = cantidadTropas;
		this.duenio = duenio;
		this.territoriosAdyacentes = territoriosAdyacentes;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantidadTropas() {
		return cantidadTropas;
	}

	public void setCantidadTropas(int cantidadTropas) {
		this.cantidadTropas = cantidadTropas;
	}

	public Jugador getDuenio() {
		return duenio;
	}

	public void setDuenio(Jugador duenio) {
		this.duenio = duenio;
	}

	public MyDoubleLinkedList<TerritorioDTO> getTerritoriosAdyacentes() {
		return territoriosAdyacentes;
	}

	public void setTerritoriosAdyacentes(MyDoubleLinkedList<TerritorioDTO> territoriosAdyacentes) {
		this.territoriosAdyacentes = territoriosAdyacentes;
	}

}
