package i5.las2peer.services.cdService.data.mapping;

import java.io.Serializable;
import java.util.ArrayList;

import i5.las2peer.api.exceptions.RemoteServiceException;
import i5.las2peer.api.exceptions.ServiceNotAvailableException;
import i5.las2peer.api.exceptions.ServiceNotFoundException;
import i5.las2peer.services.cdService.data.NetworkDataProvider;
import i5.las2peer.services.cdService.data.network.Community;
import i5.las2peer.services.cdService.data.network.Cover;

public class CoverSimulationSeriesMapping implements Serializable {

	private static final long serialVersionUID = 1L;
	
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

	public ArrayList<CommunitySimulationSeriesMapping> getCommunityMappings() {
		return mappings;
	}

	public double[] getCommunityCooperationValues() {

		ArrayList<CommunitySimulationSeriesMapping> communityList = getCommunityMappings();
		int communityCount = communityList.size();
		int datasetsCount = communityList.get(0).getMappings().size();

		double[] cooperationValues = new double[communityCount * datasetsCount + datasetsCount];

		for (int i = 0, si = communityCount; i < si; i++) {
			ArrayList<CommunitySimulationDataMapping> dataList = communityList.get(i).getMappings();
			for (int j = 0, sj = datasetsCount; j < sj; j++) {
				cooperationValues[(i * sj) + j] = dataList.get(j).getCooperationValue();
			}
		}
		return cooperationValues;
	}

	public double[] AverageCommunityCooperationValues() {

		ArrayList<CommunitySimulationSeriesMapping> communityList = getCommunityMappings();
		int communityCount = communityList.size();

		double[] cooperationValues = new double[communityCount];
		for (int i = 0, si = communityCount; i < si; i++) {

			// TODO
		}
		return cooperationValues;
	}

	public ArrayList<Community> getCommunities() throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {
		
		Cover cover = NetworkDataProvider.getInstance().getCover(graphId, coverId);
		return cover.getCommunities();
	}



}
