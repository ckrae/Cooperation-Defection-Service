package i5.las2peer.services.cdService.simulation.dynamic;

import java.io.Serializable;

import i5.las2peer.services.cdService.simulation.Agent;
import i5.las2peer.services.cdService.simulation.Simulation;

/**
 *
 * Dynamics are used to update the nodes strategies The concrete update rules
 * are implemented as sub classes
 * 
 */
public abstract class Dynamic implements Serializable {

	/////////////// Attributes ///////////

	private static final long serialVersionUID = 1L;

	/**
	 * parameters of the dynamic
	 */
	private double[] values;

	/////////////// Constructor //////////

	protected Dynamic() {
		this(null);
	}

	protected Dynamic(double[] values) {
		this.values = values;
	}

	/////////////// Methods ///////////////	

	public double[] getValues() {
		return this.values;
	}

	/////////////// Override ///////////////

	/**
	 * this method determines the concrete update rule dynamic it have to be
	 * implemented in the sub classes
	 * 
	 */
	public abstract boolean getNewStrategy(Agent agent, Simulation simulation);

	public abstract DynamicType getDynamicType();

}
