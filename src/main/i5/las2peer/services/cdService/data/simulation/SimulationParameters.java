package i5.las2peer.services.cdService.data.simulation;

import java.io.Serializable;

public class SimulationParameters implements Serializable {

	private static final long serialVersionUID = 1L;
	private long graphId;
	private String dynamic;
	private double dynamicValue;
	private double[] payoffValues;
	private int iterations;

	public long getGraphId() {
		return graphId;
	}

	public void setGraphId(long graphId) {
		this.graphId = graphId;
	}

	public String getDynamic() {
		return dynamic;
	}

	public void setDynamic(String dynamic) {
		this.dynamic = dynamic;
	}

	public double[] getPayoffValues() {
		return payoffValues;
	}

	public void setPayoffValues(double[] payoffValues) {
		this.payoffValues = payoffValues;
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public double getDynamicValue() {
		return this.dynamicValue;
	}

	public void setDynamicValue(double dynamicValue) {
		this.dynamicValue = dynamicValue;
	}

}
