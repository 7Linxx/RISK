package co.edu.unbosque.util;
import java.util.Iterator;

public class QueueImp<T> implements Queue<T>,Iterable<T>{
	
	private DequeList<T> infos;
	
	public QueueImp() {
		infos=new DequeList<>();
	}

	@Override
	public void enqueue(T info) {
		infos.insertLast(info);
		
	}

	@Override
	public T dequeue() {
		return infos.removeFirst();
	}

	@Override
	public int size() {
		return infos.size();
	}

	@Override
	public T peek() {
		return infos.getHead().getInfo();
	}
	
	@Override
	public String toString() {
		StringBuffer buf=new StringBuffer();
		buf.append("Front [");
		DNode<T> aux=infos.getHead();
		for (int i = 0; i < infos.size(); i++) {
			buf.append(aux.getInfo());
			if(aux==infos.getTail())break;
			else buf.append("<->");
			aux=aux.getNext();
		}
		buf.append("] Back");
		return buf.toString();
	}

	@Override
	public Iterator<T> iterator() {
		return infos.iterator();
	}

	@Override
	public boolean isEmpty() {
		return infos.size()==0;
	}

}
