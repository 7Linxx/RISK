package co.edu.unbosque.backRisk.model;

import java.util.List;

import co.edu.unbosque.backRisk.util.MyLinkedList;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Entidad JPA que representa un territorio en la base de datos.
 * <p>
 * Mapeada a la tabla "territorios", contiene identificador, nombre, dueño
 * (Jugador), cantidad de tropas y la lista de territorios adyacentes.
 * </p>
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
@Entity
@Table(name = "territorios")
public class Territorio {

	/**
	 * Identificador único del territorio (clave primaria).
	 */
	private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

	/**
	 * Nombre del territorio.
	 */
	private String name;

	/**
	 * Dueño actual del territorio. Relación ManyToOne con la entidad Jugador.
	 */
	@ManyToOne
	@JoinColumn(name = "jugador_id")
	private Jugador duenio;

	/**
	 * Cantidad de tropas presentes en el territorio.
	 */
	private int cantidadTropas;

	/**
	 * Lista de territorios adyacentes a este territorio.
	 * <p>
	 * Relación ManyToMany mapeada mediante la tabla "territorios_adyacentes".
	 * </p>
	 */
	@ManyToMany
	@JoinTable(name = "territorios_adyacentes", joinColumns = @JoinColumn(name = "territorio_id"), inverseJoinColumns = @JoinColumn(name = "adyacente_id"))
	private List<Territorio> territoriosAdyacentes;

	/**
	 * Constructor por defecto.
	 */
	public Territorio() {
		// Constructor vacío para frameworks/serialización
	}

	/**
	 * Constructor que inicializa los campos principales de la entidad.
	 * 
	 * @param id                    Identificador del territorio
	 * @param nombre                Nombre del territorio
	 * @param duenio                Jugador dueño del territorio
	 * @param cantidadTropas        Cantidad de tropas en el territorio
	 * @param territoriosAdyacentes Lista de territorios adyacentes
	 */
	public Territorio(Long id, String nombre, Jugador duenio, int cantidadTropas,
			List<Territorio> territoriosAdyacentes) {
		super();
		this.id = id;
		this.name = nombre;
		this.duenio = duenio;
		this.cantidadTropas = cantidadTropas;
		this.territoriosAdyacentes = territoriosAdyacentes;
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
	 * @param id Identificador a asignar
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Obtiene el nombre del territorio.
	 * 
	 * @return nombre del territorio
	 */
	public String getNombre() {
		return name;
	}

	/**
	 * Establece el nombre del territorio.
	 * 
	 * @param nombre nombre a asignar
	 */
	public void setNombre(String nombre) {
		this.name = nombre;
	}

	/**
	 * Obtiene el dueño del territorio.
	 * 
	 * @return jugador dueño del territorio
	 */
	public Jugador getDuenio() {
		return duenio;
	}

	/**
	 * Establece el dueño del territorio.
	 * 
	 * @param duenio jugador a asignar como dueño
	 */
	public void setDuenio(Jugador duenio) {
		this.duenio = duenio;
	}

	/**
	 * Obtiene la cantidad de tropas en el territorio.
	 * 
	 * @return cantidad de tropas
	 */
	public int getCantidadTropas() {
		return cantidadTropas;
	}

	/**
	 * Establece la cantidad de tropas en el territorio.
	 * 
	 * @param cantidadTropas cantidad de tropas a asignar
	 */
	public void setCantidadTropas(int cantidadTropas) {
		this.cantidadTropas = cantidadTropas;
	}

	/**
	 * Obtiene la lista de territorios adyacentes a este territorio.
	 * 
	 * @return lista de territorios adyacentes
	 */
	public List<Territorio> getTerritoriosAdyacentes() {
		return territoriosAdyacentes;
	}

	/**
	 * Establece la lista de territorios adyacentes a este territorio.
	 * 
	 * @param territoriosAdyacentes lista de adyacentes a asignar
	 */
	public void setTerritoriosAdyacentes(List<Territorio> territoriosAdyacentes) {
		this.territoriosAdyacentes = territoriosAdyacentes;
	}

}