package i5.las2peer.services.cdService.data.mappings;

import java.util.ArrayList;

public class CoverSimulationDataMapping {

	private final ArrayList<CommunitySimulationDataMapping> mappings;
	
	private final long seriesId;
	private final long dataId;	
	private final long graphId;
	private final long coverId;
	
	public CoverSimulationDataMapping(long seriesId, long dataId, long graphId, long coverId, ArrayList<CommunitySimulationDataMapping> mappings) {
		
		this.mappings = mappings;		
		this.seriesId = seriesId;
		this.dataId = dataId;
		this.graphId = graphId;
		this.coverId = coverId;
		
	}
}
