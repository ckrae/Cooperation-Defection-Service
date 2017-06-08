package i5.las2peer.services.cdService.data.mappings;

import java.util.ArrayList;

public class CoverSimulationSeriesMapping {

	private final ArrayList<CommunitySimulationSeriesMapping> mappings;

	private final long seriesId;
	private final long graphId;
	private final long coverId;

	public CoverSimulationSeriesMapping(long seriesId, long graphId, long coverId,
			ArrayList<CommunitySimulationSeriesMapping> mappings) {

		this.mappings = mappings;
		this.seriesId = seriesId;
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

}
