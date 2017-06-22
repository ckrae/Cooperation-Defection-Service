package i5.las2peer.services.cdService.data.provider;

import java.util.Set;

import i5.las2peer.api.exceptions.StorageException;
import i5.las2peer.services.cdService.CDService;
import i5.las2peer.services.cdService.data.network.Network;
import i5.las2peer.services.cdService.data.network.NetworkContainer;
import i5.las2peer.services.cdService.data.simulation.SimulationContainer;
import i5.las2peer.services.cdService.data.simulation.SimulationParameters;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;

public abstract class DataManager {	

	protected abstract SimulationSeries getSimulationSeries(long seriesId) throws StorageException;

	protected abstract long storeSimulationSeries(SimulationSeries series) throws StorageException;
	
	protected abstract void deleteSimulationSeries(SimulationSeries series) throws StorageException;

	protected abstract SimulationParameters getSimulationParameters(long seriesId) throws StorageException;

	protected abstract Set<Long> getSimulationSeriesIds();

	protected abstract SimulationContainer getSimulationContainer();
	
	
	protected abstract void storeNetwork(Network network, long networkId) throws StorageException;

	protected abstract Network getNetwork(long networkId) throws StorageException;

	protected abstract NetworkContainer getNetworkContainer();

	
	protected synchronized static DataManager getInstance() {
		
		if(CDService.USE_DATABASE) {
		return DatabaseManager.getInstance();
		}
		return StorageManager.getInstance();
		
	}

}
