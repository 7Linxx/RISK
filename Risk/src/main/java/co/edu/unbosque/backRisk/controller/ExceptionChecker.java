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

/**
 * Clase utilitaria que agrupa verificaciones comunes que lanzan excepciones
 * específicas del dominio cuando una validación falla.
 *
 * <p>
 * Cada método es estático y realiza una comprobación concreta (archivo
 * existente, tipo de archivo, formato de cadenas, números negativos, validación
 * de contraseña, validación de nombre de usuario y validación de correo). Si la
 * comprobación falla se lanza la excepción correspondiente definida en el
 * paquete de excepciones.
 * </p>
 *
 * @author Mariana Pineda
 * @since 1.0
 */
public class ExceptionChecker {

	/**
	 * Comprueba que el archivo en la ruta dada exista y sea un fichero regular.
	 *
	 * @param ruta la ruta al archivo a comprobar
	 * @throws FileNotFoundException si el archivo no existe o no es un fichero
	 *                               regular
	 */
	public static void checkFileNotFoundException(String ruta) throws FileNotFoundException {
		File file = new File(ruta);
		if (!file.exists() || !file.isFile()) {
			throw new FileNotFoundException();
		}
	}

	/**
	 * Comprueba que el nombre de archivo tenga una extensión válida (.txt, .dat o
	 * .csv).
	 *
	 * @param NombreArchivo nombre del archivo a validar
	 * @throws InvalidFileTypeException si la extensión del archivo no es válida
	 */
	public static void checkInvalidFileTypeException(String NombreArchivo) throws InvalidFileTypeException {
		if (!(NombreArchivo.endsWith(".txt") || NombreArchivo.endsWith(".dat") || NombreArchivo.endsWith(".csv"))) {
			throw new InvalidFileTypeException();
		}
	}

	/**
	 * Comprueba que la cadena contenga sólo letras (de cualquier idioma) y
	 * espacios.
	 *
	 * @param cadena la cadena a validar
	 * @throws InvalidStringException si la cadena contiene caracteres no permitidos
	 */
	public static void checkInvalidStringException(String cadena) throws InvalidStringException {
		Pattern myPattern = Pattern.compile("[^\\p{L}\\s]");
		Matcher myMatcher = myPattern.matcher(cadena);
		while (myMatcher.find()) {
			throw new InvalidStringException();
		}
	}

	/**
	 * Comprueba que el número no sea negativo.
	 *
	 * @param numero el número a comprobar
	 * @throws NegativeNumberException si el número es menor que cero
	 */
	public static void checkNegativeNumberException(int numero) throws NegativeNumberException {
		if (numero < 0) {
			throw new NegativeNumberException();
		}
	}

	/**
	 * Valida que la contraseña cumpla con los requisitos mínimos: longitud >= 8, al
	 * menos una cifra, una minúscula, una mayúscula y un carácter especial entre @
	 * # $ % ^ & + = *
	 *
	 * @param contrasena la contraseña a validar
	 * @throws PasswordValidateException si la contraseña no cumple el patrón
	 */
	public static void checkPasswordValidateException(String contrasena) throws PasswordValidateException {
		Pattern myPatern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*]).{8,}$");
		Matcher myMatcher = myPatern.matcher(contrasena);
		if (!(myMatcher.find())) {
			throw new PasswordValidateException();
		}
	}

	/**
	 * Valida el nombre de usuario: sólo letras, números, guión bajo y punto, y
	 * longitud mínima 3.
	 *
	 * @param username el nombre de usuario a validar
	 * @throws UserNameValidateException si el nombre de usuario no cumple el patrón
	 */
	public static void checkUserNameValidateException(String username) throws UserNameValidateException {
		Pattern myPattern = Pattern.compile("^[a-zA-Z0-9_.]{3,}$");
		Matcher myMatcher = myPattern.matcher(username);
		if (!(myMatcher.find())) {
			throw new UserNameValidateException();
		}
	}

	/**
	 * Valida el correo electrónico. Solo se permiten dominios: gmail.com,
	 * unbosque.edu.co y hotmail.com.
	 *
	 * @param email el correo a validar
	 * @throws InvalidEmailException si el correo no cumple el patrón o dominio
	 *                               permitido
	 */
	public static void checkInvalidEmailException(String email) throws InvalidEmailException {
		Pattern myPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(gmail\\.com|unbosque\\.edu\\.co|hotmail\\.com)$");
		Matcher myMatcher = myPattern.matcher(email);
		if (!(myMatcher.find())) {
			throw new InvalidEmailException();
		}
	}
}