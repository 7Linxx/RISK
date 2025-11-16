package co.edu.unbosque.backRisk.dto;

import java.util.Objects;

/**
 * Objeto de transferencia de datos (DTO) que representa un territorio dentro
 * del sistema.
 * 
 * <p>
 * Esta clase se utiliza para transportar información entre capas del sistema
 * sin exponer directamente la entidad del modelo. Contiene los mismos atributos
 * esenciales del territorio, como su nombre, cantidad de tropas, color, código
 * hash e identificador del jugador propietario.
 * </p>
 * 
 * <p>
 * Incluye métodos de acceso y modificación para cada atributo, así como las
 * implementaciones de {@code equals} y {@code hashCode} para permitir
 * comparaciones consistentes entre instancias.
 * </p>
 * 
 * @author Mariana
 * 
 * @version 2.0
 */
public class TerritorioDTO {

	/**
	 * Identificador único del territorio.
	 */
	private Long id;

	/**
	 * Nombre del territorio.
	 */
	private String name;

	/**
	 * Cantidad de tropas asignadas al territorio.
	 */
	private int cantidadTropas;

	/**
	 * Color asociado al territorio.
	 */
	private String color;

	/**
	 * Código hash utilizado para identificar el territorio externamente.
	 */
	private String hashCode;

	/**
	 * Identificador del jugador que controla el territorio.
	 */
	private int jugador;

	/**
	 * Constructor vacío utilizado por frameworks de serialización.
	 */
	public TerritorioDTO() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor que inicializa todos los atributos del DTO.
	 *
	 * @param id             identificador único del territorio
	 * @param name           nombre del territorio
	 * @param cantidadTropas cantidad de tropas asignadas
	 * @param color          color asociado al territorio
	 * @param hashCode       código hash del territorio
	 * @param jugador        identificador del jugador propietario
	 */
	public TerritorioDTO(Long id, String name, int cantidadTropas, String color, String hashCode, int jugador) {
		super();
		this.id = id;
		this.name = name;
		this.cantidadTropas = cantidadTropas;
		this.color = color;
		this.hashCode = hashCode;
		this.jugador = jugador;
	}

	/**
	 * Obtiene el identificador del territorio.
	 *
	 * @return id del territorio
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Establece el identificador del territorio.
	 *
	 * @param id nuevo identificador
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del territorio.
	 *
	 * @return nombre del territorio
	 */
	public String getName() {
		return name;
	}

	/**
	 * Establece el nombre del territorio.
	 *
	 * @param name nuevo nombre
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Obtiene la cantidad de tropas asignadas al territorio.
	 *
	 * @return cantidad de tropas
	 */
	public int getCantidadTropas() {
		return cantidadTropas;
	}

	/**
	 * Establece la cantidad de tropas asignadas al territorio.
	 *
	 * @param cantidadTropas nueva cantidad de tropas
	 */
	public void setCantidadTropas(int cantidadTropas) {
		this.cantidadTropas = cantidadTropas;
	}

	/**
	 * Obtiene el color asociado al territorio.
	 *
	 * @return color del territorio
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Establece el color asociado al territorio.
	 *
	 * @param color nuevo color
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Obtiene el código hash asignado al territorio.
	 *
	 * @return hashCode del territorio
	 */
	public String getHashCode() {
		return hashCode;
	}

	/**
	 * Establece el código hash del territorio.
	 *
	 * @param hashCode nuevo código hash
	 */
	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	/**
	 * Obtiene el identificador del jugador propietario del territorio.
	 *
	 * @return id del jugador
	 */
	public int getJugador() {
		return jugador;
	}

	/**
	 * Establece el identificador del jugador propietario del territorio.
	 *
	 * @param jugador id del jugador
	 */
	public void setJugador(int jugador) {
		this.jugador = jugador;
	}

	/**
	 * Genera un valor hash basado en los atributos del DTO.
	 *
	 * @return valor hash representativo del estado del objeto
	 */
	@Override
	public int hashCode() {
		return Objects.hash(cantidadTropas, color, hashCode, id, jugador, name);
	}

	/**
	 * Compara este DTO con otro objeto para determinar si representan el mismo
	 * territorio.
	 *
	 * @param obj objeto a comparar
	 * @return {@code true} si ambos objetos representan el mismo territorio,
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
		TerritorioDTO other = (TerritorioDTO) obj;
		return cantidadTropas == other.cantidadTropas && Objects.equals(color, other.color)
				&& Objects.equals(hashCode, other.hashCode) && Objects.equals(id, other.id) && jugador == other.jugador
				&& Objects.equals(name, other.name);
	}

}
