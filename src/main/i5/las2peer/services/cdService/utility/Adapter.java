package i5.las2peer.services.cdService.utility;

import java.util.ArrayList;
import java.util.HashMap;

import i5.las2peer.api.Context;
import i5.las2peer.api.exceptions.RemoteServiceException;
import i5.las2peer.api.exceptions.ServiceNotAvailableException;
import i5.las2peer.api.exceptions.ServiceNotFoundException;
import i5.las2peer.services.cdService.CDService;
import i5.las2peer.services.cdService.simulation.Agent;
import sim.field.network.Network;

/**
 *
 * Adapter Get the graphs from the OCD Service and Transform them into MASON
 * compatibility networks
 * 
 */
public final class Adapter {

	public final static String graphService = "i5.las2peer.services.ocd.ServiceClass@1.0";

	private Adapter() {

	}

	////////////// Methods //////////////////

	public static Network getGraphById(String graphIdStr)
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		long graphId = Long.parseLong(graphIdStr);
		return (getGraphById(graphId));
	}

	@SuppressWarnings("unchecked")
	public static Network getGraphById(long graphId)
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		Network network = new Network(false);

		if (CDService.USE_GRAPH_RMI) {

			HashMap<String, Object> map = (HashMap<String, Object>) Context.getCurrent().invoke(graphService,
					"getGraphById", graphId);
			ArrayList<ArrayList<Integer>> graph = (ArrayList<ArrayList<Integer>>) map.get("graph");

			// Add Nodes
			HashMap<Integer, Agent> nodeKeyMap = new HashMap<Integer, Agent>();
			for (int i = 0, length = graph.size(); i < length; i++) {
				Agent currentAgent = new Agent();
				network.addNode(currentAgent);
				nodeKeyMap.put(i, currentAgent);
			}

			// Add Edges
			for (int i = 0, length = graph.size(); i < length; i++) {
				ArrayList<Integer> list = graph.get(i);

				for (int j = 0, lenght = list.size(); j < length; j++) {
					network.addEdge(nodeKeyMap.get(i), list.get(j), true);
					network.addEdge(list.get(j), nodeKeyMap.get(i), true);
				}
			}
		} else {

		}

		return createTestNetwork();
	}

	public static Network createTestNetwork() {

		Network network = new Network();

		HashMap<Integer, Agent> nodeKeyMap = new HashMap<Integer, Agent>();
		for (int i = 0, length = 40; i < length; i++) {
			Agent currentAgent = new Agent();
			network.addNode(currentAgent);
			nodeKeyMap.put(i, currentAgent);
		}

		for (int i = 0, length = 40; i < length; i++) {

			for (int j = 0, lenght = 2 + (int) (Math.random() * 12); j < length; j++) {

				network.addEdge(nodeKeyMap.get(i), nodeKeyMap.get(j), true);
				network.addEdge(nodeKeyMap.get(j), nodeKeyMap.get(i), true);
			}
		}

		return network;
	}
	
}
