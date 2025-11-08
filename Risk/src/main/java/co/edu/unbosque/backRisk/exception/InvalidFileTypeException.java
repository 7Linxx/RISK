package co.edu.unbosque.backRisk.exception;

public class InvalidFileTypeException extends Exception {

	public InvalidFileTypeException() {
		super("El tipo del archivo no coincide con el ingresado");
	}

}
