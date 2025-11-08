package co.edu.unbosque.backRisk.dto;

import co.edu.unbosque.backRisk.model.Territorio;
import co.edu.unbosque.backRisk.model.User;
import co.edu.unbosque.backRisk.util.MyLinkedList;

public class JugadorDTO {

	private Long id;
	private UserDTO userDTO;
	private String name;
	private String color;
	private MyLinkedList<TerritorioDTO> territoriosPertenecientes;
	private int tropasDisponibles;

	public JugadorDTO() {
		// TODO Auto-generated constructor stub
	}

	public JugadorDTO(UserDTO userDTO, String nombre, String color,
			MyLinkedList<TerritorioDTO> territoriosPertenecientes, int tropasDisponibles) {
		super();
		this.userDTO = userDTO;
		this.name = nombre;
		this.color = color;
		this.territoriosPertenecientes = territoriosPertenecientes;
		this.tropasDisponibles = tropasDisponibles;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}

	public String getNombre() {
		return name;
	}

	public void setNombre(String nombre) {
		this.name = nombre;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public MyLinkedList<TerritorioDTO> getTerritoriosPertenecientes() {
		return territoriosPertenecientes;
	}

	public void setTerritoriosPertenecientes(MyLinkedList<TerritorioDTO> territoriosPertenecientes) {
		this.territoriosPertenecientes = territoriosPertenecientes;
	}

	public int getTropasDisponibles() {
		return tropasDisponibles;
	}

	public void setTropasDisponibles(int tropasDisponibles) {
		this.tropasDisponibles = tropasDisponibles;
	}

}
