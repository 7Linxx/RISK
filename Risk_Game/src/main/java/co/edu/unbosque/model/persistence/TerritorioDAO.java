package co.edu.unbosque.model.persistence;

import java.util.Objects;

import co.edu.unbosque.model.TerritorioDTO;
import co.edu.unbosque.util.MyDoubleLinkedList;

public class TerritorioDAO implements CRUDOperation<Integer, TerritorioDTO> {

	private MyDoubleLinkedList<TerritorioDTO> territorios;

	public TerritorioDAO() {
		territorios = new MyDoubleLinkedList<>();
	}

	public TerritorioDAO(MyDoubleLinkedList<TerritorioDTO> territorios) {
		super();
		this.territorios = territorios;
	}

	public MyDoubleLinkedList<TerritorioDTO> getTerritorios() {
		return territorios;
	}

	public void setTerritorios(MyDoubleLinkedList<TerritorioDTO> territorios) {
		this.territorios = territorios;
	}

	@Override
	public void create(TerritorioDTO data) {
		territorios.add(data);

	}

	@Override
	public int update(Integer id, TerritorioDTO data) {
		if (id >= 0 && id < territorios.getSize()) {
			territorios.set(id, data);
			return 0;
		}
		return 1;
	}

	@Override
	public int delete(Integer id) {
		if (id >= 0 && id < territorios.getSize()) {
			territorios.remove(id);
			return 0;
		}
		return 1;
	}

	@Override
	public int hashCode() {
		return Objects.hash(territorios);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TerritorioDAO other = (TerritorioDAO) obj;
		return Objects.equals(territorios, other.territorios);
	}

	
}
