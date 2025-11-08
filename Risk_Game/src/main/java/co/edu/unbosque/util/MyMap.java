package co.edu.unbosque.util;

public class MyMap<K, T> {

	private MNode<K, T> first;
	private int size;

	public MyMap() {
		this.size = 0;
	}

	public int size() {
		return size;
	}

	public MyDoubleLinkedList<K> keySet() {
		MyDoubleLinkedList<K> keys = new MyDoubleLinkedList<>();
		MNode<K, T> current = first;
		while (current != null) {
			keys.add(current.getKey());
			current = current.getNext();
		}
		return keys;
	}

	public MyDoubleLinkedList<T> values() {
		MyDoubleLinkedList<T> values = new MyDoubleLinkedList<>();
		MNode<K, T> current = first;
		while (current != null) {
			values.add(current.getInfo());
			current = current.getNext();
		}
		return values;
	}

	public boolean containsKey(K key) {
		MNode<K, T> current = first;
		while (current != null) {
			if (current.getKey().equals(key))
				return true;
			current = current.getNext();
		}
		return false;
	}

	public T getValue(K key) {
		MNode<K, T> current = first;
		while (current != null) {
			if (current.getKey().equals(key))
				return current.getInfo();
			current = current.getNext();
		}
		return null;
	}

	public void put(K key, T info) {
		MNode<K, T> newNode = new MNode<K, T>(key, info);
		if (this.first == null)
			first = newNode;
		else {
			MNode<K, T> current = first;
			while (current.getNext() != null) {
				if (current.getKey().equals(key)) {
					current.setInfo(info);
					return;
				}
				current = current.getNext();
			}
			if (current.getKey().equals(key)) {
				current.setInfo(info);
				return;
			}
			current.setNext(newNode);
		}
		size++;
	}

	public boolean replace(K key, T info) {
		MNode<K, T> current = first;
		while (current != null) {
			if (current.getKey().equals(key)) {
				current.setInfo(info);
				return true;
			}
			current = current.getNext();
		}
		return false;
	}

	public boolean remove(K key) {
		if (this.first.getKey().equals(key)) {
			first = first.getNext();
			size--;
		} else {
			MNode<K, T> current = first;
			while (current.getNext() != null) {
				if (current.getNext().getKey().equals(key)) {
					size--;
					current.setNext(current.getNext().getNext());
					return true;
				}
				current = current.getNext();
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuffer ouput = new StringBuffer();
		MNode<K, T> current = first;
		while (current != null) {
			ouput.append(current.toString());
			current = current.getNext();
			if (current != null)
				ouput.append("-->");
		}
		return ouput.toString();
	}

	@Override
	public int hashCode() {
		MNode<K, T> current = first;
		int hashCode = 0;
		while (current != null) {
			int entryHash = current.hashCode();
			hashCode = 31 * hashCode + entryHash;
			current = current.getNext();
		}
		return hashCode;
	}

}
