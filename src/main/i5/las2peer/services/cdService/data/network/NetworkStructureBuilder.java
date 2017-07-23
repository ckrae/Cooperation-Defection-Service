package i5.las2peer.services.cdService.data.network;

import java.util.HashMap;
import java.util.Map;

public class NetworkStructureBuilder<T> {

	NetworkStructure network = new NetworkStructure();
	Map<T, Integer> nodeMap = new HashMap<>();

	public NetworkStructureBuilder() {

	}

	public int addNode(T id) {

		if (nodeMap.containsKey(id))
			return nodeMap.get(id);

		int innerId = network.addNode();
		nodeMap.put(id, innerId);
		return innerId;
	}

	public boolean addEdge(T id1, T id2) {

		int innerId1 = addNode(id1);
		int innerId2 = addNode(id2);
		try {
			network.addEdge(innerId1, innerId2);
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}

	public NetworkStructure build() {
		return network;
	}

}
