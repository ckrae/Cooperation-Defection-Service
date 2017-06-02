package i5.las2peer.services.cdService.data.manager;

import java.util.TreeSet;

import i5.las2peer.api.Context;
import i5.las2peer.api.exceptions.StorageException;
import i5.las2peer.security.UserAgent;
import i5.las2peer.services.cdService.CDService;
import i5.las2peer.services.cdService.data.SimulationContainer;
import i5.las2peer.services.cdService.data.SimulationParameters;
import i5.las2peer.services.cdService.data.SimulationSeries;

public class DataManager {

	public static SimulationSeries getSimulationSeries(long seriesId) throws StorageException {

		if (CDService.USE_DATABASE) {

			return DatabaseManager.getSimulationSeries(seriesId);
		}

		if (CDService.USE_STORAGE) {

			return StorageManager.getSimulationSeries(seriesId);
		}

		return null;
	}

	public static boolean storeSimulationSeries(SimulationSeries series) {

		SimulationContainer container = null;
		long seriesId = 4;
		boolean stored = false;

		if (CDService.USE_DATABASE) {

			// stored = true;
		}

		if (CDService.USE_STORAGE) {

			container = StorageManager.getSimulationContainer();
			container.addSimulationSeries(series);
			seriesId = series.getSeriesId();
			StorageManager.storeSimulationSeries(series, seriesId);
			StorageManager.storeSimulationContainer(container);
			stored = true;
		}

		if (CDService.USE_FILES) {

			FileManager.storeSimulationSeries(series, seriesId);

		}

		return stored;
	}

	public static SimulationParameters getSimulationParameters(long seriesId) throws StorageException {

		if (CDService.USE_DATABASE) {

			return DatabaseManager.getSimulationParameters(seriesId);
		}

		if (CDService.USE_STORAGE) {

			return StorageManager.getSimulationSeries(seriesId).getParameters();
		}

		return null;

	}

	public static TreeSet<Long> getSimulationSeriesIndices() {

		if (CDService.USE_DATABASE) {

			// return DatabaseManager.getSimulationParameters(seriesId);
		}

		if (CDService.USE_STORAGE) {
			SimulationContainer container = StorageManager.getSimulationContainer();
			return container.getIndexSet();
		}

		return null;
	}

	public static long getNextSeriesId() {

		if (CDService.USE_STORAGE) {
			SimulationContainer container = StorageManager.getSimulationContainer();
			return (container.getNextIndex());
		}
		return 0;

	}

	public static long getUserId() {
		return ((UserAgent) Context.getCurrent().getMainAgent()).getId();
	}

}
