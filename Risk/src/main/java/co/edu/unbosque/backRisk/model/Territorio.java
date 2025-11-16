package co.edu.unbosque.backRisk.model;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa un territorio dentro del sistema.
 * 
 * <p>
 * La clase almacena información relevante para identificar y gestionar cada
 * territorio del juego, incluyendo su nombre, cantidad de tropas, color, código
 * hash asociado e identificación del jugador que lo controla.
 * </p>
 *
 * <p>
 * Esta entidad está mapeada a la tabla {@code territorios} en la base de datos.
 * Incluye métodos para acceder y modificar sus atributos, además de las
 * implementaciones de {@code equals} y {@code hashCode} para asegurar
 * comparaciones consistentes entre objetos.
 * </p>
 * 
 * @author Mariana
 *
 * @version 2.0
 */
@Entity
@Table(name = "territorios")
public class Territorio {

	/**
	 * Identificador único del territorio. Se genera automáticamente.
	 */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

	/**
	 * Nombre del territorio.
	 */
	private String name;

	/**
	 * Cantidad de tropas asignadas al territorio.
	 */
	private int cantidadTropas;

	/**
	 * Color asociado al territorio, usualmente el del jugador que lo controla.
	 */
	private String color;

	/**
	 * Código hash asociado al territorio, usado para identificarlo desde capas
	 * externas.
	 */
	private String hashCode;

	/**
	 * Identificador del jugador que controla este territorio.
	 */
	private int jugador;

	/**
	 * Constructor vacío requerido por frameworks de persistencia y serialización.
	 */
	public Territorio() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor que inicializa todos los atributos del territorio.
	 *
	 * @param id             identificador único del territorio
	 * @param name           nombre del territorio
	 * @param cantidadTropas cantidad de tropas asignadas
	 * @param color          color al que pertenece el territorio
	 * @param hashCode       código hash asociado
	 * @param jugador        identificador del jugador
	 */
	public Territorio(Long id, String name, int cantidadTropas, String color, String hashCode, int jugador) {
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
	 * Obtiene el código hash del territorio.
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
	 * Obtiene el identificador del jugador que controla el territorio.
	 *
	 * @return id del jugador
	 */
	public int getJugador() {
		return jugador;
	}

	/**
	 * Establece el identificador del jugador que controla el territorio.
	 *
	 * @param jugador id del jugador
	 */
	public void setJugador(int jugador) {
		this.jugador = jugador;
	}

	/**
	 * Genera un valor hash basado en los atributos del territorio.
	 *
	 * @return valor hash representativo del estado del objeto
	 */
	@Override
	public int hashCode() {
		return Objects.hash(cantidadTropas, color, hashCode, id, jugador, name);
	}

	/**
	 * Compara este territorio con otro objeto para determinar si son equivalentes.
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
		Territorio other = (Territorio) obj;
		return cantidadTropas == other.cantidadTropas && Objects.equals(color, other.color)
				&& Objects.equals(hashCode, other.hashCode) && Objects.equals(id, other.id) && jugador == other.jugador
				&& Objects.equals(name, other.name);
	}

}
