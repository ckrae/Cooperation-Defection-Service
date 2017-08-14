package i5.las2peer.services.cdService.data.network;

import static org.junit.Assert.*;

import org.junit.Test;

public class PropertiesTest {

	@Test
	public void calculateDensity() {
		
		NetworkProperties networkProperties = new NetworkProperties();
		double density;
		
		density = networkProperties.calculateDensity(5, 4);
		assertEquals(0.4, density, 0.01);		
		
		density = networkProperties.calculateDensity(1, 4);
		assertEquals(0.0, density, 0.01);
		
		density = networkProperties.calculateDensity(6, 10);
		assertEquals(0.66, density, 0.01);	
	}
	
	@Test
	public void getDensity() {
		
		NetworkProperties networkProperties = new NetworkProperties();
		double density;
		
		networkProperties.setNodes(5);
		networkProperties.setEdges(4);
		assertEquals(5, networkProperties.getNodes());
		assertEquals(4, networkProperties.getEdges());
		density = networkProperties.getDensity();
		assertEquals(0.4, density, 0.01);
	}

	
	@Test
	public void AverageDegreeTest() {
		
		NetworkProperties networkProperties = new NetworkProperties();
		double degree;		
		
		degree = networkProperties.calculateAverageDegree(5, 4);
		assertEquals(1.6, degree, 0.01);		
		
		degree = networkProperties.calculateAverageDegree(1, 4);
		assertEquals(8.0, degree, 0.01);
		
		degree = networkProperties.calculateAverageDegree(6, 10);
		assertEquals(3.33, degree, 0.01);	
		
	}
	
	@Test
	public void getAverageDegree() {
		
		NetworkProperties networkProperties = new NetworkProperties();
		double degree;		
		
		networkProperties.setNodes(5);
		networkProperties.setEdges(4);
		assertEquals(5, networkProperties.getNodes());
		assertEquals(4, networkProperties.getEdges());
		degree = networkProperties.getAverageDegree();
		assertEquals(1.6, degree, 0.01);		
	}
	
	@Test
	public void DegreeDeviationTest() {
		
		NetworkProperties networkProperties = new NetworkProperties();
		double deviation;
		
		deviation = networkProperties.calculateDegreeDeviation(new double[]{1.0, 4.0, 3.0, 3.0});
		assertEquals(1.2583, deviation, 0.00001);
		
		deviation = networkProperties.calculateDegreeDeviation(new double[]{2.0, 2.0, 2.0, 2.0});
		assertEquals(0.0, deviation, 0.00001);	

		
	}
	
}
