package i5.las2peer.services.cdService.data.simulation;

import java.io.Serializable;

public class SimulationParameters implements Serializable {

	private static final long serialVersionUID = 1L;

	private long graphId;
	private String dynamic;
	private double[] dynamicValues;
	private double[] payoffValues;
	private int iterations;

	public SimulationParameters() {

	}

	public SimulationParameters(long graphId, double[] payoffValues, String dynamic, double[] dynamicValues,
			int iterations) {

		this.setGraphId(graphId);
		this.setPayoffValues(payoffValues);
		this.setDynamic(dynamic);
		this.setDynamicValues(dynamicValues);
		this.setIterations(iterations);
	}

	public long getGraphId() {
		return graphId;
	}

	public String getDynamic() {
		return dynamic;
	}

	public double[] getPayoffValues() {
		return payoffValues;
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public double[] getDynamicValues() {
		return this.dynamicValues;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setGraphId(long graphId) {
		this.graphId = graphId;
	}

	public void setDynamic(String dynamic) {
		this.dynamic = dynamic;
	}

	public void setDynamicValues(double[] dynamicValues) {
		this.dynamicValues = dynamicValues;
	}

	public void setPayoffValues(double[] payoffValues) {		
		this.payoffValues = payoffValues;
	}
	
	public void setDynamicValue(double dynamicValue) {
		this.setDynamicValues(new double[]{dynamicValue});
	}

	public void normalize() {		
		
		//cast two value cost game to four value game
		if(payoffValues.length == 2) {
			double[] newValues = new double[4];
			newValues[0] = payoffValues[0] - payoffValues[1];
			newValues[1] = payoffValues[0];
			newValues[2] = -payoffValues[1];
			newValues[3] = 0.0;
			setPayoffValues(newValues);
		}		
		
		//normalize payoff values
		int size=getPayoffValues().length;
		double total = 0.0;
		for(int i=0; i<size; i++) {
			total += Math.abs(payoffValues[i]);
		}
		if(total != 0.0) {
			for(int i=0; i<size; i++) {
				payoffValues[i] = payoffValues[i] / total;
			}
		}
		
	}

}
