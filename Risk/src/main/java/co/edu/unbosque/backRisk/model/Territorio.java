package co.edu.unbosque.backRisk.model;

import java.util.List;

import co.edu.unbosque.backRisk.util.MyLinkedList;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "territorios")
public class Territorio {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	private String name;
	@ManyToOne
	@JoinColumn(name = "jugador_id")
	private Jugador duenio;
	private int cantidadTropas;
	@ManyToMany
	@JoinTable(name = "territorios_adyacentes", joinColumns = @JoinColumn(name = "territorio_id"), inverseJoinColumns = @JoinColumn(name = "adyacente_id"))
	private List<Territorio> territoriosAdyacentes;

	public Territorio() {
		// TODO Auto-generated constructor stub
	}

	public Territorio(Long id, String nombre, Jugador duenio, int cantidadTropas,
			List<Territorio> territoriosAdyacentes) {
		super();
		this.id = id;
		this.name = nombre;
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
		return name;
	}

	public void setNombre(String nombre) {
		this.name = nombre;
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

	public List<Territorio> getTerritoriosAdyacentes() {
		return territoriosAdyacentes;
	}

	public void setTerritoriosAdyacentes(List<Territorio> territoriosAdyacentes) {
		this.territoriosAdyacentes = territoriosAdyacentes;
	}

}
