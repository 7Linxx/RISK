package co.edu.unbosque.backRisk.exception;

/**
 * Excepción lanzada cuando una contraseña no cumple las reglas mínimas de
 * complejidad definidas por la aplicación.
 * <p>
 * El mensaje por defecto indica que la contraseña debe tener al menos 8
 * caracteres e incluir mayúscula, minúscula, dígito y carácter especial.
 * </p>
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public class PasswordValidateException extends Exception {

	/**
	 * Constructor por defecto que inicializa la excepción con un mensaje
	 * descriptivo en español.
	 */
	public PasswordValidateException() {
		super("La contraseña debe tener mínimo 8 caracteres, incluir mayúscula, minúscula, dígito y carácter especial");
	}

}