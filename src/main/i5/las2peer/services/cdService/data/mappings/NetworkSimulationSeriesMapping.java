package i5.las2peer.services.cdService.data.mappings;

import java.util.ArrayList;

public class NetworkSimulationSeriesMapping {

	private final ArrayList<CoverSimulationSeriesMapping> mappings;

	private final long seriesId;
	private final long graphId;

	public NetworkSimulationSeriesMapping(long seriesId, long graphId,
			ArrayList<CoverSimulationSeriesMapping> mappings) {

		this.mappings = mappings;
		this.seriesId = seriesId;
		this.graphId = graphId;
	}

	public long getSeriesId() {
		return seriesId;
	}

	public long getGraphId() {
		return graphId;
	}

}
