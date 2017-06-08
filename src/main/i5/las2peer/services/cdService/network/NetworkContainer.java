package i5.las2peer.services.cdService.network;

import java.io.Serializable;
import java.util.TreeSet;

public class NetworkContainer implements Serializable {

	private static final long serialVersionUID = 1L;

	private TreeSet<Long> indexSet;

	public NetworkContainer() {

		indexSet = new TreeSet<Long>();
	}

	public void addNetwork(Network network) {

		indexSet.add(network.getNetworkId());
	}

	public void removeSimulationSeries() {

	}

	public TreeSet<Long> getIndexSet() {
		return this.indexSet;
	}

}
