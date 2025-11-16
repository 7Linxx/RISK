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
import co.edu.unbosque.backRisk.dto.JugadorDTO;
import co.edu.unbosque.backRisk.service.JugadorService;
import co.edu.unbosque.backRisk.util.MyDoubleLinkedList;

/**
 * Controlador REST para operaciones relacionadas con la entidad "Jugador".
 *
 * <p>
 * Expone endpoints bajo la ruta /jugador y proporciona operaciones CRUD, además
 * de contadores y comprobación de existencia.
 * </p>
 *
 * @author Mariana Pineda
 * @since 2.0
 */
@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/jugador" })
public class JugadorController {

	/**
	 * Servicio encargado de la lógica de negocio para jugadores. Inyectado por
	 * Spring.
	 */
	@Autowired
	private JugadorService jugadorServ;

	/**
	 * Crea un nuevo jugador.
	 *
	 * <p>
	 * Recibe un objeto JugadorDTO en el cuerpo de la petición y delega la creación
	 * al servicio. Si el servicio devuelve 0 se considera que la creación fue
	 * exitosa y se responde con HTTP 201 (CREATED). En caso contrario se responde
	 * con HTTP 406 (NOT_ACCEPTABLE).
	 * </p>
	 *
	 * @param jugador DTO con la información del jugador a crear
	 * @return ResponseEntity con un mensaje y el código de estado HTTP
	 *         correspondiente
	 */
	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestBody JugadorDTO jugador) {
		int estatus = jugadorServ.create(jugador);
		if (estatus == 0) {
			return new ResponseEntity<>("Jugador creado con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear el jugador", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	/**
	 * Obtiene todos los jugadores almacenados.
	 *
	 * @return ResponseEntity que contiene la lista de JugadorDTO y el código HTTP
	 *         200 (OK)
	 */
	@GetMapping(path = "/listar")
	public ResponseEntity<MyDoubleLinkedList<JugadorDTO>> listar() {
		MyDoubleLinkedList<JugadorDTO> lista = jugadorServ.getAll();
		return new ResponseEntity(lista, HttpStatus.OK);
	}

	/**
	 * Actualiza un jugador existente por su id.
	 *
	 * <p>
	 * Si la actualización se realiza con éxito el servicio devuelve 200 y la
	 * respuesta es HTTP 200 (OK). Si no se encuentra el jugador se devuelve HTTP
	 * 404 (NOT_FOUND).
	 * </p>
	 *
	 * @param id            identificador del jugador a actualizar
	 * @param jugadorUpdate DTO con los nuevos datos del jugador
	 * @return ResponseEntity con un mensaje y el código de estado HTTP
	 *         correspondiente
	 */
	@PutMapping(path = "/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody JugadorDTO jugadorUpdate) {
		int estatus = jugadorServ.updateById(id, jugadorUpdate);

		if (estatus == 200) {
			return new ResponseEntity<>("Jugador actualizado con éxito", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Jugador no encontrado", HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Elimina un jugador por su id.
	 *
	 * <p>
	 * Si la eliminación se realiza con éxito el servicio devuelve 200 y la
	 * respuesta es HTTP 200 (OK). Si no se encuentra el jugador se devuelve HTTP
	 * 404 (NOT_FOUND).
	 * </p>
	 *
	 * @param id identificador del jugador a eliminar
	 * @return ResponseEntity con un mensaje y el código de estado HTTP
	 *         correspondiente
	 */
	@DeleteMapping(path = "/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		int estatus = jugadorServ.deleteById(id);

		if (estatus == 200) {
			return new ResponseEntity<>("Jugador eliminado con éxito", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Jugador no encontrado", HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Devuelve la cantidad total de jugadores.
	 *
	 * @return ResponseEntity con la cuenta (Long) y el código HTTP 200 (OK)
	 */
	@GetMapping(path = "/count")
	public ResponseEntity<Long> contar() {
		return new ResponseEntity<>(jugadorServ.count(), HttpStatus.OK);
	}

	/**
	 * Comprueba si existe un jugador con el id especificado.
	 *
	 * @param id identificador del jugador a comprobar
	 * @return ResponseEntity con true si existe, false en caso contrario, y el
	 *         código HTTP 200 (OK)
	 */
	@GetMapping(path = "/exist/{id}")
	public ResponseEntity<Boolean> existe(@PathVariable Long id) {
		return new ResponseEntity<>(jugadorServ.exist(id), HttpStatus.OK);
	}
}