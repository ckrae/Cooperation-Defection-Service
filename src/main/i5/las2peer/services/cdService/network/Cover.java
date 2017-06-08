package i5.las2peer.services.cdService.network;

import java.util.ArrayList;

public class Cover {

	private final ArrayList<Community> communities;
	private final String algorithm;
	private final long graphId;
	private final long coverId;
	
	public Cover(long graphId, long coverId, String algorithm, ArrayList<Community> communities) {
		
		this.algorithm = algorithm;
		this.communities = communities;	
		this.graphId = graphId;
		this.coverId = coverId;
	}
	
	public ArrayList<Community> getCommunities() {
		
		return this.communities;
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
