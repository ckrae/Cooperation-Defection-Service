package i5.las2peer.services.cdService.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import i5.las2peer.api.Context;
import i5.las2peer.api.exceptions.RemoteServiceException;
import i5.las2peer.api.exceptions.ServiceInvocationException;
import i5.las2peer.api.exceptions.ServiceNotAvailableException;
import i5.las2peer.api.exceptions.ServiceNotFoundException;
import i5.las2peer.api.exceptions.StorageException;
import i5.las2peer.security.UserAgent;
import i5.las2peer.services.cdService.data.network.NetworkMeta;
import i5.las2peer.services.cdService.data.network.GraphAdapter;

public class NetworkDataProvider {

	private EntityHandler entityHandler;
	private GraphAdapter graphAdapter;

	public NetworkDataProvider() {
		entityHandler = EntityHandler.getInstance();
		graphAdapter = new GraphAdapter();
	}

	public static NetworkDataProvider getInstance() {
		return new NetworkDataProvider();
	}

	public NetworkMeta getNetwork(long networkId) throws ServiceInvocationException {

		NetworkMeta network = null;
		try {
			network = entityHandler.getNetwork(networkId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return network;
	}

	public long storeNetwork(NetworkMeta network) {

		long networkId = -1;
		try {
			if (network != null) {
				long id = ((UserAgent) Context.getCurrent().getMainAgent()).getId();
				network.setUserId(id);
				networkId = entityHandler.storeNetwork(network);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return networkId;
	}

	public ArrayList<NetworkMeta> getNetworks(Set<Long> networkIds)
			throws StorageException, ServiceInvocationException {

		ArrayList<NetworkMeta> networks = new ArrayList<NetworkMeta>(networkIds.size());
		for (Long networkId : networkIds) {
			networks.add(getNetwork(networkId));
		}
		return networks;
	}

	public List<NetworkMeta> getNetworks(long userId) throws ServiceInvocationException {
		
		List<NetworkMeta> networks = entityHandler.getAllNetworks();	
		return networks;
	}
	

	////// External Networks /////

	public NetworkMeta getExternalNetwork(long ocdId) {

		NetworkMeta network = null;
		try {
			network = graphAdapter.inovkeGraphMeta(ocdId);
		} catch (ServiceNotFoundException | ServiceNotAvailableException | RemoteServiceException e) {
			e.printStackTrace();
		}
		return network;
	}

	public List<NetworkMeta> getExternalNetworks(List<Long> ocdIds)
			throws StorageException, ServiceInvocationException {

		List<NetworkMeta> networks = new ArrayList<NetworkMeta>(ocdIds.size());
		for (Long ocdId : ocdIds) {
			networks.add(getExternalNetwork(ocdId));
		}
		return networks;
	}

	public List<NetworkMeta> getExternalNetworks(long userId) throws ServiceInvocationException, StorageException {

		List<Long> ids = graphAdapter.invokeGraphIds();
		return getExternalNetworks(ids);
	}

}
