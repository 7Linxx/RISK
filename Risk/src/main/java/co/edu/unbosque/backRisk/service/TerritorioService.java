package co.edu.unbosque.backRisk.service;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import co.edu.unbosque.backRisk.dto.TerritorioDTO;
import co.edu.unbosque.backRisk.model.Territorio;
import co.edu.unbosque.backRisk.repository.TerritorioRepository;
import co.edu.unbosque.backRisk.util.MyDoubleLinkedList;

@Service
public class TerritorioService implements CRUDOperation<TerritorioDTO> {

	private TerritorioRepository territorioRepo;
	private ModelMapper modelMapper;

	public TerritorioService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int create(TerritorioDTO data) {
		Territorio entity = modelMapper.map(data, Territorio.class);
		territorioRepo.save(entity);
		return 0; // Creado correctamente
	}

	@Override
	public MyDoubleLinkedList getAll() {
		MyDoubleLinkedList<Territorio> entityList = (MyDoubleLinkedList<Territorio>) territorioRepo.findAll();

		MyDoubleLinkedList<Territorio> myEntityList = new MyDoubleLinkedList<>();
		for (Territorio e : entityList) {
			myEntityList.insert(e);
		}

		MyDoubleLinkedList<TerritorioDTO> dtoList = new MyDoubleLinkedList<>();
		for (int i = 0; i < myEntityList.getSize(); i++) {
			Territorio entity = myEntityList.get(i);
			TerritorioDTO territorio = modelMapper.map(entity.getClass(), TerritorioDTO.class);
			dtoList.insert(territorio);
		}

		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		Optional<Territorio> found = territorioRepo.findById(id);
		if (found.isPresent()) {
			territorioRepo.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public int updateById(Long id, TerritorioDTO newData) {
		Optional<Territorio> found = territorioRepo.findById(id);
		Optional<Territorio> newFound = territorioRepo.findByName(newData.getName());

		if (found.isPresent() && !newFound.isPresent()) {
			Territorio temp = found.get();
			temp.setName(newData.getName());
			temp.setColor(newData.getColor());
			temp.setCantidadTropas(newData.getCantidadTropas());
			temp.setJugador(newData.getJugador());
			territorioRepo.save(temp);
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
		return territorioRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return territorioRepo.existsById(id);
	}
}