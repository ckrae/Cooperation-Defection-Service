package i5.las2peer.services.cdService.data;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class SimulationContainer implements Serializable {

	/**
	 * Simulation Container
	 * 
	 * container for all simulation series performed by an agent
	 * identifier SERVICE_PREFIX + UserId 
	 * 
	 */	
	
	
/////////////// Attributes ///////////////

	private static final long serialVersionUID = 1L;
	
	private long seriesIndex;
	private ArrayList<SimulationSeries> series;
	private HashMap<Long, ArrayList<Long>> networkMapping;
	
	
/////////////// Constructor  ///////////////	
		
	public SimulationContainer () {
			
		seriesIndex = 0;
		
	}	

	
/////////////// Methods  ///////////////////	

	public long addSimulationSeries(SimulationSeries simSe) {
		
		seriesIndex++;
		series.add(simSe);
		return seriesIndex;
	}
	
	public void removeSimulationSeries() {	
		
	}

	public long getLastIndex() {
		return this.seriesIndex;
	}
	
	
}
