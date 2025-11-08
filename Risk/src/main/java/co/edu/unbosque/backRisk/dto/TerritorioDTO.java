package co.edu.unbosque.backRisk.dto;

import co.edu.unbosque.backRisk.model.Jugador;
import co.edu.unbosque.backRisk.model.Territorio;
import co.edu.unbosque.backRisk.util.MyLinkedList;

public class TerritorioDTO {

	private Long id;
	private String nombre;
	private Jugador duenio;
	private int cantidadTropas;
	private MyLinkedList<Territorio> territoriosAdyacentes;

	public TerritorioDTO() {
		// TODO Auto-generated constructor stub
	}

	public TerritorioDTO(Long id, String nombre, Jugador duenio, int cantidadTropas,
			MyLinkedList<Territorio> territoriosAdyacentes) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.duenio = duenio;
		this.cantidadTropas = cantidadTropas;
		this.territoriosAdyacentes = territoriosAdyacentes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Jugador getDuenio() {
		return duenio;
	}

	public void setDuenio(Jugador duenio) {
		this.duenio = duenio;
	}

	public int getCantidadTropas() {
		return cantidadTropas;
	}

	public void setCantidadTropas(int cantidadTropas) {
		this.cantidadTropas = cantidadTropas;
	}

	public MyLinkedList<Territorio> getTerritoriosAdyacentes() {
		return territoriosAdyacentes;
	}

	public void setTerritoriosAdyacentes(MyLinkedList<Territorio> territoriosAdyacentes) {
		this.territoriosAdyacentes = territoriosAdyacentes;
	}

}
