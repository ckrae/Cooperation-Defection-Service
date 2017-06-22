package i5.las2peer.services.cdService.simulation.game;

import i5.las2peer.services.cdService.simulation.Agent;
import i5.las2peer.services.cdService.simulation.Simulation;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Bag;

/**
 * The Game
 */

public class Game implements Steppable {

	/////////////// Attributes ///////////////

	private static final long serialVersionUID = 1L;

	/**
	 * Payoff Matrix Values
	 *  ( cc, cd ) ( R , S ) 
	 *  ( dc, dd ) ( T , P )
	 */
	
	private final double cc;
	private final double cd;
	private final double dd;
	private final double dc;

	private final boolean StrategyA = true; // cooperate
	private final boolean StrategyB = false; // defect

	public Game(double a, double b, double c, double d) {

		this.cc = a;
		this.dc = b;
		this.cd = c;
		this.dd = d;
	}

	/////////////// Methods ///////////////////

	/**
	 * Determine the payoff values of every node
	 */
	@Override
	public void step(SimState state) {

		Simulation simulation = (Simulation) state;
		updatePayoff(simulation);

	}

	/**
	 * Updates the payoff Values for all nodes of a given network
	 * 
	 * @param agent
	 * @param neighbours
	 * @return payoff
	 */
	private void updatePayoff(Simulation simulation) {

		Bag agents = new Bag(simulation.getNetwork().getAllNodes());
		final int size = agents.size();
		double[] newValues = new double[size];

		// determine new values
		for (int i = 0; i < size; i++) {
			Agent agent = (Agent) agents.get(i);
			newValues[i] = getPayoff(agent, simulation.getNeighbourhood(agent));
		}

		// adopt new values
		for (int i = 0; i < size; i++) {
			Agent agent = (Agent) agents.get(i);
			agent.setPayoff(newValues[i]);
		}

	}

	/**
	 * Determine the total Payoff for a agent of all neighbour games
	 * 
	 * @param agent
	 * @param neighbours
	 * @return payoff
	 */
	private double getPayoff(Agent agent, Bag neighbours) {

		double payoff = 0.0;
		for (int i = 0, si=neighbours.size(); i < si; i++) {
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
	private double getPayoff(boolean myStrategy, boolean otherStrategy) {

		double payoff = 0.0;
		if (myStrategy == StrategyA) {
			if (otherStrategy == StrategyA) {
				// cooperate cooperate - reward
				payoff += cc;

			} else if (otherStrategy == StrategyB) {
				// cooperate defect - sucker
				payoff += cd;
			}
		} else if (myStrategy == StrategyB) {
			if (otherStrategy == StrategyA) {
				// defect cooperate - temptation
				payoff += dc;

			} else if (otherStrategy == StrategyB) {
				// defect defect - punishment
				payoff += dd;
			}
		}
		return payoff;

	}

	/**
	 * Get the Payoff Scheme as Double Array {cc, cd, dc, dd}
	 * 
	 * @return double array
	 */
	public double[] getPayoffScheme() {

		double[] result = { cc, cd, dc, dd };
		return result;
	}

}
