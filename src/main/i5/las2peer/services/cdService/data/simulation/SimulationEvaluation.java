package i5.las2peer.services.cdService.data.simulation;

import java.io.Serializable;

public class SimulationEvaluation implements Serializable {

	private static final long serialVersionUID = 1L;

	private double averageCooperationValue;
	private double variance;
	private double standartDeviation;

	public SimulationEvaluation(SimulationSeries series) {


		double[] values = series.getLastCooperationValues();

		this.averageCooperationValue = calculateAverageCooperationValue(values);
		this.variance = calculateVariance(values, averageCooperationValue);
		this.standartDeviation = calculateStandartDeviation(variance);
	}
	
	//// Calculations ////
	
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
	
	//// Getter ////
	
	public double getAverageCooperationValue() {
		return this.averageCooperationValue;
	}	
	
	public double getVariance() {
		return this.variance;
	}

	public double getStandartDeviation() {
		return this.standartDeviation;
	}

}
