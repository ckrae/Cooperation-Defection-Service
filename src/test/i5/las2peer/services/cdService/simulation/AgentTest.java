package i5.las2peer.services.cdService.simulation;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ec.util.MersenneTwisterFast;
import sim.field.network.Edge;
import sim.field.network.Network;
import sim.util.Bag;

@RunWith(MockitoJUnitRunner.class)
public class AgentTest {
	
	@Mock
	private MersenneTwisterFast random;	
	
	@Mock Agent agent;
	@Mock Agent agent1;	
	@Mock Agent agent2;
	@Mock Agent agent3;
	
	
	@Test
	public void getRandomNeighbourTest() {
		
		Agent result;
		Bag agents = new Bag(3);	
				
		Mockito.when(agent.getNeighbourhood()).thenReturn(agents);
		Mockito.when(agent.getRandomNeighbour(Mockito.any(MersenneTwisterFast.class))).thenCallRealMethod();
		
		// no neighbor
		
		result = agent.getRandomNeighbour(random);
		assertEquals(null, result);	
		
		// random neighbor
		
		agents.add(agent1);		
		agents.add(agent2);
		agents.add(agent3);	
		
		Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(0);
		result = agent.getRandomNeighbour(random);
		assertEquals(agent1, result);	
		
		Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(1);
		result = agent.getRandomNeighbour(random);
		assertEquals(agent2, result);
		
		Mockito.when(random.nextInt(Mockito.anyInt())).thenReturn(2);
		result = agent.getRandomNeighbour(random);
		assertEquals(agent3, result);
	
	}
	
	@Test
	public void getNeighbourhoodTest() {
		
		// Calculate new Bag
		
		Bag resultBag;
		Agent agent = new Agent();		
		Network network = new Network(false);
		network.addNode(agent);
		network.addNode(agent1);
		network.addNode(agent2);
		network.addNode(agent3);
		agent.setNetwork(network);
		
		resultBag = agent.calculateNeighbourhood(network);
		assertNotNull(resultBag);
		assertEquals(0, resultBag.size());
		
		network.addEdge(agent, agent1, true);
		resultBag = agent.calculateNeighbourhood(network);
		assertNotNull(resultBag);
		assertEquals(1, resultBag.size());
		assertEquals(agent1, resultBag.get(0));
		
		network.addEdge(agent2, agent, true);
		network.addEdge(agent, agent3, true);
		resultBag = agent.calculateNeighbourhood(network);
		assertNotNull(resultBag);
		assertEquals(3, resultBag.size());
		assertEquals(agent1, resultBag.get(0));
		assertEquals(agent2, resultBag.get(1));
		assertEquals(agent3, resultBag.get(2));	
		
		// Store the old Bag
		
		Bag resultBag1;
		Bag resultBag2;
		resultBag1 = agent.getNeighbourhood();
		assertEquals(resultBag.size(), resultBag1.size());
		
		resultBag2 = agent.getNeighbourhood();
		assertEquals(resultBag1, resultBag2);

	}

	
}
