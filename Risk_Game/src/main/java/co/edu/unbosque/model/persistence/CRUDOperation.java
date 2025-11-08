package co.edu.unbosque.model.persistence;

public interface CRUDOperation <K,T>{
	
	public void create(T data);
	public int update(K id,T data);
	public int delete(K id);
	
}
