package graph.impl;

class Edge implements Comparable <Edge>{
	public final String source;
	public final String dest;
	public final int weight;
	
	public Edge(String src,String dst,int weight) {
		source=src;
		dest=dst;
		this.weight=weight;
	}
	
	public int compareTo(Edge edgy) {
		return this.weight-edgy.weight;
	}
}
