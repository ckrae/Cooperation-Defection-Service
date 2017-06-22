package i5.las2peer.services.cdService.data.network;

import java.util.ArrayList;

public class Community {

	private final ArrayList<Integer> members;
	private final long graphId;
	private final long coverId;
	private final long communityId;

	protected Community(long graphId, long coverId, long communityId, ArrayList<Integer> members) {

		this.members = members;
		this.graphId = graphId;
		this.coverId = coverId;
		this.communityId = communityId;
	}

	public ArrayList<Integer> getMembers() {
		return this.members;
	}
	
	public long getGraphId() {
		return graphId;
	}	
	
	public long getCoverId() {
		return coverId;
	}

	public long getCommunityId() {
		return communityId;
	}
	
	//// Properties ////
	
	public double getDensity() {
		
		return 0.0;
	}
	
	public double getAverageDegree() {
		
		return 0.0;		
	}

	public double getSize() {

		return 0;
	}

	public double getClusteringCoefficient() {

		return 0;
	}
	
	

}
