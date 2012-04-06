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
		File file = new File("src/tests/open_5.txt");
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
										
					// create the villagers and add an edge
					// between every villager i_b and i_d
					for (int i = 1; i <= nVillagers; i++)
					{
						Vertex b_tmp = new Vertex (i, true);
						Vertex d_tmp = new Vertex (i, false);						
						graph.addNullVertex(b_tmp);
						graph.addNullVertex(d_tmp);
						graph.addEdge(
								graph.getBirthVertex(i),
								graph.getDeathVertex(i));
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
										 
					// construct the edge from 1_d --> 2_b based on the input
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
			// perform the search...
			graph.depthFirstSearch();
						
			// close the input
			scanner.close();
		}
	}
}
