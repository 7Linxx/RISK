package co.edu.unbosque.util;

import java.util.Objects;

public class MNode<K,T> {
	
	private MNode<K, T> next;
	private K key;
	private T info;
	
	public MNode() {}

	public MNode(K key, T info, MNode<K, T> next) {
		this.next = next;
		this.key = key;
		this.info = info;
	}
	
	public MNode(K key, T info) {
		this.key = key;
		this.info = info;
	}

	public MNode<K, T> getNext() {
		return next;
	}

	public void setNext(MNode<K, T> next) {
		this.next = next;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public T getInfo() {
		return info;
	}

	public void setInfo(T info) {
		this.info = info;
	}

	@Override
	public String toString() {
		return "MNode [key=" + key + ", info=" + info + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(info, key, next);
	}
	

}
