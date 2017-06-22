package i5.las2peer.services.cdService.data.mapping;

import java.util.ArrayList;

public class CommunitySimulationSeriesMapping {

	private final ArrayList<CommunitySimulationDataMapping> mappings;

	private final long seriesId;
	private final long graphId;
	private final long coverId;
	private final long communityId;

	public CommunitySimulationSeriesMapping(long seriesId, long graphId, long coverId, long communityId,
			ArrayList<CommunitySimulationDataMapping> mappings) {

		this.mappings = mappings;
		this.seriesId = seriesId;
		this.communityId = communityId;
		this.graphId = graphId;
		this.coverId = coverId;
	}

	public long getSeriesId() {
		return seriesId;
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
	
	public ArrayList<CommunitySimulationDataMapping> getMappings() {
		return mappings;
	}




}
