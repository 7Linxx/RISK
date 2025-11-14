package co.edu.unbosque.backRisk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.backRisk.model.Jugador;

/**
 * Repositorio JPA para la entidad Jugador.
 * <p>
 * Provee operaciones de persistencia básicas (a través de JpaRepository) y
 * consultas personalizadas para localizar o eliminar jugadores por su nombre.
 * </p>
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public interface JugadorRepository extends JpaRepository<Jugador, Long> {

	/**
	 * Busca un jugador por su nombre.
	 * 
	 * @param name nombre del jugador a buscar.
	 * @return Optional que contiene el Jugador si se encuentra, o vacío en caso
	 *         contrario.
	 */
	public Optional<Jugador> findByName(String name);

	/**
	 * Elimina un jugador por su nombre.
	 * 
	 * @param name nombre del jugador a eliminar.
	 */
	public void deleteByName(String name);

}