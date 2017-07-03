package i5.las2peer.services.cdService.simulation;

import static org.junit.Assert.*;

import org.junit.Test;

import sim.field.network.Network;
import sim.util.Bag;

public class SimulationManagerTest {

	@Test
	public void createAgentsTest() {
		
		Network network = new Network(false);
		int size = 10;		

		for (int i = 0; i < size; i++) {			
			network.addNode(i);
		}

		for (int i = 0; i < 5; i++) {			
			for (int j = 5, jSize = 10; j < jSize; j++) {
				network.addEdge(i, j, true);
			}
		}
			
		assertEquals(10, network.getAllNodes().size());
		
		
		SimulationManager simulationManager = new SimulationManager();			
		Network agentNetwork = simulationManager.createAgents(network);
		
		assertNotNull(agentNetwork );
		Bag agents = agentNetwork.getAllNodes();
		assertEquals(10, agents.size());
		assertEquals(Agent.class, agents.get(0).getClass());
		assertEquals(Agent.class, agents.get(9).getClass());
		assertEquals(0, ((Agent) agents.get(0)).getNodeId());
		assertEquals(9, ((Agent) agents.get(9)).getNodeId());	
		
	}
	
	
}
