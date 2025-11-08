package co.edu.unbosque.backRisk.util;

public class MyDoubleLinkedList<E>{

	protected DNode<E> head;
	protected DNode<E> currentPosition;


	public MyDoubleLinkedList() {

	}

	/**
	 * Increments current position -numPositions-
	 */
	public void forward(int numPositions) {
		if (numPositions > 0 && head != null) {
			int positionsForward = numPositions;
			if (currentPosition == null) {
				currentPosition = head;
				positionsForward--;
			}
			while (currentPosition.getNext() != null && positionsForward > 0) {
				currentPosition = currentPosition.getNext();
				positionsForward--;
			}
		}
	}
	public void back(int numPositions) {
		if (numPositions <= 0 || head == null || currentPosition == null)
			return;
		int positionsBack = numPositions;
		while (currentPosition != null && positionsBack > 0) {
			currentPosition = currentPosition.getPrevious();
			positionsBack--;
		}
	}

	
	public void insert(E data) {
		DNode<E> node = new DNode<E>(data);

		if (currentPosition == null) {
			node.setNext(head);       
			if (head != null) {
				head.setPrevious(node);
			}
			head = node;
		} else {
			node.setNext(currentPosition.getNext());
			node.setPrevious(currentPosition);
			if (currentPosition.getNext() != null) {
				currentPosition.getNext().setPrevious(node);
			}
			currentPosition.setNext(node);
		}

		
		currentPosition = node;
	}

	public E extract() {
		E info = null;

		if (currentPosition != null) {
			info = currentPosition.getInfo();

			if (head == currentPosition) {
				head = currentPosition.getNext();
			} else {
				currentPosition.getPrevious().setNext(currentPosition.getNext());
			}

			if (currentPosition.getNext() != null) {
				currentPosition.getNext().setPrevious(currentPosition.getPrevious());
			}

			currentPosition = currentPosition.getNext();
		}
		return info;
	}

	public String toString() {
		DNode<E> aux = head;
		StringBuilder sb = new StringBuilder();
		while (aux != null) {

			sb.append(aux.getInfo());
			if (aux.getNext() != null) {
				sb.append(" <-> ");
			}
			aux = aux.getNext();
		}
		return sb.toString();
	}

	

	public DNode<E> getHead() {
		return head;
	}

	public void setHead(DNode<E> head) {
		this.head = head;
	}

	public DNode<E> getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(DNode<E> currentPosition) {
		this.currentPosition = currentPosition;
	}


}