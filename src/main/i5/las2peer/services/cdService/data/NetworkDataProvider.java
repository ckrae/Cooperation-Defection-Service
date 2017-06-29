package i5.las2peer.services.cdService.data;

import java.util.ArrayList;
import java.util.Set;

import i5.las2peer.api.exceptions.RemoteServiceException;
import i5.las2peer.api.exceptions.ServiceNotAvailableException;
import i5.las2peer.api.exceptions.ServiceNotFoundException;
import i5.las2peer.api.exceptions.StorageException;
import i5.las2peer.services.cdService.data.network.Cover;
import i5.las2peer.services.cdService.data.network.Network;
import i5.las2peer.services.cdService.data.network.NetworkAdapter;
import i5.las2peer.services.cdService.data.network.NetworkContainer;
import i5.las2peer.services.cdService.simulation.Agent;
import sim.util.Bag;

public class NetworkDataProvider {

	private NetworkDataProvider() {

	}		

	public static NetworkDataProvider getInstance() {
		return new NetworkDataProvider();
	}

	public final Network getNetwork(long networkId) {

		Network network = null;
		try {
			network = NetworkAdapter.inovkeGraphById(networkId);
		} catch (ServiceNotFoundException | ServiceNotAvailableException | RemoteServiceException e) {
			e.printStackTrace();
		}
		return network;
	}

	public final ArrayList<Network> getNetworks(Set<Long> networkIds) throws StorageException {

		ArrayList<Network> networks = new ArrayList<Network>(networkIds.size());
		for (Long networkId : networkIds) {
			networks.add(getNetwork(networkId));
		}
		return networks;
	}

	public final Set<Long> getNetworkIds() {

		return null;
	}

	public final Cover getCover(Network network, String algorithm)
			throws StorageException, ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		Cover cover = NetworkAdapter.inovkeCoverByAlgorithm(network.getocdId(), algorithm);
		return cover;
	}

	public Cover getCover(long graphId, long coverId)
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		return NetworkAdapter.inovkeCoverById(graphId, coverId);

	}

	public ArrayList<Cover> getCovers(ArrayList<Network> networks, String algorithm) throws StorageException {

		ArrayList<Cover> covers = new ArrayList<Cover>(networks.size());
		for (Network network : networks) {

		}

		return covers;
	}

	public Network getTestNetwork() {

		Network network = new Network(2);
		for (int i = 0; i < 40; i++) {
			Agent agent = new Agent(i);
			network.addNode(agent);
		}
		Bag agents = new Bag(network.getAllNodes());
		network.addEdge(agents.get(3), agents.get(5), 1);
		network.addEdge(agents.get(3), agents.get(6), 1);
		network.addEdge(agents.get(3), agents.get(7), 1);

		return network;
	}

}
