package co.edu.unbosque.backRisk.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.backRisk.model.Jugador;

/**
 * Repositorio para la entidad {@link Jugador}. Extiende {@link JpaRepository}
 * para proporcionar operaciones CRUD y consultas personalizadas sobre la base
 * de datos.
 *
 * <p>
 * Incluye métodos adicionales para buscar jugadores por nombre y eliminar
 * registros utilizando el hashCode.
 * </p>
 *
 * @author Mariana Pineda
 * @version 2.0
 */
public interface JugadorRepository extends JpaRepository<Jugador, Long> {

	/**
	 * Elimina un jugador según su hashCode.
	 *
	 * @param hashCode valor hash asociado al jugador que se desea eliminar
	 */
	public void deleteByHashCode(String hashCode);

	/**
	 * Busca un jugador por su nombre.
	 *
	 * @param nombre nombre del jugador a buscar
	 * @return un {@link Optional} que contiene el jugador si es encontrado
	 */
	public Optional<Jugador> findByName(String nombre);

}
