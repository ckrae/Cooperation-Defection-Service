
package i5.las2peer.services.cdService.simulation;

import sim.engine.SimState;
import sim.engine.Steppable;

public class Agent implements Steppable {
	private static final long serialVersionUID = 1;

	private final long nodeId;
	
	private boolean currentStrategy;
	private double currentPayoff;

	public Agent(long nodeId) {
		
		this.nodeId = nodeId;
		this.currentStrategy = false;
		this.currentPayoff = 0.0;

	}

	/////////////////// Steps ///////////////////////////
	@Override
	public void step(SimState state) {
		Simulation simulation = (Simulation) state;

	}

	
	////////////////// Utility ///////////////////////////

	/**
	 * Compares agents payoff values
	 * 
	 * @param agent
	 *            of comparison
	 * @return agent with higher payoff
	 */
	public Agent comparePayoff(Agent neighbour) {

		if (this.getPayoff() > neighbour.getPayoff()) {
			return this;
		}

		return neighbour;
	}

	//////////////////// Getter / Setter ///////////////

	public boolean getStrategy() {
		return this.currentStrategy;
	}

	public void setStrategy(boolean strategy) {
		this.currentStrategy = strategy;
	}

	public double getPayoff() {
		return currentPayoff;
	}

	public void setPayoff(double payoff) {
		this.currentPayoff = payoff;
	}

	public long getNodeId() {
		return nodeId;
	}

}
