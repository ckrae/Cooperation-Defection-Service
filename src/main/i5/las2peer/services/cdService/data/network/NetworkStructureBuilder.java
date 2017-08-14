package i5.las2peer.services.cdService.data.network;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkStructureBuilder {

	NetworkStructure network = new NetworkStructure();
	Map<Integer, Integer> nodeMap = new HashMap<>();

	public NetworkStructureBuilder() {

	}

	public int addNode(int id) {

		if (nodeMap.containsKey(id))
			return nodeMap.get(id);

		int innerId = network.addNode();
		nodeMap.put(id, innerId);
		return innerId;
	}
	
	public boolean addEdge(String id1, String id2) {
		return addEdge(Integer.valueOf(id1), Integer.valueOf(id2));		
	}
	
	public boolean addEdge(int id1, int id2) {

		int innerId1 = addNode(id1);
		int innerId2 = addNode(id2);
		try {
			network.addEdge(innerId1, innerId2);
		} catch (IllegalArgumentException e) {

		}
		return true;

	}

	public NetworkStructure build() {
		return this.network;
	}

	public static NetworkStructure parseAdjList(List<List<Integer>> adjList) {

		int size = adjList.size();
		NetworkStructure network = new NetworkStructure(size);

		for (int nodeId = 1; nodeId < size; nodeId++) {
			List<Integer> nodeLinks = adjList.get(nodeId);

			for (int nodeLink = 0, jSize = nodeLinks.size(); nodeLink < jSize; nodeLink++) {
				int linkedNodeId = nodeLinks.get(nodeLink);
				network.addEdge(nodeId, linkedNodeId);
			}
		}

		return network;
	}



}
