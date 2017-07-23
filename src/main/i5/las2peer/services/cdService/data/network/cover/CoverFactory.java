package i5.las2peer.services.cdService.data.network.cover;

import java.util.ArrayList;
import java.util.List;

import i5.las2peer.services.cdService.data.network.NetworkStructure;
import i5.las2peer.services.cdService.data.network.Properties;

public class CoverFactory {

	public Cover build(NetworkStructure network, AlgorithmType algorithm, List<List<Integer>> communityMemberLists) {
		
		Cover cover = new Cover();
		cover.setAlgorithmType(algorithm);
		int communityCount = communityMemberLists.size();

		List<Community> communities = new ArrayList<Community>(communityCount);
		for (int i = 0; i < communityCount; i++) {

			List<Integer> memberList = communityMemberLists.get(i);
			NetworkStructure communityNetwork = network.getSubNetwork(memberList);
			communities.add(buildCommunity(communityNetwork, cover, memberList));
		}

		cover.setCommunities(communities);
		return cover;

	}

	protected Community buildCommunity(NetworkStructure communityNetwork, Cover cover, List<Integer> memberList) {

		// Set Properties
		int edgeCount = communityNetwork.edgeCount();
		int nodeCount = communityNetwork.nodeCount();

		Properties properties = new Properties();
		properties.setNodes(nodeCount);
		properties.setEdges(edgeCount);

		// Build Community
		Community community = new Community();
		community.setCover(cover);
		community.setMembers(memberList);
		community.setProperties(properties);

		return community;
	}

}
