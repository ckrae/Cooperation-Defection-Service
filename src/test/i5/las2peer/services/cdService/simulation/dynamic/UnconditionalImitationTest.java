package i5.las2peer.services.cdService.simulation.dynamic;

import static org.junit.Assert.*;

import org.junit.Test;

public class UnconditionalImitationTest {

	@Test
	public void getNewStrategyAlgorithm() {

		UnconditionalImitation dynamic = new UnconditionalImitation();

		boolean strategy;
		int size = 3;
		boolean[] strategies;
		double[] payoff;

		strategies = new boolean[] { false, false, true };
		payoff = new double[] { 3.0, 1.0, 2.0 };
		strategy = dynamic.getNewStrategy(size, strategies, payoff);
		assertEquals(false, strategy);

		strategies = new boolean[] { false, false, true };
		payoff = new double[] { 2.0, 1.0, 3.0 };
		strategy = dynamic.getNewStrategy(size, strategies, payoff);
		assertEquals(true, strategy);

	}

}
