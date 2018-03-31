import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShortestPath {
	int S;/* Source value */
	int D;/* Destination value */
	Map<Integer, Node> DirectedGraph = new HashMap<Integer, Node>();/*
																	 * DirectedGraph keep my nodes and edges(So this is
																	 * my directed graph)
																	 */
	/* I can define empty ShortestPath with this constructor */

	public ShortestPath() {

	}

	public ShortestPath(int s, int d, Map<Integer, Node> directedGraph) {
		super();
		this.S = s;
		this.D = d;
		this.DirectedGraph = directedGraph;
	}

	/*
	 * This function find shortestpaths from a source in a graph and return all
	 * shortest path in a map
	 */
	public Map<Integer, Edge> FindingShortestPath(Map<Integer, Node> DGraph, int S, int D)
			throws FileNotFoundException, UnsupportedEncodingException {
		Map<Integer, Node> copyGraph = new HashMap<Integer, Node>();/*
																	 * I take a copy of my graph.I do not want to lose
																	 * anything in my origina graph
																	 */
		copyGraph.putAll(DGraph);
		Map<Integer, Edge> shortest = new HashMap<Integer, Edge>();/*
																	 * This map will keep all shortest path from a
																	 * source
																	 */
		int nextNode = S;/* My nextNode is S at the beginning */
		int target;/* this is my target vertex */
		int minDistance;/* I use this for determine my nextNode for loop */
		int minDistanceNode;/*
							 * My nextNode will be which has minDistance and this value keep
							 * minDistanceNode's key
							 */
		int dist = 0;/* I use for total distance calculation */
		while (nextNode != -1) {/* exit the loop when nextNode is -1 */
			minDistance = Integer.MAX_VALUE;
			minDistanceNode = Integer.MAX_VALUE;
			if (copyGraph.get(nextNode).edges.size() != 0)/* If node has not any edge do not finding operation */
				for (int i = 0; i < copyGraph.get(nextNode).edges.size(); i++) {
					dist = 0;
					target = copyGraph.get(nextNode).edges.get(i).target;/* I use target for easier access */
					if (target != S) {/* my target should not be source */

						if (copyGraph.get(nextNode).vertex == S) {/* if source is S add shortest path this edge */
							shortest.put(target, copyGraph.get(nextNode).edges.get(i));
						} else if (shortest
								.containsKey(target)) {/*
														 * if I have an edge which it has same target I will compare
														 * distance and I keep which have smaller distence
														 */
							if (copyGraph.get(nextNode).vertex != S) {
								dist = copyGraph.get(nextNode).edges.get(i).distance
										+ shortest.get(copyGraph.get(nextNode).vertex).distance;
							} else
								dist = copyGraph.get(nextNode).edges.get(i).distance;

							if (dist < shortest.get(target).distance) {
								edgeSwap(shortest.get(target), copyGraph.get(nextNode).edges.get(i));
								shortest.get(target).distance = dist;

							}

						} else {/* If not I add the edge in shortest path */
							dist = copyGraph.get(nextNode).edges.get(i).distance
									+ shortest.get(copyGraph.get(nextNode).vertex).distance;
							shortest.put(target, copyGraph.get(nextNode).edges.get(i));
							shortest.get(target).distance = dist;
						}

					}

				}
			/*
			 * I find node which have minDistance for find
			 */
			for (int key : shortest.keySet()) {
				if (copyGraph.get(shortest.get(key).target).visited != true
						&& shortest.get(key).distance < minDistance) {
					minDistance = shortest.get(key).distance;
					minDistanceNode = shortest.get(key).target;
				}
			}
			if (minDistance != Integer.MAX_VALUE) {/*
													 * If minDistance equal to Integer.MAX_VALUE I do not have more not
													 * visited vertex in map
													 */
				nextNode = minDistanceNode;
				copyGraph.get(shortest.get(nextNode).target).visited = true;
			} else
				nextNode = -1;/*
								 * If I do not have any vertex in map nextNode will be -1 and I exit fron the
								 * loop
								 */
		}
		copyGraph.clear();/* Clear the graph */
		return shortest;/* Return shortest path edges */

	}

	/*
	 * This function write shortestpath with mustpass in output file with proper
	 * format
	 */
	public void ShortestPathPrintMP(ArrayList<Integer> MustPassPath, PrintWriter writer) {
		if (MustPassPath.size() > 0) {
			ArrayList<Integer> MustPassList = findMustPasses(DirectedGraph);
			int control = 0;
			writer.print(
					"Constrained Shortest Path:\tDistance=" + MustPassPath.get(MustPassPath.size() - 1) + "\tPath=(");
			MustPassPath.remove(MustPassPath.size() - 1);
			for (int i = 0; i < MustPassPath.size(); i++) {
				control = 0;
				for (int j = 0; j < MustPassList.size(); j++) {
					if (MustPassPath.get(i) == MustPassList.get(j)) {
						control = 1;
					}
				}
				if (control == 1) {
					writer.print(MustPassPath.get(i) + "(mustpass) ");
				} else
					writer.print(MustPassPath.get(i) + " ");
			}
			writer.println(")");
		} else {
			writer.println("There is not any path!!!");/* If we do not have any path, write that error message */
		}
		writer.close();
	}

	/*
	 * This function write shortest path in output file with proper format
	 */
	public void ShortestPathPrint(Map<Integer, Edge> path, PrintWriter writer, int target)
			throws FileNotFoundException, UnsupportedEncodingException {
		ArrayList<Integer> shortest_path = new ArrayList<Integer>();
		int x = target;
		if (path.containsKey(x)) {
			shortest_path.add(x);
			while (x != -1) {
				if (path.containsKey(path.get(x).beginning)) {
					shortest_path.add(path.get(x).beginning);
					x = path.get(x).beginning;
				} else {
					shortest_path.add(path.get(x).beginning);
					x = -1;
				}
			}
			writer.println("21328232");
			writer.println();
			writer.print("Shortest Path:\tDistance=" + path.get(target).distance + "\t");
			writer.print("Path=(");
			for (int i = shortest_path.size() - 1; i >= 0; i--) {
				writer.print(shortest_path.get(i) + " ");
			}
			writer.println(")");
			writer.println();
		} else {
			writer.println("21328232");
			writer.println();
			writer.println("There is not any path!!!");/* If we do not have any path, write that error message */
			writer.println();
		}
	}

	/*
	 * This function find all mustpass vertex and return their vertexes in an
	 * arraylist
	 */
	public ArrayList<Integer> findMustPasses(Map<Integer, Node> DirectedGraph) {
		ArrayList<Integer> mustpasslist = new ArrayList<Integer>();
		for (int key : DirectedGraph.keySet()) {
			if (DirectedGraph.get(key).mustpass == true) {
				mustpasslist.add(DirectedGraph.get(key).vertex);
			}
		}
		return mustpasslist;
	}/*
		 * This function find shortest path contains with mustpass points and return
		 * this path
		 */

	public ArrayList<Integer> MustPass_ShortestPath(Map<Integer, Node> DirectedGraph, Map<Integer, Edge> shortest,
			int S, int D, String input) throws IOException {
		ArrayList<Integer> mustpasslist = findMustPasses(
				DirectedGraph);/* I take must pass points with findMustPasses function */
		Map<Integer, Map<Integer, Edge>> allMustPassPaths = new HashMap<Integer, Map<Integer, Edge>>();/*
																										 * This map keep
																										 * all shortest
																										 * paths when
																										 * sources are
																										 * must pass
																										 * points
																										 */
		/*
		 * I take shortest path when sources are must pass points
		 */
		for (int i = 0; i < mustpasslist.size(); i++) {
			InputReader reader = new InputReader();
			ShortestPath Graph = new ShortestPath();
			Graph = reader.ReadFile(input);
			allMustPassPaths.put(mustpasslist.get(i),
					FindingShortestPath(Graph.DirectedGraph, mustpasslist.get(i), Graph.D));
		}
		ArrayList<ArrayList<Integer>> permutations = Permutations(
				mustpasslist);/* I keep all way between any two must pass points */
		ArrayList<ArrayList<Integer>> Final_Path = new ArrayList<ArrayList<Integer>>();/*
																						 * This arraylist keep shortest
																						 * path contains with must pass
																						 * points
																						 */
		for (int i = 0; i < permutations.size(); i++) {
			Final_Path.add(new ArrayList<Integer>());
		}
		int distance = 0;
		/* I take all shortest path S to D contains with must pass points */
		for (int i = 0; i < permutations.size(); i++) {
			distance = 0;
			Final_Path.get(i).addAll(MustPassPath(shortest, permutations.get(i).get(0)));
			distance = distance + shortest.get(permutations.get(i).get(0)).distance;
			for (int j = 0; j < permutations.get(i).size() - 1; j++) {
				distance = distance
						+ allMustPassPaths.get(permutations.get(i).get(j)).get(permutations.get(i).get(j + 1)).distance;
				Final_Path.get(i).addAll(
						MustPassPath(allMustPassPaths.get(permutations.get(i).get(j)), permutations.get(i).get(j + 1)));
			}
			Final_Path.get(i).addAll(MustPassPath(
					allMustPassPaths.get(permutations.get(i).get(permutations.get(i).size() - 1)), this.D));
			distance = distance + allMustPassPaths.get(permutations.get(i).get(permutations.get(i).size() - 1))
					.get(this.D).distance;
			Final_Path.get(i).add(distance);
		}
		ArrayList<Integer> noPaths = new ArrayList<Integer>();/* this arraylist keep indexes of improper paths */
		noPaths.add(this.S);
		noPaths.add(this.D);
		for (int i = 0; i < Final_Path.size(); i++) {
			for (int j = 0; j < mustpasslist.size(); j++) {
				if (!Final_Path.get(i).contains(mustpasslist.get(j))) {
					noPaths.add(i);
					break;
				}
			}
		}
		if (noPaths.size() > 0)
			for (int i = 0; i < noPaths.size(); i++) {/* I remove improper paths from Final_Path list */
				Final_Path.remove(noPaths.get(i));
			}
		int minDistance = Integer.MAX_VALUE;
		int minPath = 0;
		/*
		 * I find path which has smallest distance and keep its index in minpath
		 */
		for (int i = 0; i < Final_Path.size(); i++) {
			if (Final_Path.get(i).get(Final_Path.get(i).size() - 1) < minDistance) {
				minDistance = Final_Path.get(i).get(Final_Path.get(i).size() - 1);
				minPath = i;
			}
		}
		return Final_Path.get(minPath);/* return shortest path contain must pass points */

	}

	/*
	 * This function create a path S to D and return it with an arraylist
	 */
	public ArrayList<Integer> MustPassPath(Map<Integer, Edge> path, int target) {
		ArrayList<Integer> final_path = new ArrayList<Integer>();
		int x = target;
		if (path.containsKey(x)) {
			final_path.add(x);
			while (x != -1) {
				if (path.containsKey(path.get(x).beginning)) {
					final_path.add(path.get(x).beginning);
					x = path.get(x).beginning;

				}

				else {
					if (path.get(x).beginning == this.S) {
						final_path.add(this.S);
					}
					x = -1;
				}
			}
			Collections.reverse(final_path);
		}
		return final_path;
	}

	/*
	 * This function do edge swaping operation
	 */
	public void edgeSwap(Edge a, Edge b) {
		a.beginning = b.beginning;
		a.distance = b.distance;
		a.target = b.target;
	}

	/*
	 * Permutations function find all possible way between all points
	 */
	private ArrayList<ArrayList<Integer>> Permutations(ArrayList<Integer> x) {
		if (x.size() == 0) {
			ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
			result.add(new ArrayList<Integer>());
			return result;
		}
		ArrayList<ArrayList<Integer>> returnValue = new ArrayList<ArrayList<Integer>>();
		Integer first = x.remove(0);
		ArrayList<ArrayList<Integer>> Results = Permutations(x);
		for (List<Integer> list : Results) {
			for (int i = 0; i <= list.size(); i++) {
				ArrayList<Integer> temp = new ArrayList<Integer>(list);
				temp.add(i, first);
				returnValue.add(temp);
			}
		}
		return returnValue;
	}
}
