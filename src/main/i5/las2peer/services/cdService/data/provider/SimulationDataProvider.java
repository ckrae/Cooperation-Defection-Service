package i5.las2peer.services.cdService.data.provider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import i5.las2peer.api.exceptions.StorageException;
import i5.las2peer.services.cdService.data.simulation.SimulationContainer;
import i5.las2peer.services.cdService.data.simulation.SimulationMeta;
import i5.las2peer.services.cdService.data.simulation.SimulationParameters;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;
import i5.las2peer.services.cdService.simulation.dynamic.DynamicType;

public final class SimulationDataProvider {

	private final DataManager dataManager;
	
	private SimulationDataProvider() {
		this.dataManager = StorageManager.getInstance();
	}

	public static SimulationDataProvider getInstance() {		
		return new SimulationDataProvider();
	}

	public final SimulationSeries getSimulationSeries(long seriesId) throws StorageException {

		return dataManager.getSimulationSeries(seriesId);
	}

	public final ArrayList<SimulationSeries> getSimulationSeries() throws StorageException {

		ArrayList<SimulationSeries> series = new ArrayList<SimulationSeries>();

		Set<Long> set = dataManager.getSimulationSeriesIds();
		for (Long seriesId : set) {
			series.add(getSimulationSeries(seriesId));
		}
		return series;
	}

	public final ArrayList<SimulationSeries> getSimulationSeries(SimulationParameters parameters)
			throws StorageException {

		if (parameters == null) {
			return getSimulationSeries();
		}

		if (parameters.getDynamic() != null) {
			DynamicType type = DynamicType.fromString(parameters.getDynamic());
			if (parameters.getPayoffValues() != null) {
				double[] scheme = parameters.getPayoffValues();
				return getSimulationSeries(scheme, type);
			} else {
				return getSimulationSeriesByDynamicType(type);
			}
		}
		if (parameters.getPayoffValues() != null) {
			return getSimulationSeriesByPayoffScheme(parameters.getPayoffValues());
		}

		return null;

	}

	public final ArrayList<SimulationSeries> getSimulationSeriesByNetworkId(long graphId)
			throws StorageException {

		ArrayList<SimulationSeries> series = new ArrayList<SimulationSeries>();

		SimulationContainer container = dataManager.getSimulationContainer();
		Set<Long> set = container.getSimulationSeriesIdsByGraphId(graphId);
		for (Long seriesId : set) {
			series.add(getSimulationSeries(seriesId));
		}
		return series;
	}

	public final ArrayList<SimulationSeries> getSimulationSeriesByDynamicType(DynamicType type)
			throws StorageException {

		ArrayList<SimulationSeries> series = new ArrayList<SimulationSeries>();

		SimulationContainer container = dataManager.getSimulationContainer();
		Set<Long> set = container.getSimulationSeriesIdsByDynamic(type);
		for (Long seriesId : set) {
			series.add(getSimulationSeries(seriesId));
		}
		return series;
	}

	public final ArrayList<SimulationSeries> getSimulationSeriesByPayoffScheme(double[] scheme)
			throws StorageException {

		ArrayList<SimulationSeries> series = new ArrayList<SimulationSeries>();

		SimulationContainer container = dataManager.getSimulationContainer();
		Set<Long> set = container.getSimulationSeriesIdsByPayoffScheme(scheme);
		for (Long seriesId : set) {
			series.add(getSimulationSeries(seriesId));
		}
		return series;
	}

	public final ArrayList<SimulationSeries> getSimulationSeries(long networkId, DynamicType type)
			throws StorageException {

		ArrayList<SimulationSeries> series = new ArrayList<SimulationSeries>();

		return series;
	}

	public final ArrayList<SimulationSeries> getSimulationSeries(long networkId, double[] scheme)
			throws StorageException {

		ArrayList<SimulationSeries> series = new ArrayList<SimulationSeries>();

		return series;
	}

	public final ArrayList<SimulationSeries> getSimulationSeries(double[] scheme, DynamicType type)
			throws StorageException {

		ArrayList<SimulationSeries> series = new ArrayList<SimulationSeries>();

		return series;
	}

	public final ArrayList<SimulationSeries> getSimulationSeries(long networkId, double[] scheme,
			DynamicType type) throws StorageException {

		ArrayList<SimulationSeries> series = new ArrayList<SimulationSeries>();

		return series;
	}

	public final Set<Long> getNetworkIds(ArrayList<SimulationSeries> seriesList) {

		Set<Long> networkIds = new HashSet<Long>();
		for (SimulationSeries series : seriesList) {
			networkIds.add(series.getParameters().getGraphId());
		}
		return networkIds;
	}

	public final SimulationParameters getSimulationParameters(long seriesId) throws StorageException {

		return dataManager.getSimulationParameters(seriesId);

	}

	public final ArrayList<SimulationParameters> getSimulationParameters() throws StorageException {

		ArrayList<SimulationSeries> series = getSimulationSeries();
		int size = series.size();
		ArrayList<SimulationParameters> parametersList = new ArrayList<SimulationParameters>(size);
		for (int i = 0; i < size; i++) {
			parametersList.add(series.get(i).getParameters());
		}

		return parametersList;
	}

	public final ArrayList<SimulationParameters> getSimulationParameters(SimulationParameters parameters)
			throws StorageException {

		if (parameters == null) {
			return getSimulationParameters();
		}

		ArrayList<SimulationSeries> series = getSimulationSeries(parameters);
		int size = series.size();
		ArrayList<SimulationParameters> parametersList = new ArrayList<SimulationParameters>(size);
		for (int i = 0; i < size; i++) {
			parametersList.add(series.get(i).getParameters());
		}

		return parametersList;
	}

	public long storeSimulationSeries(SimulationSeries series) throws StorageException {
		return dataManager.storeSimulationSeries(series);
	}
	
	public void deleteSimulationSeries(SimulationSeries series) throws StorageException {
			dataManager.deleteSimulationSeries(series);
	}

	public ArrayList<SimulationMeta> getSimulationMeta(SimulationParameters parameters) {
		
		SimulationContainer container = dataManager.getSimulationContainer();
		return container.getSimulationMeta(parameters);

	}
}
