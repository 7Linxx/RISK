package co.edu.unbosque.backRisk.exception;

/**
 * Excepción que indica la presencia de un número negativo donde no está
 * permitido.
 * <p>
 * Se lanza cuando una operación recibe o calcula un valor numérico que debe ser
 * no negativo (por ejemplo conteos, cantidades o índices) y se detecta un valor
 * menor que cero.
 * </p>
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public class NegativeNumberException extends Exception {

	/**
	 * Constructor por defecto que inicializa la excepción con un mensaje
	 * descriptivo en español.
	 */
	public NegativeNumberException() {
		super("No se permiten numeros negativos.");
	}

}