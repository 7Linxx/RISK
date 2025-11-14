package co.edu.unbosque.backRisk.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.backRisk.dto.UserDTO;
import co.edu.unbosque.backRisk.model.User;
import co.edu.unbosque.backRisk.repository.UserRepository;
import co.edu.unbosque.backRisk.util.MyLinkedList;
import co.edu.unbosque.backRisk.util.Node;

/**
 * Servicio que implementa las operaciones CRUD para la entidad User.
 * <p>
 * Actúa como capa de servicio entre el repositorio (persistencia) y la capa de
 * controladores, realizando conversiones entre DTOs y entidades mediante
 * ModelMapper y encapsulando la lógica de negocio relacionada con usuarios.
 * </p>
 * <p>
 * Códigos de retorno utilizados en esta implementación: - create(UserDTO): 0 =
 * creado correctamente, 1 = username ya tomado. - deleteById(Long): 0 =
 * eliminado correctamente, 1 = no encontrado. - updateById(Long, UserDTO): 0 =
 * actualizado correctamente, 1 = username ya existente, 2 = no encontrado, 3 =
 * error genérico.
 * </p>
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
@Service
public class UserService implements CRUDOperation<UserDTO> {

	/**
	 * Repositorio JPA para la entidad User, inyectado por Spring.
	 */
	@Autowired
	private UserRepository userRepo;

	/**
	 * Utilizado para mapear entre DTOs y entidades.
	 */
	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Constructor por defecto.
	 */
	public UserService() {
	}

	/**
	 * Crea un nuevo usuario a partir del DTO proporcionado.
	 * <p>
	 * Retorna 1 si el username ya está tomado, o 0 si se creó con éxito.
	 * </p>
	 * 
	 * @param data DTO con la información del usuario a crear.
	 * @return código de estado (0 = creado, 1 = username ya tomado).
	 */
	@Override
	public int create(UserDTO data) {
		User entity = modelMapper.map(data, User.class);
		if (findUsernameAlreadyTaken(entity)) {
			return 1;
		} else {
			userRepo.save(entity);
			return 0;
		}
	}

	/**
	 * Obtiene todos los usuarios como una lista de UserDTO.
	 * <p>
	 * Recupera todas las entidades desde el repositorio, las convierte a una
	 * MyLinkedList interna y luego mapea cada elemento a UserDTO para devolver la
	 * lista de DTOs.
	 * </p>
	 * 
	 * @return MyLinkedList con todos los UserDTO existentes.
	 */
	@Override
	public MyLinkedList<UserDTO> getAll() {
		// 1. Obtenemos la lista normal del repositorio
		List<User> entityList = userRepo.findAll();

		// 2. Creamos una MyLinkedList de Users
		MyLinkedList<User> myEntityList = new MyLinkedList<>();
		for (User e : entityList) {
			myEntityList.add(e);
		}

		// 3. Convertimos MyLinkedList<User> en MyLinkedList<UserDTO>
		MyLinkedList<UserDTO> dtoList = new MyLinkedList<>();
		for (int i = 0; i < myEntityList.size(); i++) {
			User entity = myEntityList.get(i).getInfo(); // obtiene el objeto User real
			UserDTO dto = modelMapper.map(entity, UserDTO.class);
			dtoList.add(dto);
		}

		return dtoList;
	}

	/**
	 * Elimina un usuario por su identificador.
	 * 
	 * @param id Identificador del usuario a eliminar.
	 * @return 0 si se eliminó correctamente, 1 si no se encontró.
	 */
	@Override
	public int deleteById(Long id) {
		Optional<User> found = userRepo.findById(id);
		if (found.isPresent()) {
			userRepo.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * Actualiza el usuario identificado por id con la información del DTO newData.
	 * <p>
	 * Reglas de negocio en la implementación: - Si el usuario a actualizar existe y
	 * el nuevo username no está en uso por otro usuario, se aplican las
	 * actualizaciones y se guarda la entidad actualizada. - Si el nuevo username ya
	 * está en uso por otro usuario, retorna 1. - Si el usuario a actualizar no
	 * existe, retorna 2. - En cualquier otro caso se retorna 3 indicando un error
	 * genérico.
	 * </p>
	 * 
	 * @param id      Identificador del usuario a actualizar.
	 * @param newData DTO con los nuevos datos del usuario.
	 * @return código de estado: 0 actualizado, 1 username ya existente, 2 no
	 *         encontrado, 3 error genérico.
	 */
	@Override
	public int updateById(Long id, UserDTO newData) {
		Optional<User> found = userRepo.findById(id);
		Optional<User> newFound = userRepo.findByUsername(newData.getUsername());

		if (found.isPresent() && !newFound.isPresent()) {
			User temp = found.get();
			temp.setUsername(newData.getUsername());
			temp.setContrasenia(newData.getContrasenia());
			temp.setCorreo(newData.getCorreo());
			temp.setFechaNacimiento(newData.getFechaNacimiento());
			temp.setNombre(newData.getNombre());
			userRepo.save(temp);
			return 0;
		}
		if (found.isPresent() && newFound.isPresent()) {
			return 1;
		}
		if (!found.isPresent()) {
			return 2;
		} else {
			return 3;
		}
	}

	/**
	 * Retorna el número total de usuarios.
	 * 
	 * @return cantidad de usuarios en la base de datos.
	 */
	@Override
	public long count() {
		return userRepo.count();
	}

	/**
	 * Indica si existe un usuario con el identificador dado.
	 * 
	 * @param id Identificador a verificar.
	 * @return true si existe, false en caso contrario.
	 */
	@Override
	public boolean exist(Long id) {
		return userRepo.existsById(id);
	}

	/**
	 * Elimina un usuario por su username.
	 * 
	 * @param username Nombre de usuario a eliminar.
	 * @return 0 si se eliminó correctamente, 1 si no se encontró.
	 */
	public int deleteByUsername(String username) {
		Optional<User> found = userRepo.findByUsername(username);
		if (found.isPresent()) {
			userRepo.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * Obtiene un UserDTO por el id de la entidad.
	 * 
	 * @param id Identificador del usuario.
	 * @return UserDTO mapeado si existe, o null en caso contrario.
	 */
	public UserDTO getById(Long id) {
		Optional<User> found = userRepo.findById(id);
		return found.map(user -> modelMapper.map(user, UserDTO.class)).orElse(null);
	}

	/**
	 * Verifica si el username del nuevo usuario ya está registrado.
	 * 
	 * @param newUser Entidad User con el username a verificar.
	 * @return true si el username ya está tomado, false en caso contrario.
	 */
	public boolean findUsernameAlreadyTaken(User newUser) {
		return userRepo.findByUsername(newUser.getUsername()).isPresent();
	}

	/**
	 * Valida las credenciales de usuario (username + password).
	 * 
	 * @param username Nombre de usuario
	 * @param password Contraseña
	 * @return 0 si las credenciales son válidas, 1 si no lo son.
	 */
	public int validateCredentials(String username, String password) {
		return userRepo.findByUsernameAndContrasenia(username, password).isPresent() ? 0 : 1;
	}

}