package co.edu.unbosque.backRisk.controller;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import co.edu.unbosque.backRisk.exception.FileNotFoundException;
import co.edu.unbosque.backRisk.exception.InvalidEmailException;
import co.edu.unbosque.backRisk.exception.InvalidFileTypeException;
import co.edu.unbosque.backRisk.exception.InvalidStringException;
import co.edu.unbosque.backRisk.exception.NegativeNumberException;
import co.edu.unbosque.backRisk.exception.PasswordValidateException;
import co.edu.unbosque.backRisk.exception.UserNameValidateException;

public class ExceptionChecker {

	public static void checkFileNotFoundException(String ruta) throws FileNotFoundException {
		File file = new File(ruta);
		if (!file.exists() || !file.isFile()) {
			throw new FileNotFoundException();
		}
	}

	public static void checkInvalidFileTypeException(String NombreArchivo) throws InvalidFileTypeException {
		if (!(NombreArchivo.endsWith(".txt") || NombreArchivo.endsWith(".dat") || NombreArchivo.endsWith(".csv"))) {
			throw new InvalidFileTypeException();
		}
	}

	public static void checkInvalidStringException(String cadena) throws InvalidStringException {
		Pattern myPattern = Pattern.compile("[^\\p{L}\\s]");
		Matcher myMatcher = myPattern.matcher(cadena);
		while (myMatcher.find()) {
			throw new InvalidStringException();
		}
	}

	public static void checkNegativeNumberException(int numero) throws NegativeNumberException {
		if (numero < 0) {
			throw new NegativeNumberException();
		}
	}

	public static void checkPasswordValidateException(String contrasena) throws PasswordValidateException {
		Pattern myPatern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*]).{8,}$");
		Matcher myMatcher = myPatern.matcher(contrasena);
		if (!(myMatcher.find())) {
			throw new PasswordValidateException();
		}
	}

	public static void checkUserNameValidateException(String username) throws UserNameValidateException {
		Pattern myPattern = Pattern.compile("^[a-zA-Z0-9_.]{3,}$");
		Matcher myMatcher = myPattern.matcher(username);
		if (!(myMatcher.find())) {
			throw new UserNameValidateException();
		}
	}

	public static void checkInvalidEmailException(String email) throws InvalidEmailException {
		Pattern myPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(gmail\\.com|unbosque\\.edu\\.co|hotmail\\.com)$");
		Matcher myMatcher = myPattern.matcher(email);
		if (!(myMatcher.find())) {
			throw new InvalidEmailException();
		}
	}
}