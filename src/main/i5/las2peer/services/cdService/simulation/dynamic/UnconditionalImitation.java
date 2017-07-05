package i5.las2peer.services.cdService.simulation.dynamic;

import i5.las2peer.services.cdService.simulation.Agent;
import i5.las2peer.services.cdService.simulation.Simulation;
import sim.util.Bag;

/**
 *
 * Unconditional Imitation
 * 
 * Search Neighbor with best Payoff and adopt his strategy
 * 
 */
public class UnconditionalImitation extends Dynamic {

	/////////////// Attributes ////////////

	private static final long serialVersionUID = 1L;

	final static DynamicType TYPE = DynamicType.UNCONDITIONAL_IMITATION;

	/////////////// Constructor ////////////

	protected UnconditionalImitation() {

		super();
	}

	/////////////// Methods ///////////////

	@Override
	/// Dependencies
	public boolean getNewStrategy(Agent agent, Simulation simulation) {
		
		int round = simulation.getRound()-1;
		Bag neighbours = agent.getNeighbourhood();
		int size = neighbours.size();		
		boolean[] strategies = new boolean[size];
		double[] payoff = new double[size];
		strategies[0] = agent.getStrategy(round);
		payoff[0] = agent.getPayoff(round);
		for (int i = 1; i < size; i++) {
			Agent neighbour = (Agent) neighbours.get(i);
			strategies[i] = neighbour.getStrategy(round);
			payoff[i] = neighbour.getPayoff(round);
		}
		return getNewStrategy(size, strategies, payoff);
	}
	
	
	/// Algorithm
	protected boolean getNewStrategy(int size, boolean[] strategies, double[] payoff) {

		double bestPayoff = payoff[0];
		boolean bestStrategy = strategies[0];
		for (int i = 1; i < size; i++) {
			if (payoff[i] > bestPayoff) {
				bestPayoff = payoff[i];
				bestStrategy = strategies[i];
			}
		}
		return bestStrategy;
	}

	@Override
	public DynamicType getDynamicType() {
		return UnconditionalImitation.TYPE;
	}

}
