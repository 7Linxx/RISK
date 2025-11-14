package co.edu.unbosque.model;

import java.util.Objects;

/**
 * DTO simplificado para exponer información de un territorio en la API.
 *
 * <p>
 * Este DTO usa nombres y formatos orientados a la API (name, color,
 * numberTroops) y redefine hashCode apropiadamente.
 * </p>
 * 
 * @author Mariana Pineda
 * @since 1.0
 */
public class TerritoryDTO {

	/** Nombre amigable del territorio */
	private String name;

	/** Color asociado (por ejemplo color del dueño) */
	private String color;

	/** Número de tropas presentes en el territorio */
	private int numberTroops;

	/** Constructor por defecto. */
	public TerritoryDTO() {
	}

	/**
	 * Constructor completo.
	 *
	 * @param name         nombre del territorio
	 * @param color        color asociado
	 * @param numberTroops cantidad de tropas
	 */
	public TerritoryDTO(String name, String color, int numberTroops) {
		this.name = name;
		this.color = color;
		this.numberTroops = numberTroops;
	}

	/**
	 * Obtiene el nombre del territorio.
	 *
	 * @return name
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
	 * Obtiene el color asociado.
	 *
	 * @return color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Establece el color asociado.
	 *
	 * @param color nuevo color
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Obtiene la cantidad de tropas.
	 *
	 * @return numberTroops
	 */
	public int getNumberTroops() {
		return numberTroops;
	}

	/**
	 * Establece la cantidad de tropas.
	 *
	 * @param numberTroops nueva cantidad de tropas
	 */
	public void setNumberTroops(int numberTroops) {
		this.numberTroops = numberTroops;
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, name, numberTroops);
	}

}