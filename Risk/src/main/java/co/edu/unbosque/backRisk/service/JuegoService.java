package co.edu.unbosque.backRisk.service;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import co.edu.unbosque.backRisk.dto.JuegoDTO;
import co.edu.unbosque.backRisk.model.Juego;
import co.edu.unbosque.backRisk.repository.JuegoRepository;
import co.edu.unbosque.backRisk.util.MyDoubleLinkedList;

@Service
public class JuegoService implements CRUDOperation<JuegoDTO> {

	private JuegoRepository juegoRepo;
	private ModelMapper modelMapper;

	public JuegoService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int create(JuegoDTO data) {
		Juego entity = modelMapper.map(data, Juego.class);
		juegoRepo.save(entity);
		return 0; // Creado correctamente
	}

	@Override
	public MyDoubleLinkedList getAll() {
		MyDoubleLinkedList<Juego> entityList = (MyDoubleLinkedList<Juego>) juegoRepo.findAll();

		MyDoubleLinkedList<Juego> myEntityList = new MyDoubleLinkedList<>();
		for (Juego e : entityList) {
			myEntityList.insert(e);
		}

		MyDoubleLinkedList<JuegoDTO> dtoList = new MyDoubleLinkedList<>();
		for (int i = 0; i < myEntityList.getSize(); i++) {
			Juego entity = myEntityList.get(i);
			JuegoDTO juego = modelMapper.map(entity.getClass(), JuegoDTO.class);
			dtoList.insert(juego);
		}

		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		Optional<Juego> found = juegoRepo.findById(id);
		if (found.isPresent()) {
			juegoRepo.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int updateById(Long id, JuegoDTO newData) {
		Optional<Juego> found = juegoRepo.findById(id);
		Optional<Juego> newFound = juegoRepo.findByDetalles(newData.getDetalles());

		if (found.isPresent() && !newFound.isPresent()) {
			Juego temp = found.get();
			temp.setDetalles(newData.getDetalles());
			temp.setFase(newData.getFase());
			temp.setTurnoJugador(newData.getTurnoJugador());
			juegoRepo.save(temp);
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
		return juegoRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return juegoRepo.existsById(id);
	}
}