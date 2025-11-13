package co.edu.unbosque.util;
public class MyLinkedList<E> {

	protected Node<E> first;

	public MyLinkedList() {
		this.first = null;
	}

	public Node<E> getFirst() {
		return this.first;
	}

	public void setFirst(Node<E> first) {
		this.first = first;
	}

	public boolean isEmpty() {
		return (this.first == null);
	}

	public void add(E info) {
		Node<E> newNode = new Node<E>(info);
		newNode.setNext(this.first);
		first = newNode;
	}
	public void insert(E info, Node<E> previous) {
		if (previous != null) {
			Node<E> newNode = new Node<E>(info);
			newNode.setNext(previous.getNext());
			previous.setNext(newNode);
		}
	}
	
	public void addLast(E info) {
		Node<E> lastNode = getLastNode();

		if (lastNode != null) {
			insert(info, lastNode);
		} else {
			this.first = new Node<E>(info);
			
		}
	}
	
	
	

	public E extract() {
		E data = null;
		if (this.first != null) {
			data = this.first.getInfo();
			this.first = this.first.getNext();
		}
		return data;
	}
	
	public E extract(Node<E> previous) {
		E data = null;
		if (previous != null && previous.getNext() != null) {
			data = previous.getNext().getInfo();
			previous.setNext(previous.getNext().getNext());
		}
		return data;
	}

	public int size() {
		int size = 0;
		Node<E> current = this.first;

		while (current != null) {
			size++;
			current = current.getNext();
		}
		return size;
	}


	public String print() {
		return this.toString();
	}

	public Node<E> get(E info) {
		Node<E> targetNode = null;
		Node<E> currentNode = this.first;

		while (currentNode != null && !currentNode.getInfo().equals(info)) {
			currentNode = currentNode.getNext();
		}
		if (currentNode != null) {
			targetNode = currentNode;
		}
		return targetNode;
	}

	public Node<E> get(int n) {
		Node<E> targetNode = null;
		Node<E> currentNode = this.first;
		int counter = 0;

		while (currentNode != null && counter < n) {
			currentNode = currentNode.getNext();
			counter++;
		}
		if (currentNode != null) {
			targetNode = currentNode;
		}
		return targetNode;
	}

	public Node<E> getLastNode() {
		Node<E> current = this.first;

		while (current != null && current.getNext() != null) {
			current = current.getNext();
		}
		return current;
	}

	public int indexOf(E info) {
		Node<E> current = this.first;
		int infoPosition = -1;

		if (!isEmpty()) {
			infoPosition = 0;
			while (current != null && !current.getInfo().equals(info)) {
				infoPosition++;
				current = current.getNext();
			}
		}
		return infoPosition;
	}

	public int numberOfOccurrences(E info) {
		int counter = 0;
		Node<E> current = this.first;

		while (current != null) {
			if (current.getInfo().equals(info)) {
				counter++;
			}
			current = current.getNext();
		}
		return counter;
	}

	
	public E extractLast() {
		E info = null;
		Node<E> current = this.first;
		int listSize = size();

		if (!isEmpty()) {
			if (listSize == 1) {
				info = current.getInfo();
				this.first = null;
			} else {
				Node<E> previousLastNode = get(listSize - 2);
				info = extract(previousLastNode);
			}
		}
		return info;
	}

	public String print(int position) {
		StringBuilder sb = new StringBuilder();
		Node<E> current = this.first;
		int counter = 0;

		if (!isEmpty()) {
			while (current != null && counter < position) {
				current = current.getNext();
				counter++;
			}
			while (current != null) {
				sb.append( current.getInfo().toString());
				if (current.getNext() != null) {
					sb.append(" -> ");
				}
				current = current.getNext();
			}
		}
		return sb.toString();
	}
	

	public String toString() {
		String listText = "";
		Node<E> current = this.first;

		while (current != null) {
			listText = listText + current.getInfo().toString();
			if (current.getNext() != null) {
				listText = listText + " -> ";
			}
			current = current.getNext();
		}
		return listText;
	}
}