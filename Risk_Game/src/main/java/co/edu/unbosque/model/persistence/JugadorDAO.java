package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.Jugador;
import co.edu.unbosque.model.JugadorDTO;
import co.edu.unbosque.util.MyDoubleLinkedList;

public class JugadorDAO implements CRUDOperation<Long, JugadorDTO>{
	
	private MyDoubleLinkedList<JugadorDTO> jugadores;

	@Override
	public void create(JugadorDTO data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int update(Long id, JugadorDTO data) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}
}
