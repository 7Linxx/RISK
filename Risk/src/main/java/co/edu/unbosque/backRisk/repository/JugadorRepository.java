package co.edu.unbosque.backRisk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import co.edu.unbosque.backRisk.model.Jugador;
import co.edu.unbosque.backRisk.model.Territorio;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {

	public void deleteByHashCode(String hashCode);

	public Optional<Jugador> findByName(String nombre);

}
