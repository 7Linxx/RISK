package co.edu.unbosque.model;

import java.util.Date;

/**
 * Representa un usuario del sistema.
 *
 * <p>
 * Contiene identificador, nombre de usuario, correo, contraseña y fecha de
 * nacimiento. Esta clase se usa en el modelo de dominio para representar la
 * entidad persistente o de negocio.
 * </p>
 *
 * @author Mariana Pineda
 * @since 1.0
 */
public class Usuario {

	/** Identificador único del usuario */
	private Long id;

	/** Nombre de usuario (login) */
	private String username;

	/** Correo electrónico del usuario */
	private String correo;

	/**
	 * Contraseña del usuario (en contexto real no debería almacenarse en texto
	 * plano)
	 */
	private String contrasenia;

	/** Fecha de nacimiento del usuario */
	private Date fechaNacimiento;

	/** Constructor por defecto. */
	public Usuario() {
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
	public Usuario(Long id, String username, String correo, String contrasenia, Date fechaNacimiento) {
		super();
		this.id = id;
		this.username = username;
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
	 * Establece el identificador del usuario.
	 *
	 * @param id nuevo identificador
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre de usuario.
	 *
	 * @return nombre de usuario
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
	 * @return correo electrónico
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Establece el correo electrónico.
	 *
	 * @param correo nuevo correo electrónico
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
	 * @param contrasenia nueva contraseña
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
	 * @param fechaNacimiento nueva fecha de nacimiento
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

}