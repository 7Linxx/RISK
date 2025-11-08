package co.edu.unbosque.backRisk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.backRisk.dto.JugadorDTO;
import co.edu.unbosque.backRisk.model.Jugador;
import co.edu.unbosque.backRisk.model.Territorio;
import co.edu.unbosque.backRisk.model.User;
import co.edu.unbosque.backRisk.repository.JugadorRepository;
import co.edu.unbosque.backRisk.util.MyLinkedList;
import co.edu.unbosque.backRisk.util.Node;

@Service
public class JugadorService implements CRUDOperation<JugadorDTO> {

	@Autowired
	private JugadorRepository jugadorRepo;

	@Autowired
	private ModelMapper modelMapper;

	public JugadorService() {
	}

	@Override
	public int create(JugadorDTO data) {
		Jugador entity = modelMapper.map(data, Jugador.class);
		if (findJugadorAlreadyExists(entity)) {
			return 1; // Ya existe
		} else {
			jugadorRepo.save(entity);
			return 0; // Creado correctamente
		}
	}

	@Override
	public MyLinkedList<JugadorDTO> getAll() {
		List<Jugador> entityList = jugadorRepo.findAll();

		MyLinkedList<Jugador> myEntityList = new MyLinkedList<>();
		for (Jugador e : entityList) {
			myEntityList.add(e);
		}

		MyLinkedList<JugadorDTO> dtoList = new MyLinkedList<>();
		for (int i = 0; i < myEntityList.size(); i++) {
			Node<Jugador> entity = myEntityList.get(i);
			JugadorDTO jugador = modelMapper.map(entity.getInfo(), JugadorDTO.class);
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

	@Override
	public int updateById(Long id, JugadorDTO newData) {
		Optional<Jugador> found = jugadorRepo.findById(id);
		Optional<Jugador> newFound = jugadorRepo.findByName(newData.getNombre());

		if (found.isPresent() && !newFound.isPresent()) {
			Jugador temp = found.get();
			temp.setNombre(newData.getNombre());
			temp.setColor(newData.getColor());
			List<Territorio> newTerritorio = new ArrayList<>();
			for (int i = 0; i < newData.getTerritoriosPertenecientes().size(); i++) {
				newTerritorio.add(modelMapper.map(newData.getTerritoriosPertenecientes().get(i), Territorio.class));
			}
			temp.setTerritoriosPertenecientes(newTerritorio);
			temp.setTropasDisponibles(newData.getTropasDisponibles());
			User newUser = modelMapper.map(newData.getUserDTO(), User.class);
			temp.setUser(newUser);
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
}
