package co.edu.unbosque.backRisk.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.backRisk.dto.TerritorioDTO;
import co.edu.unbosque.backRisk.model.Territorio;
import co.edu.unbosque.backRisk.repository.TerritorioRepository;
import co.edu.unbosque.backRisk.util.MyLinkedList;
import co.edu.unbosque.backRisk.util.Node;

@Service
public class TerritorioService implements CRUDOperation<TerritorioDTO> {

	@Autowired
	private TerritorioRepository territorioRepo;

	@Autowired
	private ModelMapper modelMapper;

	public TerritorioService() {
	}

	@Override
	public MyLinkedList<TerritorioDTO> getAll() {
		List<Territorio> entityList = territorioRepo.findAll();

		MyLinkedList<Territorio> myEntityList = new MyLinkedList<>();
		for (Territorio e : entityList) {
			myEntityList.add(e);
		}

		MyLinkedList<TerritorioDTO> dtoList = new MyLinkedList<>();
		for (int i = 0; i < myEntityList.size(); i++) {
			Node<Territorio> entity = myEntityList.get(i);
			TerritorioDTO territorio = modelMapper.map(entity.getInfo(), TerritorioDTO.class);
			dtoList.add(territorio);
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
	public long count() {
		return territorioRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return territorioRepo.existsById(id);
	}

	public Territorio getById(Long id) {
		Optional<Territorio> found = territorioRepo.findById(id);
		return found.orElse(null);
	}

	public boolean findTerritorioAlreadyExists(Territorio newTerritorio) {
		return territorioRepo.findByName(newTerritorio.getNombre()).isPresent();
	}

	@Override
	public int create(TerritorioDTO data) {
		Territorio entity = modelMapper.map(data, Territorio.class);
		if (findTerritorioAlreadyExists(entity)) {
			return 1; // Ya existe
		} else {
			territorioRepo.save(entity);
			return 0; // Creado correctamente
		}
	}

	@Override
	public int updateById(Long id, TerritorioDTO newData) {
		Optional<Territorio> found = territorioRepo.findById(id);
		Optional<Territorio> newFound = territorioRepo.findByName(newData.getNombre());

		if (found.isPresent() && !newFound.isPresent()) {
			Territorio temp = found.get();
			temp.setNombre(newData.getNombre());
			temp.setCantidadTropas(newData.getCantidadTropas());
			temp.setDuenio(newData.getDuenio());
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
}
