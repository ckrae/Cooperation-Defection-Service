package i5.las2peer.services.cdService.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

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
	private final boolean stable;

	private final ArrayList<Double> cooperationValues;
	private final ArrayList<Double> payoffValues;

	private final Collection<ArrayList<Boolean>> nodeStrategies;
	private final Collection<ArrayList<Double>> nodePayoff;

	/////////////// Constructor ///////////////

	public SimulationData(ArrayList<Double> cooperationValues, ArrayList<Double> payoffValues,
			Collection<ArrayList<Boolean>> nodeStrategies, Collection<ArrayList<Double>> nodePayoff, boolean stable) {

		this.cooperationValues = cooperationValues;
		this.payoffValues = payoffValues;
		this.nodeStrategies = nodeStrategies;
		this.nodePayoff = nodePayoff;

		this.stable = stable;

	}
	/////////////// Getter ///////////////

	public ArrayList<Double> getCooperationValues() {
		return this.cooperationValues;
	}

	public ArrayList<Double> getPayoffValues() {
		return this.payoffValues;
	}

	public Collection<ArrayList<Boolean>> getNodeStrategies() {
		return this.nodeStrategies;
	}

	public Collection<ArrayList<Double>> getNodePayoff() {
		return this.nodePayoff;
	}

	public double getLastCooperationValue() {
		return cooperationValues.get(cooperationValues.size() - 1);
	}

	public boolean isStable() {
		return stable;
	}

}
