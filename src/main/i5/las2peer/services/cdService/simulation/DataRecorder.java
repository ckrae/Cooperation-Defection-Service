
package i5.las2peer.services.cdService.simulation;

import java.util.ArrayList;
import java.util.List;

import sim.engine.SimState;
import sim.engine.Steppable;

public class DataRecorder implements Steppable {
	
	private static final long serialVersionUID = 1;

	private List<Double> cooperationValues;
	private List<Double> payoffValues;

	public DataRecorder(int maxIterations) {

		this.cooperationValues = new ArrayList<Double>(maxIterations);
		this.payoffValues = new ArrayList<Double>(maxIterations);
	}

	/////////////////// Steps ///////////////////////////

	/**
	 * Stores the average cooperation and average payoff value every time it is
	 * stepped.
	 */
	@Override
	public void step(SimState state) {

		Simulation simulation = (Simulation) state;
		cooperationValues.add(simulation.getCooperationValue());
		payoffValues.add(simulation.getAveragePayoff());
	}

	///// Methods /////

	/**
	 * Clear the cooperation and payoff value lists.
	 */
	protected void clear() {
		cooperationValues.clear();
		payoffValues.clear();
	}

	public List<Double> getCooperationValues() {
		return this.cooperationValues;
	}

	public List<Double> getPayoffValues() {
		return this.payoffValues;
	}

	public double getCooperationValue(int round) {

		return cooperationValues.get(round - 1);
	}

	public double getPayoffValue(int round) {

		return payoffValues.get(round - 1);
	}

}
