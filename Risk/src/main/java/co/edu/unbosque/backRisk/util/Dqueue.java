package co.edu.unbosque.backRisk.util;

public interface Dqueue<E> {

	

	public void insertLast(E info);

	public E removeFirst();

	public E removeLast();

	public int size();

	public void insertFirst(E info);
}