package co.edu.unbosque.util;

public class DequeList<T> extends MyDoubleLinkedList<T> implements Deque<T>{
	
	private DNode<T> tail;
	
	public DequeList() {
		super();
		head=new DNode<T>();
		tail=new DNode<T>();
		head.setNext(tail);
		tail.setPrevious(head);
	}

	@Override
	public void insertLast(T info) {
		DNode<T> t=this.tail;
		DNode<T> temp=new DNode<T>();
		temp.setInfo(info);
		temp.setPrevious(t);
		t.setNext(temp);
		current=temp;
		tail=temp;
		if(size==0)head=temp;
		size++;
		position=size-1;
	}

	@Override
	public T removeLast() {
		if(tail==null)return null;
		T value=tail.getInfo();
		tail=tail.getPrevious();
		size--;
		position=size-1;
		current=tail;
		return value;
	}

	@Override
	public void insertFirst(T info) {
		DNode<T> h=this.head;
		DNode<T> temp=new DNode<T>();
		temp.setInfo(info);
		temp.setNext(h);
		h.setPrevious(temp);
		current=temp;
		this.head=temp;
		if(size==0)tail=temp;
		size++;
		position=0;
	}

	@Override
	public T removeFirst() {
		if(head==null)return null;
		T value=this.head.getInfo();
		head=head.getNext();
		size--;
		position=0;
		current=head;
		return value;
	}

	@Override
	public int size() {
		return size;
	}

	public DNode<T> getTail() {
		return tail;
	}

	public void setTail(DNode<T> tail) {
		this.tail = tail;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	@Override
	public String toString() {
		StringBuffer buf=new StringBuffer();
		buf.append("head [");
		DNode<T> aux=this.head;
		for (int i = 0; i < size; i++) {
			buf.append(aux.getInfo());
			if(aux==tail)break;
			else buf.append("<->");
			aux=aux.getNext();
		}
		buf.append("] tail");
		return buf.toString();
	}

}
