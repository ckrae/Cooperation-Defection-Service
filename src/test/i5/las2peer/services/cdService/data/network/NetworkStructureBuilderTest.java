package i5.las2peer.services.cdService.data.network;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class NetworkStructureBuilderTest {

	NetworkStructure network;
	List<NetworkStructureNode> nodes;
	List<NetworkStructureEdge> edges;
	
	@Test
	public void addNodeTest() {
		
		NetworkStructureBuilder<Integer> networkBuilder = new NetworkStructureBuilder<Integer>();
		networkBuilder.addNode(34);
		networkBuilder.addNode(11);
		
		network = networkBuilder.build();
		nodes = network.getNodes();
		assertEquals(2, nodes.size());
		assertEquals(0, nodes.get(0).getId());
		assertEquals(1, nodes.get(1).getId());
		
		networkBuilder.addNode(13);
		networkBuilder.addNode(17);
		networkBuilder.addNode(17);
		
		network = networkBuilder.build();
		nodes = network.getNodes();
		assertEquals(4, nodes.size());
		
		NetworkStructureBuilder<String> networkBuilderString = new NetworkStructureBuilder<String>();
		networkBuilderString.addNode("test");
		networkBuilderString.addNode("hello");
		networkBuilderString.addNode("123");
		
		network = networkBuilderString.build();
		nodes = network.getNodes();
		assertEquals(3, nodes.size());
		
		networkBuilderString.addNode("qwer");
		networkBuilderString.addNode("qwer");
		networkBuilderString.addNode("qwer");
		
		network = networkBuilderString.build();
		nodes = network.getNodes();
		assertEquals(4, nodes.size());
		assertEquals(0, nodes.get(0).getId());
		assertEquals(1, nodes.get(1).getId());
		assertEquals(2, nodes.get(2).getId());
		assertEquals(3, nodes.get(3).getId());
		
	}
	
	@Test
	public void addEdgeTest() {
		
		NetworkStructureBuilder<Integer> networkBuilder = new NetworkStructureBuilder<Integer>();
		
		//// add edge of non existing nodes
		networkBuilder.addEdge(4, 20);
		
		network = networkBuilder.build();
		nodes = network.getNodes();
		edges = network.getEdges();
		assertEquals(2, nodes.size());
		assertEquals(1, edges.size());
		
		networkBuilder.addEdge(20, 14);
		
		network = networkBuilder.build();
		nodes = network.getNodes();
		edges = network.getEdges();
		assertEquals(3, nodes.size());
		assertEquals(2, edges.size());
		
		//// add edges that already exists
		
		networkBuilder.addEdge(16, 17);
		networkBuilder.addEdge(16, 17);
		networkBuilder.addEdge(16, 17);
		
		network = networkBuilder.build();
		nodes = network.getNodes();
		edges = network.getEdges();
		assertEquals(5, nodes.size());
		assertEquals(3, edges.size());
		
		//// add edge of existing nodes
		networkBuilder.addNode(121);
		networkBuilder.addNode(122);
		networkBuilder.addEdge(121, 122);
		
		network = networkBuilder.build();
		nodes = network.getNodes();
		edges = network.getEdges();
		assertEquals(7, nodes.size());
		assertEquals(4, edges.size());
		
		//// add loop
		networkBuilder.addEdge(4, 4);
		
		network = networkBuilder.build();
		nodes = network.getNodes();
		edges = network.getEdges();
		assertEquals(7, nodes.size());
		assertEquals(4, edges.size());
		
		//// add reversed edges
		networkBuilder.addEdge(5, 7);
		networkBuilder.addEdge(7, 5);
		
		network = networkBuilder.build();
		nodes = network.getNodes();
		edges = network.getEdges();
		assertEquals(9, nodes.size());
		assertEquals(5, edges.size());		
		
	}
	

	
}
