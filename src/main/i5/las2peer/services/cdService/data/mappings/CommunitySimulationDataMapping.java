package i5.las2peer.services.cdService.data.mappings;

import java.util.ArrayList;

public class CommunitySimulationDataMapping {

	private final ArrayList<Double> cooperationValues;
	private final ArrayList<Double> payoffValues;

	private final long seriesId;
	private final long dataId;
	private final long graphId;
	private final long coverId;
	private final long communityId;

	public CommunitySimulationDataMapping(long seriesId, long dataId, long graphId, long coverId, long communityId,
			ArrayList<Double> cooperationValues, ArrayList<Double> payoffValues) {

		this.cooperationValues = cooperationValues;
		this.payoffValues = payoffValues;
		this.seriesId = seriesId;
		this.dataId = dataId;
		this.graphId = graphId;
		this.coverId = coverId;
		this.communityId = communityId;
	}

	public long getSeriesId() {
		return seriesId;
	}

	public long getDataId() {
		return dataId;
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

	public ArrayList<Double> getCooperationValues() {
		return cooperationValues;
	}

	public ArrayList<Double> getPayoffValues() {
		return payoffValues;
	}

}
