package co.edu.unbosque.backRisk.util;

/**
 * Interfaz que define las operaciones básicas de una pila genérica (LIFO).
 * <p>
 * Implementaciones de esta interfaz deben garantizar el comportamiento
 * Last-In-First-Out: los elementos añadidos por último son los primeros en
 * obtenerse mediante pop().
 * </p>
 * 
 * @param <E> Tipo de los elementos almacenados en la pila.
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public interface Stack<E> {

	/**
	 * Inserta un elemento en la cima de la pila.
	 * 
	 * @param info elemento a apilar.
	 */
	void push(E info);

	/**
	 * Elimina y retorna el elemento en la cima de la pila.
	 * 
	 * @return elemento desapilado, o null si la pila está vacía.
	 */
	E pop();

	/**
	 * Retorna la cantidad de elementos actualmente almacenados en la pila.
	 * 
	 * @return tamaño de la pila.
	 */
	int size();
}