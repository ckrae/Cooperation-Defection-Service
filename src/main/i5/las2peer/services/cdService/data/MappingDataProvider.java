package i5.las2peer.services.cdService.data;

import java.util.ArrayList;
import java.util.List;

import i5.las2peer.api.exceptions.ServiceInvocationException;
import i5.las2peer.services.cdService.data.mapping.CoverSimulationSeriesMapping;
import i5.las2peer.services.cdService.data.mapping.MappingFactory;
import i5.las2peer.services.cdService.data.network.GraphAdapter;
import i5.las2peer.services.cdService.data.network.NetworkMeta;
import i5.las2peer.services.cdService.data.network.cover.Cover;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;

public class MappingDataProvider {

	SimulationDataProvider simulationDataProvider;
	NetworkDataProvider networkDataProvider;
	CoverDataProvider coverDataProvider;
	GraphAdapter graphAdapter;
	MappingFactory factory;

	private MappingDataProvider() {
		simulationDataProvider = SimulationDataProvider.getInstance();
		networkDataProvider = NetworkDataProvider.getInstance();
		coverDataProvider = CoverDataProvider.getInstance();
		factory = new MappingFactory();
	}

	public static MappingDataProvider getInstance() {
		return new MappingDataProvider();
	}
	
	public CoverSimulationSeriesMapping getCoverSimulationSeriesMapping(long coverId, long seriesId) throws ServiceInvocationException {

		SimulationSeries series = simulationDataProvider.getSimulationSeries(seriesId);		
		Cover cover = coverDataProvider.getCover(coverId);
				
		if(cover == null || series == null)
			return null;
		
		CoverSimulationSeriesMapping mapping = factory.build(cover, series);
		return mapping;
	}

	public List<CoverSimulationSeriesMapping> getCoverSimulationSeriesMappings(long seriesId) {
		
		List<CoverSimulationSeriesMapping> mappings = new ArrayList<>();
		
		SimulationSeries simulation = simulationDataProvider.getSimulationSeries(seriesId);
		long graphId = simulation.getParameters().getGraphId();
		NetworkMeta network = networkDataProvider.getNetwork(graphId);
		List<Cover> covers = coverDataProvider.getCovers(network);
		
		for(Cover cover: covers) {
			mappings.add(factory.build(cover, simulation));
		}		
		
		return mappings;
	}
	
}
