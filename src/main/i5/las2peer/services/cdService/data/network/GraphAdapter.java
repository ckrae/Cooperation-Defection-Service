package i5.las2peer.services.cdService.data.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import i5.las2peer.api.Context;
import i5.las2peer.api.exceptions.RemoteServiceException;
import i5.las2peer.api.exceptions.ServiceNotAvailableException;
import i5.las2peer.api.exceptions.ServiceNotFoundException;

/**
 *
 * Adapter Get the graphs from the OCD Service and Transform them
 * 
 */
public class GraphAdapter {

	public final static String graphService = "i5.las2peer.services.ocd.ServiceClass@1.0";

	public GraphAdapter() {

	}

	@SuppressWarnings("unchecked")
	public ArrayList<Long> invokeGraphIds()
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		ArrayList<Long> networks = (ArrayList<Long>) Context.getCurrent().invoke(graphService, "getGraphIds");

		return networks;
	}

	@SuppressWarnings("unchecked")
	public NetworkMeta inovkeGraph(long ocdGraphId)
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		HashMap<String, Object> graphDataMap = (HashMap<String, Object>) Context.getCurrent().invoke(graphService,
				"getGraphById", ocdGraphId);

		int nodes = (int) graphDataMap.get("nodes");
		int edges = (int) graphDataMap.get("edges");
		boolean directed = (boolean) graphDataMap.get("directed");
		boolean weighted = (boolean) graphDataMap.get("weighted");
		String name = (String) graphDataMap.get("name");
		List<List<Integer>> adjList = (List<List<Integer>>) graphDataMap.get("graph");

		NetworkMeta network = new NetworkMeta();
		network.setOrigin(NetworkOrigin.OCD_Service);
		network.setGraphName(name);
		network.setOriginId(ocdGraphId);
		network.setSize(nodes);
		network.setNetworkStructure(buildNetworkStructure(adjList));

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

	protected NetworkStructure buildNetworkStructure(List<List<Integer>> adjList) {

		return NetworkStructureBuilder.parseAdjList(adjList);
	}

}
