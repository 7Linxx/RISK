package co.edu.unbosque.util;

public class MyGraph<K> {

	private int numNodes, numEdges;
	private MyMap<K, Integer> traduct;
	private MyDoubleLinkedList<MyDoubleLinkedList<Integer>> adj;
	private MyMap<Integer, K> inverseTraduct;

	public MyGraph() {
		numNodes = 0;
		numEdges = 0;
		traduct = new MyMap<>();
		inverseTraduct=new MyMap<>();
		adj = new MyDoubleLinkedList<>();
	}

	public void addNode(K node) {
		traduct.put(node, numNodes);
		inverseTraduct.put(numNodes, node);
		adj.add(new MyDoubleLinkedList<>());
		numNodes++;
	}

	public void addEdge(K source, K destination) {
		adj.get(traduct.getValue(source)).add(traduct.getValue(destination));
		adj.get(traduct.getValue(destination)).add(traduct.getValue(source));
		numEdges++;
	}
	
	public int nodeToNumber(K node) {
		if(traduct.containsKey(node)) {
			return traduct.getValue(node);
		}
		return -1;
	}
	
	public K numberToNode(int node) {
		return inverseTraduct.getValue(node);
	}

	public int getNumNodes() {
		return numNodes;
	}

	public void setNumNodes(int numNodes) {
		this.numNodes = numNodes;
	}

	public int getNumEdges() {
		return numEdges;
	}

	public void setNumEdges(int numEdges) {
		this.numEdges = numEdges;
	}

	public MyMap<K, Integer> getTraduct() {
		return traduct;
	}

	public void setTraduct(MyMap<K, Integer> traduct) {
		this.traduct = traduct;
	}

	public MyMap<Integer, K> getInverseTraduct() {
		return inverseTraduct;
	}

	public void setInverseTraduct(MyMap<Integer, K> inverseTraduct) {
		this.inverseTraduct = inverseTraduct;
	}

	public MyDoubleLinkedList<MyDoubleLinkedList<Integer>> getAdj() {
		return adj;
	}

	public void setAdj(MyDoubleLinkedList<MyDoubleLinkedList<Integer>> adj) {
		this.adj = adj;
	}

}