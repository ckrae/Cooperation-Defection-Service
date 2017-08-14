package i5.las2peer.services.cdService.data.simulation;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParametersTest {

	@Test
	public void normalize() {
		
		Parameters parameters = new Parameters();
		parameters.setPayoffCC(2.0);
		parameters.setPayoffCD(4.0);
		parameters.setPayoffDC(1.0);
		parameters.setPayoffDD(3.0);
		
		parameters.normalize();
		
		double payoffCC = parameters.getPayoffCC();
		double payoffCD = parameters.getPayoffCD();
		double payoffDC = parameters.getPayoffDC();
		double payoffDD = parameters.getPayoffDD();
		
		assertEquals(0.2, payoffCC, 0.01);
		assertEquals(0.4, payoffCD, 0.01);
		assertEquals(0.1, payoffDC, 0.01);
		assertEquals(0.3, payoffDD, 0.01);
		
	}
	
}
