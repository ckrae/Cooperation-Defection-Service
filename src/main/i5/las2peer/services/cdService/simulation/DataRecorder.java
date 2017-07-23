
package i5.las2peer.services.cdService.simulation;

import java.util.ArrayList;
import java.util.List;

import i5.las2peer.services.cdService.data.simulation.AgentData;
import i5.las2peer.services.cdService.data.simulation.SimulationDataset;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.field.network.Network;
import sim.util.Bag;

public class DataRecorder implements Steppable {
	private static final long serialVersionUID = 1;

	private ArrayList<Double> cooperationValues;
	private ArrayList<Double> payoffValues;

	private SimulationDataset simulationData;

	public DataRecorder(Simulation simulation) {

		int maxIterations = simulation.getMaxIterations();

		this.cooperationValues = new ArrayList<Double>(maxIterations);
		this.payoffValues = new ArrayList<Double>(maxIterations);		
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
		
		if (simulation.isBreakCondition()) {
			storeResults(simulation);
		}

	}

	private SimulationDataset storeResults(Simulation simulation) {
		
		Bag agents = new Bag(simulation.getNetwork().getAllNodes());
		int size = agents.size();
		List<AgentData> agentDataList = new ArrayList<AgentData>(size);
		for(int i=0; i<size; i++) {
			agentDataList.add(((Agent) agents.get(i)).getAgentData());
		}
		
		simulationData = new SimulationDataset(cooperationValues, payoffValues, agentDataList,
				(simulation.getRound() < simulation.getMaxIterations()));
		return simulationData;
	}
	
	protected void clear() {
		cooperationValues = new ArrayList<>(cooperationValues.size());
		payoffValues = new ArrayList<>(payoffValues.size());
	}
	
	///// Getter /////
	
	public double getCooperationValue(int round) {

		return cooperationValues.get(round-1);
	}

	public double getPayoffValue(int round) {

		return payoffValues.get(round-1);
	}

	public SimulationDataset getSimulationData() {
		return this.simulationData;
	}


}
