package co.edu.unbosque.model.persistence;

import java.util.Objects;

import co.edu.unbosque.model.Territorio;
import co.edu.unbosque.util.MyMap;

public class TerritorioDAO implements CRUDOperation<String, Territorio> {

	private MyMap<String, Territorio> territorios;

	public TerritorioDAO() {
		territorios = new MyMap<>();
	}

	public MyMap<String, Territorio> getTerritorios() {
		return territorios;
	}

	public void setTerritorios(MyMap<String, Territorio> territorios) {
		this.territorios = territorios;
	}

	@Override
	public void create(Territorio data) {
		territorios.put(data.getNombre(), data);
	}

	@Override
	public int update(String id, Territorio data) {
		if (territorios.containsKey(id)) {
			territorios.replace(id, data);
			return 0;
		}
		return 1;
	}

	@Override
	public int delete(String id) {
		if (territorios.containsKey(id)) {
			territorios.remove(id);
			return 0;
		}
		return 1;
	}

	@Override
	public int hashCode() {
		return Objects.hash(territorios);
	}

}