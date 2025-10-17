package co.edu.unbosque.exception;

public class FileNotFoundException extends Exception {

	public FileNotFoundException() {
		super("El archivo no fue encontrado");
	}
}
