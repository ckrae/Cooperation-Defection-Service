
package i5.las2peer.services.cdService.simulation;

import java.util.ArrayList;
import java.util.List;

import ec.util.MersenneTwisterFast;
import i5.las2peer.services.cdService.data.simulation.AgentData;
import i5.las2peer.services.cdService.simulation.dynamic.Dynamic;
import i5.las2peer.services.cdService.simulation.game.Game;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.field.network.Edge;
import sim.field.network.Network;
import sim.util.Bag;

public class Agent implements Steppable {

	private static final long serialVersionUID = 1;

	private int nodeId;
	private int stepped;

	private List<Boolean> strategies;
	private List<Double> payoff;
	
	private double currentPayoff;
	private boolean currentStrategy;

	public MersenneTwisterFast random;
	private Network network;
	private Game game;
	private Dynamic dynamic;

	public Agent(int nodeId) {
		
		this.nodeId = nodeId;
		strategies = new ArrayList<Boolean>();
		strategies.add(false);
		payoff = new ArrayList<Double>();
		payoff.add(0.0);
	}
	
	public Agent() {
		
	}

	public void initialize(boolean strategy, Simulation simulation) {
		
		stepped = 0;
		
		strategies = new ArrayList<Boolean>();
		currentStrategy = strategy;
		strategies.add(strategy);
		currentPayoff = 0.0;
		payoff = new ArrayList<Double>();
		payoff.add(0.0);
		
		random = simulation.random;
		network = simulation.getNetwork();
		game = simulation.getGame();
		dynamic = simulation.getDynamic();

	}

	/////////////////// Steps ///////////////////////////

	public void step(SimState state) {
		Simulation simulation = (Simulation) state;
		currentPayoff = game.getPayoff(this);
		payoff.add(currentPayoff);

	}

	public void updateDynamicStep(SimState state) {
		Simulation simulation = (Simulation) state;
		currentStrategy = dynamic.getNewStrategy(this, simulation);
		strategies.add(dynamic.getNewStrategy(this, simulation));

	}

	////////////////// Utility ///////////////////////////

	/**
	 * Get a random neighbour agent
	 * 
	 * @return agent
	 */
	public Agent getRandomNeighbour() {

		Bag agents = new Bag(getNeighbourhood());
		if (agents.size() > 0) {
			return (Agent) agents.get(random.nextInt(agents.size()));
		}
		return null;
	}

	public Bag getNeighbourhood() {

		Bag nodes = new Bag(network.getAllNodes());
		Bag edges = new Bag(network.getEdges(this, nodes));
		Bag neighbours = new Bag();
		for (int i = 0; i < edges.size(); i++) {
			Edge edge = (Edge) (edges.get(i));
			Agent from = (Agent) edge.getFrom();
			Agent to = (Agent) edge.getTo();

			if (!to.equals(this)) {
				neighbours.add(to);
			} else {
				if (!from.equals(this))
					neighbours.add(from);
			}
		}
		return neighbours;
	}

	//////////////////// Getter / Setter ///////////////

	public boolean getStrategy(int round) {
		return currentStrategy;
	}
	
	public boolean getStrategy() {		
		return currentStrategy;		
	}

	public double getPayoff(int round) {
		return currentPayoff;
	}
	
	public double getPayoff() {
		return currentPayoff;
	}
	
	public boolean testMethod() {
		return false;
	}

	public long getNodeId() {
		return nodeId;
	}

	public AgentData getAgentData() {
		return new AgentData(strategies, payoff);
	}

}
