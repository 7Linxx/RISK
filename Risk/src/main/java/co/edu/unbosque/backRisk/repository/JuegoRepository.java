package co.edu.unbosque.backRisk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.backRisk.model.Juego;
import co.edu.unbosque.backRisk.model.Territorio;
import java.util.List;


public interface JuegoRepository extends JpaRepository<Juego, Long> {

	public void deleteByHashCode(String hashCode);

	public Optional<Juego> findByDetalles(String detalles);;

}
