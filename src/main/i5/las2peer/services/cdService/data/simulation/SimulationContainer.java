package i5.las2peer.services.cdService.data.simulation;

import java.io.Serializable;
import java.util.HashMap;
import java.util.TreeSet;

public class SimulationContainer implements Serializable {

	/**
	 * Simulation Container
	 * 
	 * container for all simulation series performed by an agent identifier
	 * SERVICE_PREFIX + UserId
	 * 
	 */

	/////////////// Attributes ///////////////

	private static final long serialVersionUID = 1L;

	private TreeSet<Long> indexSet;
	private HashMap<Long, SimulationParameters> parameters;
	
	/////////////// Constructor ///////////////

	public SimulationContainer() {

		indexSet = new TreeSet<Long>();
		parameters = new HashMap<Long, SimulationParameters>();		

	}

	/////////////// Methods ///////////////////

	public long addSimulationSeries(SimulationSeries simSe) {

		indexSet.add(simSe.getSeriesId());
		parameters.put(simSe.getSeriesId(), simSe.getParameters());
		return simSe.getSeriesId();
	}

	public void removeSimulationSeries() {

	}

	public TreeSet<Long> getIndexSet() {
		return this.indexSet;
	}
	

	public long getNextIndex() {
		if (indexSet.isEmpty()) {
			return 0;
		}
		return this.indexSet.last() + 1;
	}
}
