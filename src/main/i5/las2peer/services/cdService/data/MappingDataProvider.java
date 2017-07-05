package i5.las2peer.services.cdService.data;

import java.util.ArrayList;
import java.util.List;

import i5.las2peer.api.exceptions.RemoteServiceException;
import i5.las2peer.api.exceptions.ServiceNotAvailableException;
import i5.las2peer.api.exceptions.ServiceNotFoundException;
import i5.las2peer.services.cdService.data.mapping.CoverSimulationSeriesMapping;
import i5.las2peer.services.cdService.data.mapping.MappingFactory;
import i5.las2peer.services.cdService.data.network.Cover;
import i5.las2peer.services.cdService.data.network.NetworkAdapter;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;

public class MappingDataProvider {

	SimulationDataProvider simulationDataProvider;
	NetworkDataProvider networkDataProvider;
	MappingFactory factory;

	private MappingDataProvider() {
		simulationDataProvider = SimulationDataProvider.getInstance();
		networkDataProvider = NetworkDataProvider.getInstance();
		factory = new MappingFactory();
	}

	public static MappingDataProvider getInstance() {
		return new MappingDataProvider();
	}
	
	public List<CoverSimulationSeriesMapping> getCoverSimulationSeriesMappings(long seriesId) throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		SimulationSeries series = simulationDataProvider.getSimulationSeries(seriesId);
		long graphId = series.getParameters().getGraphId();
		
		List<Integer> coverIds = NetworkAdapter.inovkeCovers(series.getParameters().getGraphId());		
		
		if(coverIds == null || coverIds.isEmpty())
			return null;
		
		List<CoverSimulationSeriesMapping> mappings = new ArrayList<CoverSimulationSeriesMapping>();
		for (Integer coverId : coverIds) {
			Cover cover = networkDataProvider.getCover(graphId, coverId);
			mappings.add(factory.build(cover, series));
		}
		return mappings;
	}
	
}
