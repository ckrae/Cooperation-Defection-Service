package i5.las2peer.services.cdService.data.network;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;

import sim.field.network.Network;
import sim.util.Bag;

public class NetworkAdapterTest {
	
	@Test
	public void EdgeCountTest() {
		
		int edges;
		
		Network network = new Network(false);
		network.addEdge(1, 2, true);
		
		edges = NetworkAdapter.countEdges(network);
		assertEquals(1, edges);
		
		network.addEdge(1, 3, true);
		network.addEdge(2, 4, true);
		
		edges = NetworkAdapter.countEdges(network);
		assertEquals(3, edges);
		
	}
	
	@Test
	public void getSubNetworkTest() {
		
		Network network = new Network(false);
		Network subNetwork;
		Bag nodes;
		network.addEdge(1, 2, true);
		network.addEdge(1, 3, true);
		network.addEdge(2, 4, true);
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(1);		
		
		subNetwork = NetworkAdapter.getSubNetwork(network, list);
		assertNotNull(subNetwork);
		nodes = subNetwork.getAllNodes();
		assertEquals(1, nodes.size());
		assertEquals(1, nodes.get(0));
		
		list.add(4);
		list.add(2);		
		
		subNetwork = NetworkAdapter.getSubNetwork(network, list);
		assertNotNull(subNetwork);
		nodes = subNetwork.getAllNodes();
		assertEquals(3, nodes.size());
		assertEquals(1, nodes.get(0));
		assertEquals(2, nodes.get(1));
		assertEquals(4, nodes.get(2));
		assertNotNull(subNetwork.getEdge(1, 2));
		
		
	}
	

	
}
