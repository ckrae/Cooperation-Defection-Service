
package i5.las2peer.services.cdService.simulation;

import i5.las2peer.services.cdService.simulation.dynamics.Dynamic;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Bag;

public class Agent implements Steppable {
	private static final long serialVersionUID = 1;

	private boolean currentStrategy;
	private double currentPayoff;

	public Agent() {

		this.currentStrategy = false;
		this.currentPayoff = 0.0;

	}

	/////////////////// Steps ///////////////////////////
	@Override
	public void step(SimState state) {
		Simulation simulation = (Simulation) state;

	}

	///////// Payoff //////////

	public void updatePayoff(SimState state) {
		Simulation simulation = (Simulation) state;
		double payoff = simulation.getGame().getPayoff(this, new Bag(simulation.getNeighbourhood(this)));

		this.currentPayoff = payoff; 

	}

	///////// Dynamic //////////

	public void updateDynamic(SimState state) {
		Simulation simulation = (Simulation) state;
		Dynamic dynamic = simulation.getDynamic();
		boolean strategy = dynamic.getNewStrategy(this, simulation);
		this.currentStrategy = strategy;
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

}
