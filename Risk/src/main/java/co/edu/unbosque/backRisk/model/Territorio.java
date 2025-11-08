package co.edu.unbosque.backRisk.model;

import co.edu.unbosque.backRisk.util.MyLinkedList;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "territorios")
public class Territorio {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	private String nombre;
	private Jugador duenio;
	private int cantidadTropas;
	private MyLinkedList<Territorio> territoriosAdyacentes;

	
	public Territorio() {
		// TODO Auto-generated constructor stub
	}


	public Territorio(Long id, String nombre, Jugador duenio, int cantidadTropas,
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
