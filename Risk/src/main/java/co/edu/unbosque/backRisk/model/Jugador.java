package co.edu.unbosque.backRisk.model;

import co.edu.unbosque.backRisk.util.MyLinkedList;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "jugadores")
public class Jugador {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	private User user;
	private String nombre;
	private String color;
	private MyLinkedList<Territorio> territoriosPertenecientes;
	private int tropasDisponibles;

	public Jugador() {
		// TODO Auto-generated constructor stub
	}

	public Jugador(User user, String nombre, String color, MyLinkedList<Territorio> territoriosPertenecientes,
			int tropasDisponibles) {
		super();
		this.user = user;
		this.nombre = nombre;
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

	public MyLinkedList<Territorio> getTerritoriosPertenecientes() {
		return territoriosPertenecientes;
	}

	public void setTerritoriosPertenecientes(MyLinkedList<Territorio> territoriosPertenecientes) {
		this.territoriosPertenecientes = territoriosPertenecientes;
	}

	public int getTropasDisponibles() {
		return tropasDisponibles;
	}

	public void setTropasDisponibles(int tropasDisponibles) {
		this.tropasDisponibles = tropasDisponibles;
	}

}
