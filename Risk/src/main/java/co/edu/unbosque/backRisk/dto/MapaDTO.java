package co.edu.unbosque.backRisk.dto;

import co.edu.unbosque.backRisk.model.Territorio;
import co.edu.unbosque.backRisk.util.MyLinkedList;

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
