package i5.las2peer.services.cdService.data.simulation;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Evaluation implements Serializable {

	private static final long serialVersionUID = 1L;

	///////// Entity Fields ///////////

	@Id
	@OneToOne(fetch = FetchType.LAZY)
	SimulationSeries series;

	@Basic
	private double averageCooperationValue;

	@Basic
	private double variance;

	@Basic
	private double standartDeviation;

	/////////// Constructor ///////////

	public Evaluation() {

	}

	public Evaluation(SimulationSeries series) {

		this.series = series;

		double[] values = series.getLastCooperationValues();
		this.averageCooperationValue = calculateAverageCooperationValue(values);
		this.variance = calculateVariance(values, averageCooperationValue);
		this.standartDeviation = calculateStandartDeviation(variance);
	}

	/////////// Calculations ///////////

	private double calculateAverageCooperationValue(double[] values) {

		if (values == null)
			return 0.0;

		double sum = 0.0;
		for (double value : values) {
			sum += value;
		}
		return (sum / values.length);

	}

	private double calculateVariance(double[] values, double average) {

		if (values == null)
			return 0.0;

		double sum = 0.0;
		for (double value : values) {
			sum += Math.pow((value - average), 2);
		}
		return (sum / values.length);
	}

	private double calculateStandartDeviation(double varianz) {

		return Math.sqrt(varianz);

	}

	//////////// Getter /////////////
	
	@JsonIgnore
	public SimulationSeries getSeries() {
		return this.series;
	}
	
	@JsonProperty
	public double getAverageCooperationValue() {
		return this.averageCooperationValue;
	}
	
	@JsonProperty
	public double getVariance() {
		return this.variance;
	}
	
	@JsonProperty
	public double getStandartDeviation() {
		return this.standartDeviation;
	}

}
