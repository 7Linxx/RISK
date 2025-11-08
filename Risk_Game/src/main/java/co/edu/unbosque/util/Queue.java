package co.edu.unbosque.util;

public interface Queue<T> {
	
	public void enqueue(T info);
	public T dequeue();
	public int size();
	public T peek();
	public boolean isEmpty();
}
