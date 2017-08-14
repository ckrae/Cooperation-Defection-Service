package i5.las2peer.services.cdService.simulation.dynamic;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalMatchers;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import ec.util.MersenneTwisterFast;
import i5.las2peer.services.cdService.simulation.Agent;
import i5.las2peer.services.cdService.simulation.Simulation;
import sim.util.Bag;

@RunWith(MockitoJUnitRunner.class)
public class MoranTest {
	
	@Spy
	Moran moran;
	
	@Mock
	private MersenneTwisterFast random;
		
	@Mock
	Agent agent1;
	@Mock
	Agent agent2;
	@Mock
	Agent agent3;
	@Mock
	Agent agent4;

	
	
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
	
	@Test
	public void testGetNewStrategyDependencies() {		
		
		Simulation simulation = new Simulation();
		
		int size = 4;
		
		boolean[] strategy = new boolean[size];		
		strategy[0] = false;
		strategy[1] = true;
		strategy[2] = true;
		strategy[3] = false;
		
		double[] payoff = new double[size];
		payoff[0] = 0.0;
		payoff[1] = 1.0;
		payoff[2] = 2.0;
		payoff[3] = 3.0;
		
		Bag neighbourBag = new Bag();
		neighbourBag.add(agent1);
		neighbourBag.add(agent2);
		neighbourBag.add(agent3);		

		Mockito.when(agent4.getNeighbourhood()).thenReturn(neighbourBag);
		
		Mockito.when(agent1.getPayoff(Matchers.anyInt())).thenReturn(payoff[0]);
		Mockito.when(agent2.getPayoff(Matchers.anyInt())).thenReturn(payoff[1]);
		Mockito.when(agent3.getPayoff(Matchers.anyInt())).thenReturn(payoff[2]);
		Mockito.when(agent4.getPayoff(Matchers.anyInt())).thenReturn(payoff[3]);
		Mockito.when(agent1.getStrategy(Matchers.anyInt())).thenReturn(strategy[0]);
		Mockito.when(agent2.getStrategy(Matchers.anyInt())).thenReturn(strategy[1]);
		Mockito.when(agent3.getStrategy(Matchers.anyInt())).thenReturn(strategy[2]);
		Mockito.when(agent4.getStrategy(Matchers.anyInt())).thenReturn(strategy[3]);						
		
		boolean resultStrategy = moran.getNewStrategy(agent4, simulation);

		Mockito.verify(moran, Mockito.times(1)).getNewStrategy(Matchers.eq(size), AdditionalMatchers.aryEq(strategy),
				AdditionalMatchers.aryEq(payoff), Matchers.isA(MersenneTwisterFast.class));

	}

	
}
