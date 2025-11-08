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

import co.edu.unbosque.backRisk.dto.TerritorioDTO;
import co.edu.unbosque.backRisk.service.TerritorioService;
import co.edu.unbosque.backRisk.util.MyLinkedList;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/territorio" })
public class TerritorioController {

	@Autowired
	private TerritorioService territorioServ;

	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestBody TerritorioDTO territorio) {
		int estatus = territorioServ.create(territorio);
		if (estatus == 0) {
			return new ResponseEntity<>("User creado con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear el ave", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	// READ ALL
	@GetMapping(path = "/listar")
	public ResponseEntity<MyLinkedList<TerritorioDTO>> listar() {
		MyLinkedList<TerritorioDTO> lista = territorioServ.getAll();
		return new ResponseEntity(lista, HttpStatus.OK);
	}

	// UPDATE
	@PutMapping(path = "/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody TerritorioDTO aveUpdate) {
		int estatus = territorioServ.updateById(id, aveUpdate);

		if (estatus == 200) {
			return new ResponseEntity<>("Territorio actualizado con éxito", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Territorio no encontrado", HttpStatus.NOT_FOUND);
		}
	}

	// DELETE
	@DeleteMapping(path = "/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		int estatus = territorioServ.deleteById(id);

		if (estatus == 200) {
			return new ResponseEntity<>("Territorio eliminado con éxito", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Territorio no encontrado", HttpStatus.NOT_FOUND);
		}
	}

	// COUNT
	@GetMapping(path = "/count")
	public ResponseEntity<Long> contar() {
		return new ResponseEntity<>(territorioServ.count(), HttpStatus.OK);
	}

	// EXIST
	@GetMapping(path = "/exist/{id}")
	public ResponseEntity<Boolean> existe(@PathVariable Long id) {
		return new ResponseEntity<>(territorioServ.exist(id), HttpStatus.OK);
	}
}
