package graph;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This Vertex represents a villager's birth or a villager's death
 * in the village's overall graph timeline. 
 * 
 * @author tylerbell
 *
 */
public class Vertex 
{
	
	private List <Edge> 	incomingEdges;
	private List <Edge> 	outgoingEdges;
	private boolean 		mark;
	private int 			markState;
	private boolean			isBirth;
	private int				personID;
	private int				timeStamp;
	private boolean			onPath;
	
	/**
	 * Create a Vertex of a villager
	 * @param personID the villager's id number
	 * @param isBirth whether or not this Vertex represents a birth or a death
	 */
	public Vertex (int personID, boolean isBirth)
	{
		incomingEdges = new ArrayList <Edge> ();
		outgoingEdges = new ArrayList <Edge> ();
		this.personID = personID;
		this.isBirth = 	isBirth;
		this.mark = 	false;
		this.timeStamp = -1;
	}
	
	/**
	 * Asks the Vertex for its timestamp
	 * @return the Vertex's timestamp
	 */
	public int getTime ()
	{
		return this.timeStamp;
	}
	
	/**
	 * Sets the Vertex's timestamp
	 * @param time the Vertex's new timestamp
	 */
	public void setTime (int time)
	{
		this.timeStamp = time;
	}
	
	public boolean getOnPath ()
	{
		return this.onPath;
	}
	
	public void setOnPath (boolean isOnPath)
	{
		this.onPath = isOnPath;
	}
	
	/**
	 * Ask the Vertex for its incoming edges
	 * @return The Vertex's incoming edges.
	 */
	public List <Edge> getIncomingEdges ()
	{
		return this.incomingEdges;
	}
	
	/**
	 * Ask the Vertex how many incoming edges it has.
	 * @return The number of incoming edges to Vertex.
	 */
	public int getIncomingEdgesCount ()
	{
		return this.incomingEdges.size();
	}
	
	/**
	 * Add a new incoming edge to the Vertex
	 * @param from The Vertex that the Edge is originating from.
	 */
	public void addIncomingEdge (Vertex from)
	{
		Edge in = new Edge (from, this);
		incomingEdges.add(in);
	}
	
	/**
	 * Ask the Vertex for its outgoing edges
	 * @return The Vertex's outgoing edges.
	 */
	public List <Edge> getOutgoingEdges ()
	{
		return this.outgoingEdges;
	}
	
	/**
	 * Ask the Vertex how many outgoing edges it has.
	 * @return The number of outgoing edges from Vertex.
	 */
	public int getOutgoingEdgesCount ()
	{
		return this.outgoingEdges.size();
	}
	
	/**
	 * Add a new outgoing edge from Vertex
	 * @param to The Vertex that the Edge is going 'to'
	 */
	public void addOutgoingEdge (Vertex to)
	{
		Edge out = new Edge (this, to);
		outgoingEdges.add(out);
	}
	
	/**
	 * Adds an edge to the Vertex
	 * @param e The Edge
	 * @return true if addition of Edge is successful
	 */
	public boolean addEdge (Edge e)
	{
		if (e.getFrom() == this)
		{
			outgoingEdges.add(e);
		}
		else if (e.getTo() == this)
		{
			incomingEdges.add(e);
		}
		else
		{
			return false;
		}
		return true;
	}
	
	/**
	 * Asks the Vertex if it has the edge e
	 * @param e The Edge
	 * @return true if the edge is either coming into or leaving the Vertex
	 */
	public boolean hasEdge (Edge e)
	{
		if (e.getFrom() == this)
		{
			return outgoingEdges.contains(e);
		}
		else if (e.getTo() == this)
		{
			return incomingEdges.contains(e);
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Has the Vertex been visited?
	 * @return true if mark == true
	 */
	public boolean visited ()
	{
		return (this.timeStamp != -1);
	}
	
	/**
	 * Mark the Vertex as visited
	 */
	public void visit ()
	{
		mark();
	}
	
	/**
	 * Mark the Vertex
	 */
	public void mark()
	{
		this.mark = true;
	}
	
	/**
	 * Clear the Vertex's marks
	 */
	public void clearMark ()
	{
		this.mark = false;
	}
	
	/**
	 * Set the Vertex's mark state
	 * @param state the state to set
	 */
	public void setMarkState (int state)
	{
		this.markState = state;
	}
	
	/**
	 * Asks the Vertex for its mark state
	 * @return an index code corresponding to a mark state.
	 */
	public int getMarkState ()
	{
		return this.markState;
	}
	
	/**
	 * Asks the Vertex if it is a Birth vertex
	 * @return true if it is a birth vertex, false otherwise.
	 */
	public boolean getIsBirth ()
	{
		return this.isBirth;
	}
	
	/**
	 * Asks the Vertex for its person's ID number
	 * @return Ther person's ID number
	 */
	public int getPersonID ()
	{
		return this.personID;
	}
	
	/**
	 * Set the Vertex's personID number
	 * @param newID The new personID
	 */
	public void setPersonID (int newID)
	{
		this.personID = newID;
	}
	
	/**
	 * Ask the Vertex if it has an edge to a dest
	 * @param dest The destination of the edge being searched for
	 * @return the Edge if one exists
	 */
	public Edge findEdge (Vertex dest)
	{
		for (Edge e : outgoingEdges)
		{
			if (e.getTo() == dest)
			{
				return e;
			}
		}
		return null;
	}
	
	/**
	 * Constructs a 'name' for the Vertex
	 * @return The name, id + ( B | D); ex: 1B, 2D, etc. 
	 */
	public String name ()
	{
		if (isBirth)
		{
			return personID + "B";
		}
		else
		{
			return personID + "D";
		}
	}
	
	@Override
	public boolean equals (Object obj)
	{
		if (obj == null || obj.getClass() != this.getClass())
		{
			return false;
		}
		
		Vertex other = (Vertex) obj;
		
		return this.isBirth == other.isBirth
		&& this.personID == other.personID;
	}
	
	@Override
	public String toString ()
	{
		StringBuffer tmp = new StringBuffer ("Vertex (");
		if (isBirth)
		{
			tmp.append(personID + "B");
		}
		else
		{
			tmp.append(personID + "D");
		}
		tmp.append("), in:[");
		for (int i = 0; i < incomingEdges.size(); i++)
		{
			Edge e = incomingEdges.get(i);
			if (i > 0)
				tmp.append(',');
			tmp.append('{');
			tmp.append(e.getFrom().name());
			tmp.append(',');
			tmp.append('}');
		}
		tmp.append("], out:[");
		for (int i = 0; i < outgoingEdges.size(); i++)
		{
			Edge e = outgoingEdges.get(i);
			if (i > 0)
				tmp.append(',');
			tmp.append('{');
			tmp.append(e.getTo().name());
			tmp.append(',');
			tmp.append('}');
		}
		tmp.append("] \n");
		return tmp.toString();
	}
}
