
package i5.las2peer.services.cdService.simulation;

import java.util.ArrayList;

import i5.las2peer.services.cdService.data.SimulationData;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.field.network.Network;
import sim.util.Bag;

public class DataRecorder implements Steppable {
	private static final long serialVersionUID = 1;

	private ArrayList<Double> cooperationValues;
	private ArrayList<Double> payoffValues;
	private ArrayList<ArrayList<Boolean>> nodeStrategies;
	private ArrayList<ArrayList<Double>> nodePayoff;

	private SimulationData simulationData;

	public DataRecorder(int maxIterations) {

		this.cooperationValues = new ArrayList<Double>(maxIterations + 1);
		this.payoffValues = new ArrayList<Double>(maxIterations + 1);
		this.nodeStrategies = new ArrayList<ArrayList<Boolean>>();
		this.nodePayoff = new ArrayList<ArrayList<Double>>();

	}

	/////////////////// Steps ///////////////////////////

	@Override
	public void step(SimState state) {
		Simulation simulation = (Simulation) state;
		Network network = simulation.getNetwork();
		Bag agents = new Bag(network.getAllNodes());
		int size = agents.size();

		cooperationValues.add(simulation.getCooperationValue());
		payoffValues.add(simulation.getAveragePayoff());

		ArrayList<Boolean> strategies = new ArrayList<Boolean>(size);
		ArrayList<Double> payoff = new ArrayList<Double>(size);

		for (int i = 0; i < size; i++) {
			Agent agent = (Agent) agents.get(i);
			strategies.add(i, agent.getStrategy());
			payoff.add(i, agent.getPayoff());
		}

		nodeStrategies.add(strategies);
		nodePayoff.add(payoff);

		if (simulation.isBreakCondition()) {

			((ArrayList<ArrayList<Boolean>>) nodeStrategies).trimToSize();
			((ArrayList<ArrayList<Double>>) nodePayoff).trimToSize();
			storeResults(simulation);
		}

	}

	private SimulationData storeResults(Simulation simulation) {

		simulationData = new SimulationData(cooperationValues, payoffValues, nodeStrategies, nodePayoff,
				(simulation.getRound() < simulation.getMaxIterations()));
		printToFile(simulationData);
		return simulationData;
	}

	public double getCooperationValue(int round) {

		return cooperationValues.get(round);
	}

	public double getPayoffValue(int round) {

		return payoffValues.get(round);
	}

	public SimulationData getSimulationData() {
		return this.simulationData;
	}

	public void printToFile(SimulationData data) {

	}

}
