package i5.las2peer.services.cdService.simulation;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import i5.las2peer.services.cdService.data.network.Network;
import i5.las2peer.services.cdService.simulation.Agent;
import i5.las2peer.services.cdService.simulation.Simulation;
import i5.las2peer.services.cdService.simulation.dynamic.DynamicFactory;
import i5.las2peer.services.cdService.simulation.dynamic.DynamicType;
import i5.las2peer.services.cdService.simulation.game.GameFactory;
import sim.util.Bag;

/**
 * Example Test Class demonstrating a basic JUnit test structure.
 *
 */
public class SimulationTest {

	@Test
	public void networkTest() {

		Network network = new Network(0);
		for (int i = 0; i < 20; i++) {
			Agent agent = new Agent(i);
			network.addNode(agent);
		}
		Bag agents = new Bag(network.getAllNodes());
		network.addEdge(agents.get(3), agents.get(5), 1);
		network.addEdge(agents.get(3), agents.get(6), 1);
		network.addEdge(agents.get(3), agents.get(7), 1);
		
		((Agent) agents.get(3)).setPayoff(2.0);
		((Agent) agents.get(5)).setPayoff(5.0);
		((Agent) agents.get(6)).setPayoff(1.0);
		((Agent) agents.get(7)).setPayoff(1.0);
		((Agent) agents.get(5)).setStrategy(true);

		Simulation simulation = new Simulation(0, network, GameFactory.build(1, 1),
				DynamicFactory.build(DynamicType.UNCONDITIONAL_IMITATION));

		Agent agent = (Agent) agents.get(3);
		
		Bag neighbours = simulation.getNeighbourhood(agent);
		assertEquals(3, neighbours.size());
		double bestPayoff = agent.getPayoff();
		assertEquals(2.0, 2.0, agent.getPayoff());
		boolean bestStrategy = agent.getStrategy();
		assertEquals(bestStrategy, false);
		for (int i = 0; i < neighbours.size(); i++) {
			Agent neighbour = (Agent) neighbours.get(i);
			if (neighbour.getPayoff() > bestPayoff) {
				bestStrategy = neighbour.getStrategy();
				bestPayoff = neighbour.getPayoff();
			}
		}
		assertEquals(true, bestStrategy);
				
				
		assertEquals(3, neighbours.size());

		
	}
}
