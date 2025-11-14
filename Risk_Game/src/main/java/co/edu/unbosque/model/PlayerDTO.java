package co.edu.unbosque.model;

import java.util.Objects;

import co.edu.unbosque.util.MyDoubleLinkedList;

/**
 * DTO usado para exponer/recibir información básica de un jugador en la API.
 *
 * <p>
 * Incluye datos públicos como nombre, color, correo, contraseña y una lista de
 * nombres de territorios. Proporciona utilidades sencillas para manipular la
 * colección de territorios (comprobar existencia y eliminar).
 * </p>
 *
 * @author Mariana Pineda
 * @since 1.0
 */
public class PlayerDTO {

	/** Nombre del jugador */
	private String name;

	/** Color del jugador */
	private String color;

	/** Correo electrónico del usuario */
	private String email;

	/**
	 * Contraseña del usuario (en contexto real no debería exponerse en texto plano)
	 */
	private String password;

	/** Lista de territorios (por nombre) asociados al jugador */
	private MyDoubleLinkedList<String> territories;

	/**
	 * Constructor por defecto.
	 */
	public PlayerDTO() {
	}

	/**
	 * Constructor principal que inicializa los campos principales y crea la lista
	 * vacía de territorios.
	 *
	 * @param name     nombre del jugador
	 * @param password contraseña del jugador
	 * @param color    color asignado
	 * @param email    correo electrónico
	 */
	public PlayerDTO(String name, String password, String color, String email) {
		this.name = name;
		this.color = color;
		this.password = password;
		this.email = email;
		this.territories = new MyDoubleLinkedList<>();
	}

	/**
	 * Obtiene la contraseña.
	 *
	 * @return contraseña
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Establece la contraseña.
	 *
	 * @param password nueva contraseña
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Obtiene el nombre del jugador.
	 *
	 * @return nombre
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
	 * Obtiene el color del jugador.
	 *
	 * @return color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * Establece el color del jugador.
	 *
	 * @param color nuevo color
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Obtiene la lista de territorios (por nombre).
	 *
	 * @return lista de territorios
	 */
	public MyDoubleLinkedList<String> getTerritories() {
		return territories;
	}

	/**
	 * Establece la lista de territorios.
	 *
	 * @param territories nueva lista de territorios
	 */
	public void setTerritories(MyDoubleLinkedList<String> territories) {
		this.territories = territories;
	}

	/**
	 * Obtiene el correo electrónico.
	 *
	 * @return correo electrónico
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Establece el correo electrónico.
	 *
	 * @param email nuevo correo electrónico
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Comprueba si la lista de territorios contiene el territorio indicado.
	 *
	 * @param territory nombre del territorio a comprobar
	 * @return true si la lista contiene el territorio, false en caso contrario
	 */
	public boolean containsTerritory(String territory) {
		for (String aux : territories) {
			if (aux.equals(territory))
				return true;
		}
		return false;
	}

	/**
	 * Elimina un territorio por su nombre si existe en la lista.
	 *
	 * @param terrytory nombre del territorio a eliminar
	 */
	public void removeTerritory(String terrytory) {
		for (int i = 0; i < territories.getSize(); i++) {
			if (terrytory.equals(territories.get(i))) {
				territories.remove(i);
				return;
			}
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, email, name, territories);
	}

}