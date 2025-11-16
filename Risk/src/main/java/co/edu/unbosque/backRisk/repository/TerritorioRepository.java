package co.edu.unbosque.backRisk.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.backRisk.model.Territorio;

/**
 * Repositorio para la entidad {@link Territorio}. 
 * Extiende {@link JpaRepository} para proporcionar operaciones CRUD 
 * y métodos de consulta personalizados sobre la base de datos.
 *
 * <p>Incluye métodos adicionales para buscar territorios por nombre 
 * y eliminar por hashCode.</p>
 *
 * @author Mariana Pineda
 * @version 2.0
 */
public interface TerritorioRepository extends JpaRepository<Territorio, Long> {

    /**
     * Elimina un territorio según su hashCode.
     *
     * @param hashCode valor hash asociado al territorio a eliminar
     */
    public void deleteByHashCode(String hashCode);

    /**
     * Busca un territorio por su nombre.
     *
     * @param nombre nombre del territorio a buscar
     * @return un {@link Optional} que contiene el territorio si existe
     */
    public Optional<Territorio> findByName(String nombre);

}
