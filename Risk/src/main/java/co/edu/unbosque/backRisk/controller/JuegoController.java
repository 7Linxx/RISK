package co.edu.unbosque.backRisk.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

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
import co.edu.unbosque.backRisk.dto.JuegoDTO;
import co.edu.unbosque.backRisk.model.Juego;
import co.edu.unbosque.backRisk.service.JuegoService;
import co.edu.unbosque.backRisk.util.MyDoubleLinkedList;

/**
 * Controlador REST para operaciones relacionadas con la entidad "Juego".
 *
 * <p>
 * Expone endpoints bajo la ruta /juego y proporciona operaciones CRUD, además
 * de contadores y comprobación de existencia.
 * </p>
 *
 * @author Mariana Pineda
 * @since 2.0
 */
@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping(path = { "/juego" })
public class JuegoController {

	/**
	 * Servicio encargado de la lógica de negocio para juegos. Inyectado por Spring.
	 */
	@Autowired
	private JuegoService juegoServ;

	/**
	 * Crea un nuevo juego.
	 *
	 * <p>
	 * Recibe un objeto JuegoDTO en el cuerpo de la petición y delega la creación al
	 * servicio. Si el servicio devuelve 0 se considera que la creación fue exitosa
	 * y se responde con HTTP 201 (CREATED). En caso contrario se responde con HTTP
	 * 406 (NOT_ACCEPTABLE).
	 * </p>
	 *
	 * @param juego DTO con la información del juego a crear
	 * @return ResponseEntity con un mensaje y el código de estado HTTP
	 *         correspondiente
	 */
	@PostMapping(path = "/crear")
	public ResponseEntity<String> crear(@RequestBody JuegoDTO juego) {
		int estatus = juegoServ.create(juego);
		if (estatus == 0) {
			return new ResponseEntity<>("Juego creado con éxito", HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Error al crear el juego", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	/**
	 * Obtiene todos los juegos almacenados.
	 *
	 * @return ResponseEntity que contiene la lista de JuegoDTO y el código HTTP 200
	 *         (OK)
	 */
	@GetMapping(path = "/listar")
	public ResponseEntity<MyDoubleLinkedList<JuegoDTO>> listar() {
		MyDoubleLinkedList<JuegoDTO> lista = juegoServ.getAll();
		return new ResponseEntity(lista, HttpStatus.OK);
	}

	/**
	 * Actualiza un juego existente por su id.
	 *
	 * <p>
	 * Si la actualización se realiza con éxito el servicio devuelve 200 y la
	 * respuesta es HTTP 200 (OK). Si no se encuentra el juego se devuelve HTTP 404
	 * (NOT_FOUND).
	 * </p>
	 *
	 * @param id          identificador del juego a actualizar
	 * @param juegoUpdate DTO con los nuevos datos del juego
	 * @return ResponseEntity con un mensaje y el código de estado HTTP
	 *         correspondiente
	 */
	@PutMapping(path = "/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody JuegoDTO juegoUpdate) {
		int estatus = juegoServ.updateById(id, juegoUpdate);

		if (estatus == 200) {
			return new ResponseEntity<>("Juego actualizado con éxito", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Juego no encontrado", HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Elimina un juego por su id.
	 *
	 * <p>
	 * Si la eliminación se realiza con éxito el servicio devuelve 200 y la
	 * respuesta es HTTP 200 (OK). Si no se encuentra el juego se devuelve HTTP 404
	 * (NOT_FOUND).
	 * </p>
	 *
	 * @param id identificador del juego a eliminar
	 * @return ResponseEntity con un mensaje y el código de estado HTTP
	 *         correspondiente
	 */
	@DeleteMapping(path = "/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		int estatus = juegoServ.deleteById(id);

		if (estatus == 200) {
			return new ResponseEntity<>("Juego eliminado con éxito", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Juego no encontrado", HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Devuelve la cantidad total de juegos.
	 *
	 * @return ResponseEntity con la cuenta (Long) y el código HTTP 200 (OK)
	 */
	@GetMapping(path = "/count")
	public ResponseEntity<Long> contar() {
		return new ResponseEntity<>(juegoServ.count(), HttpStatus.OK);
	}

	/**
	 * Comprueba si existe un juego con el id especificado.
	 *
	 * @param id identificador del juego a comprobar
	 * @return ResponseEntity con true si existe, false en caso contrario, y el
	 *         código HTTP 200 (OK)
	 */
	@GetMapping(path = "/exist/{id}")
	public ResponseEntity<Boolean> existe(@PathVariable Long id) {
		return new ResponseEntity<>(juegoServ.exist(id), HttpStatus.OK);
	}

	@GetMapping(path = "/getbyhash/{hash}")
	public ResponseEntity<String> getByHashCode(@PathVariable String hash) {
		if (!juegoServ.existHashCode(hash)) {
			return new ResponseEntity<String>("Hash not found", HttpStatus.NOT_FOUND);
		}
		MyDoubleLinkedList<Juego> juego = juegoServ.getAll();
		if (juego.getSize() <= 0) {
			return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
		}
		StringBuffer buf = new StringBuffer();
		for (Juego current : juego) {
			if (hash.equals(current.getHashCode())) {
				buf.append(current.getId() + ",");
				buf.append(current.getFase() + ",");
				buf.append(current.getTurnoJugador() + ",");
				buf.append(current.getDetalles());
				break;
			}
		}
		return new ResponseEntity<String>(buf.toString(), HttpStatus.ACCEPTED);
	}

	@GetMapping(path = "/getallhashcode")
	public ResponseEntity<String> getAllHashCode() {
		MyDoubleLinkedList<String> allHashCode = juegoServ.getAllHashCode();
		if (allHashCode.getSize()<=0) {
			return new ResponseEntity<String>("", HttpStatus.NO_CONTENT);
		}
		StringBuffer buf = new StringBuffer();
		for (String hash : allHashCode) {
			buf.append(hash + ",");
		}
		return new ResponseEntity<String>(buf.substring(0, buf.length() - 1), HttpStatus.ACCEPTED);
	}
	
	/**
	 * If the specified hascode exists.
	 * 
	 * @param hash The hascode to verify
	 * @return true if it exists, false if it does not
	 */
	@GetMapping(path = "/existhash/{hash}")
	public ResponseEntity<String> existHash(@PathVariable String hash) {
		return new ResponseEntity<String>(juegoServ.existHashCode(hash) ? "Yes" : "No", HttpStatus.ACCEPTED);
	}

	/**
	 * Generate a hashcode for a game
	 * @param password Password needed to generate the hash
	 * @return Game hashcode
	 */
	@GetMapping(path = "/generatehash/{password}")
	public ResponseEntity<String> generateHash(@PathVariable int password) {
		String texto = Integer.toString(password);
		String hashCode = "";
		do {
			try {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				byte[] hash = digest.digest(texto.getBytes(StandardCharsets.UTF_8));
				String generated=Base64.getUrlEncoder().encodeToString(hash);
				if(generated.length()>=12) {
					hashCode=generated.substring(0,12);
				}
			} catch (NoSuchAlgorithmException e) {
				hashCode = "AE3456";
			}
			if (juegoServ.existHashCode(hashCode)) {
				SecureRandom sr=new SecureRandom();
				texto=sr.nextInt(10)+texto;
			}
		} while (juegoServ.existHashCode(hashCode));
		return new ResponseEntity<String>(hashCode,HttpStatus.OK);
	}

	@DeleteMapping(path = "/deletebyhash/{hash}")
	public ResponseEntity<String> deleteByHashCode(@PathVariable String hash) {
		int status = juegoServ.deleteByHashCode(hash);
		if (status == 1) {
			return new ResponseEntity<String>("Hash not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("Delete successfully.", HttpStatus.ACCEPTED);
	}
}