package co.edu.unbosque.exception;

public class UserNameValidateException extends Exception {

	public UserNameValidateException() {
		super("El nombre de usuario debe tener al menos 3 caracteres y solo puede incluir letras, números, guiones bajos o puntos");
	}

}
