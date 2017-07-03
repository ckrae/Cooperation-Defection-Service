package i5.las2peer.services.cdService.data;

import java.util.ArrayList;

import i5.las2peer.api.exceptions.RemoteServiceException;
import i5.las2peer.api.exceptions.ServiceNotAvailableException;
import i5.las2peer.api.exceptions.ServiceNotFoundException;
import i5.las2peer.api.exceptions.StorageException;
import i5.las2peer.services.cdService.data.mapping.CoverSimulationSeriesMapping;
import i5.las2peer.services.cdService.data.mapping.MappingFactory;
import i5.las2peer.services.cdService.data.network.Cover;
import i5.las2peer.services.cdService.data.network.Graph;
import i5.las2peer.services.cdService.data.network.NetworkAdapter;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;
import i5.las2peer.services.cdService.simulation.dynamic.DynamicType;

public class MappingDataProvider {

	SimulationDataProvider simulationDataProvider;
	NetworkDataProvider networkDataProvider;

	private MappingDataProvider() {
		simulationDataProvider = SimulationDataProvider.getInstance();
		networkDataProvider = NetworkDataProvider.getInstance();
	}

	public static MappingDataProvider getInstance() {

		return new MappingDataProvider();
	}

	public ArrayList<CoverSimulationSeriesMapping> getCoverSimulationSeriesMappings(DynamicType dynamic,
			double[] scheme, String algorithm) throws StorageException {

		//ArrayList<SimulationSeries> series = simulationDataProvider.getSimulationSeries(scheme, dynamic);
		//ArrayList<Network> networks = networkDataProvider.getNetworks(simulationDataProvider.getNetworkIds(series));
		//ArrayList<Cover> covers = networkDataProvider.getCovers(networks, algorithm);

		//return getCoverSimulationSeriesMappings(covers, series);
		return null;
	}

	public ArrayList<CoverSimulationSeriesMapping> getCoverSimulationSeriesMappings(ArrayList<Cover> covers,
			ArrayList<SimulationSeries> series) throws StorageException {

		ArrayList<CoverSimulationSeriesMapping> mappings = new ArrayList<CoverSimulationSeriesMapping>(covers.size() * series.size());
		for (int i = 0, si = covers.size(); i < si; i++) {
			for (int j = 0, sj = series.size(); j < sj; j++) {
				mappings.add(MappingFactory.build(covers.get(i), series.get(j)));
			}
		}
		return mappings;
	}

	public ArrayList<CoverSimulationSeriesMapping> getCoverSimulationSeriesMappings(long seriesId)
			throws StorageException, ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		SimulationSeries series = simulationDataProvider.getSimulationSeries(seriesId);
		ArrayList<Integer> covers = NetworkAdapter.inovkeCovers(series.getParameters().getGraphId());
		ArrayList<CoverSimulationSeriesMapping> mappings = new ArrayList<CoverSimulationSeriesMapping>();		
		
		if (covers == null)
			return mappings;

		for (Integer coverId : covers) {
			mappings.add(MappingFactory.build(NetworkAdapter.inovkeCoverById(series.getParameters().getGraphId(), coverId), series));
		}

		return mappings;
	}

}
