package co.edu.unbosque.backRisk.exception;

public class InvalidStringException extends Exception {

	public InvalidStringException() {
		super("No se pueden tener caracteres especiales ni numeros en el texto");
	}
}
