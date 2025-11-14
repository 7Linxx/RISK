package co.edu.unbosque.backRisk.exception;

/**
 * Excepción que indica que un correo electrónico proporcionado no cumple con el
 * formato o las reglas de validación esperadas.
 * <p>
 * Se puede lanzar al validar datos de entrada (por ejemplo en registros o
 * actualizaciones de usuario) cuando el correo no es válido.
 * </p>
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public class InvalidEmailException extends Exception {

	/**
	 * Constructor por defecto que inicializa la excepción con un mensaje
	 * descriptivo en español.
	 */
	public InvalidEmailException() {
		super("El correo es invalido");
	}

}