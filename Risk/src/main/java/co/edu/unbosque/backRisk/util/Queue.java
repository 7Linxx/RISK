package co.edu.unbosque.backRisk.util;

public interface Queue<E> {

	public void enqueue(E info);

	public E dequeue();

	int size();
}