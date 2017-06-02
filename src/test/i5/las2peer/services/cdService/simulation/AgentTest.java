

package i5.las2peer.services.cdService.simulation;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import sim.field.network.Edge;
import sim.field.network.Network;
import sim.util.Bag;

public class AgentTest 
{
	   

	public Network generateNetwork() {
		
		Network network = new Network(false);		
        for(int i = 0; i < 40; i++)
        {
            Agent agent = new Agent();
            network.addNode(agent);            
        }
        Bag agents = new Bag(network.getAllNodes());
        network.addEdge(agents.get(3), agents.get(5), 1);
        network.addEdge(agents.get(3), agents.get(6), 1);
        network.addEdge(agents.get(3), agents.get(7), 1);
		
		return network;
	}

	
	
	@Test
	public void NetworkTest() {
		
		Network network = generateNetwork();
		Bag agents = new Bag(network.getAllNodes());
		assertTrue(agents.size() == 40);
		Edge edge = network.getEdge(agents.get(3), agents.get(5));
		assertTrue(!edge.equals(null));
		
    	int index = network.getNodeIndex(agents.get(3));
    	assertTrue(index == 3);
    	Agent node = (Agent) agents.get(index);
    	assertTrue(node.equals(agents.get(3)));
    	
    	Bag edges = new Bag(network.getEdges(node, agents));
    	assertTrue(edges.size() == 3);
    	
    	//Bag neighbours = node.getNeighbourhood();
		//assertTrue(neighbours.size() == 3);
	}


} 
    
