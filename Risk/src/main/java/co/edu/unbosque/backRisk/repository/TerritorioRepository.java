package co.edu.unbosque.backRisk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.backRisk.model.Territorio;

/**
 * Repositorio JPA para la entidad Territorio.
 * <p>
 * Extiende JpaRepository para operaciones CRUD y añade consultas personalizadas
 * relacionadas con la búsqueda de territorios por nombre.
 * </p>
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public interface TerritorioRepository extends JpaRepository<Territorio, Long> {

	/**
	 * Busca un territorio por su nombre.
	 * 
	 * @param nombre nombre del territorio a buscar.
	 * @return Optional que contiene el Territorio si se encuentra, o vacío en caso
	 *         contrario.
	 */
	public Optional<Territorio> findByName(String nombre);

}