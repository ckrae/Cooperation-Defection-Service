package i5.las2peer.services.cdService.simulation.dynamic;

import i5.las2peer.services.cdService.simulation.Agent;
import i5.las2peer.services.cdService.simulation.Simulation;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Bag;

/**
 *
 * Dynamics are used to update the nodes strategies The concrete update rules
 * are implemented as sub classes
 * 
 */
public abstract class Dynamic implements Steppable {

	/////////////// Attributes ///////////

	private static final long serialVersionUID = 1L;

	/**
	 * generation counter
	 */
	private long generationCounter;

	/**
	 * parameters of the dynamic
	 */
	private double[] values;

	/////////////// Constructor //////////

	public Dynamic() {

		this(null);
	}

	public Dynamic(double[] values) {

		generationCounter = 0;
		this.values = values;
	}

	/////////////// Methods ///////////////

	/**
	 * Determine the new strategy of every node
	 */
	@Override
	public void step(SimState state) {

		Simulation simulation = (Simulation) state;
		updateStrategies(simulation);
	}

	/**
	 * Updates the strategy of all nodes of a given network
	 * 
	 * @param network
	 * 
	 */
	private void updateStrategies(Simulation simulation) {

		Bag agents = new Bag(simulation.getNetwork().getAllNodes());
		int size = agents.size();
		boolean[] newStrategies = new boolean[size];

		// determine new values
		for (int i = 0; i < size; i++) {
			Agent agent = (Agent) agents.get(i);
			newStrategies[i] = getNewStrategy(agent, simulation);
		}

		// adopt new values
		for (int i = 0; i < size; i++) {
			Agent agent = (Agent) agents.get(i);
			agent.setStrategy(newStrategies[i]);
		}

		generationCounter++;

	}

	public long generationCount() {
		return this.generationCounter;
	}

	public double[] getValues() {
		return this.values;
	}

	/////////////// Override ///////////////

	/**
	 * this method determines the concrete update rule dynamic it have to be
	 * implemented in the sub classes
	 * 
	 */
	protected abstract boolean getNewStrategy(Agent agent, Simulation simulation);

	public abstract DynamicType getDynamicType();

}
