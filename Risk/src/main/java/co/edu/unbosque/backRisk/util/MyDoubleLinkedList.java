package co.edu.unbosque.backRisk.util;

import java.util.Iterator;

/**
 * Implementación básica de una lista doblemente enlazada con una posición
 * actual (currentPosition) que permite operaciones de inserción y extracción en
 * la posición actual, así como avanzar o retroceder varias posiciones.
 * 
 * @param <E> Tipo de elementos almacenados en la lista.
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public class MyDoubleLinkedList<E> implements Iterable<E> {

	/**
	 * Referencia al primer nodo de la lista.
	 */
	protected DNode<E> head;

	/**
	 * Referencia al nodo en la posición "actual". Muchas operaciones usan esta
	 * referencia como punto de inserción/extracción.
	 */
	protected DNode<E> currentPosition;
	protected int size;
	protected int position;

	/**
	 * Constructor por defecto.
	 */
	public MyDoubleLinkedList() {
		position = -1;
		size = 0;
	}

	/**
	 * Avanza la posición actual numPositions pasos hacia adelante.
	 * <p>
	 * Si currentPosition es null se inicializa en head y se reduce en uno la
	 * cantidad de posiciones a avanzar.
	 * </p>
	 * 
	 * @param numPositions número de posiciones a avanzar (debe ser > 0).
	 */
	public void forward(int numPositions) {
		if (numPositions > 0 && head != null) {
			int positionsForward = numPositions;
			if (currentPosition == null) {
				currentPosition = head;
				positionsForward--;
				position++;
			}
			while (currentPosition.getNext() != null && positionsForward > 0) {
				currentPosition = currentPosition.getNext();
				positionsForward--;
				position++;
			}
		}
	}

	/**
	 * Retrocede la posición actual numPositions pasos hacia atrás.
	 * <p>
	 * Si numPositions <= 0, head es null o currentPosition es null, no realiza
	 * cambios.
	 * </p>
	 * 
	 * @param numPositions número de posiciones a retroceder.
	 */
	public void back(int numPositions) {
		if (numPositions <= 0 || head == null || currentPosition == null)
			return;
		int positionsBack = numPositions;
		while (currentPosition != null && positionsBack > 0) {
			currentPosition = currentPosition.getPrevious();
			positionsBack--;
			position--;
		}
	}

	/**
	 * Inserta un nuevo elemento inmediatamente después de la posición actual. Si
	 * currentPosition es null, inserta al inicio de la lista.
	 * 
	 * @param data elemento a insertar.
	 */
	public void insert(E data) {
		DNode<E> node = new DNode<E>(data);

		if (currentPosition == null) {
			node.setNext(head);
			if (head != null) {
				head.setPrevious(node);
			}
			head = node;
		} else {
			node.setNext(currentPosition.getNext());
			node.setPrevious(currentPosition);
			if (currentPosition.getNext() != null) {
				currentPosition.getNext().setPrevious(node);
			}
			currentPosition.setNext(node);
		}

		currentPosition = node;
		position++;
		size++;
	}

	/**
	 * Extrae (elimina) el nodo en la posición actual y retorna su información.
	 * <p>
	 * Después de la extracción, la posición actual se mueve al siguiente nodo.
	 * </p>
	 * 
	 * @return la información del nodo extraído, o null si currentPosition es null.
	 */
	public E extract() {
		E info = null;

		if (currentPosition != null) {
			info = currentPosition.getInfo();

			if (head == currentPosition) {
				head = currentPosition.getNext();
			} else {
				currentPosition.getPrevious().setNext(currentPosition.getNext());
			}

			if (currentPosition.getNext() != null) {
				currentPosition.getNext().setPrevious(currentPosition.getPrevious());
			}
			size--;
			currentPosition = currentPosition.getNext();
		}
		return info;

	}

	/**
	 * Representación en cadena de la lista en formato "a <-> b <-> c".
	 * 
	 * @return cadena con los elementos de la lista en orden.
	 */
	public String toString() {
		DNode<E> aux = head;
		StringBuilder sb = new StringBuilder();
		while (aux != null) {

			sb.append(aux.getInfo());
			if (aux.getNext() != null) {
				sb.append(" <-> ");
			}
			aux = aux.getNext();
		}
		return sb.toString();
	}

	/**
	 * Obtiene el nodo cabeza (head).
	 * 
	 * @return nodo head.
	 */
	public DNode<E> getHead() {
		return head;
	}

	/**
	 * Establece el nodo cabeza (head).
	 * 
	 * @param head nodo a asignar como head.
	 */
	public void setHead(DNode<E> head) {
		this.head = head;
	}

	/**
	 * Obtiene la posición actual (currentPosition).
	 * 
	 * @return nodo en la posición actual.
	 */
	public DNode<E> getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * Establece la posición actual (currentPosition).
	 * 
	 * @param currentPosition nodo a asignar como posición actual.
	 */
	public void setCurrentPosition(DNode<E> currentPosition) {
		this.currentPosition = currentPosition;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			int index = 0;

			@Override
			public boolean hasNext() {
				return index < size;
			}

			@Override
			public E next() {
				return get(index++);
			}
		};
	}

	public E get(int index) {
		if (position > index)
			back(Math.abs(position - index));
		else
			forward(Math.abs(position - index));
		return currentPosition.getInfo();
	}
}