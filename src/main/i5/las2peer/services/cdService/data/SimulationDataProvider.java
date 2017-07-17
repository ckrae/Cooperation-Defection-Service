package i5.las2peer.services.cdService.data;

import java.util.List;
import i5.las2peer.services.cdService.data.simulation.SimulationMeta;
import i5.las2peer.services.cdService.data.simulation.Parameters;
import i5.las2peer.services.cdService.data.simulation.SeriesGroup;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;
import i5.las2peer.services.cdService.data.simulation.SimulationSeriesGroup;

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

	public List<SimulationMeta> getSimulationMeta(Parameters parameters) {

		SeriesGroup group = new SeriesGroup(getSimulationSeries(parameters));
		return group.getSimulationMeta();

	}

	public long storeSimulationGroup(SimulationSeriesGroup group) {
		return entityHandler.storeSimulation(group);
	}
	
	public List<SimulationSeriesGroup> getSimulationSeriesGroups() {
		return entityHandler .getSimulationSeriesGroups();
	}
}
