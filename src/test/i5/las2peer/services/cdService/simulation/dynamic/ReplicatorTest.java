package i5.las2peer.services.cdService.simulation.dynamic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ec.util.MersenneTwisterFast;

@RunWith(MockitoJUnitRunner.class)
public class ReplicatorTest {

	@Mock
	private MersenneTwisterFast random;

	@Test
	public void testGetNewStrategyAlgorithm() {

		Replicator replicator = new Replicator(1);
		
		boolean strategy;
		boolean myStrategy = true;
		boolean otherStrategy = false;
		int myNeighSize = 1;
		int otherNeighSize = 2;		
		
		//// myPayoff > otherPayoff
		
		double myPayoff = 2.0;
		double otherPayoff = 1.0;
		
		myStrategy = true;
		otherStrategy = false;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize);		
		assertEquals(true, strategy);
		
		myStrategy = false;
		otherStrategy = true;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize);	
		assertEquals(false, strategy);
		
		myStrategy = false;
		otherStrategy = false;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize);	
		assertEquals(false, strategy);
		
		myStrategy = true;
		otherStrategy = true;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize);	
		assertEquals(true, strategy);
		
		//// myPayoff < otherPayoff
		
		myPayoff = 1.0;
		otherPayoff = 2.0;
		
		// low random
		
		Mockito.when(random.nextDouble(true, true)).thenReturn(0.2);
		
		myStrategy = true;
		otherStrategy = false;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize);		
		assertEquals(otherStrategy , strategy);
		
		myStrategy = false;
		otherStrategy = true;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize);	
		assertEquals(otherStrategy , strategy);
		
		myStrategy = false;
		otherStrategy = false;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize);	
		assertEquals(otherStrategy , strategy);
		
		myStrategy = true;
		otherStrategy = true;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize);	
		assertEquals(otherStrategy , strategy);
		
		// high random
		
		Mockito.when(random.nextDouble(true, true)).thenReturn(0.8);
		
		myStrategy = true;
		otherStrategy = false;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize);		
		assertEquals(myStrategy, strategy);
		
		myStrategy = false;
		otherStrategy = true;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize);	
		assertEquals(myStrategy, strategy);
		
		myStrategy = false;
		otherStrategy = false;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize);	
		assertEquals(myStrategy, strategy);
		
		myStrategy = true;
		otherStrategy = true;
		strategy = replicator.getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random,
				myNeighSize, otherNeighSize);	
		assertEquals(myStrategy, strategy);
		
	}

}
