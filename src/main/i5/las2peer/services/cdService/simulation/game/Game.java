package i5.las2peer.services.cdService.simulation.game;

import i5.las2peer.services.cdService.simulation.Agent;
import i5.las2peer.services.cdService.simulation.Simulation;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Bag;

public class Game {

	/////////////// Attributes ///////////////

	private final double payoffAA;
	private final double payoffAB;
	private final double payoffBA;
	private final double payoffBB;

	private final boolean StrategyA = true; // cooperate
	private final boolean StrategyB = false; // defect

	protected Game(double aa, double ab, double ba, double bb) {

		this.payoffAA = aa;
		this.payoffAB = ab;
		this.payoffBA = ba;
		this.payoffBB = bb;
	}

	/////////////// Methods ///////////////////

	/**
	 * Determine the total Payoff for a agent of all neighbour games
	 * 
	 * @param agent
	 * @param neighbours
	 * @return payoff
	 */
	public double getPayoff(Agent agent) {
		
		Bag neighbours = agent.getNeighbourhood();
		double payoff = 0.0;
		for (int i = 0, si = neighbours.size(); i < si; i++) {
			Agent neighbour = (Agent) neighbours.get(i);
			payoff += getPayoff(agent.getStrategy(), neighbour.getStrategy());
		}
		
		return payoff;
	}

	/**
	 * Determine the Payoff between two Strategies
	 * 
	 * @param myStrategy
	 * @param otherStrategy
	 * @return payoff
	 */
	protected double getPayoff(boolean myStrategy, boolean otherStrategy) {

		if (myStrategy == StrategyA) {
			if (otherStrategy == StrategyA) {
				return payoffAA;
			} else {
				return payoffAB;
			}
		}
		if (otherStrategy == StrategyA) {
			return payoffBA;
		}
		return payoffBB;
	}

	/**
	 * Get the Payoff Scheme as Double Array 
	 * 
	 * @return double array
	 */
	public double[] getPayoffScheme() {

		double[] result = { payoffAA, payoffAB, payoffBA, payoffBB };
		return result;
	}

}
