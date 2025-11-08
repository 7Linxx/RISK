package co.edu.unbosque.model.persistence;

import java.util.Objects;

import co.edu.unbosque.model.PlayerDTO;
import co.edu.unbosque.util.MyDoubleLinkedList;

public class PlayerDAO implements CRUDOperation<Integer, PlayerDTO>{
	
	private MyDoubleLinkedList<PlayerDTO> players;
	
	public PlayerDAO() {
		players=new MyDoubleLinkedList<>();
	}

	public PlayerDAO(MyDoubleLinkedList<PlayerDTO> players) {
		this.players = players;
	}

	public MyDoubleLinkedList<PlayerDTO> getPlayers() {
		return players;
	}

	public void setPlayers(MyDoubleLinkedList<PlayerDTO> players) {
		this.players = players;
	}

	@Override
	public void create(PlayerDTO data) {
		players.add(data);
	}

	@Override
	public int update(Integer id, PlayerDTO data) {
		if(id>=0&&id<players.getSize()) {
			players.set(id, data);
			return 0;
		}
		return 1;
	}

	@Override
	public int delete(Integer id) {
		if(id>=0&&id<players.getSize()) {
			players.remove(id);
			return 0;
		}
		return 1;
	}

	@Override
	public int hashCode() {
		return Objects.hash(players);
	}

}
