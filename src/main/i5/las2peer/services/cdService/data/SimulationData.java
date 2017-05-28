package i5.las2peer.services.cdService.data;


import java.io.Serializable;


/**
 * Simulation Data
 * 
 * Objects of this class represent the data collected by simulation series
 * The objects can be stored 
 * 
 */
public class SimulationData implements Serializable {

/////////////// Attributes ///////////////

	private static final long serialVersionUID = 1L;		
	
	/////// Results ///////
	
	private final double[] CooperationValues;
	private final boolean[] CooperationNetwork;
	private final double[] PayoffNetwork;
	
/////////////// Constructor ///////////////
	
	public SimulationData (double[] values, boolean[] networkCooperation, double[] networkPayoff) {		
		
		this.CooperationValues = values;
		this.CooperationNetwork = networkCooperation;
		this.PayoffNetwork = networkPayoff;
	}	

/////////////// Getter ///////////////	

	public double[] getCooperationValues() {		
		return this.CooperationValues;
	}

	public boolean[] getCooperationNetwork() {
		return CooperationNetwork;
	}

	public double[] getPayoffNetwork() {
		return PayoffNetwork;
	}	
	
	
	
	
}
