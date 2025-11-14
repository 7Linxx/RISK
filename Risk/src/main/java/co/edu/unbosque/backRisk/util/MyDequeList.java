package co.edu.unbosque.backRisk.util;

/**
 * Implementación de una deque (cola doble) basada en una lista doblemente
 * enlazada personalizada. Extiende MyDoubleLinkedList y proporciona operaciones
 * para insertar/extraer en ambos extremos.
 * <p>
 * Esta clase mantiene referencias explícitas a la cola (tail) y al tamaño
 * (size) para optimizar operaciones en el final de la lista.
 * </p>
 * 
 * @param <E> Tipo de elementos almacenados en la estructura.
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public class MyDequeList<E> extends MyDoubleLinkedList<E> implements Dqueue<E> {

	/**
	 * Referencia al último nodo de la deque.
	 */
	private DNode<E> tail;

	/**
	 * Número de elementos almacenados en la deque.
	 */
	private int size;

	/**
	 * Constructor por defecto. Inicializa la estructura con nodos centinela para
	 * head y tail, y tamaño cero.
	 */
	public MyDequeList() {
		head = new DNode<E>();
		tail = new DNode<E>();
		head.setNext(tail);
		tail.setPrevious(head);
		size = 0;
	}

	/**
	 * Inserta un elemento al inicio (frente) de la deque.
	 * 
	 * @param info elemento a insertar al frente.
	 */
	@Override
	public void insertFirst(E info) {
		DNode<E> h = this.head;
		DNode<E> node = new DNode<E>();
		node.setInfo(info);
		node.setNext(h);
		h.setPrevious(node);
		head = node;
		if (size == 0)
			tail = node;
		size++;
	}

	/**
	 * Inserta un elemento al final (cola) de la deque.
	 * 
	 * @param info elemento a insertar al final.
	 */
	@Override
	public void insertLast(E info) {
		DNode<E> t = tail;
		DNode<E> node = new DNode<E>();
		node.setInfo(info);
		node.setPrevious(t);
		t.setNext(node);
		tail = node;
		if (size == 0)
			head = node;
		size++;
	}

	/**
	 * Elimina y retorna el primer elemento (frente) de la deque.
	 * <p>
	 * Si la deque está vacía o head es null, retorna null.
	 * </p>
	 * 
	 * @return el elemento eliminado desde el frente, o null si no existe.
	 */
	@Override
	public E removeFirst() {
		if (head == null)
			return null;
		E val = head.getInfo();
		head = head.getNext();
		size--;
		return val;
	}

	/**
	 * Elimina y retorna el último elemento (final) de la deque.
	 * <p>
	 * Si la deque está vacía o tail es null, retorna null.
	 * </p>
	 * 
	 * @return el elemento eliminado desde el final, o null si no existe.
	 */
	@Override
	public E removeLast() {
		if (tail == null)
			return null;
		E val = tail.getInfo();
		tail = tail.getPrevious();
		size--;
		return val;
	}

	/**
	 * Retorna la cantidad de elementos almacenados en la deque.
	 * 
	 * @return tamaño actual de la deque.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Obtiene la referencia al nodo cabeza (head).
	 * 
	 * @return nodo head.
	 */
	public DNode<E> getHead() {
		return head;
	}

	/**
	 * Establece la referencia al nodo cabeza (head).
	 * 
	 * @param head nodo a asignar como head.
	 */
	public void setHead(DNode<E> head) {
		this.head = head;
	}

	/**
	 * Obtiene la referencia al nodo cola (tail).
	 * 
	 * @return nodo tail.
	 */
	public DNode<E> getTail() {
		return tail;
	}

	/**
	 * Establece la referencia al nodo cola (tail).
	 * 
	 * @param tail nodo a asignar como tail.
	 */
	public void setTail(DNode<E> tail) {
		this.tail = tail;
	}

	/**
	 * Obtiene la posición actual usada por MyDoubleLinkedList (currentPosition).
	 * 
	 * @return nodo en la posición actual.
	 */
	public DNode<E> getCurrentPosition() {
		return currentPosition;
	}

	/**
	 * Establece la posición actual usada por MyDoubleLinkedList (currentPosition).
	 * 
	 * @param currentPosition nodo a asignar como posición actual.
	 */
	public void setCurrentPosition(DNode<E> currentPosition) {
		this.currentPosition = currentPosition;
	}

	/**
	 * Retorna el tamaño interno (misma funcionalidad que size()).
	 * 
	 * @return tamaño interno.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Establece el tamaño interno (se debe usar con precaución para mantener la
	 * integridad de la estructura).
	 * 
	 * @param size nuevo tamaño a asignar.
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Representación en cadena de la deque en formato "head [a-b-...-z] tail".
	 * <p>
	 * Recorre desde head hasta tail usando el contador size para evitar bucles
	 * infinitos en estructuras malformadas.
	 * </p>
	 * 
	 * @return representación en cadena de la deque.
	 */
	@Override
	public String toString() {
		String s = "head [";
		DNode<E> aux = head;
		for (int i = 0; i < size; i++) {
			s += aux.getInfo();
			if (aux == tail) {
				break;
			}
			s += "-";
			aux = aux.getNext();
		}
		return s + "] tail";
	}

}