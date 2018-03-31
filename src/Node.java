import java.util.ArrayList;

public class Node {
	int vertex;/* vertex value */
	ArrayList<Edge> edges = new ArrayList<Edge>();/* all edges Node's has got */
	boolean mustpass = false;/* If Node is mustpass then this boolean value will be true if not stay false */
	boolean visited = false;/*
							 * I use this value for finding shortest path if I visit this node visited will
							 * be true and I do not view this node again.
							 */

	public Node(int vertex) {
		super();
		this.vertex = vertex;
	}
}
