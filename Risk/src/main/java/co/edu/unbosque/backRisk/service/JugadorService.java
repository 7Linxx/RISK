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

/**
 * Servicio que implementa las operaciones CRUD para la entidad Jugador.
 * <p>
 * Esta clase actúa como capa de servicio entre el repositorio (persistencia) y
 * la capa de controladores, realizando conversiones entre DTOs y entidades
 * mediante ModelMapper y encapsulando la lógica de negocio relacionada con
 * jugadores.
 * </p>
 * 
 * Comportamiento de los códigos de retorno usados en esta implementación: -
 * create(JugadorDTO): 0 = creado correctamente, 1 = ya existe. -
 * deleteById(Long): 0 = eliminado correctamente, 1 = no encontrado. -
 * updateById(Long, JugadorDTO): 0 = actualizado correctamente, 1 = nombre ya
 * existente, 2 = no encontrado, 3 = error genérico.
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
@Service
public class JugadorService implements CRUDOperation<JugadorDTO> {

	/**
	 * Repositorio JPA para la entidad Jugador, inyectado por Spring.
	 */
	@Autowired
	private JugadorRepository jugadorRepo;

	/**
	 * Utilizado para mapear entre DTOs y entidades.
	 */
	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Constructor por defecto.
	 */
	public JugadorService() {
	}

	/**
	 * Crea un nuevo jugador a partir del DTO proporcionado.
	 * <p>
	 * Verifica si ya existe un jugador con el mismo nombre mediante
	 * findJugadorAlreadyExists. Retorna 1 si ya existe, o 0 si se creó con éxito.
	 * </p>
	 * 
	 * @param data DTO con la información del jugador a crear.
	 * @return código de estado (0 = creado, 1 = ya existe).
	 */
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

	/**
	 * Obtiene todos los jugadores como una lista de JugadorDTO.
	 * <p>
	 * Recupera todas las entidades desde el repositorio, las convierte a una
	 * MyLinkedList interna y luego mapea cada elemento a JugadorDTO para devolver
	 * la lista de DTOs.
	 * </p>
	 * 
	 * @return MyLinkedList con todos los JugadorDTO existentes.
	 */
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

	/**
	 * Elimina un jugador por su identificador.
	 * 
	 * @param id Identificador del jugador a eliminar.
	 * @return 0 si se eliminó correctamente, 1 si no se encontró.
	 */
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

	/**
	 * Retorna el número total de jugadores.
	 * 
	 * @return cantidad de jugadores en la base de datos.
	 */
	@Override
	public long count() {
		return jugadorRepo.count();
	}

	/**
	 * Indica si existe un jugador con el identificador dado.
	 * 
	 * @param id Identificador a verificar.
	 * @return true si existe, false en caso contrario.
	 */
	@Override
	public boolean exist(Long id) {
		return jugadorRepo.existsById(id);
	}

	/**
	 * Obtiene la entidad Jugador por su id.
	 * 
	 * @param id Identificador del jugador.
	 * @return la entidad Jugador si existe, o null en caso contrario.
	 */
	public Jugador getById(Long id) {
		Optional<Jugador> found = jugadorRepo.findById(id);
		return found.orElse(null);
	}

	/**
	 * Verifica si ya existe un jugador con el mismo nombre que newJugador.
	 * 
	 * @param newJugador Entidad Jugador con el nombre a verificar.
	 * @return true si ya existe un jugador con ese nombre, false en caso contrario.
	 */
	public boolean findJugadorAlreadyExists(Jugador newJugador) {
		return jugadorRepo.findByName(newJugador.getNombre()).isPresent();
	}

	/**
	 * Actualiza el jugador identificado por id con la información del DTO newData.
	 * <p>
	 * Reglas de negocio en la implementación: - Si el jugador a actualizar existe y
	 * el nuevo nombre no está en uso por otro jugador, se aplican las
	 * actualizaciones (incluyendo mapear los territorios desde DTO a entidad) y se
	 * guarda la entidad actualizada. - Si el nuevo nombre ya está en uso por otro
	 * jugador, retorna 1. - Si el jugador a actualizar no existe, retorna 2. - En
	 * cualquier otro caso se retorna 3 indicando un error genérico.
	 * </p>
	 * 
	 * @param id      Identificador del jugador a actualizar.
	 * @param newData DTO con los nuevos datos del jugador.
	 * @return código de estado: 0 actualizado, 1 nombre ya existente, 2 no
	 *         encontrado, 3 error genérico.
	 */
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