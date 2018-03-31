import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) throws IOException {
		String input = args[0];/* Take input file name from command line */
		String output = args[1];/* Take output file name from command line */
		PrintWriter writer = new PrintWriter(output, "UTF-8");/* Define PrintWriter for writing opertions */
		InputReader reader = new InputReader();
		ShortestPath Graph = new ShortestPath();
		Graph = reader.ReadFile(input);/*
										 * Define a shortestpath(Graph),create a graph, take graph, S and D value from
										 * input file
										 */
		Map<Integer, Edge> shortest = new HashMap<Integer, Edge>();/*
																	 * shortest will keep the shortest path S to D way
																	 * in graph
																	 */
		shortest = Graph.FindingShortestPath(Graph.DirectedGraph, Graph.S,
				Graph.D);/* We take the shortest path with FindingShortestPath function */
		Graph.ShortestPathPrint(shortest, writer, Graph.D);/* We write shortest path information in output file. */
		ArrayList<Integer> ShortestPathMP = new ArrayList<Integer>();/*
																		 * ShortestPathMP will keep shortest path with
																		 * mustpass points
																		 */
		ShortestPathMP = Graph.MustPass_ShortestPath(Graph.DirectedGraph, shortest, Graph.S, Graph.D,
				input);/* We take shortest path(with mustpass) */
		Graph.ShortestPathPrintMP(ShortestPathMP,
				writer);/* We write shortest path(with mustpass) infromation in output file */
	}

}
