package i5.las2peer.services.cdService.simulation.dynamic;

import ec.util.MersenneTwisterFast;
import i5.las2peer.services.cdService.simulation.Agent;
import i5.las2peer.services.cdService.simulation.Simulation;
import sim.util.Bag;

/**
 *
 * Moran-Like
 * 
 */
public class Moran extends Dynamic {

	/////////////// Attributes ////////////

	private static final long serialVersionUID = 1L;

	final static DynamicType TYPE = DynamicType.MORAN;

	/////////////// Constructor ////////////

	protected Moran() {

		super();
	}

	/////////////// Methods /////////////////

	@Override
	/// Dependencies
	public boolean getNewStrategy(Agent agent, Simulation simulation) {

		Bag neighbours = agent.getNeighbourhood();
		int size = neighbours.size();
		int round = simulation.getRound();
		MersenneTwisterFast random = simulation.random;
		
		boolean[] strategies = new boolean[size];
		double[] payoff = new double[size];
		strategies[0] = agent.getStrategy(round);
		payoff[0] = agent.getPayoff(round);
		for (int i = 1; i < size; i++) {
			Agent neighbour = (Agent) neighbours.get(i);
			strategies[i] = neighbour.getStrategy(round);
			payoff[i] = neighbour.getPayoff(round);
		}
		
		return getNewStrategy(round, strategies, payoff, null);
	}
	
	/// Algorithm
	protected boolean getNewStrategy(int size, boolean[] strategies, double[] payoff, MersenneTwisterFast random) {

		double totalPayoff = 0.0;
		for (int i = 0; i < size; i++) {			
			totalPayoff += payoff[i];
		}

		double[] probability = new double[size];
		for (int i = 0; i < size; i++) {
			probability[i] = payoff[i] / totalPayoff;
		}

		double randomDouble = random.nextDouble(true, true);
		for (int i = 0; i < size; i++) {
			double value = 0.0;
			for (int j = 0; j <= i; j++) {
				value += probability[j];
				if (randomDouble <= value) {					
					return strategies[i];
				}
			}
		}
		return strategies[0];
	}
	
	@Override
	public DynamicType getDynamicType() {
		return Moran.TYPE;
	}

}
