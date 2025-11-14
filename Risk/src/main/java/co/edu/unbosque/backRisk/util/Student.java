package co.edu.unbosque.backRisk.util;

/**
 * Clase de ejemplo que representa a un estudiante con nombre y semestre.
 * <p>
 * Incluye constructores, getters/setters y una representación en cadena.
 * </p>
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public class Student {

	/**
	 * Nombre del estudiante.
	 */
	private String name;

	/**
	 * Semestre actual del estudiante.
	 */
	private int semester;

	/**
	 * Constructor por defecto.
	 */
	public Student() {
		// Constructor vacío para frameworks/serialización o pruebas
	}

	/**
	 * Constructor que inicializa los campos del estudiante.
	 * 
	 * @param name     Nombre del estudiante.
	 * @param semester Semestre actual.
	 */
	public Student(String name, int semester) {
		super();
		this.name = name;
		this.semester = semester;
	}

	/**
	 * Obtiene el nombre del estudiante.
	 * 
	 * @return nombre del estudiante.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Establece el nombre del estudiante.
	 * 
	 * @param name nombre a asignar.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Obtiene el semestre actual del estudiante.
	 * 
	 * @return semestre actual.
	 */
	public int getSemester() {
		return semester;
	}

	/**
	 * Establece el semestre actual del estudiante.
	 * 
	 * @param semester semestre a asignar.
	 */
	public void setSemester(int semester) {
		this.semester = semester;
	}

	/**
	 * Representación en cadena del estudiante.
	 * 
	 * @return cadena con nombre y semestre.
	 */
	@Override
	public String toString() {
		return "Student [name=" + name + ", semester=" + semester + "]\n";
	}

}