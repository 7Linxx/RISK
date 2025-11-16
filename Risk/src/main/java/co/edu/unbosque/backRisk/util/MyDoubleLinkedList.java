package co.edu.unbosque.backRisk.util;

import java.util.Iterator;

/**
 * Implementación básica de una lista doblemente enlazada con una posición
 * actual (currentPosition) que permite operaciones de inserción y extracción en
 * la posición actual, así como avanzar o retroceder varias posiciones.
 * <p>
 * Esta estructura mantiene referencias al nodo inicial (head), al nodo actual
 * (currentPosition), al tamaño de la lista (size) y a la posición numérica
 * correspondiente al nodo actual (position). Permite recorrer la lista de forma
 * secuencial hacia adelante o hacia atrás.
 * </p>
 *
 * @param <E> Tipo de elementos almacenados en la lista.
 * 
 * @author Mariana Pineda
 * @version 2.0
 */
public class MyDoubleLinkedList<E> implements Iterable<E> {

	/**
	 * Referencia al primer nodo de la lista. Representa la entrada principal para
	 * recorrer la estructura desde el inicio.
	 */
	protected DNode<E> head;

	/**
	 * Referencia al nodo en la posición "actual". Muchas operaciones usan esta
	 * referencia como punto de inserción/extracción. También se utiliza para
	 * cálculos de desplazamiento dentro de la lista.
	 */
	protected DNode<E> currentPosition;

	/**
	 * Cantidad total de elementos almacenados en la lista.
	 */
	protected int size;

	/**
	 * Índice del nodo donde se encuentra "currentPosition". Comienza en -1 cuando
	 * la lista está vacía o currentPosition no ha sido inicializado.
	 */
	protected int position;

	/**
	 * Constructor por defecto. Inicializa la lista vacía, con tamaño 0 y sin nodo
	 * actual seleccionado.
	 */
	public MyDoubleLinkedList() {
		position = -1;
		size = 0;
	}

	/**
	 * Avanza la posición actual {@code numPositions} pasos hacia adelante.
	 * <p>
	 * Si {@code currentPosition} es {@code null}, se inicializa como {@code head}
	 * antes de comenzar a avanzar, ajustando la cantidad de pasos restantes.
	 * </p>
	 * <p>
	 * Si se alcanza el final de la lista antes de completar los pasos, el avance se
	 * detiene.
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
	 * Retrocede la posición actual {@code numPositions} pasos hacia atrás.
	 * <p>
	 * Si {@code numPositions <= 0}, {@code head == null} o
	 * {@code currentPosition == null}, no realiza ninguna acción.
	 * </p>
	 * <p>
	 * La operación se detiene si se llega al inicio de la lista.
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
	 * Inserta un nuevo elemento inmediatamente después de la posición actual.
	 * <p>
	 * Si {@code currentPosition} es {@code null}, el elemento se inserta al inicio
	 * de la lista, convirtiéndose en el nuevo head.
	 * </p>
	 * <p>
	 * Después de la inserción, el nuevo nodo pasa a ser la posición actual.
	 * </p>
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
	 * Después de la extracción, la posición actual pasa a ser el siguiente nodo. Si
	 * el nodo eliminado era el head, el siguiente nodo se convierte en el nuevo
	 * head.
	 * </p>
	 *
	 * @return la información del nodo extraído, o {@code null} si
	 *         {@code currentPosition} es {@code null}.
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
	 * Representación en cadena de la lista en formato {@code "a <-> b <-> c"}.
	 * <p>
	 * Recorre la lista desde el head concatenando los elementos en orden.
	 * </p>
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

	/**
	 * Obtiene el número de elementos almacenados en la lista.
	 *
	 * @return tamaño de la lista.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Establece el tamaño de la lista.
	 * <p>
	 * Útil en casos específicos donde el tamaño debe ajustarse manualmente.
	 * </p>
	 *
	 * @param size nuevo tamaño de la lista.
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Retorna un iterador que recorre la lista desde el índice 0 hasta el final.
	 * <p>
	 * El iterador utiliza el método {@link #get(int)} para obtener los elementos.
	 * </p>
	 *
	 * @return iterador sobre los elementos de la lista.
	 */
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

	/**
	 * Retorna el elemento almacenado en el índice indicado.
	 * <p>
	 * Ajusta la posición actual desplazándose hacia adelante o atrás mediante
	 * {@code forward()} o {@code back()}, según corresponda.
	 * </p>
	 *
	 * @param index índice del elemento deseado (0 ≤ index < size).
	 * @return contenido del nodo en la posición solicitada.
	 */
	public E get(int index) {
		if (position > index)
			back(Math.abs(position - index));
		else
			forward(Math.abs(position - index));
		return currentPosition.getInfo();
	}
}
