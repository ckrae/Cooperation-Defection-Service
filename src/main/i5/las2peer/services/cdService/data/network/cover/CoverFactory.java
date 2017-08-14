package i5.las2peer.services.cdService.data.network.cover;

import java.util.ArrayList;
import java.util.List;

import i5.las2peer.services.cdService.data.network.NetworkStructure;
import i5.las2peer.services.cdService.data.network.NetworkProperties;
import i5.las2peer.services.cdService.data.network.PropertyType;

public class CoverFactory {

	public Cover build(NetworkStructure network, AlgorithmType algorithm, List<List<Integer>> communityMemberLists) {

		System.out.println("memberList Size" + communityMemberLists.size());

		Cover cover = new Cover();
		cover.setNetwork(network.getNetworkMeta());
		cover.setAlgorithmType(algorithm);
		int communityCount = communityMemberLists.size();
		double[] sizes = new double[communityCount];
		
		List<Community> communities = new ArrayList<Community>(communityCount);
		for (int communityId = 0; communityId < communityCount; communityId++) {

			List<Integer> memberList = communityMemberLists.get(communityId);
			System.out.println("memberList Size " + communityId + " " + communityMemberLists.size());
			NetworkStructure communityNetwork = network.getSubNetwork(memberList);
					
			Community community = buildCommunity(communityNetwork, cover, memberList);
			community.setCommunityId(communityId);			
			communities.add(community);
			sizes[communityId] = community.getProperty(PropertyType.SIZE);
		}

		CoverProperties properties = new CoverProperties();
		properties.setTotalCommunities(communityCount);
		properties.setAverageCommunitySize(properties.calculateAverage(sizes));
		properties.setCommunitySizeDeviation(properties.calculateDeviation(sizes));
		
		cover.setCommunities(communities);
		cover.setProperties(properties);
		return cover;

	}

	public Cover buildMemberCommunity(NetworkStructure network, AlgorithmType algorithm,
			List<List<Integer>> memberCommunityLists) {

		System.out.println("memberCommunityList Size" + memberCommunityLists.size());

		Cover cover = new Cover();
		cover.setAlgorithmType(algorithm);

		int nodeCount = memberCommunityLists.size();

		int maxCommunity = 0;
		for (int i = 0; i < nodeCount; i++) {
			List<Integer> communityList = memberCommunityLists.get(i);
			for (int j = 0; j < communityList.size(); j++) {
				if (communityList.get(j) > maxCommunity)
					maxCommunity = communityList.get(j);
			}
		}

		List<Community> communities = new ArrayList<Community>();
		for (int communityId = 0; communityId < maxCommunity; communityId++) {
			List<Integer> memberList = new ArrayList<>();

			for (int j = 0; j < nodeCount; j++) {
				if (memberCommunityLists.get(j).contains(communityId)) {
					memberList.add(j);
				}
			}

			NetworkStructure communityNetwork = network.getSubNetwork(memberList);
			Community community = buildCommunity(communityNetwork, cover, memberList);
			communities.add(community);
		}
				
		cover.setCommunities(communities);
		
		
		
		return cover;

	}

	protected Community buildCommunity(NetworkStructure communityNetwork, Cover cover, List<Integer> memberList) {

		int edgeCount = communityNetwork.edgeCount();
		int nodeCount = communityNetwork.nodeCount();
		
		for(int memberId: memberList) {
			if(memberId >= nodeCount)
				throw new IllegalArgumentException("network and memberList do not match");
			if(memberId < 0)
				throw new IllegalArgumentException("negative memberId");
		}
		
		double[] degrees = new double[nodeCount];
		for (int nodeId = 0; nodeId < nodeCount; nodeId++) {
			degrees[nodeId] = communityNetwork.getEdges(nodeId).size();
		}

		NetworkProperties networkProperties = new NetworkProperties();
		networkProperties.setNodes(memberList.size());
		networkProperties.setEdges(edgeCount);	
		networkProperties.setDegreeDeviation(networkProperties.calculateDegreeDeviation(degrees));

		Community community = new Community();
		community.setCover(cover);
		community.setMembers(memberList);
		community.setProperties(networkProperties);

		return community;
	}
/*
	public Cover build(NetworkMeta network, AlgorithmType type, Matrix memberships) {
		
		Cover cover = new Cover();
		cover.setNetwork(network.getNetworkMeta());
		cover.setAlgorithmType(type);
		
		int communityCount = memberships.columns();
		int nodeCount = memberships.rows();
		double[] sizes = new double[communityCount];
				
		List<Community> communities = new ArrayList<Community>(communityCount);
		for (int communityId = 0; communityId < communityCount; communityId++) {
			
			List<Integer> memberList = new ArrayList<Integer>();

			for (int nodeId = 0; nodeId < nodeCount; nodeId++) {
				if(memberships.get(nodeId, communityId) > 0) {
					memberList.add(nodeId);
				}
			}
			
			NetworkStructure communityNetwork = null;
			try {
				communityNetwork = network.getNetworkStructure().getSubNetwork(memberList);
			} catch (ServiceNotFoundException | ServiceNotAvailableException | RemoteServiceException e) {
				e.printStackTrace();
			}
			
			Community community = buildCommunity(communityNetwork, cover, memberList);
			community.setCommunityId(communityId);			
			communities.add(community);
			sizes[communityId] = community.getProperty(PropertyType.SIZE);
		}
		
		CoverProperties properties = new CoverProperties();
		properties.setTotalCommunities(communityCount);
		properties.setAverageCommunitySize(properties.calculateAverage(sizes));
		properties.setCommunitySizeDeviation(properties.calculateDeviation(sizes));
		
		cover.setCommunities(communities);
		cover.setProperties(properties);
		
		return cover;

	}
*/
}
