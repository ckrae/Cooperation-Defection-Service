package i5.las2peer.services.cdService.network;

import java.util.TreeSet;

import i5.las2peer.api.exceptions.RemoteServiceException;
import i5.las2peer.api.exceptions.ServiceNotAvailableException;
import i5.las2peer.api.exceptions.ServiceNotFoundException;
import i5.las2peer.api.exceptions.StorageException;
import i5.las2peer.services.cdService.data.manager.StorageManager;

public class NetworkManager {

	public NetworkManager() {

	}

	public static Network getNetwork(long networkId) throws StorageException {

		Network network = null;
		try {
			network = StorageManager.getNetwork(networkId);
		} catch (StorageException e) {
			try {
				network = NetworkAdapter.inovkeGraphById(networkId);
			} catch (ServiceNotFoundException | ServiceNotAvailableException | RemoteServiceException e1) {
				e1.printStackTrace();
				throw e;
			}
			StorageManager.storeNetwork(network, networkId);


		}
		return network;
	}
	
	
	
	
	

	public static TreeSet<Long> getNetworkIndices() {

		NetworkContainer container = StorageManager.getNetworkContainer();
		TreeSet<Long> indi = container.getIndexSet();

		return indi;
	}

}
