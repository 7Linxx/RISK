package co.edu.unbosque.dto;

import co.edu.unbosque.model.Territorio;
import co.edu.unbosque.util.MyLinkedList;

public class MapaDTO {

	MyLinkedList<TerritorioDTO> territorios;

	public MapaDTO() {
		// TODO Auto-generated constructor stub
	}

	public MapaDTO(MyLinkedList<TerritorioDTO> territorios) {
		super();
		this.territorios = territorios;
	}

	public MyLinkedList<TerritorioDTO> getTerritorios() {
		return territorios;
	}

	public void setTerritorios(MyLinkedList<TerritorioDTO> territorios) {
		this.territorios = territorios;
	}

}
