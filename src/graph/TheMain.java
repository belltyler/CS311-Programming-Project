package graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 * Reads in the provided file and, based on the file format, constructs a graph
 * of villagers (birth and death).
 * 
 * Constructs edges between each villager; these edges determine their lifespan;
 * more specifically, which villager was born before the other one.
 * 
 * The graph will be analyzed for inconsistencies. If there are no
 * inconsistencies, the program will output the timeline of each villagers birth
 * and death.
 * 
 * @author tylerbell
 * @see http://www.java2s.com/Code/Java/Collections-Data-Structure/Adirectedgraphdatastructure.htm
 * ÑÑ Giving credit to a resource used for beginning graph implementation.
 */
public class TheMain 
{
	public static void main(String[] args) throws IOException 
	{
		Graph graph = new Graph();
		int nVillagers = -1;
		
		
		int section = 0;
		int line = 0;

		File file = new File("src/tests/open_1.txt");
		FileInputStream fis = new FileInputStream(file);
		Scanner scanner = new Scanner(fis);
		try 
		{
			while (scanner.hasNextLine())
			{
				String currentLine = scanner.nextLine();
				
				// get the number of villagers from the first line
				if (line == 0) 
				{
					nVillagers = Integer.parseInt(currentLine);
					log("Constructing " + nVillagers + " villagers.");
				}

				// increment the 'section' if a '#' has been hit
				// this determines what type of input we are getting
				if (currentLine.equals("#"))
				{
					section += 1;
				}

				// we are constructing nodes of the first type
				else if (section == 1)
				{
					log("Constructing new nodes from section one.");
					
					// scrape out needed info from the current line
					String[] numStrs = currentLine.split(" ");
					int personID01 = Integer.parseInt(numStrs[0]);
					int personID02 = Integer.parseInt(numStrs[1]);
					
					// create the new vertices
					Vertex b_left = new Vertex (personID01, true);
					Vertex d_left = new Vertex (personID01, false);
					Vertex b_right = new Vertex (personID02, true);
					Vertex d_right = new Vertex (personID02, false);
					
					// add the vertices to the graph
					graph.addVertex(b_left);
					graph.addVertex(d_left);
					graph.addVertex(b_right);
					graph.addVertex(d_right);
					
					// add the edges to the graph
					graph.addEdge(b_left, d_left);
					graph.addEdge(b_right, d_right);
					graph.addEdge(d_left, b_right);
					
				}

				// we are constructing nodes of the second type
				else if (section == 2)
				{
					log("Constructing new nodes from section two.");
				}

				line++;
			}
		} 
		finally 
		{
			log(graph.toString());
			log("size = " + graph.nVertices());
			scanner.close();
		}
	}

	/**
	 * A helper function for printing lines. Solely made to reduce keystrokes.
	 * 
	 * @param aMessage
	 *            The message to print out to the console using println
	 */
	public static void log(String aMessage)
	{
		System.out.println(aMessage);
	}
}
