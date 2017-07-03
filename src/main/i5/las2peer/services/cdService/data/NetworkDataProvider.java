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
import i5.las2peer.services.cdService.data.network.Cover;
import i5.las2peer.services.cdService.data.network.Graph;
import i5.las2peer.services.cdService.data.network.NetworkAdapter;
import i5.las2peer.services.cdService.simulation.Agent;
import sim.util.Bag;

public class NetworkDataProvider {
	
	private EntityHandler entityHandler;
	
	public NetworkDataProvider() {
		entityHandler = EntityHandler.getInstance();
	}

	public static NetworkDataProvider getInstance() {
		return new NetworkDataProvider();
	}

	public Graph getNetwork(long networkId) throws ServiceInvocationException {

		// First try to get network from CDS Database
		Graph network = null;
		network = EntityHandler.getInstance().getNetwork(networkId);
		if (network != null)
			return network;

		// Invoke from OCD Service
		try {
			network = NetworkAdapter.inovkeGraphMeta(networkId);
		} catch (ServiceNotFoundException | ServiceNotAvailableException | RemoteServiceException e) {
			e.printStackTrace();
			throw e;
		}
		
		// Store in CDS Database
		try {
		if(network != null) {
			long id = ((UserAgent) Context.getCurrent().getMainAgent()).getId();
			network.setUserId(id);
			EntityHandler.getInstance().storeNetwork(network);
		}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return network;
	}

	public ArrayList<Graph> getNetworks(Set<Long> networkIds) throws StorageException, ServiceInvocationException {

		ArrayList<Graph> networks = new ArrayList<Graph>(networkIds.size());
		for (Long networkId : networkIds) {
			networks.add(getNetwork(networkId));
		}
		return networks;
	}

	public Set<Long> getNetworkIds() {

		return null;
	}

	public Cover getCover(Graph network, String algorithm)
			throws StorageException, ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		Cover cover = NetworkAdapter.inovkeCoverByAlgorithm(network.getNetworkId(), algorithm);
		return cover;
	}

	public Cover getCover(long graphId, long coverId)
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		return NetworkAdapter.inovkeCoverById(graphId, coverId);

	}

	public ArrayList<Cover> getCovers(ArrayList<Graph> networks, String algorithm) throws StorageException {

		ArrayList<Cover> covers = new ArrayList<Cover>(networks.size());
		for (Graph network : networks) {

		}

		return covers;
	}

	public List<Graph> getNetworks(long userId) throws ServiceInvocationException {
		List<Long> ids = NetworkAdapter.invokeGraphIds();
		List<Graph> networks = new ArrayList<Graph>(ids.size());
		for(long id: ids) {
			networks.add(getNetwork(id));
		}
		return networks;
	}

}
