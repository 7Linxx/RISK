package co.edu.unbosque.backRisk.util;

/**
 * Implementación simple de una lista enlazada simples genérica.
 * <p>
 * Proporciona operaciones básicas: agregar al inicio o final, insertar después
 * de un nodo, extraer primero o último, obtener tamaño, buscar por valor o por
 * índice, contar ocurrencias y obtener una representación en cadena.
 * </p>
 * 
 * @param <E> Tipo de los elementos almacenados en la lista.
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public class MyLinkedList<E> {

	/**
	 * Referencia al primer nodo de la lista (cabeza).
	 */
	protected Node<E> first;

	/**
	 * Constructor por defecto. Inicializa la lista vacía.
	 */
	public MyLinkedList() {
		this.first = null;
	}

	/**
	 * Obtiene el primer nodo de la lista.
	 * 
	 * @return el primer nodo, o null si la lista está vacía.
	 */
	public Node<E> getFirst() {
		return this.first;
	}

	/**
	 * Establece el primer nodo de la lista.
	 * 
	 * @param first nodo a asignar como cabeza.
	 */
	public void setFirst(Node<E> first) {
		this.first = first;
	}

	/**
	 * Indica si la lista está vacía.
	 * 
	 * @return true si no tiene nodos, false en caso contrario.
	 */
	public boolean isEmpty() {
		return (this.first == null);
	}

	/**
	 * Agrega un elemento al inicio de la lista.
	 * 
	 * @param info elemento a agregar.
	 */
	public void add(E info) {
		Node<E> newNode = new Node<E>(info);
		newNode.setNext(this.first);
		first = newNode;
	}

	/**
	 * Inserta un elemento inmediatamente después del nodo dado (previous).
	 * <p>
	 * Si previous es null no realiza ninguna acción.
	 * </p>
	 * 
	 * @param info     elemento a insertar.
	 * @param previous nodo después del cual se insertará el nuevo nodo.
	 */
	public void insert(E info, Node<E> previous) {
		if (previous != null) {
			Node<E> newNode = new Node<E>(info);
			newNode.setNext(previous.getNext());
			previous.setNext(newNode);
		}
	}

	/**
	 * Agrega un elemento al final de la lista.
	 * 
	 * @param info elemento a agregar al último.
	 */
	public void addLast(E info) {
		Node<E> lastNode = getLastNode();

		if (lastNode != null) {
			insert(info, lastNode);
		} else {
			this.first = new Node<E>(info);

		}
	}

	/**
	 * Extrae (elimina) y retorna el primer elemento de la lista.
	 * 
	 * @return el dato extraído, o null si la lista está vacía.
	 */
	public E extract() {
		E data = null;
		if (this.first != null) {
			data = this.first.getInfo();
			this.first = this.first.getNext();
		}
		return data;
	}

	/**
	 * Extrae (elimina) y retorna el elemento que está después del nodo previous.
	 * <p>
	 * Si previous es null o previous.next es null, retorna null.
	 * </p>
	 * 
	 * @param previous nodo anterior al que se eliminará su siguiente.
	 * @return dato extraído, o null si no es posible extraer.
	 */
	public E extract(Node<E> previous) {
		E data = null;
		if (previous != null && previous.getNext() != null) {
			data = previous.getNext().getInfo();
			previous.setNext(previous.getNext().getNext());
		}
		return data;
	}

	/**
	 * Calcula el número de elementos actuales en la lista.
	 * 
	 * @return tamaño de la lista.
	 */
	public int size() {
		int size = 0;
		Node<E> current = this.first;

		while (current != null) {
			size++;
			current = current.getNext();
		}
		return size;
	}

	/**
	 * Alias de toString(), mantiene compatibilidad con usos anteriores.
	 * 
	 * @return representación en cadena de la lista.
	 */
	public String print() {
		return this.toString();
	}

	/**
	 * Busca y devuelve el nodo que contiene el elemento info.
	 * 
	 * @param info elemento a buscar.
	 * @return nodo que contiene info, o null si no se encuentra.
	 */
	public Node<E> get(E info) {
		Node<E> targetNode = null;
		Node<E> currentNode = this.first;

		while (currentNode != null && !currentNode.getInfo().equals(info)) {
			currentNode = currentNode.getNext();
		}
		if (currentNode != null) {
			targetNode = currentNode;
		}
		return targetNode;
	}

	/**
	 * Obtiene el nodo en la posición n (0-based).
	 * 
	 * @param n índice del nodo buscado.
	 * @return nodo en la posición n, o null si no existe.
	 */
	public Node<E> get(int n) {
		Node<E> targetNode = null;
		Node<E> currentNode = this.first;
		int counter = 0;

		while (currentNode != null && counter < n) {
			currentNode = currentNode.getNext();
			counter++;
		}
		if (currentNode != null) {
			targetNode = currentNode;
		}
		return targetNode;
	}

	/**
	 * Obtiene el último nodo de la lista.
	 * 
	 * @return último nodo, o null si la lista está vacía.
	 */
	public Node<E> getLastNode() {
		Node<E> current = this.first;

		while (current != null && current.getNext() != null) {
			current = current.getNext();
		}
		return current;
	}

	/**
	 * Devuelve el índice (0-based) de la primera aparición de info en la lista.
	 * <p>
	 * Si la lista está vacía retorna -1; si no está vacía y no se encuentra,
	 * también retornará el índice donde la búsqueda terminó (según la lógica).
	 * </p>
	 * 
	 * @param info elemento a localizar.
	 * @return posición del elemento o -1 si la lista está vacía.
	 */
	public int indexOf(E info) {
		Node<E> current = this.first;
		int infoPosition = -1;

		if (!isEmpty()) {
			infoPosition = 0;
			while (current != null && !current.getInfo().equals(info)) {
				infoPosition++;
				current = current.getNext();
			}
		}
		return infoPosition;
	}

	/**
	 * Cuenta cuántas veces aparece info en la lista.
	 * 
	 * @param info elemento a contar.
	 * @return número de ocurrencias.
	 */
	public int numberOfOccurrences(E info) {
		int counter = 0;
		Node<E> current = this.first;

		while (current != null) {
			if (current.getInfo().equals(info)) {
				counter++;
			}
			current = current.getNext();
		}
		return counter;
	}

	/**
	 * Extrae (elimina) y retorna el último elemento de la lista.
	 * 
	 * @return dato eliminado del final, o null si la lista está vacía.
	 */
	public E extractLast() {
		E info = null;
		Node<E> current = this.first;
		int listSize = size();

		if (!isEmpty()) {
			if (listSize == 1) {
				info = current.getInfo();
				this.first = null;
			} else {
				Node<E> previousLastNode = get(listSize - 2);
				info = extract(previousLastNode);
			}
		}
		return info;
	}

	/**
	 * Devuelve una representación en cadena de la sublista que comienza en la
	 * posición indicada.
	 * 
	 * @param position posición (0-based) desde donde se imprimirá hasta el final.
	 * @return cadena con los elementos a partir de la posición indicada.
	 */
	public String print(int position) {
		StringBuilder sb = new StringBuilder();
		Node<E> current = this.first;
		int counter = 0;

		if (!isEmpty()) {
			while (current != null && counter < position) {
				current = current.getNext();
				counter++;
			}
			while (current != null) {
				sb.append(current.getInfo().toString());
				if (current.getNext() != null) {
					sb.append(" -> ");
				}
				current = current.getNext();
			}
		}
		return sb.toString();
	}

	/**
	 * Representación en cadena de toda la lista en formato "a -> b -> c".
	 * 
	 * @return cadena con los elementos de la lista en orden.
	 */
	public String toString() {
		String listText = "";
		Node<E> current = this.first;

		while (current != null) {
			listText = listText + current.getInfo().toString();
			if (current.getNext() != null) {
				listText = listText + " -> ";
			}
			current = current.getNext();
		}
		return listText;
	}
}