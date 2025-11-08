package co.edu.unbosque.backRisk.service;

import java.util.List;

import co.edu.unbosque.backRisk.util.MyLinkedList;
public interface CRUDOperation<T> {
	
	public int create(T data);
	
	public MyLinkedList<T> getAll();
	
	public int deleteById(Long id);
	
	public int updateById(Long id,T newData);
	
	public long count();
	
	public boolean exist(Long id);
}
