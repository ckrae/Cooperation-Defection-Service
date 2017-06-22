package i5.las2peer.services.cdService.simulation.dynamic;

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

	public Replicator(double[] value) {

		super(value);
	}

	/////////////// Methods /////////////////

	@Override
	protected boolean getNewStrategy(Agent agent, Simulation simulation) {

		Agent neighbour = simulation.getRandomNeighbour(agent);		
		if(neighbour == null) 
			return agent.getStrategy();
		
		Agent betterAgent = agent.comparePayoff(neighbour);
		if (!betterAgent.equals(agent)) {

			double probability = (neighbour.getPayoff() - agent.getPayoff()) / (getValues()[0] * Math
					.max(simulation.getNeighbourhood(neighbour).size(), simulation.getNeighbourhood(agent).size()));
			if (simulation.random.nextDouble(true, true) < probability)
				;
			return neighbour.getStrategy();

		}
		return agent.getStrategy();

	}
	
	@Override
	public DynamicType getDynamicType() {
		return Replicator.TYPE;
	}

}
