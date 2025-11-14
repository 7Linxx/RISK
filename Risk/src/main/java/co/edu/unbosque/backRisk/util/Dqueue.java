package co.edu.unbosque.backRisk.util;

/**
 * Interfaz que define las operaciones básicas para una cola doble (deque)
 * genérica.
 * <p>
 * Provee métodos para insertar en ambos extremos, eliminar desde ambos extremos
 * y consultar el tamaño.
 * </p>
 * 
 * @param <E> Tipo de los elementos almacenados en la cola doble.
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public interface Dqueue<E> {

	/**
	 * Inserta un elemento al final (cola) de la estructura.
	 * 
	 * @param info elemento a insertar al final.
	 */
	public void insertLast(E info);

	/**
	 * Elimina y retorna el primer elemento (frente) de la estructura.
	 * 
	 * @return elemento eliminado desde el frente, o null si la estructura está
	 *         vacía.
	 */
	public E removeFirst();

	/**
	 * Elimina y retorna el último elemento (final) de la estructura.
	 * 
	 * @return elemento eliminado desde el final, o null si la estructura está
	 *         vacía.
	 */
	public E removeLast();

	/**
	 * Retorna la cantidad de elementos actualmente almacenados en la estructura.
	 * 
	 * @return tamaño de la cola doble.
	 */
	public int size();

	/**
	 * Inserta un elemento al comienzo (frente) de la estructura.
	 * 
	 * @param info elemento a insertar al frente.
	 */
	public void insertFirst(E info);
}