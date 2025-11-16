package co.edu.unbosque.backRisk.exception;

/**
 * Excepción que indica que una cadena de texto no cumple las reglas de
 * validación esperadas (por ejemplo, contiene caracteres especiales o dígitos
 * cuando no se permiten).
 * <p>
 * Se utiliza al validar entradas de texto que deben contener solamente letras u
 * otros caracteres permitidos por la aplicación.
 * </p>
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public class InvalidStringException extends Exception {

	/**
	 * Constructor por defecto que inicializa la excepción con un mensaje
	 * descriptivo en español.
	 */
	public InvalidStringException() {
		super("No se pueden tener caracteres especiales ni numeros en el texto");
	}
}