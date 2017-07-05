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
		
		nodes = 5;
		edges = 10;
		properties.setNodes(nodes);
		properties.setEdges(edges);
		density = properties.getDensity();
		assertEquals(0.5, density, 0.01);
		
	}
	
}
