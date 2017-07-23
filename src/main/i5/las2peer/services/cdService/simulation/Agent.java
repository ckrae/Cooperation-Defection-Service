
package i5.las2peer.services.cdService.simulation;

import java.util.ArrayList;
import java.util.List;

import ec.util.MersenneTwisterFast;
import i5.las2peer.services.cdService.data.simulation.AgentData;
import i5.las2peer.services.cdService.simulation.dynamic.Dynamic;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.field.network.Edge;
import sim.field.network.Network;
import sim.util.Bag;

public class Agent implements Steppable {

	private static final long serialVersionUID = 1;

	private int nodeId;	

	private double currentPayoff;
	private boolean currentStrategy;
	private List<Boolean> strategies;
	private List<Double> payoff;

	private Bag neighbours;

	public Agent(int nodeId) {
		
		this.nodeId = nodeId;
		strategies = new ArrayList<Boolean>();
		strategies.add(false);
		payoff = new ArrayList<Double>();
		payoff.add(0.0);
	}
	
	public Agent() {
		this(0);
	}

	public void initialize(boolean strategy, Network network) {
				
		strategies.clear();
		currentStrategy = strategy;
		strategies.add(0, currentStrategy);
		
		payoff.clear();
		currentPayoff = 0.0;		
		payoff.add(0, currentPayoff);
		
		if(this.neighbours == null)
			this.neighbours = calculateNeighbourhood(network);		
	}

	/////////////////// Step ///////////////////////////

	@Override
	public void step(SimState state) {
		Simulation simulation = (Simulation) state;
		currentPayoff = simulation.getGame().getPayoff(this);
		payoff.add(currentPayoff);

	}

	public void updateDynamicStep(SimState state) {
		Simulation simulation = (Simulation) state;
		currentStrategy = simulation.getDynamic().getNewStrategy(this, simulation);
		strategies.add(simulation.getDynamic().getNewStrategy(this, simulation));

	}

	////////////////// Network Utility ///////////////////////////

	/**
	 * Get a random neighbour agent
	 * 
	 * @return agent
	 */
	public Agent getRandomNeighbour(MersenneTwisterFast random) {
		
		Bag agents = getNeighbourhood();
		if (agents.size() > 0) {
			return (Agent) agents.get(random.nextInt(agents.size()));
		}
		return null;
	}

	public Bag getNeighbourhood() {
		
		return this.neighbours;
	}
	
	protected Bag calculateNeighbourhood(Network network) {	
	
		Bag edges = new Bag(network.getEdgesIn(this));		
		Bag neighbours = new Bag();
		for (int i = 0, si = edges.size(); i < si; i++) {			
			Edge edge = (Edge) edges.get(i);
			Agent neighbour = (Agent) edge.getOtherNode(this);			
			neighbours.add(neighbour);			
		}		
		return neighbours;		
	}

	//////////////////// Getter / Setter ///////////////
	
	public boolean getStrategy() {		
		return currentStrategy;		
	}
	
	public boolean getStrategy(int round) {
		return currentStrategy;
	}
	
	public double getPayoff() {
		return currentPayoff;
	}
	
	public double getPayoff(int round) {
		return currentPayoff;
	}

	public long getNodeId() {
		return nodeId;
	}	

	public AgentData getAgentData() {
		return new AgentData(strategies, payoff);
	}

}
