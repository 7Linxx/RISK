package co.edu.unbosque.model;

import co.edu.unbosque.util.MyLinkedList;

public class Mapa {

	MyLinkedList<Territorio> territorios;

	public Mapa() {
		// TODO Auto-generated constructor stub
	}

	public Mapa(MyLinkedList<Territorio> territorios) {
		super();
		this.territorios = territorios;
	}

	public MyLinkedList<Territorio> getTerritorios() {
		return territorios;
	}

	public void setTerritorios(MyLinkedList<Territorio> territorios) {
		this.territorios = territorios;
	}

}
