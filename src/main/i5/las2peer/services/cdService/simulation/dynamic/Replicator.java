package i5.las2peer.services.cdService.simulation.dynamic;

import ec.util.MersenneTwisterFast;
import i5.las2peer.services.cdService.simulation.Agent;
import i5.las2peer.services.cdService.simulation.Simulation;

/**
 *
 * Replicator
 * 
 */
public class Replicator extends Dynamic {

	/////////////// Attributes ////////////

	private static final long serialVersionUID = 1L;

	final static DynamicType TYPE = DynamicType.REPLICATOR;

	/////////////// Constructor ////////////

	protected Replicator(double[] value) {

		super(value);
	}
	
	public Replicator(double value) {
		this(new double[]{value});
	}

	/////////////// Methods /////////////////

	/// Dependencies
	@Override
	public boolean getNewStrategy(Agent agent, Simulation simulation) {
		
		int round = simulation.getRound()-1;
		Agent neighbour = agent.getRandomNeighbour();
		if (neighbour == null)
			return agent.getStrategy(round);
		
		boolean myStrategy = agent.getStrategy(round);
		boolean otherStrategy = neighbour.getStrategy(round);
		double myPayoff = agent.getPayoff(round);
		double otherPayoff = neighbour.getPayoff(round);
		MersenneTwisterFast random = simulation.random;
		int myNeighSize = agent.getNeighbourhood().size();
		int otherNeighSize = neighbour.getNeighbourhood().size();
		
		return getNewStrategy(myStrategy, otherStrategy, myPayoff, otherPayoff, random, myNeighSize, otherNeighSize);
	}
	
	/// Algorithm
	protected boolean getNewStrategy(boolean myStrategy, boolean otherStrategy, double myPayoff, double otherPayoff,
			MersenneTwisterFast random, int myNeighSize, int otherNeighSize) {

		if (otherPayoff > myPayoff) {
			double probability = (otherPayoff - myPayoff) / (getValues()[0] * Math.max(otherNeighSize, myNeighSize));
			if (random.nextDouble(true, true) < probability) {
				return otherStrategy;
			}
		}
		return myStrategy;
	}

	@Override
	public DynamicType getDynamicType() {
		return Replicator.TYPE;
	}

}
