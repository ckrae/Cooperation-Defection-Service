package i5.las2peer.services.cdService.data.evaluation;

import java.util.ArrayList;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

import i5.las2peer.api.exceptions.RemoteServiceException;
import i5.las2peer.api.exceptions.ServiceNotAvailableException;
import i5.las2peer.api.exceptions.ServiceNotFoundException;
import i5.las2peer.api.exceptions.StorageException;
import i5.las2peer.services.cdService.data.mapping.CoverSimulationSeriesMapping;
import i5.las2peer.services.cdService.data.network.Community;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;

public class Evaluation {

	public Evaluation() {

	}


	public void getCommunityCorrelations(ArrayList<SimulationSeries> series) throws StorageException {
		
		//ArrayList<CoverSimulationSeriesMapping> mappings = MappingDataProvider.getCoverSimulationSeriesMappings(series);
		
		
	}
	

	
	
	
	
	
	public double getPearsonsCorrelation(CoverSimulationSeriesMapping mapping, PropertyType property) throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		double[] cooperationValues = mapping.getCommunityCooperationValues();
		double[] propertyValues = getCommunitesProperties(mapping.getCommunities(), property);

		double correlation = new PearsonsCorrelation().correlation(cooperationValues, propertyValues);
		return correlation;

	}

	public double getSpearmansCorrelation(CoverSimulationSeriesMapping mapping, PropertyType property) throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		double[] cooperationValues = mapping.getCommunityCooperationValues();
		double[] propertyValues = getCommunitesProperties(mapping.getCommunities(), property);;

		double correlation = new SpearmansCorrelation().correlation(cooperationValues, propertyValues);
		return correlation;

	}
	
	

	
	
	public double[] getCommunitesProperties(ArrayList<Community> communities, PropertyType property) {
		
		double[] result = new double[communities.size()];
		for(int i=0, si=communities.size(); i<si; i++) {
			result[i] = getCommunityProperty(communities.get(i), property);			
		}
		return result;
	}
	
	
	public double getCommunityProperty(Community community, PropertyType property) {

		switch (property) {
		case SIZE:
			return community.getSize();
		
		case DENSITY:
			return community.getDensity();

		case AVERAGE_DEGREE:
			return community.getAverageDegree();
			
		case CLUSTERING_COEFFICIENT:
			return community.getClusteringCoefficient();

		default:
			break;
		}
		return 0.0;
	}

}
