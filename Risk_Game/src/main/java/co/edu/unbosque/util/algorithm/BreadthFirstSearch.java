package co.edu.unbosque.util.algorithm;

import co.edu.unbosque.util.MyGraph;
import co.edu.unbosque.util.QueueImp;

public class BreadthFirstSearch<T> {
	
	private MyGraph<T> graph;
	private T source;
	private int[]dis;
	
	public BreadthFirstSearch(MyGraph<T> graph, T source) {
		this.graph = graph;
		this.source = source;
		this.dis=new int[graph.getNumNodes()];
	}
	
	public MyGraph<T> getGraph() {
		return graph;
	}

	public void setGraph(MyGraph<T> graph) {
		this.graph = graph;
	}

	public T getSource() {
		return source;
	}

	public void setSource(T source) {
		this.source = source;
	}

	public int[] getDis() {
		return dis;
	}

	public void setDis(int[] dis) {
		this.dis = dis;
	}

	public void init() {
		for(int i=0;i<graph.getNumNodes();i++) {
			dis[i]=-1;
		}
	}
	
	public void runSearch() {
		init();
		int first=graph.nodeToNumber(source);
		QueueImp<Integer> queue=new QueueImp<>();
		dis[first]=0;
		queue.enqueue(first);
		while(!queue.isEmpty()) {
			int u=queue.dequeue();
			for(int v: graph.getAdj().get(u)) {
				if(dis[v]==-1) {
					dis[v]=dis[u]+1;
					queue.enqueue(v);
				}
			}
		}
	}
	
	public int obtainDistance(T node) {
		return dis[graph.nodeToNumber(node)];
	}

}
