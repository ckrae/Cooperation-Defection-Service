package i5.las2peer.services.cdService.simulation.dynamic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import ec.util.MersenneTwisterFast;
import i5.las2peer.services.cdService.simulation.Agent;
import i5.las2peer.services.cdService.simulation.Simulation;
import sim.util.Bag;

@RunWith(MockitoJUnitRunner.class)
public class ReplicatorTest {

	@Mock
	private MersenneTwisterFast random;	

	@Mock Agent agent1;	
	@Mock Agent agent2;
	
	@Spy Replicator replicator;
	
	@Test
	public void testGetNewStrategyAlgorithm() {

		Replicator replicator = new Replicator(1);
		
		boolean strategy;
		boolean myStrategy = true;
		boolean otherStrategy = false;
		int myNeighSize = 1;
		int otherNeighSize = 2;	
		int value = 1;
		
		//// myPayoff > otherPayoff
		
		double myPayoff = 2.0;
		double otherPayoff = 1.0;
		
		myStrategy = true;
		otherStrategy = false;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize, value);		
		assertEquals(true, strategy);
		
		myStrategy = false;
		otherStrategy = true;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize, value);	
		assertEquals(false, strategy);
		
		myStrategy = false;
		otherStrategy = false;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize, value);	
		assertEquals(false, strategy);
		
		myStrategy = true;
		otherStrategy = true;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize, value);	
		assertEquals(true, strategy);
		
		//// myPayoff < otherPayoff
		
		myPayoff = 1.0;
		otherPayoff = 2.0;
		
		// low random
		
		Mockito.when(random.nextDouble(true, true)).thenReturn(0.2);
		
		myStrategy = true;
		otherStrategy = false;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize, value);		
		assertEquals(otherStrategy , strategy);
		
		myStrategy = false;
		otherStrategy = true;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize, value);	
		assertEquals(otherStrategy , strategy);
		
		myStrategy = false;
		otherStrategy = false;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize, value);	
		assertEquals(otherStrategy , strategy);
		
		myStrategy = true;
		otherStrategy = true;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize, value);	
		assertEquals(otherStrategy , strategy);
		
		// high random
		
		Mockito.when(random.nextDouble(true, true)).thenReturn(0.8);
		
		myStrategy = true;
		otherStrategy = false;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize, value);		
		assertEquals(myStrategy, strategy);
		
		myStrategy = false;
		otherStrategy = true;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize, value);	
		assertEquals(myStrategy, strategy);
		
		myStrategy = false;
		otherStrategy = false;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize, value);	
		assertEquals(myStrategy, strategy);
		
		myStrategy = true;
		otherStrategy = true;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize, value);	
		assertEquals(myStrategy, strategy);
		
	}
	
	@Test
	public void testGetNewStrategyDependencies() {
		
		Simulation simulation = new Simulation(2);
		boolean myStrategy = false;
		boolean otherStrategy = true;
		double myPayoff = 4.0;
		double otherPayoff = 3.0;
		int myNeighSize = 2;
		int otherNeighSize = 2;
		double value = 1.5;
		
		
		Mockito.when(agent1.getPayoff(Mockito.anyInt())).thenReturn(myPayoff);
		Mockito.when(agent2.getPayoff(Mockito.anyInt())).thenReturn(otherPayoff);		
		Mockito.when(agent1.getStrategy(Mockito.anyInt())).thenReturn(myStrategy);
		Mockito.when(agent2.getStrategy(Mockito.anyInt())).thenReturn(otherStrategy);		
		Mockito.when(agent1.getRandomNeighbour(Mockito.any())).thenReturn(agent2);
		Mockito.when(agent2.getRandomNeighbour(Mockito.any())).thenReturn(agent1);
		
		Bag bag = new Bag();
		bag.add(1);
		bag.add(2);
		Mockito.when(agent1.getNeighbourhood()).thenReturn(bag);
		Mockito.when(agent2.getNeighbourhood()).thenReturn(bag);
		Mockito.when(replicator.getValues()).thenReturn(new double[]{value});
		
		boolean strategy = replicator.getNewStrategy(agent1, simulation);
		
		Mockito.verify(replicator).getNewStrategy(Mockito.eq(myStrategy), Mockito.eq(otherStrategy), Mockito.eq(myPayoff), Mockito.eq(otherPayoff), Mockito.isA(MersenneTwisterFast.class), Mockito.eq(myNeighSize), Mockito.eq(otherNeighSize), Mockito.eq(value));  
		

	}
}
