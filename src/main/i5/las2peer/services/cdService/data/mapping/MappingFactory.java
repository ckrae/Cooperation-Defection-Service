package i5.las2peer.services.cdService.data.mapping;

import java.util.ArrayList;
import java.util.List;

import i5.las2peer.services.cdService.data.network.Community;
import i5.las2peer.services.cdService.data.network.Cover;
import i5.las2peer.services.cdService.data.simulation.AgentData;
import i5.las2peer.services.cdService.data.simulation.DataSet;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;

public class MappingFactory {

	public CoverSimulationSeriesMapping build(Cover cover, SimulationSeries series) {
		
		if(cover == null || series == null) 
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
		
		if(community == null || series == null)
			return null;
		
		List<CommunityDataSetMapping> list = new ArrayList<CommunityDataSetMapping>();
		List<DataSet> datasets = series.getDatasets();
		double cooperationValue = 0.0;
		
		int size = datasets.size();
		for (int i = 0; i < size; i++) {
			CommunityDataSetMapping mapping = getCommunityDatasetMapping(community, datasets.get(i));
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

	public CommunityDataSetMapping getCommunityDatasetMapping(Community community, DataSet dataSet) {
		
		if(community == null || dataSet == null)
			return null;
		
		List<Double> cooperationValues = getCommunityCooperationValues(dataSet.getAgentData(), community.getMembers());
		
		CommunityDataSetMapping mapping = new CommunityDataSetMapping();
		mapping.setCommunity(community);
		mapping.setDataSet(dataSet);		
		mapping.setCooperationValues(cooperationValues);
		mapping.setCooperationValue(cooperationValues.get(cooperationValues.size()-1));

		return mapping;
	}

	public List<Double> getCommunityCooperationValues(List<AgentData> agentList, List<Integer> memberList) {

		if (agentList == null || memberList == null || memberList.size() == 0 || agentList.size() == 0)
			return new ArrayList<Double>();

		List<Double> cooperationValues = new ArrayList<Double>();
		int maxRounds = agentList.get(0).getStrategies().size();
		for (int round = 0; round < maxRounds; round++) {

			int cooperators = 0;
			int maxMembers = memberList.size();
			for (int i = 0; i < maxMembers; i++) {
				int intMember = memberList.get(i);
				if (agentList.get(intMember).getStrategies().get(round)) {
					cooperators++;
				}
			}
			double value = cooperators / maxMembers;
			cooperationValues.add(value);
		}
		return cooperationValues;
	}

}
