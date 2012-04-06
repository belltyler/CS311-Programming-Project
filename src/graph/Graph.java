package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * The graph represents the timeline of the village.
 * 
 * Constructed of nodes that represent either a villager's birth or a villager's death
 * and edges that connect these events, the graph will maintain a state of the timeline.
 * 
 * A properly constructed Graph can then be queried for inconsistencies in the timeline's data.
 * 
 * If there are no consistencies, the Graph can again be queried for the correct timeline of data.
 * 
 * @author tylerbell
 *
 */
public class Graph
{
	// visit colors of the nodes for different states
	public static final int	VISIT_COLOR_WHITE = 1;
	public static final int	VISIT_COLOR_GREY = 2;
	public static final int	VISIT_COLOR_BLACK = 3;
	
	// properties used by the graph
	public int startYear = 1800;
	public int time;
	public boolean isCycle = false;
		
	// graph's data
	private List<Vertex> 	vertices;
	private List<Edge> 		edges;
	private Stack<Vertex>	verticesToPrint;
	private Vertex 			rootVertex;
	
	/**
	 * Init the empty graph.
	 */
	public Graph () 
	{
		vertices = new ArrayList <Vertex> ();
		edges = new ArrayList <Edge> ();
		verticesToPrint = new Stack <Vertex> ();
	}
	
	/**
	 * Performs the depth first search on the graph
	 */
	public void depthFirstSearch ()
	{
		time = 0;
		for (Vertex v : vertices)
		{
			// unmark every vertex
			v.setTime(-1);
		}
		
		for (Vertex v : vertices)
		{
			if (v.visited() == false)
			{
				depthFirstSearch_visit (v);
			}
		}
		
		// determine what the outcome of the DFS run was
		if (this.isCycle)
		{
			System.out.println("Inconsistent information.");
		}
		else
		{
			this.printTimeline();
		}
	}
	
	/**
	 * Performs the visit of the DFS on the Vertex v
	 * Marks v as has been visited and tells it 
	 * to run DFS on its adjacency list.
	 * 
	 * @param v The Vertex to be marked as visited and
	 * to have DFS ran on its adjacency lists
	 */
	public void depthFirstSearch_visit (Vertex v)
	{
		time += 1;
		v.setTime(time);
		v.setOnPath(true);
		
		for (int i = 0; i < v.getOutgoingEdgesCount(); i++)
		{
			Vertex u = v.getOutgoingEdges().get(i).getTo();
			if (u.visited() == false)
			{
				depthFirstSearch_visit(u);
			}
			else if (u.getOnPath())
			{
				this.isCycle = true;
				break;
			}
		}
		verticesToPrint.push(v);
		v.setOnPath(false);
	}
	
	/**
	 * Helper function to print the timeline
	 * if there is no cycle found in the 
	 * DFS run of the graph.
	 */
	private void printTimeline ()
	{
		StringBuffer sb = new StringBuffer ();
		sb.append("(");
		int size = verticesToPrint.size();
		for (int i = 0; i < size; i++)
		{
			Vertex v = verticesToPrint.pop();
			sb.append("(" + v.name() + " " + (startYear++) + ")");
		}
		sb.append(")");
		System.out.println(sb.toString());
	}
	
	/**
	 * Asks the graph if it is empty or not.
	 * @return true if the graph is empty
	 */
	public boolean isEmpty()
	{
		return (vertices.size() == 0);
	}
	
	/**
	 * Adds a vertex to the graph if it has not already been added.
	 * @param v The vertex to add
	 * @return true if the vertex was properly added,
	 * false if it was already contained in the graph
	 */
	public boolean addVertex (Vertex v) 
	{
		boolean ret = false;
		if (vertices.contains(v) == false)
		{
			ret = vertices.add(v);
		}
		return ret;
	}
	
	/**
	 * Adds a null Vertex to the graph.
	 * This is primarily used for constructing a village of n people without any data
	 * about the villagers being provided.
	 * 
	 * @param nullVertex The null Vertex to add 
	 * @return true if the null Vertex was added correctly.
	 */
	public boolean addNullVertex (Vertex nullVertex)
	{
		return vertices.add(nullVertex);
	}
	
	/**
	 * Asks the graph how many vertices it has.
	 * @return the number of vertices 
	 */
	public int nVertices()
	{
		return vertices.size();
	}
	
