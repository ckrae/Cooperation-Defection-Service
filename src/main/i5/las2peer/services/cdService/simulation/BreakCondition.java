package i5.las2peer.services.cdService.simulation;

import java.util.ArrayList;
import java.util.List;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.util.Bag;


/**
 * Break Condition
 *
 */
public class BreakCondition implements Steppable {

	private static final long serialVersionUID = 1L;

	/**
	 * the maximum rounds of one simulation
	 */
	private int maxIterations = 1000;

	/**
	 * the minimum rounds of one simulation
	 */
	private int minIterations = 40;

	/**
	 * This List holds everything thats in the schedule. If the break condition
	 * is fulfilled every object in the list is removed from the schedule
	 *
	 */
	private List<Stoppable> stopper;

	public BreakCondition() {
		
			this.stopper = new ArrayList<>();
	}

	/**
	 * checks if the break condition is fulfilled
	 * 
	 * @param simulation
	 * @return break condition fulfilled
	 */
	public boolean isFullfilled(Simulation simulation) {

		int round = simulation.getRound();

		if (round < getMinIterations())
			return false;

		if (round >= getMaxIterations())
			return true;

		Bag agents = simulation.getAgents();
		int size = agents.size();
		for (int agentId = 0; agentId < size; agentId++) {
			if (!((Agent) agents.get(agentId)).isSteady())
				return false;
		}
		return true;
	}

	/**
	 * Called every time this object is stepped. Checks if the break condition
	 * is fulfilled and calls stop if necessary.
	 * 
	 * @param state
	 *            Simulation
	 */
	@Override
	public void step(SimState state) {
		Simulation simulation = (Simulation) state;
		if (this.isFullfilled(simulation)) {
			stopScheduledObjects();
		}
	}

	
	/**
	 * stops all scheduled object in the stopper list
	 */
	protected void stopScheduledObjects() {		
		for (int i = 0, size = stopper.size(); i < size; i++) {
			stopper.get(i).stop();
		}
	}
	
	/**
	 * Register a stoppable object that have to be stopped after the simulation
	 * ends.
	 * 
	 * @param stoppable
	 */
	public void add(Stoppable stoppable) {
		stopper.add(stoppable);
	}

	public int getMaxIterations() {
		return maxIterations;
	}

	public void setMaxIterations(int maxIterations) {
		this.maxIterations = maxIterations;
	}

	public int getMinIterations() {
		return minIterations;
	}

	public void setMinIterations(int minIterations) {
		this.minIterations = minIterations;
	}

}
