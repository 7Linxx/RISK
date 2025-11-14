package co.edu.unbosque.backRisk.util;

/**
 * Implementación de una cola genérica basada en MyDequeList.
 * <p>
 * Esta implementación aprovecha la deque subyacente para ofrecer operaciones
 * FIFO: enqueue añade un elemento al frente interno y dequeue elimina desde el
 * final, preservando el orden de llegada.
 * </p>
 * 
 * @param <E> Tipo de los elementos almacenados en la cola.
 * 
 * @author Mariana Pineda
 * @version 1.0
 */
public class QueueImpl<E> implements Queue<E> {
	/**
	 * Estructura interna que soporta las operaciones de la cola.
	 * <p>
	 * MyDequeList permite inserciones y extracciones eficientes en ambos extremos,
	 * y aquí se usa para implementar el comportamiento FIFO.
	 * </p>
	 */
	private MyDequeList<E> data;

	/**
	 * Constructor por defecto. Inicializa la estructura interna.
	 */
	public QueueImpl() {
		data = new MyDequeList<E>();
	}

	/**
	 * Añade un elemento a la cola.
	 * <p>
	 * En esta implementación, el elemento se inserta al principio de la deque
	 * interna (insertFirst) y será recuperado por dequeue desde el otro extremo,
	 * preservando FIFO.
	 * </p>
	 * 
	 * @param info elemento a encolar.
	 */
	@Override
	public void enqueue(E info) {
		data.insertFirst(info);
	}

	/**
	 * Elimina y retorna el siguiente elemento según FIFO.
	 * <p>
	 * Aquí se elimina desde el final de la deque interna (removeLast).
	 * </p>
	 * 
	 * @return elemento desencolado, o null si la cola está vacía.
	 */
	@Override
	public E dequeue() {
		return data.removeLast();
	}

	/**
	 * Retorna el número de elementos en la cola.
	 * 
	 * @return tamaño actual de la cola.
	 */
	@Override
	public int size() {
		return data.size();
	}

	/**
	 * Representación en cadena delegada a la estructura interna.
	 * 
	 * @return cadena representando el contenido de la cola.
	 */
	@Override
	public String toString() {
		return data.toString();
	}
}