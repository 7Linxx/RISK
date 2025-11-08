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

@Service
public class UserService implements CRUDOperation<UserDTO> {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ModelMapper modelMapper;

	public UserService() {
	}

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
			Node<User> entity = myEntityList.get(i);
			UserDTO dto = modelMapper.map(entity, UserDTO.class);
			dtoList.add(dto);
		}

		return dtoList;
	}

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

	@Override
	public long count() {
		return userRepo.count();
	}

	@Override
	public boolean exist(Long id) {
		return userRepo.existsById(id);
	}

	public int deleteByUsername(String username) {
		Optional<User> found = userRepo.findByUsername(username);
		if (found.isPresent()) {
			userRepo.delete(found.get());
			return 0;
		} else {
			return 1;
		}
	}

	public UserDTO getById(Long id) {
		Optional<User> found = userRepo.findById(id);
		return found.map(user -> modelMapper.map(user, UserDTO.class)).orElse(null);
	}

	public boolean findUsernameAlreadyTaken(User newUser) {
		return userRepo.findByUsername(newUser.getUsername()).isPresent();
	}

	public int validateCredentials(String username, String password) {
		MyLinkedList<UserDTO> users = getAll();
		for (int i = 0; i < users.size(); i++) {
			Node<UserDTO> u = users.get(i);
			if (u.getInfo().getUsername().equals(username) && u.getInfo().getContrasenia().equals(password)) {
				return 0; // credenciales válidas
			}
		}
		return 1; // credenciales incorrectas
	}
}
