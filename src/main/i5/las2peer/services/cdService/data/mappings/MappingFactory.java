package i5.las2peer.services.cdService.data.mappings;

import java.util.ArrayList;
import java.util.HashMap;

import i5.las2peer.api.exceptions.RemoteServiceException;
import i5.las2peer.api.exceptions.ServiceNotAvailableException;
import i5.las2peer.api.exceptions.ServiceNotFoundException;
import i5.las2peer.api.exceptions.StorageException;
import i5.las2peer.services.cdService.data.manager.DataManager;
import i5.las2peer.services.cdService.data.simulation.SimulationData;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;
import i5.las2peer.services.cdService.network.Community;
import i5.las2peer.services.cdService.network.Cover;
import i5.las2peer.services.cdService.network.Network;
import i5.las2peer.services.cdService.network.NetworkManager;

public class MappingFactory {

	public static NetworkSimulationSeriesMapping build(long seriesId)
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException, StorageException {

		SimulationSeries series = DataManager.getSimulationSeries(seriesId);
		long graphId = series.getParameters().getGraphId();
		Network network = NetworkManager.getNetwork(graphId);

		HashMap<Integer, Cover> map = network.getCovers();
		ArrayList<CoverSimulationSeriesMapping> list = new ArrayList<CoverSimulationSeriesMapping>();
		for (Cover cover : map.values()) {
			list.add(getCoverSimulationSeriesMapping(cover, series));
		}
		return new NetworkSimulationSeriesMapping(seriesId, graphId, list);

	}

	private static CoverSimulationSeriesMapping getCoverSimulationSeriesMapping(Cover cover, SimulationSeries series) {

		ArrayList<CommunitySimulationSeriesMapping> list = new ArrayList<CommunitySimulationSeriesMapping>();
		ArrayList<Community> communities = cover.getCommunities();
		for (int i = 0, size = communities.size(); i < size; i++) {
			list.add(getCommunitySimulationSeriesMapping(communities.get(i), series));
		}

		return new CoverSimulationSeriesMapping(series.getSeriesId(), cover.getGraphId(), cover.getCoverId(), list);
	}

	private static CommunitySimulationSeriesMapping getCommunitySimulationSeriesMapping(Community community,
			SimulationSeries series) {

		ArrayList<CommunitySimulationDataMapping> list = new ArrayList<CommunitySimulationDataMapping>();
		ArrayList<SimulationData> datasets = series.getDatasets();
		for (int i = 0, size = datasets.size(); i < size; i++) {
			list.add(getCommunitySimulationSeriesMapping(community, datasets.get(i)));
		}

		return new CommunitySimulationSeriesMapping(series.getSeriesId(), series.getParameters().getGraphId(),
				community.getCoverId(), community.getCommunityId(), list);
	}

	private static CommunitySimulationDataMapping getCommunitySimulationSeriesMapping(Community community,
			SimulationData simulationData) {
		ArrayList<Double> values = getCommunityCooperationValues(simulationData.getNodeStrategies(),
				community.getMembers());
		return new CommunitySimulationDataMapping(0, simulationData.getDataId(), 0, community.getCoverId(), community.getCommunityId(), values, null);
	}

	public static ArrayList<Double> getCommunityCooperationValues(ArrayList<ArrayList<Boolean>> nodeStrategies,
			ArrayList<Long> members) {

		ArrayList<Double> cooperationValues = new ArrayList<Double>();
		for (int round = 0, maxRound = nodeStrategies.get(0).size(); round < maxRound; round++) {

			int cooperators = 0;
			int defectors = 0;
			for (int memberInt = 0, size = members.size(); memberInt < size; memberInt++) {
				if (nodeStrategies.get(memberInt).get(round)) {
					cooperators++;
				} else {
					defectors++;
				}
				double value = cooperators / (cooperators + defectors);
				cooperationValues.add(value);
			}
		}

		return cooperationValues;
	}

}
