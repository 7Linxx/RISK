package co.edu.unbosque.backRisk.exception;

/**
 * Excepción que indica que no se encontró el archivo esperado.
 * <p>
 * Se lanza cuando una operación requiere la existencia de un archivo y este no
 * está presente o no es accesible.
 * </p>
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public class FileNotFoundException extends Exception {

	/**
	 * Constructor por defecto que inicializa la excepción con un mensaje
	 * descriptivo en español.
	 */
	public FileNotFoundException() {
		super("El archivo no fue encontrado");
	}
}