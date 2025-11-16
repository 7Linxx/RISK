package co.edu.unbosque.backRisk.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.backRisk.model.Juego;

/**
 * Repositorio para la entidad {@link Juego}. Extiende {@link JpaRepository}
 * para proporcionar operaciones CRUD y consultas personalizadas sobre la base
 * de datos.
 *
 * <p>
 * Incluye métodos adicionales para buscar juegos por sus detalles y eliminar
 * registros utilizando el hashCode.
 * </p>
 *
 * author Mariana Pineda
 * 
 * @version 2.0
 */
public interface JuegoRepository extends JpaRepository<Juego, Long> {

	/**
	 * Elimina un juego según su hashCode.
	 *
	 * @param hashCode valor hash asociado al juego que se desea eliminar
	 */
	public void deleteByHashCode(String hashCode);

	/**
	 * Busca un juego por sus detalles.
	 *
	 * @param detalles detalles del juego a buscar
	 * @return un {@link Optional} que contiene el juego si es encontrado
	 */
	public Optional<Juego> findByDetalles(String detalles);

}
