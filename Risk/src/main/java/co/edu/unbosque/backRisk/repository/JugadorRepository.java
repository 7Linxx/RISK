package co.edu.unbosque.backRisk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.backRisk.model.Jugador;

public interface JugadorRepository extends JpaRepository<Jugador, Long> {

	
	public Optional<Jugador> findByName(String name);

	public void deleteByName(String name);

}
