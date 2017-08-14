package i5.las2peer.services.cdService.data.network.cover;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import i5.las2peer.api.Context;
import i5.las2peer.api.exceptions.RemoteServiceException;
import i5.las2peer.api.exceptions.ServiceNotAvailableException;
import i5.las2peer.api.exceptions.ServiceNotFoundException;
import i5.las2peer.services.cdService.data.NetworkDataProvider;
import i5.las2peer.services.cdService.data.network.NetworkStructure;
import sim.field.network.Edge;
import sim.field.network.Network;
import sim.util.Bag;

public class CoverAdapter {
	
	public final static String coverService = "i5.las2peer.services.ocd.ServiceClass@1.0";
	
	public static CoverAdapter getInstance() {
		return new CoverAdapter();
	}
	
	public CoverAdapter() {
		
	}	
	
	
	///// Invocation /////
	
	@SuppressWarnings("unchecked")
	public Cover inovkeCoverById(long graphId, long coverId)
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		HashMap<String, Object> map = (HashMap<String, Object>) Context.getCurrent().invoke(coverService,
				"getCoverById", graphId, coverId);

		return processCoverMap(map);

	}

	@SuppressWarnings("unchecked")
	public Cover inovkeCoverByAlgorithm(long graphId, String algorithm)
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		HashMap<String, Object> map = (HashMap<String, Object>) Context.getCurrent().invoke(coverService,
				"getCoverByAlgorithm", graphId, algorithm);

		return processCoverMap(map);
	}

	public List<List<Integer>> getCoverList(double[][] memberships, int communityCount) {
		List<List<Integer>> coverList = new ArrayList<>(communityCount);

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
	
	@SuppressWarnings("unchecked")
	public ArrayList<Integer> inovkeCovers(long graphId)
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		ArrayList<Integer> coverList = (ArrayList<Integer>) Context.getCurrent().invoke(coverService,
				"getCoverIdsByGraphId", graphId);

		return coverList;
	}

	public Cover processCoverMap(HashMap<String, Object> map) {

		// parse map
		long coverId = (long) map.get("coverId");
		long graphId = (long) map.get("graphId");
		String algorithmStr = (String) map.get("algorithm");
		int communityCount = (int) map.get("communities");
		double[][] memberships = (double[][]) map.get("cover");
		
		AlgorithmType algorithm = AlgorithmType.fromString(algorithmStr);
		List<List<Integer>> communityMemberLists = getCoverList(memberships, communityCount);

		// Get Network
		NetworkDataProvider networkDataProvider = NetworkDataProvider.getInstance();
		NetworkStructure network = null;
		network = networkDataProvider.getExternalNetwork(graphId).getNetworkStructure();

		// Build Cover
		CoverFactory factory = new CoverFactory();
		Cover cover = factory.build(network, algorithm, communityMemberLists);	
		cover.setOriginId(coverId);
		return cover;
	}
	

}
