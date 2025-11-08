package co.edu.unbosque.backRisk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.backRisk.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> findByUsernameAndContrasenia(String username, String contrasenia);

	public Optional<User> findByUsername(String username);

	public void deleteByUsername(String username);
}
