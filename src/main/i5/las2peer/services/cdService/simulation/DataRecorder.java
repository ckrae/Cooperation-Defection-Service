
package i5.las2peer.services.cdService.simulation;

import java.util.ArrayList;

import i5.las2peer.services.cdService.data.simulation.SimulationData;
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

	public DataRecorder(Simulation simulation) {

		int maxIterations = simulation.getMaxIterations();
		int networkSize = simulation.getNetwork().allNodes.size();

		this.cooperationValues = new ArrayList<Double>(maxIterations + 1);
		this.payoffValues = new ArrayList<Double>(maxIterations + 1);

		this.nodeStrategies = new ArrayList<ArrayList<Boolean>>(networkSize);
		this.nodePayoff = new ArrayList<ArrayList<Double>>(networkSize);

		for (int i = 0; i < networkSize; i++) {

			ArrayList<Boolean> coopList = new ArrayList<Boolean>();
			ArrayList<Double> payoffList = new ArrayList<Double>();

		}

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

		for (int i = 0; i < size; i++) {						
			
			Agent agent = (Agent) agents.get(i);
			nodeStrategies.get(i).add(agent.getStrategy());
			nodePayoff.get(i).add(agent.getPayoff());
		}

		if (simulation.isBreakCondition()) {
			storeResults(simulation);
		}

	}

	private SimulationData storeResults(Simulation simulation) {

		simulationData = new SimulationData(simulation.getRound(), cooperationValues, payoffValues, nodeStrategies, nodePayoff,
				(simulation.getRound() < simulation.getMaxIterations()));
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


}
