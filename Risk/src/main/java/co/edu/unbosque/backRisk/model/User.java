package co.edu.unbosque.backRisk.model;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
	private String username;
	private String nombre;
	private String correo;
	private String contrasenia;
	private Date fechaNacimiento;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String username, String nombre, String correo, String contrasenia, Date fechaNacimiento) {
		super();
		this.username = username;
		this.nombre = nombre;
		this.correo = correo;
		this.contrasenia = contrasenia;
		this.fechaNacimiento = fechaNacimiento;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contrasenia, correo, fechaNacimiento, id, nombre, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(contrasenia, other.contrasenia) && Objects.equals(correo, other.correo)
				&& Objects.equals(fechaNacimiento, other.fechaNacimiento) && id == other.id
				&& Objects.equals(nombre, other.nombre) && Objects.equals(username, other.username);
	}

}
