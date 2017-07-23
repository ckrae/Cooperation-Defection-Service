package i5.las2peer.services.cdService.data;

import java.util.List;
import i5.las2peer.services.cdService.data.simulation.MetaData;
import i5.las2peer.services.cdService.data.simulation.Parameters;
import i5.las2peer.services.cdService.data.simulation.SimulationSeriesGroup;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;

public final class SimulationDataProvider {

	private final EntityHandler entityHandler;

	private SimulationDataProvider() {
		this.entityHandler = EntityHandler.getInstance();
	}

	public static SimulationDataProvider getInstance() {

		return new SimulationDataProvider();
	}

	public final SimulationSeries getSimulationSeries(long seriesId) {

		return entityHandler.getSimulationSeries(seriesId);
	}

	public final List<SimulationSeries> getSimulationSeries(Parameters parameters) {

		return entityHandler.getSimulationSeries(parameters);
	}

	public final List<SimulationSeries> getSimulationSeries() {

		return getSimulationSeries(new Parameters());
	}

	public final Parameters getSimulationParameters(long seriesId) {

		return entityHandler.getSimulationParameters(seriesId);
	}

	public long storeSimulationSeries(SimulationSeries series) {
		
		return entityHandler.storeSimulationSeries(series);
	}

	public void deleteSimulationSeries(SimulationSeries series) {
		
		entityHandler.deleteSimulationSeries(series);
	}

	public List<MetaData> getSimulationMeta(Parameters parameters) {

		SimulationSeriesGroup group = new SimulationSeriesGroup(getSimulationSeries(parameters));
		return group.getMetaData();

	}

	public long storeSimulationGroup(SimulationSeriesGroup group) {
		return entityHandler.storeSimulation(group);
	}
	
	public List<SimulationSeriesGroup> getSimulationSeriesGroups() {
		return entityHandler .getSimulationSeriesGroups();
	}
}
