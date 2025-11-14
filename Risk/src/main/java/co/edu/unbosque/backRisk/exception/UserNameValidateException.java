package co.edu.unbosque.backRisk.exception;

/**
 * Excepción que indica que el nombre de usuario no cumple las reglas de
 * validación establecidas.
 * <p>
 * El mensaje por defecto refleja que el username debe tener al menos 3
 * caracteres y solo puede incluir letras, números, guiones bajos o puntos.
 * </p>
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public class UserNameValidateException extends Exception {

	/**
	 * Constructor por defecto que inicializa la excepción con un mensaje
	 * descriptivo en español.
	 */
	public UserNameValidateException() {
		super("El nombre de usuario debe tener al menos 3 caracteres y solo puede incluir letras, números, guiones bajos o puntos");
	}

}