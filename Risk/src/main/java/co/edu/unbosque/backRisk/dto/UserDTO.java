package co.edu.unbosque.backRisk.dto;

import java.util.Date;
import java.util.Objects;

import co.edu.unbosque.backRisk.model.User;

/**
 * Data Transfer Object (DTO) que representa la información de un usuario para
 * la capa de transferencia de datos de la aplicación.
 * <p>
 * Contiene los datos básicos del usuario como identificador, nombre de usuario,
 * nombre completo, correo, contraseña y fecha de nacimiento.
 * </p>
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public class UserDTO {
	/**
	 * Identificador único del usuario.
	 */
	private Long id;

	/**
	 * Nombre de usuario (username) utilizado para autenticación.
	 */
	private String username;

	/**
	 * Nombre completo del usuario.
	 */
	private String nombre;

	/**
	 * Correo electrónico del usuario.
	 */
	private String correo;

	/**
	 * Contraseña del usuario (debe manejarse con precaución y no exponerse).
	 */
	private String contrasenia;

	/**
	 * Fecha de nacimiento del usuario.
	 */
	private Date fechaNacimiento;

	/**
	 * Constructor por defecto.
	 */
	public UserDTO() {
		// Constructor vacío para frameworks/serialización
	}

	/**
	 * Constructor que inicializa los campos principales del DTO.
	 * 
	 * @param username        Nombre de usuario
	 * @param nombre          Nombre completo
	 * @param correo          Correo electrónico
	 * @param contrasenia     Contraseña
	 * @param fechaNacimiento Fecha de nacimiento
	 */
	public UserDTO(String username, String nombre, String correo, String contrasenia, Date fechaNacimiento) {
		super();
		this.username = username;
		this.nombre = nombre;
		this.correo = correo;
		this.contrasenia = contrasenia;
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * Obtiene el identificador del usuario.
	 * 
	 * @return id del usuario
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Obtiene el nombre completo del usuario.
	 * 
	 * @return nombre completo
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre completo del usuario.
	 * 
	 * @param nombre nombre completo a asignar
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el correo electrónico del usuario.
	 * 
	 * @return correo electrónico
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Establece el correo electrónico del usuario.
	 * 
	 * @param correo correo a asignar
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Obtiene la contraseña del usuario.
	 * 
	 * @return contraseña
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * Establece la contraseña del usuario.
	 * 
	 * @param contrasenia contraseña a asignar
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	/**
	 * Obtiene la fecha de nacimiento del usuario.
	 * 
	 * @return fecha de nacimiento
	 */
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * Establece la fecha de nacimiento del usuario.
	 * 
	 * @param fechaNacimiento fecha de nacimiento a asignar
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * Obtiene el nombre de usuario (username).
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Establece el nombre de usuario (username).
	 * 
	 * @param username username a asignar
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Calcula el código hash del DTO basado en sus campos relevantes.
	 * 
	 * @return código hash
	 */
	@Override
	public int hashCode() {
		return Objects.hash(contrasenia, correo, fechaNacimiento, id, nombre, username);
	}

	/**
	 * Compara este DTO con otro objeto para determinar igualdad.
	 * <p>
	 * La comparación se realiza comprobando que el objeto sea de la misma clase y
	 * comparando los campos relevantes.
	 * </p>
	 * 
	 * @param obj objeto a comparar
	 * @return true si son iguales, false en caso contrario
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		return Objects.equals(contrasenia, other.contrasenia) && Objects.equals(correo, other.correo)
				&& Objects.equals(fechaNacimiento, other.fechaNacimiento) && id == other.id
				&& Objects.equals(nombre, other.nombre) && Objects.equals(username, other.username);
	}

}