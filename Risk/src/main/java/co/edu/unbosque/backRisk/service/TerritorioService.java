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

/**
 * Servicio que implementa las operaciones CRUD para la entidad Territorio.
 * <p>
 * Actúa como capa de servicio entre el repositorio (persistencia) y la capa de
 * controladores, realizando conversiones entre DTOs y entidades mediante
 * ModelMapper y encapsulando la lógica de negocio relacionada con territorios.
 * </p>
 * <p>
 * Códigos de retorno utilizados en esta implementación: -
 * create(TerritorioDTO): 0 = creado correctamente, 1 = ya existe. -
 * deleteById(Long): 0 = eliminado correctamente, 1 = no encontrado. -
 * updateById(Long, TerritorioDTO): 0 = actualizado correctamente, 1 = nombre
 * duplicado, 2 = no encontrado, 3 = error genérico.
 * </p>
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
@Service
public class TerritorioService implements CRUDOperation<TerritorioDTO> {

	/**
	 * Repositorio JPA para la entidad Territorio, inyectado por Spring.
	 */
	@Autowired
	private TerritorioRepository territorioRepo;

	/**
	 * Utilizado para mapear entre DTOs y entidades.
	 */
	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Constructor por defecto.
	 */
	public TerritorioService() {
	}

	/**
	 * Obtiene todos los territorios como una lista de TerritorioDTO.
	 * <p>
	 * Recupera todas las entidades desde el repositorio, las convierte a una
	 * MyLinkedList interna y luego mapea cada elemento a TerritorioDTO para
	 * devolver la lista de DTOs.
	 * </p>
	 * 
	 * @return MyLinkedList con todos los TerritorioDTO existentes.
	 */
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

	/**
	 * Elimina un territorio por su identificador.
	 * 
	 * @param id Identificador del territorio a eliminar.
	 * @return 0 si se eliminó correctamente, 1 si no se encontró.
	 */
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

	/**
	 * Retorna el número total de territorios.
	 * 
	 * @return cantidad de territorios en la base de datos.
	 */
	@Override
	public long count() {
		return territorioRepo.count();
	}

	/**
	 * Indica si existe un territorio con el identificador dado.
	 * 
	 * @param id Identificador a verificar.
	 * @return true si existe, false en caso contrario.
	 */
	@Override
	public boolean exist(Long id) {
		return territorioRepo.existsById(id);
	}

	/**
	 * Obtiene la entidad Territorio por su id.
	 * 
	 * @param id Identificador del territorio.
	 * @return la entidad Territorio si existe, o null en caso contrario.
	 */
	public Territorio getById(Long id) {
		Optional<Territorio> found = territorioRepo.findById(id);
		return found.orElse(null);
	}

	/**
	 * Verifica si ya existe un territorio con el mismo nombre que newTerritorio.
	 * 
	 * @param newTerritorio Entidad Territorio con el nombre a verificar.
	 * @return true si ya existe un territorio con ese nombre, false en caso
	 *         contrario.
	 */
	public boolean findTerritorioAlreadyExists(Territorio newTerritorio) {
		return territorioRepo.findByName(newTerritorio.getNombre()).isPresent();
	}

	/**
	 * Crea un nuevo territorio a partir del DTO proporcionado.
	 * <p>
	 * Retorna 1 si ya existe un territorio con el mismo nombre, o 0 si se creó con
	 * éxito.
	 * </p>
	 * 
	 * @param data DTO con la información del territorio a crear.
	 * @return código de estado (0 = creado, 1 = ya existe).
	 */
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

	/**
	 * Actualiza el territorio identificado por id con la información del DTO
	 * newData.
	 * <p>
	 * Reglas de negocio en la implementación: - Si el territorio a actualizar
	 * existe y el nuevo nombre no está en uso por otro territorio, se aplican las
	 * actualizaciones y se guarda la entidad actualizada. - Si el nuevo nombre ya
	 * está en uso por otro territorio, retorna 1. - Si el territorio a actualizar
	 * no existe, retorna 2. - En cualquier otro caso se retorna 3 indicando un
	 * error genérico.
	 * </p>
	 * 
	 * @param id      Identificador del territorio a actualizar.
	 * @param newData DTO con los nuevos datos del territorio.
	 * @return código de estado: 0 actualizado, 1 nombre duplicado, 2 no encontrado,
	 *         3 error genérico.
	 */
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