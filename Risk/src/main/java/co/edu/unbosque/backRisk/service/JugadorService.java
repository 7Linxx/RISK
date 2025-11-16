package co.edu.unbosque.backRisk.service;

import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unbosque.backRisk.dto.JugadorDTO;
import co.edu.unbosque.backRisk.model.Jugador;
import co.edu.unbosque.backRisk.repository.JugadorRepository;
import co.edu.unbosque.backRisk.util.MyDoubleLinkedList;

@Service
/**
 * Servicio encargado de gestionar las operaciones CRUD para la entidad
 * {@link Jugador}, utilizando {@link JugadorRepository} como capa de acceso a
 * datos y {@link ModelMapper} para la conversión entre entidades y DTOs.
 * <p>
 * Implementa la interfaz {@link CRUDOperation} para estandarizar las
 * operaciones de creación, consulta, actualización y eliminación.
 * </p>
 *
 * @author Mariana Pineda
 * @version 2.0
 */
public class JugadorService implements CRUDOperation<JugadorDTO> {

	/**
	 * Repositorio encargado de la persistencia y consulta de datos de jugadores.
	 */
	@Autowired
	private JugadorRepository jugadorRepo;

	/**
	 * Utilidad para mapear entre clases de entidad y DTO automáticamente.
	 */
	private ModelMapper modelMapper;

	/**
	 * Constructor por defecto requerido por Spring para la inyección de
	 * dependencias.
	 */
	public JugadorService() {
		modelMapper = new ModelMapper();
	}

	/**
	 * Crea un nuevo jugador en la base de datos a partir de un DTO.
	 *
	 * @param data DTO con la información del jugador a registrar.
	 * @return 0 si la creación fue exitosa.
	 */
	@Override
	public int create(JugadorDTO data) {
		Jugador entity = modelMapper.map(data, Jugador.class);
		entity.setId(null); // Forzar ID null para creación
		jugadorRepo.save(entity);
		return 0; // Creado correctamente
	}

	/**
	 * Obtiene todos los registros de jugadores en la base de datos, convirtiéndolos
	 * primero en una lista personalizada de entidades y luego en una lista de DTOs.
	 *
	 * @return lista doblemente enlazada con los DTO de los jugadores almacenados.
	 */
	@Override
	public MyDoubleLinkedList getAll() {
		List<Jugador> entityList = jugadorRepo.findAll();

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

	/**
	 * Elimina un jugador de la base de datos según su ID.
	 *
	 * @param id identificador del jugador a eliminar.
	 * @return 0 si fue eliminado correctamente, 1 si no se encontró.
	 */
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

	/**
	 * Actualiza un jugador existente utilizando su ID y los datos de un DTO.
	 * <p>
	 * También verifica que el nuevo nombre no esté repetido en otro jugador.
	 * </p>
	 *
	 * @param id      ID del jugador a actualizar.
	 * @param newData DTO con la nueva información del jugador.
	 * @return 0 si se actualizó correctamente, 1 si el nombre ya existe, 2 si el
	 *         jugador no fue encontrado, 3 si ocurrió un error inesperado.
	 */
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

	/**
	 * Obtiene la cantidad total de jugadores almacenados en la base de datos.
	 *
	 * @return número total de registros.
	 */
	@Override
	public long count() {
		return jugadorRepo.count();
	}

	/**
	 * Verifica si existe un jugador con el ID proporcionado.
	 *
	 * @param id identificador del jugador.
	 * @return true si existe, false de lo contrario.
	 */
	@Override
	public boolean exist(Long id) {
		return jugadorRepo.existsById(id);
	}

}