	/**
	 * Asks the graph how many edges it has.
	 * @return the number of edges.
	 */
	public int nEdges()
	{
		return edges.size();
	}
	
	/**
	 * Asks the graph for its root vertex
	 * @return the graph's root vertex
	 */
	public Vertex getRootVertex ()
	{
		return rootVertex;
	}
	
	/**
	 * Sets the graph's root vertex
	 * and adds it to the graph.
	 * @param v The graph's new root vertex
	 */
	public void setRootVertex (Vertex v)
	{
		this.rootVertex = v;
		if (vertices.contains(v) == false)
		{
			this.addVertex(v);
		}
	}
	
	/**
	 * Get the vertex at the given index
	 * @param n the index
	 * @return the vertex at the index
	 */
	public Vertex getVertex (int n)
	{
		return vertices.get(n);
	}
	
	/**
	 * Asks the graph for the first Vertex, that is a birth vertex,
	 * that has a matching name. (Linear search).
	 * 
	 * @param id ID of the person
	 * @return The Vertex found
	 */
	public Vertex getBirthVertex (int id)
	{
		Vertex v = null;
		for (int i = 0; i < vertices.size(); i++)
		{
			v = vertices.get(i);
			if (v.getIsBirth() == true && v.getPersonID() == id)
			{
				break;
			}
		}
		
		if (v != null) return v;
		return null;
	}
	
	/**
	 * Asks the graph if it has a birth vertex with a matching ID
	 * @param name ID of the person
	 * @return true if the graph has a corresponding Vertex
	 */
	public boolean hasBirthVertex (int name)
	{
		for (int i = 0; i < vertices.size(); i++)
		{
			if (this.vertices.get(i).getIsBirth() == true && this.vertices.get(i).getPersonID() == name) return true; 
		}
		return false;
	}
	
	/**
	 * Asks the graph if it has a death vertex with a matching ID
	 * @param name ID of the person
	 * @return true if the graph has a corresponding Vertex
	 */
	public boolean hasDeathVertex (int name)
	{
		for (int i = 0; i < vertices.size(); i++)
		{
			if (this.vertices.get(i).getIsBirth() == false && this.vertices.get(i).getPersonID() == name) return true; 
		}
		return false;
	}
	
	/**
	 * Asks the graph for the first Vertex, that is a death vertex,
	 * that has a matching name. (Linear search).
	 * 
	 * @param id ID of the person
	 * @return The Vertex found
	 */
	public Vertex getDeathVertex (int name)
	{
		Vertex v = null;
		for (int i = 0; i < vertices.size(); i++)
		{
			v = vertices.get(i);
			if (v.getIsBirth() == false && v.getPersonID() == name)
			{
				break;
			}
		}
		if (v != null) return v;
		return null;
	}
	
	/**
	 * Utility to print every birth and death vertex in the graph
	 */
	public void printListOfVertices ()
	{
		for (Vertex v : vertices)
		{
			System.out.print(v.name() + ", ");
		}
		System.out.println("\n");
	}
	
	/**
	 * Asks the graph for its vertices
	 * @return the graph's vertices
	 */
	public List <Vertex> getVertices ()
	{
		return this.vertices;
	}
	
	/**
	 * Ask the graph for its edges
	 * @return the graph's edges
	 */
	public List <Edge> getEdges ()
	{
		return this.edges;
	}
	
	/**
	 * Insert a directed edge into the graph
	 * @param from the vertex the edge originates from
	 * @param to the vertex the edge is going to
	 * @return true if edge was successfully added
	 */
	public boolean addEdge (Vertex from, Vertex to)
	{
		if (vertices.contains(from) == false)
			throw new IllegalArgumentException ("vertex 'from' is not in the current graph.");
		if (vertices.contains(to) == false)
			throw new IllegalArgumentException ("vertex 'to' is not in the current graph.");
		
		Edge e = new Edge (from, to);
		if (from.findEdge(to) != null)
		{
			return false;
		}
		else
		{
			from.addEdge(e);
			to.addEdge(e);
			edges.add(e);
			return true;
		}
	}
	
	
	@Override
	public String toString ()
	{
		StringBuffer tmp = new StringBuffer("Graph[ \n");
		for (Vertex v : vertices)
		{
			tmp.append(v);
		}
		tmp.append(']');
		return tmp.toString();
	}

}
