package co.edu.unbosque.backRisk.exception;

public class PasswordValidateException extends Exception {

	public PasswordValidateException() {
		super("La contraseña debe tener mínimo 8 caracteres, incluir mayúscula, minúscula, dígito y carácter especial");
	}

}
