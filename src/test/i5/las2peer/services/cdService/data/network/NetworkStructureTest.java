package i5.las2peer.services.cdService.data.network;

import static org.junit.Assert.*;

import org.junit.Test;

public class NetworkStructureTest {
	
	NetworkStructure network;
	
	@Test
	public void getEdgesTest() {
		
		network = new NetworkStructure();
		network.addNode();
		network.addNode();
		network.addNode();
		network.addNode();
		network.addNode();
		
		network.addEdge(0, 2);
		network.addEdge(1, 3);
		network.addEdge(3, 2);
		network.addEdge(2, 1);
		
		assertEquals(1, network.getEdges(0).size());
		assertEquals(2, network.getEdges(1).size());
		assertEquals(3, network.getEdges(2).size());
		assertEquals(2, network.getEdges(3).size());
		assertEquals(0, network.getEdges(4).size());
		
	}
	
}
