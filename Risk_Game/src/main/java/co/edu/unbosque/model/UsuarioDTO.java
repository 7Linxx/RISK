package co.edu.unbosque.model;

import java.util.Date;

/**
 * Data Transfer Object (DTO) para transportar información de un
 * {@link Usuario}.
 *
 * <p>
 * Contiene los mismos campos que la entidad Usuario y está pensado para el
 * intercambio de datos entre capas (por ejemplo, controlador <-> servicio).
 * </p>
 * 
 * @author Mariana Pineda
 * @since 1.0
 */
public class UsuarioDTO {

	/** Identificador del usuario */
	private Long id;

	/** Nombre de usuario (login) */
	private String username;

	/** Correo electrónico */
	private String correo;

	/** Contraseña (tener cuidado al exponerla) */
	private String contrasenia;

	/** Fecha de nacimiento */
	private Date fechaNacimiento;

	/** Constructor por defecto. */
	public UsuarioDTO() {
		// Constructor vacío
	}

	/**
	 * Constructor con todos los atributos.
	 *
	 * @param id              identificador del usuario
	 * @param username        nombre de usuario
	 * @param correo          correo electrónico
	 * @param contrasenia     contraseña
	 * @param fechaNacimiento fecha de nacimiento
	 */
	public UsuarioDTO(Long id, String username, String correo, String contrasenia, Date fechaNacimiento) {
		super();
		this.id = id;
		this.username = username;
		this.correo = correo;
		this.contrasenia = contrasenia;
		this.fechaNacimiento = fechaNacimiento;
	}

	/**
	 * Obtiene el identificador.
	 *
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el identificador.
	 *
	 * @param id nuevo id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre de usuario.
	 *
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Establece el nombre de usuario.
	 *
	 * @param username nuevo nombre de usuario
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Obtiene el correo electrónico.
	 *
	 * @return correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Establece el correo electrónico.
	 *
	 * @param correo nuevo correo
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Obtiene la contraseña.
	 *
	 * @return contrasenia
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * Establece la contraseña.
	 *
	 * @param contrasenia nueva contraseña
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	/**
	 * Obtiene la fecha de nacimiento.
	 *
	 * @return fecha de nacimiento
	 */
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * Establece la fecha de nacimiento.
	 *
	 * @param fechaNacimiento nueva fecha de nacimiento
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

}