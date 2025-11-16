package co.edu.unbosque.backRisk.util;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Implementación de una pila (Stack) basada en MyDequeList.
 * <p>
 * Esta implementación aprovecha MyDequeList para insertar y eliminar elementos
 * por el extremo correspondiente, preservando la semántica LIFO.
 * </p>
 * 
 * @param <E> Tipo de los elementos almacenados en la pila.
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public class StackImpl<E> implements Stack<E> {
	/**
	 * Estructura subyacente que soporta las operaciones de la pila.
	 */
	private MyDequeList<E> data;

	/**
	 * Constructor por defecto. Inicializa la estructura interna.
	 * <p>
	 * Nota: hay imports de LinkedList y Queue (posiblemente para pruebas), aunque
	 * no se usan para la lógica de la pila.
	 * </p>
	 */
	public StackImpl() {
		data = new MyDequeList<E>();
		Queue<String> q = new LinkedList<String>();
	}

	/**
	 * Añade un elemento a la cima de la pila.
	 * 
	 * @param info elemento a apilar.
	 */
	@Override
	public void push(E info) {
		data.insertFirst(info);
	}

	/**
	 * Elimina y retorna el elemento en la cima de la pila (LIFO).
	 * 
	 * @return elemento desapilado, o null si la pila está vacía.
	 */
	@Override
	public E pop() {
		return data.removeFirst();
	}

	/**
	 * Retorna el número de elementos en la pila.
	 * 
	 * @return tamaño actual de la pila.
	 */
	@Override
	public int size() {
		return data.size();
	}

	/**
	 * Delegación a la representación en cadena de la estructura interna.
	 * 
	 * @return representación en cadena de la pila.
	 */
	@Override
	public String toString() {
		return data.toString();
	}
}