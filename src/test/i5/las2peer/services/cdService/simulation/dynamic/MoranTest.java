package i5.las2peer.services.cdService.simulation.dynamic;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ec.util.MersenneTwisterFast;

@RunWith(MockitoJUnitRunner.class)
public class MoranTest {
	
	@Mock
	private MersenneTwisterFast random;
	
	@Test
	public void getNewStrategyAlgorithm() {
		
		Moran dynamic = new Moran();
		
		boolean strategy;
		int size = 3;
		boolean[] strategies;
		double[] payoff;		
		
		strategies = new boolean[] { true, false, false };
		payoff = new double[] { 4.0, 3.0, 3.0 };
		
		Mockito.when(random.nextDouble(true, true)).thenReturn(0.2);
		strategy = dynamic.getNewStrategy(size, strategies, payoff, random);
		assertEquals(true, strategy);
		
		Mockito.when(random.nextDouble(true, true)).thenReturn(0.5);
		strategy = dynamic.getNewStrategy(size, strategies, payoff, random);
		assertEquals(false, strategy);
		
	}
	
}
