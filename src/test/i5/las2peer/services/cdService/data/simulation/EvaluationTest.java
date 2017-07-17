package i5.las2peer.services.cdService.data.simulation;

import static org.junit.Assert.*;

import org.junit.Test;

public class EvaluationTest {
	
	double[] values;
	double result;
	Evaluation evaluation;
	
	@Test
	public void averageTest() {
		
		values = new double[]{2.4, 4.2, 3.3, 2.1};
		evaluation = new Evaluation(values);		
		result = evaluation.getAverageValue();
		assertEquals(3.0, result, 0.01);
		
		values = new double[]{};
		evaluation = new Evaluation(values);		
		result = evaluation.getAverageValue();
		assertEquals(3.0, result, 0.01);
	}
	
}
