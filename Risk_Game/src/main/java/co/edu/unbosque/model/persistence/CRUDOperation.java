package co.edu.unbosque.model.persistence;

import java.util.ArrayList;

import co.edu.unbosque.util.MyDoubleLinkedList;

/**
 * Interfaz genérica que define operaciones CRUD y métodos de persistencia
 * esperados por los DAOs de la aplicación.
 *
 * <p>
 * D representa el tipo DTO (Data Transfer Object) manejado por el DAO y E la
 * entidad de dominio correspondiente.
 * </p>
 * 
 * @param <D> Tipo DTO usado para entrada/salida en las operaciones
 * @param <E> Tipo de entidad de dominio gestionada por el DAO
 * 
 * @author Mariana Pineda
 * @since 1.0
 */
public interface CRUDOperation<D, E> {

	/**
	 * Crea un nuevo registro a partir de un DTO.
	 *
	 * @param nuevoDato DTO con los datos a insertar
	 * @return true si se creó correctamente, false si ya existía
	 */
	public boolean crear(D nuevoDato);

	/**
	 * Elimina un registro buscándolo por nombre.
	 *
	 * @param nombre Nombre del elemento a eliminar
	 * @return true si se eliminó, false si no se encontró
	 */
	public boolean eliminarPorNombre(String nombre);

	/**
	 * Devuelve una representación en String de todos los registros.
	 *
	 * @return Cadena con la información de todos los elementos
	 */
	public String mostrarTodo();

	/**
	 * Devuelve todos los elementos en una lista de DTOs.
	 *
	 * @return Lista enlazada con los DTOs de todos los registros
	 */
	public MyDoubleLinkedList<D> getAll();

	/**
	 * Busca una entidad por su nombre.
	 *
	 * @param nombre Nombre a buscar
	 * @return La entidad encontrada o null si no existe
	 */
	public E findByNombre(String nombre);

	/**
	 * Busca un DTO por su ID.
	 *
	 * @param id ID del registro a buscar
	 * @return DTO correspondiente o null si no existe
	 */
	public D buscarPorId(int id);

	/** Escribe todos los registros en un archivo de texto. */
	public void escribirArchivo();

	/** Carga registros desde un archivo de texto. */
	public void cargarDesdeArchivo();

	/** Escribe todos los registros en un archivo serializado (binario). */
	public void escribirArchivoSerializado();

	/** Lee registros desde un archivo serializado (binario). */
	public void leerArchivoSerializado();
}