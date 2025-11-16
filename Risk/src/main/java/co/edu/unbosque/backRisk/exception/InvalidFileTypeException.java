package co.edu.unbosque.backRisk.exception;

/**
 * Excepción que indica que el tipo de archivo recibido no coincide con el tipo
 * esperado o permitido por la operación.
 * <p>
 * Se lanza cuando, por ejemplo, al validar una carga de archivo el tipo MIME o
 * la extensión no corresponde con los formatos aceptados.
 * </p>
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public class InvalidFileTypeException extends Exception {

	/**
	 * Constructor por defecto que inicializa la excepción con un mensaje
	 * descriptivo en español.
	 */
	public InvalidFileTypeException() {
		super("El tipo del archivo no coincide con el ingresado");
	}

}