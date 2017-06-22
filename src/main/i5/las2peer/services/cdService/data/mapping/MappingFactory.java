package i5.las2peer.services.cdService.data.mapping;

import java.util.ArrayList;

import i5.las2peer.services.cdService.data.network.Community;
import i5.las2peer.services.cdService.data.network.Cover;
import i5.las2peer.services.cdService.data.simulation.SimulationData;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;

public class MappingFactory {


	public static CoverSimulationSeriesMapping build(Cover cover, SimulationSeries series) {

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

	private static ArrayList<Double> getCommunityCooperationValues(ArrayList<ArrayList<Boolean>> nodeStrategies,
			ArrayList<Integer> arrayList) {

		ArrayList<Double> cooperationValues = new ArrayList<Double>();
		for (int round = 0, maxRound = nodeStrategies.get(0).size(); round < maxRound; round++) {

			int cooperators = 0;
			int defectors = 0;
			for (int memberInt = 0, size = arrayList.size(); memberInt < size; memberInt++) {
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
