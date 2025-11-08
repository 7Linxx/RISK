package co.edu.unbosque.controller;

import java.util.List;

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

import co.edu.unbosque.dto.UserDTO;
import co.edu.unbosque.service.UserService;
import co.edu.unbosque.util.MyLinkedList;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/user" })
public class UserController {

	@Autowired
	private UserService userServ;

	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestBody UserDTO user) {
		int estatus = userServ.create(user);
		if (estatus == 0) {
			return new ResponseEntity<>("User creado con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear el ave", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	// READ ALL
	@GetMapping(path = "/listar")
	public ResponseEntity<MyLinkedList<UserDTO>> listar() {
		MyLinkedList<UserDTO> lista = userServ.getAll();
		return new ResponseEntity(lista, HttpStatus.OK);
	}

	// UPDATE
	@PutMapping(path = "/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody UserDTO aveUpdate) {
		int estatus = userServ.updateById(id, aveUpdate);

		if (estatus == 200) {
			return new ResponseEntity<>("User actualizado con éxito", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("User no encontrado", HttpStatus.NOT_FOUND);
		}
	}

	// DELETE
	@DeleteMapping(path = "/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		int estatus = userServ.deleteById(id);

		if (estatus == 200) {
			return new ResponseEntity<>("User eliminado con éxito", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("User no encontrado", HttpStatus.NOT_FOUND);
		}
	}

	// COUNT
	@GetMapping(path = "/count")
	public ResponseEntity<Long> contar() {
		return new ResponseEntity<>(userServ.count(), HttpStatus.OK);
	}

	// EXIST
	@GetMapping(path = "/exist/{id}")
	public ResponseEntity<Boolean> existe(@PathVariable Long id) {
		return new ResponseEntity<>(userServ.exist(id), HttpStatus.OK);
	}
}