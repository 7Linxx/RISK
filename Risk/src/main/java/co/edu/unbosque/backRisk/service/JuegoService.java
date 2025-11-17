package co.edu.unbosque.backRisk.service;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unbosque.backRisk.dto.JuegoDTO;
import co.edu.unbosque.backRisk.model.Juego;
import co.edu.unbosque.backRisk.repository.JuegoRepository;
import co.edu.unbosque.backRisk.util.MyDoubleLinkedList;

/**
 * Servicio que gestiona las operaciones CRUD para la entidad {@link Juego}. Se
 * encarga de mapear los datos entre {@link JuegoDTO} y {@link Juego}, así como
 * de interactuar con el repositorio correspondiente.
 *
 * <p>
 * Este servicio incluye métodos para crear, obtener, actualizar, eliminar y
 * verificar la existencia de juegos en la base de datos.
 * </p>
 *
 * @author Mariana Pineda
 * @version 2.0
 */
@Service
public class JuegoService implements CRUDOperation<JuegoDTO> {

	@Autowired
	private JuegoRepository juegoRepo;
	private ModelMapper modelMapper;

	/**
	 * Constructor por defecto.
	 */
	public JuegoService() {
		modelMapper = new ModelMapper();
	}

	/**
	 * Crea un nuevo juego en la base de datos a partir del DTO proporcionado.
	 *
	 * @param data DTO del juego a crear
	 * @return 0 si se crea correctamente
	 */
	@Override
	public int create(JuegoDTO data) {
		Juego entity = modelMapper.map(data, Juego.class);
		entity.setId(null); // Forzar ID null para creación
		juegoRepo.save(entity);
		return 0; // Creado correctamente
	}

	/**
	 * Obtiene todos los juegos almacenados en la base de datos y los retorna en una
	 * lista personalizada {@link MyDoubleLinkedList} convertidos a DTO.
	 *
	 * @return lista de juegos en formato DTO
	 */
	@Override
	public MyDoubleLinkedList getAll() {
		List<Juego> entityList = juegoRepo.findAll();

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

	/**
	 * Elimina un juego según su ID.
	 *
	 * @param id identificador del juego a eliminar
	 * @return 0 si se elimina correctamente, 1 si no existe
	 */
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

	/**
	 * Actualiza un juego según su ID siempre y cuando no exista otro con los mismos
	 * detalles.
	 *
	 * @param id      identificador del juego a actualizar
	 * @param newData datos nuevos a aplicar
	 * @return 0 si se actualiza correctamente, 1 si los detalles están duplicados,
	 *         2 si no se encuentra el juego, 3 error general
	 */
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

	/**
	 * Cuenta la cantidad de juegos almacenados.
	 *
	 * @return cantidad total de juegos
	 */
	@Override
	public long count() {
		return juegoRepo.count();
	}

	/**
	 * Verifica si existe un juego con el ID proporcionado.
	 *
	 * @param id identificador del juego
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		return juegoRepo.existsById(id);
	}

	public MyDoubleLinkedList<String> getAllHashCode() {
		MyDoubleLinkedList<String> hashCodes = new MyDoubleLinkedList<>();
		boolean add;
		for (Juego juego : juegoRepo.findAll()) {
			add = true;
			for (String hash : hashCodes) {
				if (hash.equals(juego.getHashCode())) {
					add = false;
					break;
				}
			}
			if (add) {
				hashCodes.insert(juego.getHashCode());
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
			juegoRepo.deleteByHashCode(hashCode);
			return 0;
		}
		return 1;
	}
}
