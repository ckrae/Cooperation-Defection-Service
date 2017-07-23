package i5.las2peer.services.cdService.data;

import java.util.ArrayList;
import java.util.List;

import i5.las2peer.api.exceptions.RemoteServiceException;
import i5.las2peer.api.exceptions.ServiceInvocationException;
import i5.las2peer.api.exceptions.ServiceNotAvailableException;
import i5.las2peer.api.exceptions.ServiceNotFoundException;
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
	
	public List<CoverSimulationSeriesMapping> getCoverSimulationSeriesMappings(long seriesId) throws ServiceInvocationException {

		SimulationSeries series = simulationDataProvider.getSimulationSeries(seriesId);
		long graphId = series.getParameters().getGraphId();
		NetworkMeta network = networkDataProvider.getNetwork(graphId);
		
		List<Integer> coverIds = null;	
		
		if(coverIds == null || coverIds.isEmpty())
			return null;
		
		List<CoverSimulationSeriesMapping> mappings = new ArrayList<CoverSimulationSeriesMapping>();
		for (Integer coverId : coverIds) {
			Cover cover = null;
			mappings.add(factory.build(cover, series));
		}
		return mappings;
	}
	
}
