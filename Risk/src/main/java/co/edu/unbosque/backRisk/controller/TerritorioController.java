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
import co.edu.unbosque.backRisk.util.MyDoubleLinkedList;
import co.edu.unbosque.backRisk.util.MyLinkedList;

/**
 * Controlador REST para operaciones relacionadas con la entidad "Territorio".
 *
 * <p>
 * Expone endpoints bajo la ruta /territorio y proporciona operaciones CRUD,
 * además de contadores y comprobación de existencia.
 * </p>
 *
 * @author Mariana Pineda
 * @since 1.0
 */
@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/territorio" })
public class TerritorioController {

	/**
	 * Servicio encargado de la lógica de negocio para territorios. Inyectado por
	 * Spring.
	 */
	@Autowired
	private TerritorioService territorioServ;

	/**
	 * Crea un nuevo territorio.
	 *
	 * <p>
	 * Recibe un objeto TerritorioDTO en el cuerpo de la petición y delega la
	 * creación al servicio. Si el servicio devuelve 0 se considera que la creación
	 * fue exitosa y se responde con HTTP 201 (CREATED). En caso contrario se
	 * responde con HTTP 406 (NOT_ACCEPTABLE).
	 * </p>
	 *
	 * @param territorio DTO con la información del territorio a crear
	 * @return ResponseEntity con un mensaje y el código de estado HTTP
	 *         correspondiente
	 */
	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestBody TerritorioDTO territorio) {
		int estatus = territorioServ.create(territorio);
		if (estatus == 0) {
			return new ResponseEntity<>("Territorio creado con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear el territorio", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	/**
	 * Obtiene todos los territorios almacenados.
	 *
	 * @return ResponseEntity que contiene la lista de TerritorioDTO y el código
	 *         HTTP 200 (OK)
	 */
	@GetMapping(path = "/listar")
	public ResponseEntity<MyDoubleLinkedList<TerritorioDTO>> listar() {
		MyDoubleLinkedList<TerritorioDTO> lista = territorioServ.getAll();
		return new ResponseEntity(lista, HttpStatus.OK);
	}

	/**
	 * Actualiza un territorio existente por su id.
	 *
	 * <p>
	 * Si la actualización se realiza con éxito el servicio devuelve 200 y la
	 * respuesta es HTTP 200 (OK). Si no se encuentra el territorio se devuelve HTTP
	 * 404 (NOT_FOUND).
	 * </p>
	 *
	 * @param id        identificador del territorio a actualizar
	 * @param aveUpdate DTO con los nuevos datos del territorio
	 * @return ResponseEntity con un mensaje y el código de estado HTTP
	 *         correspondiente
	 */
	@PutMapping(path = "/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody TerritorioDTO aveUpdate) {
		int estatus = territorioServ.updateById(id, aveUpdate);

		if (estatus == 200) {
			return new ResponseEntity<>("Territorio actualizado con éxito", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Territorio no encontrado", HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Elimina un territorio por su id.
	 *
	 * <p>
	 * Si la eliminación se realiza con éxito el servicio devuelve 200 y la
	 * respuesta es HTTP 200 (OK). Si no se encuentra el territorio se devuelve HTTP
	 * 404 (NOT_FOUND).
	 * </p>
	 *
	 * @param id identificador del territorio a eliminar
	 * @return ResponseEntity con un mensaje y el código de estado HTTP
	 *         correspondiente
	 */
	@DeleteMapping(path = "/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		int estatus = territorioServ.deleteById(id);

		if (estatus == 200) {
			return new ResponseEntity<>("Territorio eliminado con éxito", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Territorio no encontrado", HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Devuelve la cantidad total de territorios.
	 *
	 * @return ResponseEntity con la cuenta (Long) y el código HTTP 200 (OK)
	 */
	@GetMapping(path = "/count")
	public ResponseEntity<Long> contar() {
		return new ResponseEntity<>(territorioServ.count(), HttpStatus.OK);
	}

	/**
	 * Comprueba si existe un territorio con el id especificado.
	 *
	 * @param id identificador del territorio a comprobar
	 * @return ResponseEntity con true si existe, false en caso contrario, y el
	 *         código HTTP 200 (OK)
	 */
	@GetMapping(path = "/exist/{id}")
	public ResponseEntity<Boolean> existe(@PathVariable Long id) {
		return new ResponseEntity<>(territorioServ.exist(id), HttpStatus.OK);
	}
}