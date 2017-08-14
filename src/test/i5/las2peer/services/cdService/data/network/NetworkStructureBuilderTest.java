package i5.las2peer.services.cdService.data.network;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class NetworkStructureBuilderTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	NetworkStructure network;
	List<NetworkStructureNode> nodes;
	List<NetworkStructureEdge> edges;

	@Test
	public void addNodeTest() {

		NetworkStructureBuilder networkBuilder = new NetworkStructureBuilder();
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
		assertEquals(0, nodes.get(0).getId());
		assertEquals(1, nodes.get(1).getId());
		assertEquals(2, nodes.get(2).getId());
		assertEquals(3, nodes.get(3).getId());
	}

	@Test
	public void addEdgeNewNodes() {

		NetworkStructureBuilder networkBuilder = new NetworkStructureBuilder();

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

	}
	
	@Test
	public void addEdgeExisting() {
		
		NetworkStructureBuilder networkBuilder = new NetworkStructureBuilder();
		networkBuilder.addEdge(16, 17);
		networkBuilder.addEdge(16, 17);
		
		network = networkBuilder.build();
		nodes = network.getNodes();
		edges = network.getEdges();
		assertEquals(2, nodes.size());
		assertEquals(1, edges.size());

	}

	@Test
	public void addEdgeExistingNodes() {
				
		NetworkStructureBuilder networkBuilder = new NetworkStructureBuilder();

		//// add edge of existing nodes
		networkBuilder.addNode(121);
		networkBuilder.addNode(122);
		networkBuilder.addEdge(121, 122);

		network = networkBuilder.build();
		nodes = network.getNodes();
		edges = network.getEdges();
		assertEquals(2, nodes.size());
		assertEquals(1, edges.size());
	}
	
	@Test
	public void addEdgeLoop() {

		NetworkStructureBuilder networkBuilder = new NetworkStructureBuilder();
		networkBuilder.addEdge(4, 4);
		
		network = networkBuilder.build();
		nodes = network.getNodes();
		edges = network.getEdges();
		assertEquals(1, nodes.size());
		assertEquals(0, edges.size());
		
	}
	
	@Test
	public void addReverseEdges() {
		
		NetworkStructureBuilder networkBuilder = new NetworkStructureBuilder();

		networkBuilder.addEdge(5, 7);
		networkBuilder.addEdge(7, 5);
		
		network = networkBuilder.build();
		nodes = network.getNodes();
		edges = network.getEdges();
		assertEquals(2, nodes.size());
		assertEquals(1, edges.size());		
		
	}

}
