package co.edu.unbosque.backRisk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.backRisk.dto.UserDTO;
import co.edu.unbosque.backRisk.service.UserService;
import co.edu.unbosque.backRisk.util.MyLinkedList;

/**
 * Controlador REST para operaciones relacionadas con la entidad "User".
 *
 * <p>
 * Expone endpoints bajo la ruta /user y proporciona operaciones CRUD, además de
 * contadores y comprobación de existencia.
 * </p>
 * 
 * @author Mariana Pineda
 * @since 1.0
 */
@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/user" })
public class UserController {

	/**
	 * Servicio encargado de la lógica de negocio para usuarios. Inyectado por
	 * Spring.
	 */
	@Autowired
	private UserService userServ;

	/**
	 * Crea un nuevo usuario.
	 *
	 * <p>
	 * Recibe un objeto UserDTO en el cuerpo de la petición y delega la creación al
	 * servicio. Si el servicio devuelve 0 se considera que la creación fue exitosa
	 * y se responde con HTTP 201 (CREATED). En caso contrario se responde con HTTP
	 * 406 (NOT_ACCEPTABLE).
	 * </p>
	 *
	 * @param user DTO con la información del usuario a crear
	 * @return ResponseEntity con un mensaje y el código de estado HTTP
	 *         correspondiente
	 */
	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestBody UserDTO user) {
		int estatus = userServ.create(user);
		if (estatus == 0) {
			return new ResponseEntity<>("User creado con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear el user", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	/**
	 * Obtiene todos los usuarios almacenados.
	 *
	 * @return ResponseEntity que contiene la lista de UserDTO y el código HTTP 200
	 *         (OK)
	 */
	@GetMapping(path = "/listar")
	public ResponseEntity<MyLinkedList<UserDTO>> listar() {
		MyLinkedList<UserDTO> lista = userServ.getAll();
		return new ResponseEntity(lista, HttpStatus.OK);
	}

	/**
	 * Actualiza un usuario existente por su id.
	 *
	 * <p>
	 * Si la actualización se realiza con éxito el servicio devuelve 200 y la
	 * respuesta es HTTP 200 (OK). Si no se encuentra el usuario se devuelve HTTP
	 * 404 (NOT_FOUND).
	 * </p>
	 *
	 * @param id        identificador del usuario a actualizar
	 * @param aveUpdate DTO con los nuevos datos del usuario
	 * @return ResponseEntity con un mensaje y el código de estado HTTP
	 *         correspondiente
	 */
	@PutMapping(path = "/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody UserDTO aveUpdate) {
		int estatus = userServ.updateById(id, aveUpdate);

		if (estatus == 200) {
			return new ResponseEntity<>("User actualizado con éxito", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("User no encontrado", HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Elimina un usuario por su id.
	 *
	 * <p>
	 * Si la eliminación se realiza con éxito el servicio devuelve 200 y la
	 * respuesta es HTTP 200 (OK). Si no se encuentra el usuario se devuelve HTTP
	 * 404 (NOT_FOUND).
	 * </p>
	 *
	 * @param id identificador del usuario a eliminar
	 * @return ResponseEntity con un mensaje y el código de estado HTTP
	 *         correspondiente
	 */
	@DeleteMapping(path = "/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		int estatus = userServ.deleteById(id);

		if (estatus == 200) {
			return new ResponseEntity<>("User eliminado con éxito", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("User no encontrado", HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Devuelve la cantidad total de usuarios.
	 *
	 * @return ResponseEntity con la cuenta (Long) y el código HTTP 200 (OK)
	 */
	@GetMapping(path = "/count")
	public ResponseEntity<Long> contar() {
		return new ResponseEntity<>(userServ.count(), HttpStatus.OK);
	}

	/**
	 * Comprueba si existe un usuario con el id especificado.
	 *
	 * @param id identificador del usuario a comprobar
	 * @return ResponseEntity con true si existe, false en caso contrario, y el
	 *         código HTTP 200 (OK)
	 */
	@GetMapping(path = "/exist/{id}")
	public ResponseEntity<Boolean> existe(@PathVariable Long id) {
		return new ResponseEntity<>(userServ.exist(id), HttpStatus.OK);
	}
}