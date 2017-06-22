package i5.las2peer.services.cdService.simulation.dynamic;

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

	public Moran() {

		super();
	}

	/////////////// Methods /////////////////

	@Override
	protected boolean getNewStrategy(Agent agent, Simulation simulation) {

		Bag neighbours = new Bag(simulation.getNetwork().getAllNodes());
		int size = neighbours.size();

		double totalPayoff = 0.0;
		for (int i = 0; i < size; i++) {
			Agent neighbour = (Agent) neighbours.get(i);
			totalPayoff += neighbour.getPayoff();
		}

		double[] probability = new double[size];
		for (int i = 0; i < size; i++) {
			Agent neighbour = (Agent) neighbours.get(i);
			probability[i] = neighbour.getPayoff() / totalPayoff;
		}

		double random = simulation.random.nextDouble(true, true);
		for (int i = 0; i < size; i++) {

			double val = 0.0;
			for (int j = 0; j <= i; j++) {
				val += probability[j];
				if (random <= val) {
					Agent neighbour = (Agent) neighbours.get(i);
					return neighbour.getStrategy();
				}
			}
		}
		return agent.getStrategy();
	}
	
	@Override
	public DynamicType getDynamicType() {
		return Moran.TYPE;
	}

}
