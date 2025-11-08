package co.edu.unbosque.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.model.Territorio;

public interface TerritorioRepository extends JpaRepository<Territorio, Long> {

	public Optional<Territorio> findByName(String nombre);

}
