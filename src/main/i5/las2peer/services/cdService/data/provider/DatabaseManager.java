package i5.las2peer.services.cdService.data.provider;

import java.util.Set;

import i5.las2peer.api.exceptions.StorageException;
import i5.las2peer.services.cdService.data.network.Network;
import i5.las2peer.services.cdService.data.network.NetworkContainer;
import i5.las2peer.services.cdService.data.simulation.SimulationContainer;
import i5.las2peer.services.cdService.data.simulation.SimulationParameters;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;

/**
 * Database Manager
 * 
 * 
 */
public class DatabaseManager extends DataManager {

	private static DatabaseManager databaseManager;

	private DatabaseManager() {

	}

	protected static synchronized DatabaseManager getInstance() {
		if (databaseManager == null) {
			databaseManager = new DatabaseManager();
		}
		return databaseManager;
	}

	@Override
	protected SimulationSeries getSimulationSeries(long seriesId) throws StorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected long storeSimulationSeries(SimulationSeries series) throws StorageException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected SimulationParameters getSimulationParameters(long seriesId) throws StorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Set<Long> getSimulationSeriesIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected SimulationContainer getSimulationContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void storeNetwork(Network network, long networkId) throws StorageException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Network getNetwork(long networkId) throws StorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected NetworkContainer getNetworkContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void deleteSimulationSeries(SimulationSeries series) throws StorageException {
		// TODO Auto-generated method stub
		
	}

}
