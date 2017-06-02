package i5.las2peer.services.cdService.data;

import java.io.Serializable;
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

	/////////////// Constructor ///////////////

	public SimulationContainer() {

		indexSet = new TreeSet<Long>();

	}

	/////////////// Methods ///////////////////

	public long addSimulationSeries(SimulationSeries simSe) {

		indexSet.add(simSe.getSeriesId());
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
