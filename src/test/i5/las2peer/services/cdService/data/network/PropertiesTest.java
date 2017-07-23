package i5.las2peer.services.cdService.data.network;

import static org.junit.Assert.*;

import org.junit.Test;

public class PropertiesTest {

	@Test
	public void DensityTest() {
		
		Properties properties = new Properties();
		int nodes;
		int edges;
		double density;		
		
		assertNotNull(properties);
		
		density = properties.calculateDensity(5, 4);
		assertEquals(0.4, density, 0.01);		
		
		density = properties.calculateDensity(1, 4);
		assertEquals(0.0, density, 0.01);
		
		density = properties.calculateDensity(6, 10);
		assertEquals(0.66, density, 0.01);	
		
		nodes = 5;
		edges = 4;
		properties.setNodes(nodes);
		properties.setEdges(edges);
		assertEquals(5, properties.getNodes());
		assertEquals(4, properties.getEdges());
		density = properties.getDensity();
		assertEquals(0.4, density, 0.01);		
	}
	
	@Test
	public void AverageDegreeTest() {
		
		Properties properties = new Properties();
		int nodes;
		int edges;
		double degree;		
		
		assertNotNull(properties);
		
		degree = properties.calculateAverageDegree(5, 4);
		assertEquals(1.6, degree, 0.01);		
		
		degree = properties.calculateAverageDegree(1, 4);
		assertEquals(8.0, degree, 0.01);
		
		degree = properties.calculateAverageDegree(6, 10);
		assertEquals(3.33, degree, 0.01);	
		
		nodes = 5;
		edges = 4;
		properties.setNodes(nodes);
		properties.setEdges(edges);
		assertEquals(5, properties.getNodes());
		assertEquals(4, properties.getEdges());
		degree = properties.getAverageDegree();
		assertEquals(1.6, degree, 0.01);		
	}
	
}
