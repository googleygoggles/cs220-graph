package graph.impl;

import java.util.*;

import graph.IGraph;
import graph.INode;
import graph.NodeVisitor;

/**
 * A basic representation of a graph that can perform BFS, DFS, Dijkstra,
 * and Prim-Jarnik's algorithm for a minimum spanning tree.
 * 
 * @author jspacco
 *
 */
public class Graph implements IGraph
{
    private Map<String, INode> graphN = new HashMap<>();
    /**
     * Return the {@link Node} with the given name.
     * 
     * If no {@link Node} with the given name exists, create
     * a new node with the given name and return it. Subsequent
     * calls to this method with the same name should
     * then return the node just created.
     * 
     * @param name
     * @return
     */
    public INode getOrCreateNode(String name) {
        if (graphN.containsKey(name)){
            return graphN.get(name);
        }
        graphN.put(name, new Node(name));
        return graphN.get(name);
    }

    /**
     * Return true if the graph contains a node with the given name,
     * and false otherwise.
     * 
     * @param name
     * @return
     */
    public boolean containsNode(String name) {
        return graphN.containsKey(name);
    }

    /**
     * Return a collection of all of the nodes in the graph.
     * 
     * @return
     */
    public Collection<INode> getAllNodes() {
        return graphN.values();
    }
    
    /**
     * Perform a breadth-first search on the graph, starting at the node
     * with the given name. The visit method of the {@link NodeVisitor} should
     * be called on each node the first time we visit the node.
     * 
     * 
     * @param startNodeName
     * @param v
     */
    public void breadthFirstSearch(String startNodeName, NodeVisitor v)
    {
        Set<INode> visited = new HashSet<>();
        Queue<INode> toBeVisited = new LinkedList<>();
        toBeVisited.add(getOrCreateNode(startNodeName));
        while (!toBeVisited.isEmpty()){
            INode x = toBeVisited.remove();
            if (!visited.contains(x)) {
                v.visit(x);
                visited.add(x);
                for (INode n : x.getNeighbors()){
                    if (!visited.contains(n)){
                        toBeVisited.add(n);
                    }
                }
            }
        }
    }

    /**
     * Perform a depth-first search on the graph, starting at the node
     * with the given name. The visit method of the {@link NodeVisitor} should
     * be called on each node the first time we visit the node.
     * 
     * 
     * @param startNodeName
     * @param v
     */
    public void depthFirstSearch(String startNodeName, NodeVisitor v)
    {
        Set<INode> visited = new HashSet<>();
        Stack<INode> toBeVisited = new Stack<>();
        toBeVisited.push(getOrCreateNode(startNodeName));
        while (!toBeVisited.isEmpty()){
            INode x = toBeVisited.pop();
            if (!visited.contains(x)) {
                v.visit(x);
                visited.add(x);
                for (INode n : x.getNeighbors()){
                    if (!visited.contains(n)){
                        toBeVisited.push(n);
                    }
                }
            }
        }
    }

    /**
     * Perform Dijkstra's algorithm for computing the cost of the shortest path
     * to every node in the graph starting at the node with the given name.
     * Return a mapping from every node in the graph to the total minimum cost of reaching
     * that node from the given start node.
     * 
     * <b>Hint:</b> Creating a helper class called Path, which stores a destination
     * (String) and a cost (Integer), and making it implement Comparable, can be
     * helpful. Well, either than or repeated linear scans.
     * 
     * @param startName
     * @return
     */
    public Map<INode,Integer> dijkstra(String startName) {
        // TODO: Implement this method
    	
    	Map<INode, Integer> result = new HashMap<>();
    	PriorityQueue<Path> toDo = new PriorityQueue<>();
    	toDo.add(new Path(startName, 0));
    	while (result.size() < graphN.size()) {
    		Path nextPath = toDo.poll();
    		INode tempN = graphN.get(nextPath.dest);
    			if(!result.containsKey(tempN)) {
    				int c = nextPath.cost;
    				result.put(tempN, c);
    				for (INode n : tempN.getNeighbors()) {
    					toDo.add(new Path(n.getName(), c + tempN.getWeight(n)));
    				}
    			}
    	}
    	return result;
    	
    }
    
    /**
     * Perform Prim-Jarnik's algorithm to compute a Minimum Spanning Tree (MST).
     * 
     * The MST is itself a graph containing the same nodes and a subset of the edges 
     * from the original graph.
     * 
     * @return
     */
    public IGraph primJarnik() {
		IGraph tempGraph=new Graph();
		INode start=this.getAllNodes().iterator().next();
		PriorityQueue<Edge> toDo= new PriorityQueue<>();
		
		for (INode n : start.getNeighbors()) {
			toDo.add(new Edge(start.getName(),n.getName(),n.getWeight(start)));
		}
			
			while (tempGraph.getAllNodes().size()!=this.getAllNodes().size()) {
				Edge e= toDo.poll();
				
				if (tempGraph.containsNode(e.dest))
					continue;
				INode destination=tempGraph.getOrCreateNode(e.dest);
				INode source =tempGraph.getOrCreateNode(e.source);
				source.addUndirectedEdgeToNode(destination, e.weight);

				INode edgesLeft= graphN.get(e.dest);
				
				for (INode node: edgesLeft.getNeighbors()) {
					toDo.add(new Edge(edgesLeft.getName(),node.getName(),node.getWeight(edgesLeft)));
				}
			}
		return tempGraph;
	}
}