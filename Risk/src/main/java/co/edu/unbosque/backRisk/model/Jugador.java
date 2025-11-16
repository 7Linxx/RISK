package co.edu.unbosque.backRisk.model;

import java.util.List;
import java.util.Objects;

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
	private String name;
	private String color;
	private String hashCode;
	private String email;
	private int jugador;
	private int tropasDisponibles;

	public Jugador() {
		// Constructor vacío para frameworks/serialización
	}

	public Jugador(Long id, String name, String color, String hashCode, String email, int jugador,
			int tropasDisponibles) {
		super();
		this.id = id;
		this.name = name;
		this.color = color;
		this.hashCode = hashCode;
		this.email = email;
		this.jugador = jugador;
		this.tropasDisponibles = tropasDisponibles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getJugador() {
		return jugador;
	}

	public void setJugador(int jugador) {
		this.jugador = jugador;
	}

	public int getTropasDisponibles() {
		return tropasDisponibles;
	}

	public void setTropasDisponibles(int tropasDisponibles) {
		this.tropasDisponibles = tropasDisponibles;
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, email, hashCode, id, jugador, name, tropasDisponibles);
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
		return Objects.equals(color, other.color) && Objects.equals(email, other.email)
				&& Objects.equals(hashCode, other.hashCode) && Objects.equals(id, other.id) && jugador == other.jugador
				&& Objects.equals(name, other.name) && tropasDisponibles == other.tropasDisponibles;
	}

}