package graph;

/**
 * Each edge represents a connection between either a villager's birth
 * or a villager's death in the graph's timeline. 
 * 
 * @author tylerbell
 *
 */
public class Edge
{
	// the Vertex the edge originates from.
	private Vertex 		from;
	
	// the Vertex the edge is going to.
	private Vertex 		to;
			
	public Edge (Vertex from, Vertex to)
	{
		this.from = from;
		this.to = to;
	}
	
	/**
	 * Asks the Edge for the Vertex it's pointing to.
	 * @return The Vertex the Edge is pointing to.
	 */
	public Vertex getTo ()
	{
		return to;
	}
	
	/**
	 * Asks the Edge for the Vertex it's originating from.
	 * @return The Vertex the Edge is originating from.
	 */
	public Vertex getFrom ()
	{
		return from;
	}
	
	@Override
	public boolean equals (Object obj)
	{
		if (obj == null || obj.getClass() != this.getClass())
		{
			return false;
		}
		
		Edge other = (Edge) obj;
		
		return this.from == other.from
		&& this.to == other.to;
	}
	
	@Override
	public String toString ()
	{
		StringBuffer tmp = new StringBuffer ("Edge[from: ");
		if (from.getIsBirth())
		{
			tmp.append(from.getPersonID() + "B");
		}
		else
		{
			tmp.append(from.getPersonID() + "D");
		}
		tmp.append(", to: ");
		if (to.getIsBirth())
		{
			tmp.append(to.getPersonID() + "B");
		}
		else
		{
			tmp.append(to.getPersonID() + "D");
		}
		tmp.append("]");
		return tmp.toString();
	}
}
