package i5.las2peer.services.cdService.data.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import main.ConverterEdge;
import main.ConverterGraph;
import sim.field.network.Network;

public class NetworkParser {
	
	public NetworkParser() {
		
	}
	
	public Network getNetwork() {
		
		ConverterGraph graph = new ConverterGraph();
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("BioJava"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {			
			
		    String line = reader.readLine();
		    while (line != null) {
		    	String[] values = line.split("\t");
		    	try {   			    	
		    	graph.putEdge(values[0], values[1]);		    	
				} catch (Exception e) {

				}
		    	line = reader.readLine();
		    }

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		    try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		
		return convertNetwork(graph);		
	}
	
	public Network convertNetwork(ConverterGraph graph) {
		
		Network network = new Network(false);		
		graph.normalize();
		for(ConverterEdge edge: graph.getEdges()) {
			network.addEdge((edge.getFrom().getId()), edge.getTo().getId(), true);
		}			
		return network;		
	}
	
	
	
}
