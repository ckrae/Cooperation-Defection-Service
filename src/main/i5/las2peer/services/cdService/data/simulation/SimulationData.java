package i5.las2peer.services.cdService.data.simulation;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Simulation Data
 * 
 * Objects of this class represent the data collected by simulation series The
 * objects can be stored
 * 
 */
public class SimulationData implements Serializable {

	/////////////// Attributes ///////////////

	private static final long serialVersionUID = 1L;

	/////// Results ///////
	private final long dataId;
	private final boolean stable;

	private final ArrayList<Double> cooperationValues;
	private final ArrayList<Double> payoffValues;

	private final ArrayList<ArrayList<Boolean>> nodeStrategies;
	private final ArrayList<ArrayList<Double>> nodePayoff;	

	

	/////////////// Constructor ///////////////

	public SimulationData(long dataId, ArrayList<Double> cooperationValues, ArrayList<Double> payoffValues,
			ArrayList<ArrayList<Boolean>> nodeStrategies, ArrayList<ArrayList<Double>> nodePayoff, boolean stable) {

		this.cooperationValues = cooperationValues;
		this.payoffValues = payoffValues;
		this.nodeStrategies = nodeStrategies;
		this.nodePayoff = nodePayoff;		

		this.dataId = dataId;
		this.stable = stable;

	}
	/////////////// Getter ///////////////

	public ArrayList<Double> getCooperationValues() {
		return this.cooperationValues;
	}

	public ArrayList<Double> getPayoffValues() {
		return this.payoffValues;
	}

	public ArrayList<ArrayList<Boolean>> getNodeStrategies() {
		return this.nodeStrategies;
	}

	public ArrayList<ArrayList<Double>> getNodePayoff() {
		return this.nodePayoff;
	}

	public double getLastCooperationValue() {
		return cooperationValues.get(cooperationValues.size() - 1);
	}

	public boolean isStable() {
		return stable;
	}

	public long getDataId() {
		return dataId;
	}

}
