package co.edu.unbosque.backRisk.service;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import co.edu.unbosque.backRisk.dto.JugadorDTO;
import co.edu.unbosque.backRisk.model.Jugador;
import co.edu.unbosque.backRisk.repository.JugadorRepository;
import co.edu.unbosque.backRisk.util.MyDoubleLinkedList;

@Service
public class JugadorService implements CRUDOperation<JugadorDTO> {

	private JugadorRepository jugadorRepo;
	private ModelMapper modelMapper;

	public JugadorService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int create(JugadorDTO data) {
		Jugador entity = modelMapper.map(data, Jugador.class);
		jugadorRepo.save(entity);
		return 0; // Creado correctamente
	}

	@Override
	public MyDoubleLinkedList getAll() {
		MyDoubleLinkedList<Jugador> entityList = (MyDoubleLinkedList<Jugador>) jugadorRepo.findAll();

		MyDoubleLinkedList<Jugador> myEntityList = new MyDoubleLinkedList<>();
		for (Jugador e : entityList) {
			myEntityList.insert(e);
		}

		MyDoubleLinkedList<JugadorDTO> dtoList = new MyDoubleLinkedList<>();
		for (int i = 0; i < myEntityList.getSize(); i++) {
			Jugador entity = myEntityList.get(i);
			JugadorDTO jugador = modelMapper.map(entity.getClass(), JugadorDTO.class);
			dtoList.insert(jugador);
		}

		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		Optional<Jugador> found = jugadorRepo.findById(id);
		if (found.isPresent()) {
			jugadorRepo.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int updateById(Long id, JugadorDTO newData) {
		Optional<Jugador> found = jugadorRepo.findById(id);
		Optional<Jugador> newFound = jugadorRepo.findByName(newData.getName());

		if (found.isPresent() && !newFound.isPresent()) {
			Jugador temp = found.get();
			temp.setName(newData.getName());
			temp.setColor(newData.getColor());
			temp.setEmail(newData.getEmail());
			temp.setTropasDisponibles(newData.getTropasDisponibles());
			jugadorRepo.save(temp);
			return 0;
		}
		if (found.isPresent() && newFound.isPresent()) {
			return 1; // Nombre duplicado
		}
		if (!found.isPresent()) {
			return 2; // No encontrado
		} else {
			return 3;
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

}