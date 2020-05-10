package graph.impl;

public class Path implements Comparable<Path> {
	
	String dest;
	int cost;
	
	public Path(String dest, int c) {
		this.dest = dest;
		cost = c;
	}	
	
	public int compareTo(Path other) {
		return this.cost - other.cost;
	}
	public String toString() {
		return this.dest + "with cost " + cost;
	}
}
