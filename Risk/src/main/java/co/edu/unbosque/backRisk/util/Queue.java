package co.edu.unbosque.backRisk.util;

/**
 * Interfaz que define las operaciones básicas de una cola genérica (FIFO).
 * <p>
 * Implementaciones de esta interfaz deben garantizar el comportamiento
 * First-In-First-Out: los elementos encolados primero deben ser desencolados
 * primero.
 * </p>
 * 
 * @param <E> Tipo de los elementos almacenados en la cola.
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public interface Queue<E> {

	/**
	 * Inserta un elemento en la cola (al final lógico de la estructura).
	 * 
	 * @param info elemento a encolar.
	 */
	public void enqueue(E info);

	/**
	 * Elimina y retorna el siguiente elemento de la cola siguiendo la política
	 * FIFO.
	 * 
	 * @return elemento desencolado, o null si la cola está vacía.
	 */
	public E dequeue();

	/**
	 * Retorna la cantidad de elementos actualmente almacenados en la cola.
	 * 
	 * @return tamaño de la cola.
	 */
	int size();
}