package co.edu.unbosque.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.model.Jugador;
import co.edu.unbosque.repository.JugadorRepository;
import co.edu.unbosque.util.MyLinkedList;
import co.edu.unbosque.util.Node;

@Service
public class JugadorService implements CRUDOperation<Jugador> {

	@Autowired
	private JugadorRepository jugadorRepo;

	@Autowired
	private ModelMapper modelMapper;

	public JugadorService() {
	}

	@Override
	public int create(Jugador data) {
		Jugador entity = modelMapper.map(data, Jugador.class);
		if (findJugadorAlreadyExists(entity)) {
			return 1; // Ya existe
		} else {
			jugadorRepo.save(entity);
			return 0; // Creado correctamente
		}
	}

	@Override
	public MyLinkedList<Jugador> getAll() {
		List<Jugador> entityList = jugadorRepo.findAll();

		MyLinkedList<Jugador> myEntityList = new MyLinkedList<>();
		for (Jugador e : entityList) {
			myEntityList.add(e);
		}

		MyLinkedList<Jugador> dtoList = new MyLinkedList<>();
		for (int i = 0; i < myEntityList.size(); i++) {
			Node<Jugador> entity = myEntityList.get(i);
			Jugador jugador = modelMapper.map(entity.getInfo(), Jugador.class);
			dtoList.add(jugador);
		}

		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		Optional<Jugador> found = jugadorRepo.findById(id);
		if (found.isPresent()) {
			jugadorRepo.delete(found.get());
			return 0; // Eliminado correctamente
		} else {
			return 1; // No encontrado
		}
	}

	@Override
	public int updateById(Long id, Jugador newData) {
		Optional<Jugador> found = jugadorRepo.findById(id);
		Optional<Jugador> newFound = jugadorRepo.findByName(newData.getNombre());

		if (found.isPresent() && !newFound.isPresent()) {
			Jugador temp = found.get();
			temp.setNombre(newData.getNombre());
			temp.setColor(newData.getColor());
			temp.setTerritoriosPertenecientes(newData.getTerritoriosPertenecientes());
			temp.setTropasDisponibles(newData.getTropasDisponibles());
			temp.setUser(newData.getUser());
			jugadorRepo.save(temp);
			return 0;
		}
		if (found.isPresent() && newFound.isPresent()) {
			return 1; // nombre ya existente
		}
		if (!found.isPresent()) {
			return 2; // no encontrado
		} else {
			return 3; // error genérico
		}
	}

	@Override
	public long count() {
		return jugadorRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return jugadorRepo.existsById(id);
	}

	public Jugador getById(Long id) {
		Optional<Jugador> found = jugadorRepo.findById(id);
		return found.orElse(null);
	}

	public boolean findJugadorAlreadyExists(Jugador newJugador) {
		return jugadorRepo.findByName(newJugador.getNombre()).isPresent();
	}
}
