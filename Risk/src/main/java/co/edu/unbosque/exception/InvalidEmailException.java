package co.edu.unbosque.exception;

public class InvalidEmailException extends Exception {

	public InvalidEmailException() {
		super("El correo es invalido");
	}

}
