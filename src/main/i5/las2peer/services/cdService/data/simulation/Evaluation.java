package i5.las2peer.services.cdService.data.simulation;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
public class Evaluation implements Serializable {

	private static final long serialVersionUID = 1L;

	///////// Entity Fields ///////////

	@Basic
	private double average;

	@Basic
	private double variance;

	@Basic
	private double deviation;

	/////////// Constructor ///////////

	public Evaluation() {

	}

	public Evaluation(double[] values) {

		this.average = calculateAverageValue(values);
		this.variance = calculateVariance(values, average);
		this.deviation = calculateStandartDeviation(variance);
	}

	public Evaluation(List<Double> list) {

		double[] values = new double[list.size()];
		for (int i = 0; i < list.size(); i++)
			values[i] = list.get(i);

		this.average = calculateAverageValue(values);
		this.variance = calculateVariance(values, average);
		this.deviation = calculateStandartDeviation(variance);
	}

	/////////// Calculations ///////////

	private double calculateAverageValue(double[] values) {

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

	@JsonProperty
	public double getAverageValue() {
		return this.average;
	}

	@JsonProperty
	public double getVariance() {
		return this.variance;
	}

	@JsonProperty
	public double getDeviation() {
		return this.deviation;
	}

	/////////// Print ///////////
	
	public String toTableLine() {		

		StringBuilder line = new StringBuilder();		
		line.append(getAverageValue()).append("\t");
		line.append(getVariance()).append("\t");
		line.append(getDeviation()).append("\t");
		
		return line.toString();		
	}	
	
	public String toHeadLine(String prefix, String suffix) {
			
		StringBuilder line = new StringBuilder();		
		line.append(prefix).append("average").append(suffix).append("\t");
		line.append(prefix).append("variance").append(suffix).append("\t");
		line.append(prefix).append("deviation").append(suffix).append("\t");
		
		return line.toString();		
	}
}
