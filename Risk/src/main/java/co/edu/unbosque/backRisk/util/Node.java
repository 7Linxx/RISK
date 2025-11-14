package co.edu.unbosque.backRisk.util;

/**
 * Nodo simple para lista enlazada singular.
 * <p>
 * Contiene la información genérica y la referencia al siguiente nodo.
 * </p>
 * 
 * @param <E> Tipo de elemento almacenado en el nodo.
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public class Node<E> {

	/**
	 * Información almacenada en el nodo.
	 */
	private E info;

	/**
	 * Referencia al siguiente nodo en la lista.
	 */
	private Node<E> next;

	/**
	 * Constructor que inicializa el nodo con información y referencia al siguiente.
	 * 
	 * @param info información del nodo.
	 * @param next referencia al siguiente nodo.
	 */
	public Node(E info, Node<E> next) {
		this.info = info;
		this.next = next;
	}

	/**
	 * Constructor que inicializa el nodo solo con la información. El siguiente se
	 * inicializa en null.
	 * 
	 * @param info información del nodo.
	 */
	public Node(E info) {
		this(info, null);
	}

	/**
	 * Constructor por defecto que crea un nodo vacío (info y next en null).
	 */
	public Node() {
		this(null, null);
	}

	/**
	 * Obtiene la información almacenada en el nodo.
	 * 
	 * @return info del nodo.
	 */
	public E getInfo() {
		return this.info;
	}

	/**
	 * Obtiene la referencia al siguiente nodo.
	 * 
	 * @return siguiente nodo.
	 */
	public Node<E> getNext() {
		return this.next;
	}

	/**
	 * Establece la información del nodo.
	 * 
	 * @param info información a asignar.
	 */
	public void setInfo(E info) {
		this.info = info;
	}

	/**
	 * Establece la referencia al siguiente nodo.
	 * 
	 * @param next nodo a asignar como siguiente.
	 */
	public void setNext(Node<E> next) {
		this.next = next;
	}

	/**
	 * Representación en cadena del nodo.
	 * 
	 * @return resultado de info.toString() si info no es null, o null en caso
	 *         contrario.
	 */
	public String toString() {
		if (info != null) {
			return info.toString();
		} else {
			return null;
		}
	}
}