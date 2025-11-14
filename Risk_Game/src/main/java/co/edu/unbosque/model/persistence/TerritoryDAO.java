package co.edu.unbosque.model.persistence;

import java.util.Objects;

import co.edu.unbosque.model.TerritoryDTO;
import co.edu.unbosque.util.MyMap;

public class TerritoryDAO implements CRUDOperation<String, TerritoryDTO> {

	private MyMap<String, TerritoryDTO> terrritories;

	public TerritoryDAO() {
		terrritories = new MyMap<>();
	}

	public MyMap<String, TerritoryDTO> getTerrritories() {
		return terrritories;
	}

	public void setTerrritories(MyMap<String, TerritoryDTO> terrritories) {
		this.terrritories = terrritories;
	}

	@Override
	public void create(TerritoryDTO data) {
		terrritories.put(data.getName(), data);
	}

	@Override
	public int update(String id, TerritoryDTO data) {
		if (terrritories.containsKey(id)) {
			terrritories.replace(id, data);
			return 0;
		}
		return 1;
	}

	@Override
	public int delete(String id) {
		if (terrritories.containsKey(id)) {
			terrritories.remove(id);
			return 0;
		}
		return 1;
	}

	@Override
	public int hashCode() {
		return Objects.hash(terrritories);
	}

}
