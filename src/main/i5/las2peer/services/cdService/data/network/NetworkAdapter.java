package i5.las2peer.services.cdService.data.network;

import java.util.ArrayList;
import java.util.HashMap;

import i5.las2peer.api.Context;
import i5.las2peer.api.exceptions.RemoteServiceException;
import i5.las2peer.api.exceptions.ServiceNotAvailableException;
import i5.las2peer.api.exceptions.ServiceNotFoundException;
import i5.las2peer.services.cdService.simulation.Agent;

/**
 *
 * Adapter Get the graphs from the OCD Service and Transform them into MASON
 * compatibility networks
 * 
 */
public final class NetworkAdapter {

	public final static String graphService = "i5.las2peer.services.ocd.ServiceClass@1.0";

	private NetworkAdapter() {

	}

	////////////// Methods //////////////////

	@SuppressWarnings("unchecked")
	public static ArrayList<Long> invokeGraphIds()
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		ArrayList<Long> networks = (ArrayList<Long>) Context.getCurrent().invoke(graphService, "getGraphIds");

		return networks;
	}

	public static Network invokeGraphById(String graphIdStr)
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		long graphId = Long.parseLong(graphIdStr);
		return (inovkeGraphById(graphId));
	}

	@SuppressWarnings("unchecked")
	public static Network inovkeGraphById(long graphId)
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		Network network = new Network(graphId);

		HashMap<String, Object> map = (HashMap<String, Object>) Context.getCurrent().invoke(graphService,
				"getGraphById", graphId);
		ArrayList<ArrayList<Integer>> graph = (ArrayList<ArrayList<Integer>>) map.get("graph");

		// Add Nodes
		HashMap<Integer, Agent> nodeKeyMap = new HashMap<Integer, Agent>();
		for (int i = 0, length = graph.size(); i < length; i++) {
			Agent currentAgent = new Agent(i);
			network.addNode(currentAgent);
			nodeKeyMap.put(i, currentAgent);
		}

		// Add Edges
		for (int i = 0, length = graph.size(); i < length; i++) {
			ArrayList<Integer> list = graph.get(i);

			for (int j = 0, size = list.size(); j < size; j++) {
				network.addEdge(nodeKeyMap.get(i), nodeKeyMap.get(j), true);
				network.addEdge(nodeKeyMap.get(j), nodeKeyMap.get(i), true);
			}
		}

		return network;
	}

	@SuppressWarnings("unchecked")
	public static Cover inovkeCoverById(long graphId, long coverId)
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		HashMap<String, Object> map = (HashMap<String, Object>) Context.getCurrent().invoke(graphService,
				"getCoverById", graphId, coverId);

		return processCoverMap(map);

	}

	@SuppressWarnings("unchecked")
	public static Cover inovkeCoverByAlgorithm(long graphId, String algorithm)
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		HashMap<String, Object> map = (HashMap<String, Object>) Context.getCurrent().invoke(graphService,
				"getCoverByAlgorithm", graphId, algorithm);

		return processCoverMap(map);
	}

	private static Cover processCoverMap(HashMap<String, Object> map) {

		int communityCount = (int) map.get("communities");
		double[][] memberships = (double[][]) map.get("cover");

		ArrayList<ArrayList<Integer>> coverList = new ArrayList<ArrayList<Integer>>(communityCount);
		for (int j = 0; j < communityCount; j++) {
			ArrayList<Integer> communityList = new ArrayList<Integer>(memberships.length);
			coverList.add(communityList);
		}

		for (int i = 0, length = memberships.length; i < length; i++) {
			for (int j = 0; j < communityCount; j++) {
				if (memberships[i][j] >= 0.9) {
					coverList.get(j).add(i);
				}
			}
		}

		long graphId = (long) map.get("graphId");
		long coverId = (long) map.get("coverId");
		

		Cover cover = new Cover(graphId, coverId, (String) map.get("algorithm"), coverList);

		return cover;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Integer> inovkeCovers(long graphId)
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		ArrayList<Integer> coverList = (ArrayList<Integer>) Context.getCurrent().invoke(graphService,
				"getCoverIdsByGraphId", graphId);

		return coverList;
	}

	@SuppressWarnings("unchecked")
	public static Long getUserId()
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		Long name = (Long) Context.getCurrent().invoke(graphService, "getUserId");

		return name;
	}

	@SuppressWarnings("unchecked")
	public static String getUserName()
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		String name = (String) Context.getCurrent().invoke(graphService, "getUserName");

		return name;
	}

}
