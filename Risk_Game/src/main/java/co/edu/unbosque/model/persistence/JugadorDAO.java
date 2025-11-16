package co.edu.unbosque.model.persistence;

import java.util.Objects;

import co.edu.unbosque.model.Jugador;
import co.edu.unbosque.util.MyDoubleLinkedList;

public class JugadorDAO implements CRUDOperation<Integer, Jugador> {

	MyDoubleLinkedList<Jugador> jugadores;

	public JugadorDAO() {
		jugadores = new MyDoubleLinkedList<>();
	}

	public JugadorDAO(MyDoubleLinkedList<Jugador> jugadores) {
		super();
		this.jugadores = jugadores;
	}

	public MyDoubleLinkedList<Jugador> getJugadores() {
		return jugadores;
	}

	public void setJugadores(MyDoubleLinkedList<Jugador> jugadores) {
		this.jugadores = jugadores;
	}

	@Override
	public void create(Jugador data) {
		jugadores.add(data);
	}

	@Override
	public int update(Integer id, Jugador data) {
		if (id >= 0 && id < jugadores.getSize()) {
			jugadores.set(id, data);
			return 0;
		}
		return 1;
	}

	@Override
	public int delete(Integer id) {
		if (id >= 0 && id < jugadores.getSize()) {
			jugadores.remove(id);
			return 0;
		}
		return 1;
	}

	@Override
	public int hashCode() {
		return Objects.hash(jugadores);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JugadorDAO other = (JugadorDAO) obj;
		return Objects.equals(jugadores, other.jugadores);
	}

}
