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
		// data for the graph and the graph itself
		Graph graph = new Graph();
		int nVillagers = -1;
		
		// counters for the input file
		int section = 0;
		int line = 0;

		// read the file...
		File file = new File("src/tests/open_3.txt");
		FileInputStream fis = new FileInputStream(file);
		Scanner scanner = new Scanner(fis);
		try 
		{
			while (scanner.hasNextLine())
			{
				// get the current line from the input
				String currentLine = scanner.nextLine();
				
				// get the number of villagers from the first line
				if (line == 0) 
				{
					// get the number of villagers
					nVillagers = Integer.parseInt(currentLine);
					
					//log("Constructing " + nVillagers + " villagers.");
					//log("So a total of " + nVillagers * 2 + " vertices.\n");
					
					// create the birth villagers without any sort of edges
					for (int i = 0; i < nVillagers; i++)
					{
						Vertex tmp = new Vertex (-1, true);
						graph.addNullVertex(tmp);
					}
					
					// create the death villagers without any sort of edges
					for (int i = 0; i < nVillagers; i++)
					{
						Vertex tmp = new Vertex (-1, false);
						graph.addNullVertex(tmp);
					}
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
					// scrape out needed info from the current line
					String[] numStrs = currentLine.split(" ");
					int personID01 = Integer.parseInt(numStrs[0]);
					int personID02 = Integer.parseInt(numStrs[1]);
										
					// update the new vertices in the graph
					if (!graph.hasBirthVertex(personID01)) 
						graph.getBirthVertex(-1).setPersonID(personID01);
					if (!graph.hasDeathVertex(personID01)) 
						graph.getDeathVertex(-1).setPersonID(personID01);
					if (!graph.hasBirthVertex(personID02)) 
						graph.getBirthVertex(-1).setPersonID(personID02);
					if (!graph.hasDeathVertex(personID02)) 
						graph.getDeathVertex(-1).setPersonID(personID02);
					
					// add the edges to the graph
					graph.addEdge(graph.getBirthVertex(personID01), graph.getDeathVertex(personID01));
					graph.addEdge(graph.getBirthVertex(personID02), graph.getDeathVertex(personID02));
					graph.addEdge(graph.getDeathVertex(personID01), graph.getBirthVertex(personID02));					
				}

				// we are constructing nodes of the second type
				else if (section == 2)
				{
					// scrape out needed info from the current line
					String[] numStrs = currentLine.split(" ");
					int personID01 = Integer.parseInt(numStrs[0]);
					int personID02 = Integer.parseInt(numStrs[1]);
					
					// add the overlapping edges to the graph
					graph.addEdge(graph.getBirthVertex(personID01), graph.getDeathVertex(personID02));
					graph.addEdge(graph.getBirthVertex(personID02), graph.getDeathVertex(personID01));
				}

				line++;
				
			}
		} 
		finally 
		{
			if (graph.getEdges().size() > 0)
			{
				graph.depthFirstSearch();
			}
			else
			{
				log ("Print special case with no edges...");
			}
			
			log("\n");
			log(graph.toString());
			
			// close the input
			scanner.close();
		}
	}

	/**
	 * A helper function for printing lines. Solely made to reduce keystrokes.
	 * 
	 * @param aMessage
	 *            The message to print out to the console using println
	 */
	private static void log(String aMessage)
	{
		System.out.println(aMessage);
	}
}
