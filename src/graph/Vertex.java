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
	
	public Vertex (int personID, boolean isBirth)
	{
		incomingEdges = new ArrayList <Edge> ();
		outgoingEdges = new ArrayList <Edge> ();
		this.personID = personID;
		this.isBirth = 	isBirth;
		this.mark = 	false;
	}
	
	public List <Edge> getIncomingEdges ()
	{
		return this.incomingEdges;
	}
	
	public int getIncomingEdgesCount ()
	{
		return this.incomingEdges.size();
	}
	
	public void addIncomingEdge (Vertex from)
	{
		Edge in = new Edge (from, this);
		incomingEdges.add(in);
	}
	
	public List <Edge> getOutgoingEdges ()
	{
		return this.outgoingEdges;
	}
	
	public int getOutgoingEdgesCount ()
	{
		return this.outgoingEdges.size();
	}
	
	public void addOutgoingEdge (Vertex to)
	{
		Edge out = new Edge (this, to);
		outgoingEdges.add(out);
	}
	
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
	
	public boolean visited ()
	{
		return this.mark;
	}
	
	public void visit ()
	{
		mark();
	}
	
	public void mark()
	{
		this.mark = true;
	}
	
	public void clearMark ()
	{
		this.mark = false;
	}
	
	public void setMarkState (int state)
	{
		this.markState = state;
	}
	
	public int getMarkState ()
	{
		return this.markState;
	}
	
	public boolean getIsBirth ()
	{
		return this.isBirth;
	}
	
	public int getPersonID ()
	{
		return this.personID;
	}
	
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
		tmp.append(']');
		return tmp.toString();
	}
}
