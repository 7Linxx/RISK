package co.edu.unbosque.exception;

public class InvalidStringException extends Exception {

	public InvalidStringException() {
		super("No se pueden tener caracteres especiales ni numeros en el texto");
	}
}
