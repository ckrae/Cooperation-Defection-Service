package i5.las2peer.services.cdService.data.simulation;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

import org.apache.commons.math3.stat.StatUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import i5.las2peer.services.cdService.data.util.table.TableRow;

/**
 * 
 *
 */
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
		
		evaluate(values);
	}

	public Evaluation(List<Double> list) {

		double[] values = new double[list.size()];
		for (int i = 0; i < list.size(); i++)
			values[i] = list.get(i);
		evaluate(values);
	}

	/////////// Calculations ///////////
		
	public void evaluate(double[] values) {
		
		this.average = calculateAverageValue(values);
		this.variance = calculateVariance(values, average);
		this.deviation = calculateStandartDeviation(variance);		
	}

	private double calculateAverageValue(double[] values) {

		if (values == null || values.length == 0)
			return 0.0;

		double average = StatUtils.mean(values);
		return average;

	}

	private double calculateVariance(double[] values, double average) {

		if (values == null || values.length == 0)
			return 0.0;

		double variance = StatUtils.variance(values, average);
		return variance;
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
	
	////// Setter //////
	
	@JsonSetter
	public void setAverageValue(double value) {
		this.average = value;
	}

	@JsonSetter
	public void setVariance(double variance) {
		this.variance = variance;
	}

	@JsonSetter
	public void setDeviation(double deviation) {
		this.deviation = deviation;
	}

	/////////// Table ///////////
	
	public TableRow toTableLine() {
		
		TableRow line = new TableRow();		
		line.add(getAverageValue()).add(getVariance()).add(getDeviation());		
		return line;
	}

	public TableRow toHeadLine() {

		TableRow line = new TableRow();
		line.add("average").add("variance").add("deviation");
		return line;
	}

}
