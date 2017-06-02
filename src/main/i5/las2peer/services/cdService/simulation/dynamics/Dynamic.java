package i5.las2peer.services.cdService.simulation.dynamics;

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
	private double value;

	/////////////// Constructor //////////

	public Dynamic() {

		this(0.0);
	}

	public Dynamic(double value) {

		generationCounter = 0;
		this.value = value;
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
	public void updateStrategies(Simulation simulation) {

		Bag agents = new Bag(simulation.getNetwork().getAllNodes());
		boolean[] newStrategies = new boolean[agents.size()];

		// determine new values
		for (int i = 0; i < agents.size(); i++) {
			Agent agent = (Agent) agents.get(i);
			newStrategies[i] = getNewStrategy(agent, simulation);
		}

		// adopt new values
		for (int i = 0; i < agents.size(); i++) {
			Agent agent = (Agent) agents.get(i);
			agent.setStrategy(newStrategies[i]);
		}

		generationCounter++;

	}

	public long generationCount() {
		return this.generationCounter;
	}

	public double getValue() {
		return value;
	}

	/////////////// Override ///////////////

	/**
	 * this method determines the concrete update rule dynamic it have to be
	 * implemented in the sub classes
	 * 
	 */
	public boolean getNewStrategy(Agent agent, Simulation simulation) {
		return false;
	}

}
