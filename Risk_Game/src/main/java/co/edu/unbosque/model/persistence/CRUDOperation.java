package co.edu.unbosque.model.persistence;

public interface CRUDOperation<E, T> {

	public void create(T data);

	public int update(E id, T data);

	public int delete(E id);

}
