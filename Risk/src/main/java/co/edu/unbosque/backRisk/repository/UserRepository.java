package co.edu.unbosque.backRisk.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.backRisk.model.User;

/**
 * Repositorio JPA para la entidad User.
 * <p>
 * Proporciona operaciones CRUD mediante JpaRepository y métodos personalizados
 * para búsquedas y eliminación basadas en el username y la combinación
 * username/contrasenia.
 * </p>
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {
	/**
	 * Busca un usuario por su nombre de usuario y contraseña.
	 * <p>
	 * Útil para validación de credenciales en procesos de autenticación simples.
	 * </p>
	 * 
	 * @param username    nombre de usuario
	 * @param contrasenia contraseña del usuario
	 * @return Optional con el User coincidente, o vacío si no existe.
	 */
	public Optional<User> findByUsernameAndContrasenia(String username, String contrasenia);

	/**
	 * Busca un usuario por su nombre de usuario.
	 * 
	 * @param username nombre de usuario a buscar.
	 * @return Optional con el User si se encuentra, o vacío en caso contrario.
	 */
	public Optional<User> findByUsername(String username);

	/**
	 * Elimina un usuario por su nombre de usuario.
	 * 
	 * @param username nombre de usuario del registro a eliminar.
	 */
	public void deleteByUsername(String username);
}