package co.edu.unbosque.backRisk.service;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unbosque.backRisk.dto.TerritorioDTO;
import co.edu.unbosque.backRisk.model.Territorio;
import co.edu.unbosque.backRisk.model.Territorio;
import co.edu.unbosque.backRisk.repository.TerritorioRepository;
import co.edu.unbosque.backRisk.util.MyDoubleLinkedList;

/**
 * Servicio encargado de gestionar las operaciones CRUD del modelo
 * {@link Territorio}, utilizando el repositorio {@link TerritorioRepository} y
 * mapeando entidades a DTOs mediante {@link ModelMapper}.
 * <p>
 * Implementa la interfaz {@link CRUDOperation} para estandarizar las
 * operaciones de creación, consulta, actualización y eliminación.
 * </p>
 *
 * @author Mariana Pineda
 * @version 2.0
 */
@Service
public class TerritorioService implements CRUDOperation<TerritorioDTO> {

	/**
	 * Repositorio encargado de la persistencia de entidades {@link Territorio}.
	 */
	@Autowired
	private TerritorioRepository territorioRepo;

	/**
	 * Utilidad para mapear entre entidades y DTOs automáticamente.
	 */
	private ModelMapper modelMapper;

	/**
	 * Constructor por defecto requerido por Spring para la inyección de
	 * dependencias.
	 */
	public TerritorioService() {
		modelMapper = new ModelMapper();
	}

	/**
	 * Crea un nuevo territorio a partir de un DTO y lo almacena en la base de
	 * datos.
	 *
	 * @param data DTO con la información del nuevo territorio.
	 * @return 0 si la creación fue exitosa.
	 */
	@Override
	public int create(TerritorioDTO data) {
		Territorio entity = modelMapper.map(data, Territorio.class);
		entity.setId(null); // Forzar ID null para creación
		territorioRepo.save(entity);
		return 0; // Creado correctamente
	}

	/**
	 * Obtiene todos los territorios almacenados y los convierte a una
	 * {@link MyDoubleLinkedList} de {@link TerritorioDTO}.
	 * <p>
	 * Primero se transforma la lista de entidades del repositorio a una lista
	 * personalizada, y luego se mapean cada una de las entidades a un DTO.
	 * </p>
	 *
	 * @return lista doblemente enlazada con los DTO de los territorios.
	 */
	@Override
	public MyDoubleLinkedList getAll() {
		List<Territorio> entityList = territorioRepo.findAll();

		MyDoubleLinkedList<Territorio> myEntityList = new MyDoubleLinkedList<>();
		for (Territorio e : entityList) {
			myEntityList.insert(e);
		}

		MyDoubleLinkedList<TerritorioDTO> dtoList = new MyDoubleLinkedList<>();
		for (int i = 0; i < myEntityList.getSize(); i++) {
			Territorio entity = myEntityList.get(i);
			TerritorioDTO territorio = modelMapper.map(entity, TerritorioDTO.class);
			dtoList.insert(territorio);
		}

		return dtoList;
	}

	/**
	 * Elimina un territorio según su ID si existe en la base de datos.
	 *
	 * @param id identificador del territorio a eliminar.
	 * @return 0 si fue eliminado, 1 si no se encontró.
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
	 * Actualiza un territorio existente utilizando su ID y los datos de un DTO.
	 * <p>
	 * También valida que el nuevo nombre no esté repetido en otro territorio.
	 * </p>
	 *
	 * @param id      ID del territorio a actualizar.
	 * @param newData DTO con la información actualizada.
	 * @return 0 si se actualizó correctamente, 1 si el nombre ya existía, 2 si no
	 *         se encontró el territorio, 3 si ocurrió un error inesperado.
	 */
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

	/**
	 * Retorna la cantidad total de territorios registrados en la base de datos.
	 *
	 * @return número total de registros.
	 */
	@Override
	public long count() {
		return territorioRepo.count();
	}

	/**
	 * Verifica si existe un territorio con el ID indicado.
	 *
	 * @param id identificador del territorio.
	 * @return true si existe, false de lo contrario.
	 */
	@Override
	public boolean exist(Long id) {
		return territorioRepo.existsById(id);
	}

	public MyDoubleLinkedList<String> getAllHashCode() {
		MyDoubleLinkedList<String> hashCodes = new MyDoubleLinkedList<>();
		boolean add;
		for (Territorio territorio : territorioRepo.findAll()) {
			add = true;
			for (String hash : hashCodes) {
				if (hash.equals(territorio.getHashCode())) {
					add = false;
					break;
				}
			}
			if (add) {
				hashCodes.insert(territorio.getHashCode());
			}
		}
		return hashCodes;
	}

	public boolean existHashCode(String searchHash) {
		for (String hash : getAllHashCode()) {
			if (hash.equals(searchHash))
				return true;
		}
		return false;
	}

	public int deleteByHashCode(String hashCode) {
		if (existHashCode(hashCode)) {
			territorioRepo.deleteByHashCode(hashCode);
			return 0;
		}
		return 1;
	}
}
