package i5.las2peer.services.cdService.data.mapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import i5.las2peer.services.cdService.data.network.PropertyType;
import i5.las2peer.services.cdService.data.network.cover.Community;
import i5.las2peer.services.cdService.data.network.cover.Cover;
import i5.las2peer.services.cdService.data.simulation.SimulationSeriesGroup;

@RunWith(MockitoJUnitRunner.class)
public class CoverSimulationGroupMappingTest {
	
	@Mock Cover cover;
	@Mock Community community1;
	@Mock Community community2;
	@Mock Community community3;
	@Mock SimulationSeriesGroup simulation;
	
	
	@Test
	public void getPropertyValues() {
		
		CoverSimulationGroupMapping mapping = new CoverSimulationGroupMapping();
		mapping.setCover(cover);
		mapping.setSimulation(simulation);
		
		Mockito.doReturn(3).when(cover).communityCount();
		Mockito.doReturn(community1).when(cover).getCommunity(0);
		Mockito.doReturn(community2).when(cover).getCommunity(1);
		Mockito.doReturn(community3).when(cover).getCommunity(2);
		Mockito.doReturn(0.342).when(community1).getProperty(PropertyType.SIZE);
		Mockito.doReturn(0.112).when(community2).getProperty(PropertyType.SIZE);
		Mockito.doReturn(0.433).when(community3).getProperty(PropertyType.SIZE);
		
		double[] result = mapping.getPropertyValues(PropertyType.SIZE);
		assertNotNull(result);
		assertEquals(3, result.length);
		assertEquals(0.342, result[0], 0.0001);
		assertEquals(0.112, result[1], 0.0001);
		assertEquals(0.433, result[2], 0.0001);

	}

	@Test
	public void getCooperationValues() {
		
		CoverSimulationGroupMapping mapping = new CoverSimulationGroupMapping();
		mapping.setCover(cover);
		mapping.setSimulation(simulation);
		
		List<Community> communityList = new ArrayList<>();
		communityList.add(community1);
		Mockito.doReturn(communityList).when(cover).getCommunities();
		Mockito.doReturn(new double[]{0.2,0.3}).when(simulation).getAverageCommunityCooperationValues(communityList);
		double[] result = mapping.getCooperationValues();
		assertNotNull(result);
		assertEquals(2, result.length);
		assertEquals(0.2, result[0], 0.0001);
		assertEquals(0.3, result[1], 0.0001);

		
	}
	
}
