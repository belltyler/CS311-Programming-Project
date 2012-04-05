package graph;

import java.util.ArrayList;
import java.util.List;

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
	
	// graph's data
	private List<Vertex> 	vertices;
	private List<Edge> 		edges;
	private Vertex 			rootVertex;
	
	/**
	 * Init the empty graph.
	 */
	public Graph () 
	{
		vertices = new ArrayList <Vertex> ();
		edges = new ArrayList <Edge> ();
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
	
	/**
	 * Clear the mark state of all vertices in the graph
	 */
	public void clearMark ()
	{
		for (Vertex v : vertices)
		{
			v.clearMark();
		}
	}
	
	/**
	 * Clear the mark state of all edges in the graph
	 */
	public void clearEdges ()
	{
		for (Edge e : edges)
		{
			e.clearMark();
		}
	}
	
	public String toString ()
	{
		StringBuffer tmp = new StringBuffer("Graph[");
		for (Vertex v : vertices)
		{
			tmp.append(v);
		}
		tmp.append(']');
		return tmp.toString();
	}

}
