import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InputReader {
	/*
	 * This function read the input file and create a graph for finding shortest
	 * path operations. First we take S and D value Second take vertex value with
	 * all edges vertex has got.I read an edge and add in edge list for node.I
	 * create a node for every read command and I add this nodes in a graph. At the
	 * I define a graph and add my S,D and nodes.
	 */
	public ShortestPath ReadFile(String input_file) throws IOException {
		Map<Integer, Node> nodes = new HashMap<Integer, Node>();
		BufferedReader br = new BufferedReader(new FileReader(input_file));
		String line;
		line = br.readLine();
		String[] parts = line.split("\\,");
		String[] S_parts = parts[0].split("\\:");
		String[] D_parts = parts[1].split("\\:");
		int S = Integer.valueOf(S_parts[1]);
		int D = Integer.valueOf(D_parts[1]);
		while ((line = br.readLine()) != null) {
			String[] parts_vertex_edges = line.split("\\. ");
			Node n = new Node(Integer.valueOf(parts_vertex_edges[0]));
			if (line.contains("()"))
				nodes.put(n.vertex, n);
			else {
				String[] edges = parts_vertex_edges[1].split("\\,");
				if (edges[edges.length - 1].contains("mustpass")) {
					edges[edges.length - 1] = edges[edges.length - 1].substring(0,
							edges[edges.length - 1].length() - 9);
					n.mustpass = true;
				}
				for (int i = 0; i < edges.length; i++) {
					String edge = edges[i].substring(0, edges[i].length() - 1);
					String[] target_and_dist = edge.split("\\(");
					int target = Integer.valueOf(target_and_dist[0]);
					int distance = Integer.valueOf(target_and_dist[1]);
					if (n.vertex != target) {
						Edge e = new Edge(n.vertex, target, distance);
						n.edges.add(e);
					}
				}
			}
			nodes.put(n.vertex, n);
		}
		ShortestPath Graph = new ShortestPath(S, D, nodes);
		br.close();
		return Graph;
	}
}
