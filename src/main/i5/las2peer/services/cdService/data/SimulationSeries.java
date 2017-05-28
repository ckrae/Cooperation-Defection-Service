package i5.las2peer.services.cdService.data;


import java.io.Serializable;
import java.util.ArrayList;

import i5.las2peer.api.Context;
import i5.las2peer.security.UserAgent;
import i5.las2peer.services.cdService.CDService;


/**
 * SimulationSeries
 * 
 * Container for all SimulationData of the same game series.
 * identifier SERVICE_PREFIX + UserId + # + SeriesId
 */

public class SimulationSeries implements Serializable {

/////////////// Attributes ///////////////

	private static final long serialVersionUID = 1L;
	
	private final SimulationParameters parameters;	
	private ArrayList<SimulationData> datasets;
	
////////////////////////////////////////////	
		
	public SimulationSeries (SimulationParameters parameters) {
		
		this.parameters = parameters;
		this.datasets = new ArrayList<SimulationData>();
	
	}	
	
	public void addSimnulationData (SimulationData data) {
		
		datasets.add(data);		
	}

	public SimulationParameters getParameters() {
		return parameters;
	}


	


	
	
	
	
}
