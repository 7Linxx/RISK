package co.edu.unbosque.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.model.Territorio;
import co.edu.unbosque.repository.TerritorioRepository;
import co.edu.unbosque.util.MyLinkedList;
import co.edu.unbosque.util.Node;

@Service
public class TerritorioService implements CRUDOperation<Territorio> {

	@Autowired
	private TerritorioRepository territorioRepo;

	@Autowired
	private ModelMapper modelMapper;

	public TerritorioService() {
	}

	@Override
	public int create(Territorio data) {
		Territorio entity = modelMapper.map(data, Territorio.class);
		if (findTerritorioAlreadyExists(entity)) {
			return 1; // Ya existe
		} else {
			territorioRepo.save(entity);
			return 0; // Creado correctamente
		}
	}

	@Override
	public MyLinkedList<Territorio> getAll() {
		List<Territorio> entityList = territorioRepo.findAll();

		MyLinkedList<Territorio> myEntityList = new MyLinkedList<>();
		for (Territorio e : entityList) {
			myEntityList.add(e);
		}

		MyLinkedList<Territorio> dtoList = new MyLinkedList<>();
		for (int i = 0; i < myEntityList.size(); i++) {
			Node<Territorio> entity = myEntityList.get(i);
			Territorio territorio = modelMapper.map(entity.getInfo(), Territorio.class);
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
	public int updateById(Long id, Territorio newData) {
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
}
