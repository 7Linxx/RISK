package co.edu.unbosque.backRisk.util;

/**
 * Nodo para una lista doblemente enlazada (Doubly-linked node).
 * <p>
 * Cada DNode mantiene una referencia al siguiente y al anterior nodo, y
 * contiene la información genérica (info) almacenada en él.
 * </p>
 * 
 * @param <E> Tipo de los elementos almacenados en el nodo.
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public class DNode<E> {
	/**
	 * Referencia al siguiente nodo en la lista doblemente enlazada.
	 */
	private DNode<E> next;

	/**
	 * Referencia al nodo anterior en la lista doblemente enlazada.
	 */
	private DNode<E> previous;

	/**
	 * Información almacenada en el nodo.
	 */
	private E info;

	/**
	 * Constructor que inicializa el nodo con una referencia al siguiente nodo y la
	 * información.
	 * 
	 * @param next Referencia al siguiente nodo.
	 * @param info Información a almacenar en este nodo.
	 */
	public DNode(DNode<E> next, E info) {
		this.next = next;
		this.info = info;
	}

	/**
	 * Constructor que inicializa el nodo únicamente con la información. La
	 * referencia al siguiente queda en null.
	 * 
	 * @param elem Elemento a almacenar en el nodo.
	 */
	public DNode(E elem) {
		this(null, elem);
	}

	/**
	 * Constructor por defecto. Crea un nodo sin información ni referencias.
	 */
	public DNode() {
		// Constructor vacío para usos de serialización o frameworks
	}

	/**
	 * Obtiene la referencia al siguiente nodo.
	 * 
	 * @return el siguiente DNode, o null si no existe.
	 */
	public DNode<E> getNext() {
		return next;
	}

	/**
	 * Establece la referencia al siguiente nodo.
	 * 
	 * @param next nodo a asignar como siguiente.
	 */
	public void setNext(DNode<E> next) {
		this.next = next;
	}

	/**
	 * Obtiene la referencia al nodo anterior.
	 * 
	 * @return el nodo anterior, o null si no existe.
	 */
	public DNode<E> getPrevious() {
		return previous;
	}

	/**
	 * Establece la referencia al nodo anterior.
	 * 
	 * @param previous nodo a asignar como anterior.
	 */
	public void setPrevious(DNode<E> previous) {
		this.previous = previous;
	}

	/**
	 * Obtiene la información almacenada en el nodo.
	 * 
	 * @return info del nodo.
	 */
	public E getInfo() {
		return info;
	}

	/**
	 * Establece la información almacenada en el nodo.
	 * 
	 * @param info información a asignar.
	 */
	public void setInfo(E info) {
		this.info = info;
	}

	/**
	 * Representación en cadena del nodo.
	 * 
	 * @return la cadena resultante de info.toString() si info no es null, o null en
	 *         caso contrario.
	 */
	public String toString() {
		if (info != null) {
			return info.toString();
		} else {
			return null;
		}
	}

}