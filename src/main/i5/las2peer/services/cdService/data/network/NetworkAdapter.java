package i5.las2peer.services.cdService.data.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import i5.las2peer.api.Context;
import i5.las2peer.api.exceptions.RemoteServiceException;
import i5.las2peer.api.exceptions.ServiceNotAvailableException;
import i5.las2peer.api.exceptions.ServiceNotFoundException;
import sim.field.network.Edge;
import sim.field.network.Network;
import sim.util.Bag;

/**
 *
 * Adapter Get the graphs from the OCD Service and Transform them
 * 
 */
public final class NetworkAdapter {

	public final static String graphService = "i5.las2peer.services.ocd.ServiceClass@1.0";

	public NetworkAdapter() {

	}

	////////////// Networks //////////////////

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

	////////////// Cover ///////////////

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

	public static ArrayList<ArrayList<Integer>> getCoverList(double[][] memberships, int communityCount) {
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

		return coverList;
	}

	public static Cover processCoverMap(HashMap<String, Object> map) {

		// parse map
		long coverId = (long) map.get("coverId");
		long graphId = (long) map.get("graphId");
		String algorithm = (String) map.get("algorithm");
		int communityCount = (int) map.get("communities");
		double[][] memberships = (double[][]) map.get("cover");

		// parse memberships
		ArrayList<ArrayList<Integer>> coverList = getCoverList(memberships, communityCount);

		// Get Network
		Network network = null;
		try {
			network = inovkeGraphData(graphId);
		} catch (ServiceNotFoundException | ServiceNotAvailableException | RemoteServiceException e) {
			e.printStackTrace();
		}

		// Build Cover
		Cover cover = new Cover(coverId, algorithm);
		
		//Iterate all communities
		ArrayList<Community> communities = new ArrayList<Community>(coverList.size());
		for (int i = 0; i < coverList.size(); i++) {
			ArrayList<Integer> communityMemberList = coverList.get(i);

			// Get Community Network Structure
			Network subNetwork = getSubNetwork(network, communityMemberList);

			// Count Edges
			int edgeCount = countEdges(subNetwork);
			int nodeCount = communityMemberList.size();

			// Set Properties
			Properties properties = new Properties();
			properties.setNodes(nodeCount);
			properties.setEdges(edgeCount);

			// Build Community
			Community community = new Community();
			community.setCover(cover);
			community.setMembers(communityMemberList);
			community.setProperties(properties);

			communities.add(community);
		}

		cover.setCommunities(communities);
		cover.setOcdId(coverId);
		return cover;
	}

	public static Network getSubNetwork(Network network, ArrayList<Integer> communityMemberList) {
		Network subNetwork = new Network(network);
		Bag nodes = new Bag(subNetwork.getAllNodes());
		int nodeCount = nodes.size();
		for (int i = 0; i < nodeCount; i++) {
			if (!communityMemberList.contains(nodes.get(i))) {
				subNetwork.removeNode(nodes.get(i));
			}
		}
		return subNetwork;
	}

	public static int countEdges(Network subNetwork) {
		Bag nodes = new Bag(subNetwork.getAllNodes());
		int nodeCount = nodes.size();
		Set<Edge> edges = new HashSet<Edge>();
		for (int i = 0; i < nodeCount; i++) {
			int node = (int) nodes.get(i);
			Bag bag = subNetwork.getEdgesIn(node);
			for(int j=0, js = bag.size(); j<js; j++) {
				edges.add((Edge) bag.get(j));
			}
		}
		return (edges.size() / 2);
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<Integer> inovkeCovers(long graphId)
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		ArrayList<Integer> coverList = (ArrayList<Integer>) Context.getCurrent().invoke(graphService, "getCoverIdsByGraphId",
				graphId);

		return coverList;
	}

}
