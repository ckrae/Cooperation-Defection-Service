package i5.las2peer.services.cdService.data.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import i5.las2peer.api.Context;
import i5.las2peer.api.exceptions.RemoteServiceException;
import i5.las2peer.api.exceptions.ServiceNotAvailableException;
import i5.las2peer.api.exceptions.ServiceNotFoundException;
import i5.las2peer.services.cdService.data.network.cover.Community;
import i5.las2peer.services.cdService.data.network.cover.Cover;
import sim.field.network.Edge;
import sim.field.network.Network;
import sim.util.Bag;

/**
 *
 * Adapter Get the graphs from the OCD Service and Transform them
 * 
 */
public class GraphAdapter {

	public final static String graphService = "i5.las2peer.services.ocd.ServiceClass@1.0";

	public GraphAdapter() {

	}

	////////////// Networks //////////////////

	@SuppressWarnings("unchecked")
	public ArrayList<Long> invokeGraphIds()
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		ArrayList<Long> networks = (ArrayList<Long>) Context.getCurrent().invoke(graphService, "getGraphIds");

		return networks;
	}

	@SuppressWarnings("unchecked")
	public NetworkMeta inovkeGraphMeta(long graphId)
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		HashMap<String, Object> map = (HashMap<String, Object>) Context.getCurrent().invoke(graphService,
				"getGraphById", graphId);

		NetworkMeta network = new NetworkMeta(graphId);
		network.setGraphName((String) map.get("name"));
		network.setOrigin(NetworkOrigin.OCD_Service);
		network.setOriginId(graphId);
		network.setSize((int) map.get("nodes"));

		return network;
	}

	@SuppressWarnings("unchecked")
	public NetworkStructure inovkeGraphStructure(long graphId)
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		HashMap<String, Object> map = (HashMap<String, Object>) Context.getCurrent().invoke(graphService,
				"getGraphById", graphId);

		List<List<Integer>> graph = (List<List<Integer>>) map.get("graph");
		return buildNetworkStructure(graph);
	}

	public NetworkStructure buildNetworkStructure(List<List<Integer>> graph) {

		int size = graph.size();
		NetworkStructureBuilder<Integer> networkBuilder = new NetworkStructureBuilder<Integer>();

		for (int node = 0; node < size; node++) {
			List<Integer> nodeLinks = graph.get(node);

			for (int nodeLink = 0, jSize = nodeLinks.size(); nodeLink < jSize; nodeLink++) {
				Integer linkedNode = nodeLinks.get(nodeLink);
				networkBuilder.addEdge(node, linkedNode);
			}
		}

		return networkBuilder.build();
	}	

}
