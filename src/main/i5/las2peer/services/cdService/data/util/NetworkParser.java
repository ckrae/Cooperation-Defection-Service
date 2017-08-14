package i5.las2peer.services.cdService.data.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import i5.las2peer.services.cdService.data.network.NetworkStructure;
import i5.las2peer.services.cdService.data.network.NetworkStructureBuilder;

public class NetworkParser {
	
	public NetworkParser() {
		
	}
	
	String network;
	
	public NetworkParser(String network) {
		this.network = network;
	}

	public NetworkStructure readNetwork() {
		
		NetworkStructureBuilder networkBuilder = new NetworkStructureBuilder();
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(network));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			
		    String line = reader.readLine();
		    while (line != null) {
		    	String[] values = line.split("\t");
		    	try {   			    	
		    	networkBuilder.addEdge(values[0], values[1]);		    	
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
		
		return networkBuilder.build();		
	}
	

	
	
	
}
