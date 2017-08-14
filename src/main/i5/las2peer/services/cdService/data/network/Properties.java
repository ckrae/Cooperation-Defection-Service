package i5.las2peer.services.cdService.data.network;

import org.apache.commons.math3.stat.StatUtils;


public abstract class Properties {
	
	abstract public double getProperty(PropertyType propertyType);
	
	public double calculateAverage(double[] values) {

		if (values == null)
			return 0.0;
		
		double average = StatUtils.mean(values);
		return average;
		
	}

	public double calculateDeviation(double[] values) {

		if (values == null)
			return 0.0;

		double variance = StatUtils.variance(values);
		return Math.sqrt(variance);
	}
	
}
