package i5.las2peer.services.cdService.data.simulation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import i5.las2peer.services.cdService.simulation.dynamic.DynamicType;

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
	private HashMap<Long, SimulationMeta> simulationMeta;

	/////////////// Constructor ///////////////

	public SimulationContainer() {

		indexSet = new TreeSet<Long>();
		simulationMeta = new HashMap<Long, SimulationMeta>();

	}

	/////////////// Methods ///////////////////

	public long addSimulationSeries(SimulationSeries series) {

		indexSet.add(series.getSeriesId());
		simulationMeta.put(series.getSeriesId(), new SimulationMeta(series));
		return series.getSeriesId();
	}

	public void removeSimulationSeries() {

	}

	public TreeSet<Long> getIndexSet() {
		return this.indexSet;
	}
	
	public ArrayList<SimulationMeta> getSimulationMeta(Parameters parameters) {
		
		return new ArrayList<SimulationMeta>(simulationMeta.values());		
	}
	
	public long getNextIndex() {
		if (indexSet.isEmpty()) {
			return 0;
		}
		return this.indexSet.last() + 1;
	}

	public Set<Long> getSimulationSeriesIdsByGraphId(long graphId) {

		Set<Long> returnSeriesIds = new HashSet<Long>();
		for (Long seriesId : indexSet) {
			Parameters simpa = simulationMeta.get(seriesId).getParameters();
			if (simpa.getGraphId() == graphId) {
				returnSeriesIds.add(seriesId);
			}
		}
		return returnSeriesIds;
	}
	
	public Set<Long> getSimulationSeriesIdsByDynamic(DynamicType type) {

		Set<Long> returnSeriesIds = new HashSet<Long>();
		for (Long seriesId : indexSet) {
			Parameters simpa = simulationMeta.get(seriesId).getParameters();
			if (simpa.getDynamic().equals(type.toString())) {
				returnSeriesIds.add(seriesId);
			}
		}
		return returnSeriesIds;
	}
	
	public Set<Long> getSimulationSeriesIdsByPayoffScheme(double[] scheme) {

		Set<Long> returnSeriesIds = new HashSet<Long>();
		for (Long seriesId : indexSet) {
			Parameters simpa = simulationMeta.get(seriesId).getParameters();
			if (simpa.getPayoffValues() .equals(scheme)) {
				returnSeriesIds.add(seriesId);
			}
		}
		return returnSeriesIds;
	}



}
