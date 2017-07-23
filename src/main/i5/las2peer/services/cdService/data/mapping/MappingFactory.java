package i5.las2peer.services.cdService.data.mapping;

import java.util.ArrayList;
import java.util.List;

import i5.las2peer.services.cdService.data.network.cover.Community;
import i5.las2peer.services.cdService.data.network.cover.Cover;
import i5.las2peer.services.cdService.data.simulation.AgentData;
import i5.las2peer.services.cdService.data.simulation.SimulationDataset;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;

public class MappingFactory {

	public CoverSimulationSeriesMapping build(Cover cover, SimulationSeries series) {

		if (cover == null || series == null)
			return null;

		List<CommunitySimulationSeriesMapping> list = new ArrayList<CommunitySimulationSeriesMapping>();
		List<Community> communities = cover.getCommunities();
		for (int i = 0, size = communities.size(); i < size; i++) {
			list.add(getCommunitySimulationSeriesMapping(communities.get(i), series));
		}

		CoverSimulationSeriesMapping mapping = new CoverSimulationSeriesMapping();
		mapping.setCover(cover);
		mapping.setMappings(list);

		return mapping;
	}

	public CommunitySimulationSeriesMapping getCommunitySimulationSeriesMapping(Community community,
			SimulationSeries series) {

		if (community == null || series == null)
			return null;

		List<CommunityDataSetMapping> list = new ArrayList<CommunityDataSetMapping>();
		List<SimulationDataset> simulationDatasets = series.getSimulationDatasets();
		double cooperationValue = 0.0;

		int size = simulationDatasets.size();
		for (int i = 0; i < size; i++) {
			CommunityDataSetMapping mapping = getCommunitySimulationDatasetMapping(community, simulationDatasets.get(i));
			list.add(mapping);
			cooperationValue += mapping.getCooperationValue();
		}
		cooperationValue = cooperationValue / size;

		CommunitySimulationSeriesMapping mapping = new CommunitySimulationSeriesMapping();
		mapping.setSeries(series);
		mapping.setMappings(list);
		mapping.setCooperationValue(cooperationValue);

		return mapping;
	}

	protected CommunityDataSetMapping getCommunitySimulationDatasetMapping(Community community, SimulationDataset dataSet) {

		if (community == null || dataSet == null)
			return null;

		List<Double> cooperationValues = getCommunityCooperationValues(dataSet.getAgentData(), community.getMembers());

		CommunityDataSetMapping mapping = new CommunityDataSetMapping();
		mapping.setCommunity(community);
		mapping.setDataSet(dataSet);
		mapping.setCooperationValues(cooperationValues);
		mapping.setCooperationValue(cooperationValues.get(cooperationValues.size() - 1));

		return mapping;
	}

	protected List<Double> getCommunityCooperationValues(List<AgentData> agentList, List<Integer> memberList) {

		if (agentList == null || memberList == null || memberList.size() == 0 || agentList.size() == 0
				|| agentList.get(0).getStrategies() == null)
			return new ArrayList<Double>();

		List<Double> cooperationValues = new ArrayList<Double>();
		int maxRounds = agentList.get(0).getStrategies().size();

		for (int round = 0; round < maxRounds; round++) {

			double value = getCooperationValue(memberList, agentList, round);
			cooperationValues.add(value);
		}
		return cooperationValues;
	}

	protected double getCooperationValue(List<Integer> memberList, List<AgentData> agents, int round) {

		double cooperators = 0;
		double defectors = 0;
		for(int i: memberList) {
			List<Boolean> strategies = agents.get(i).getStrategies();
			if (strategies.get(round)) {
				cooperators++;
			} else {
				defectors++;
			}
		}
		
		return ((cooperators) / (cooperators+defectors));

	}

}
