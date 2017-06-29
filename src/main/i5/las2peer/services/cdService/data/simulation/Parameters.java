package i5.las2peer.services.cdService.data.simulation;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import i5.las2peer.services.cdService.simulation.dynamic.DynamicType;
import i5.las2peer.services.cdService.simulation.game.GameType;

@Entity
public class Parameters implements Serializable {

	private static final long serialVersionUID = 1L;

	////////// Entity Fields //////////

	@Id
	@OneToOne(fetch = FetchType.LAZY)
	SimulationSeries series;

	@Basic
	private long graphId;

	@Enumerated(EnumType.STRING)
	private GameType game;

	@Basic
	private double[] payoffValues;

	@Enumerated(EnumType.STRING)
	private DynamicType dynamic;

	@Basic
	private double[] dynamicValues;

	@Basic
	private int iterations;

	////////// Constructor //////////

	public Parameters() {

	}

	public Parameters(SimulationSeries series, long graphId, double[] payoffValues, DynamicType dynamic,
			double[] dynamicValues, int iterations) {

		this.series = series;
		this.setGraphId(graphId);
		this.setPayoffValues(payoffValues);
		this.setDynamic(dynamic);
		this.setDynamicValues(dynamicValues);
		this.setIterations(iterations);
	}

	////////// Getter //////////

	public SimulationSeries getSimulationSeries() {
		return this.series;
	}
	
	@JsonProperty
	public long getGraphId() {
		return graphId;
	}
	
	@JsonProperty
	public double[] getPayoffValues() {
		return payoffValues;
	}
	
	@JsonProperty
	public DynamicType getDynamic() {
		return dynamic;
	}
	
	@JsonProperty
	public double[] getDynamicValues() {
		return this.dynamicValues;
	}
	
	@JsonProperty
	public int getIterations() {
		return iterations;
	}
	
	@JsonSetter
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}
	
	@JsonSetter
	public void setGraphId(long graphId) {
		this.graphId = graphId;
	}
	
	@JsonSetter
	public void setDynamic(String dynamic) {
		this.dynamic = DynamicType.fromString(dynamic);
	}
	
	@JsonIgnore
	public void setDynamic(DynamicType dynamic) {
		this.dynamic = dynamic;
	}	

	public void setDynamicValues(double[] dynamicValues) {
		this.dynamicValues = dynamicValues;
	}
	
	@JsonSetter
	public void setPayoffValues(double[] payoffValues) {
		this.payoffValues = payoffValues;
	}	
	
	public void setDynamicValue(double dynamicValue) {
		this.setDynamicValues(new double[] { dynamicValue });
	}

	///////////// Methods /////////////

	public void normalize() {

		// cast two value cost game to four value game
		if (payoffValues.length == 2) {
			double[] newValues = new double[4];
			newValues[0] = payoffValues[0] - payoffValues[1];
			newValues[1] = payoffValues[0];
			newValues[2] = -payoffValues[1];
			newValues[3] = 0.0;
			setPayoffValues(newValues);
		}

		// normalize payoff values
		int size = getPayoffValues().length;
		double total = 0.0;
		for (int i = 0; i < size; i++) {
			total += Math.abs(payoffValues[i]);
		}
		if (total != 0.0) {
			for (int i = 0; i < size; i++) {
				payoffValues[i] = payoffValues[i] / total;
			}
		}

	}

}
