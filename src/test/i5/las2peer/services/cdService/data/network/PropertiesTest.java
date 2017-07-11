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
		
		double value = properties.calculateDensity(4, 6);
		assertEquals(0.5, value, 0.01);
		
		
		
		nodes = 5;
		edges = 10;
		properties.setNodes(nodes);
		properties.setEdges(edges);
		assertEquals(5, properties.getNodes());
		density = properties.getDensity();
		assertEquals(1.0, density, 0.01);
		
		nodes = 4;
		edges = 2;
		properties.setNodes(nodes);
		assertEquals(4, properties.getNodes());		
		properties.setEdges(edges);
		assertEquals(2, properties.getEdges());
		density = properties.getDensity();
		assertEquals(0.5, density, 0.01);
		
	}
	
}
