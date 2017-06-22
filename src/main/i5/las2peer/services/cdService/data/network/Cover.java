package i5.las2peer.services.cdService.data.network;

import java.util.ArrayList;

import i5.las2peer.services.cdService.data.provider.ConsistencyException;

public class Cover {

	private final ArrayList<Community> communities;
	private final String algorithm;
	private final long graphId;
	private final long coverId;

	public Cover(long graphId, long coverId, String algorithm, ArrayList<ArrayList<Integer>> communityList) {

		this.algorithm = algorithm;
		this.graphId = graphId;
		this.coverId = coverId;
		
		this.communities = new ArrayList<Community>(communityList.size());
		for (int i = 0; i < communityList.size(); i++) {
			Community community = new Community(graphId, coverId, i, communityList.get(i));
			communities.add(community);
		}
		
	}

	public ArrayList<Community> getCommunities() {

		return this.communities;
	}

	public Community getCommunity(int communityId) throws ConsistencyException {

		if (communityId < communities.size()) {
			Community community = communities.get(communityId);
			if(communityId != community.getCommunityId()) {
				throw new ConsistencyException();
			}
			return community;
		}
		return null;
	}

	public long getGraphId() {
		return graphId;
	}

	public long getCoverId() {
		return coverId;
	}

	public String getAlgorithm() {
		return algorithm;
	}

}
