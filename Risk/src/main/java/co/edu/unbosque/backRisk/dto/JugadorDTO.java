package co.edu.unbosque.backRisk.dto;

import java.util.Objects;

/**
 * Objeto de transferencia de datos (DTO) que representa a un jugador dentro del
 * sistema.
 * 
 * <p>
 * Esta clase se utiliza para transportar información del jugador entre capas de
 * la aplicación sin exponer directamente la entidad del modelo. Contiene datos
 * esenciales como nombre, color asignado, correo electrónico, número de jugador
 * y tropas disponibles.
 * </p>
 * 
 * <p>
 * Incluye métodos para acceder y modificar sus atributos, así como
 * implementaciones de {@code equals} y {@code hashCode} que permiten
 * comparaciones precisas y consistentes entre objetos.
 * </p>
 * 
 * @version 2.0
 * @author Mariana
 */
public class JugadorDTO {

	/**
	 * Identificador único del jugador.
	 */
	private Long id;

	/**
	 * Nombre del jugador.
	 */
	private String name;

	/**
	 * Color asignado al jugador.
	 */
	private String color;

	/**
	 * Código hash asociado al jugador.
	 */
	private String hashCode;

	/**
	 * Correo electrónico del jugador.
	 */
	private String email;

	/**
	 * Número identificador del jugador.
	 */
	private int jugador;

	/**
	 * Cantidad de tropas disponibles para el jugador.
	 */
	private int tropasDisponibles;

	/**
	 * Constructor vacío utilizado por frameworks de serialización.
	 */
	public JugadorDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor que inicializa todos los atributos del DTO.
	 *
	 * @param id                identificador único del jugador
	 * @param name              nombre del jugador
	 * @param color             color asignado al jugador
	 * @param hashCode          código hash del jugador
	 * @param email             correo electrónico del jugador
	 * @param jugador           número identificador del jugador
	 * @param tropasDisponibles cantidad de tropas disponibles
	 */
	public JugadorDTO(Long id, String name, String color, String hashCode, String email, int jugador,
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

	/**
	 * Obtiene el identificador del jugador.
	 *
	 * @return id del jugador
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el identificador del jugador.
	 *
	 * @param id nuevo identificador
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del jugador.
	 *
	 * @return nombre del jugador
	 */
	public String getName() {
		return name;
	}

	/**
	 * Establece el nombre del jugador.
	 *
	 * @param name nuevo nombre
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Obtiene el color asignado al jugador.
	 *
	 * @return color del jugador
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Establece el color asignado al jugador.
	 *
	 * @param color nuevo color
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Obtiene el código hash del jugador.
	 *
	 * @return hashCode del jugador
	 */
	public String getHashCode() {
		return hashCode;
	}

	/**
	 * Establece el código hash del jugador.
	 *
	 * @param hashCode nuevo código hash
	 */
	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	/**
	 * Obtiene el correo electrónico del jugador.
	 *
	 * @return email del jugador
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Establece el correo electrónico del jugador.
	 *
	 * @param email nuevo correo electrónico
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Obtiene el número de jugador.
	 *
	 * @return número del jugador
	 */
	public int getJugador() {
		return jugador;
	}

	/**
	 * Establece el número del jugador.
	 *
	 * @param jugador nuevo número de jugador
	 */
	public void setJugador(int jugador) {
		this.jugador = jugador;
	}

	/**
	 * Obtiene la cantidad de tropas disponibles del jugador.
	 *
	 * @return tropas disponibles
	 */
	public int getTropasDisponibles() {
		return tropasDisponibles;
	}

	/**
	 * Establece la cantidad de tropas disponibles para el jugador.
	 *
	 * @param tropasDisponibles nueva cantidad de tropas disponibles
	 */
	public void setTropasDisponibles(int tropasDisponibles) {
		this.tropasDisponibles = tropasDisponibles;
	}

	/**
	 * Genera un valor hash basado en los atributos del DTO.
	 *
	 * @return valor hash representativo del estado del objeto
	 */
	@Override
	public int hashCode() {
		return Objects.hash(color, email, hashCode, id, jugador, name, tropasDisponibles);
	}

	/**
	 * Compara este DTO con otro objeto para determinar si representan al mismo
	 * jugador.
	 *
	 * @param obj objeto a comparar
	 * @return {@code true} si ambos objetos representan el mismo jugador,
	 *         {@code false} en caso contrario
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JugadorDTO other = (JugadorDTO) obj;
		return Objects.equals(color, other.color) && Objects.equals(email, other.email)
				&& Objects.equals(hashCode, other.hashCode) && Objects.equals(id, other.id) && jugador == other.jugador
				&& Objects.equals(name, other.name) && tropasDisponibles == other.tropasDisponibles;
	}

}
