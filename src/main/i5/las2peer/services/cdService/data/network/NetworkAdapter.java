package i5.las2peer.services.cdService.data.network;

import java.util.ArrayList;
import java.util.HashMap;

import i5.las2peer.api.Context;
import i5.las2peer.api.exceptions.RemoteServiceException;
import i5.las2peer.api.exceptions.ServiceNotAvailableException;
import i5.las2peer.api.exceptions.ServiceNotFoundException;
import i5.las2peer.services.cdService.simulation.Agent;
import sim.field.network.Network;

/**
 *
 * Adapter Get the graphs from the OCD Service and Transform them into MASON
 * compatibility networks
 * 
 */
public final class NetworkAdapter {

	public final static String graphService = "i5.las2peer.services.ocd.ServiceClass@1.0";

	public NetworkAdapter() {

	}

	////////////// Methods //////////////////

	@SuppressWarnings("unchecked")
	public static ArrayList<Long> invokeGraphIds()
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		ArrayList<Long> networks = (ArrayList<Long>) Context.getCurrent().invoke(graphService, "getGraphIds");

		return networks;
	}

	@SuppressWarnings("unchecked")
	public static Graph inovkeGraphMeta(long graphId)
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		HashMap<String, Object> map = (HashMap<String, Object>) Context.getCurrent().invoke(graphService,
				"getGraphById", graphId);

		Graph network = new Graph(graphId);
		network.setGraphName((String) map.get("name"));

		return network;
	}

	@SuppressWarnings("unchecked")
	public static Network inovkeGraphData(long graphId)
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		HashMap<String, Object> map = (HashMap<String, Object>) Context.getCurrent().invoke(graphService,
				"getGraphById", graphId);

		ArrayList<ArrayList<Integer>> graph = (ArrayList<ArrayList<Integer>>) map.get("graph");
		Network network = new Network(false);
		int size = graph.size();
		
		// Add Nodes
		for (int i = 0; i < size; i++) {			
			network.addNode(i);
		}

		// Add Edges
		for (int i = 0; i < size; i++) {	
			ArrayList<Integer> list = graph.get(i);
			
			for (int j = 0, jSize = list.size(); j < jSize; j++) {
				network.addEdge(i, j, true);
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

		long coverId = (long) map.get("coverId");
		long graphId = (long) map.get("GraphId");

		Network network = null;
		try {
			network = inovkeGraphData(graphId);
		} catch (ServiceNotFoundException | ServiceNotAvailableException | RemoteServiceException e) {
			e.printStackTrace();
		}

		Cover cover = new Cover(coverId, (String) map.get("algorithm"));

		ArrayList<Community> communities = new ArrayList<Community>(coverList.size());
		for (int i = 0; i < coverList.size(); i++) {
			Community community = new Community(cover, coverList.get(i));

			Network subNetwork = new Network(false);
			// Compute SubGraph
			if (network != null) {
				// Add Nodes
				for (int j = 0; j < coverList.get(i).size(); j++) {
					subNetwork.addNode(coverList.get(i).get(j));
				}

				for (int j = 0; j < coverList.get(i).size(); j++) {
					for (int k = 0; k < coverList.get(i).size(); k++) {

					}
				}
			}
			communities.add(community);
		}

		cover.setCommunities(communities);
		return cover;
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Integer> inovkeCovers(long graphId)
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		ArrayList<Integer> coverList = (ArrayList<Integer>) Context.getCurrent().invoke(graphService,
				"getCoverIdsByGraphId", graphId);

		return coverList;
	}

	public static Long getUserId()
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		Long name = (Long) Context.getCurrent().invoke(graphService, "getUserId");

		return name;
	}

	public static String getUserName()
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		String name = (String) Context.getCurrent().invoke(graphService, "getUserName");

		return name;
	}

}
