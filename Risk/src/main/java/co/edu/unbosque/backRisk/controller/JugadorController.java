package co.edu.unbosque.backRisk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.backRisk.dto.JugadorDTO;
import co.edu.unbosque.backRisk.service.JugadorService;

/**
 * Controlador REST para operaciones relacionadas con la entidad "Jugador".
 *
 * <p>
 * Expone endpoints bajo la ruta /jugador y actualmente proporciona la operación
 * de creación de jugadores.
 * </p>
 *
 * @author Mariana Pineda
 * @since 1.0
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
}