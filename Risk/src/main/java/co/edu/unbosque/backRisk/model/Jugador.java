package co.edu.unbosque.backRisk.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "jugadores")
public class Jugador {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	private String name;
	private String color;
	@OneToMany
	private List<Territorio> territoriosPertenecientes;
	private int tropasDisponibles;

	public Jugador() {
		// TODO Auto-generated constructor stub
	}

	public Jugador(User user, String nombre, String color, List<Territorio> territoriosPertenecientes,
			int tropasDisponibles) {
		super();
		this.user = user;
		this.name = nombre;
		this.color = color;
		this.territoriosPertenecientes = territoriosPertenecientes;
		this.tropasDisponibles = tropasDisponibles;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNombre() {
		return name;
	}

	public void setNombre(String nombre) {
		this.name = nombre;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<Territorio> getTerritoriosPertenecientes() {
		return territoriosPertenecientes;
	}

	public void setTerritoriosPertenecientes(List<Territorio> territoriosPertenecientes) {
		this.territoriosPertenecientes = territoriosPertenecientes;
	}

	public int getTropasDisponibles() {
		return tropasDisponibles;
	}

	public void setTropasDisponibles(int tropasDisponibles) {
		this.tropasDisponibles = tropasDisponibles;
	}

}
