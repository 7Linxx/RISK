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

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/jugador" })
public class JugadorController {

	@Autowired
	private JugadorService jugadorServ;

	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestBody JugadorDTO jugador) {
		int estatus = jugadorServ.create(jugador);
		if (estatus == 0) {
			return new ResponseEntity<>("User creado con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear el ave", HttpStatus.NOT_ACCEPTABLE);
		}
	}	
}
