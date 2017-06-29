package i5.las2peer.services.cdService.data.evaluation;

import i5.las2peer.services.cdService.data.ConsistencyException;
import i5.las2peer.services.cdService.data.mapping.CommunitySimulationSeriesMapping;
import i5.las2peer.services.cdService.data.network.Community;

public class CommunityData {

	final private long communityId;
	final private double cooperatioValue;
	final private double density;
	final private double averageDegree;

	public CommunityData(CommunitySimulationSeriesMapping mapping, Community community) throws ConsistencyException {
		
		if(mapping.getCommunityId() == community.getCommunityId()) {
			this.communityId = community.getCommunityId();
		} else {
			throw new ConsistencyException();
		}
		
		this.cooperatioValue = 0.0;
		this.density =  0.0;
		this.averageDegree = 0.0;
				
	}
	
	public long getCommunityId() {
		return this.communityId;		
	}
	
	public double getCooperationValue() {
		return this.cooperatioValue;
	}
	
	public double getDensity() {
		return this.density;
	}
	
	public double getAverageDegree() {
		return this.averageDegree;
	}
	
}
