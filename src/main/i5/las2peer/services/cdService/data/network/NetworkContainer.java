package i5.las2peer.services.cdService.data.network;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class NetworkContainer implements Serializable {

	private static final long serialVersionUID = 1L;

	private Set<Long> indexSet;

	public NetworkContainer() {

		indexSet = new TreeSet<Long>();
	}

	public void addNetwork(Network network) {

		indexSet.add(network.getNetworkId());
	}

	public void removeSimulationSeries() {

	}

	public Set<Long> getIndexSet() {
		return this.indexSet;
	}

}
