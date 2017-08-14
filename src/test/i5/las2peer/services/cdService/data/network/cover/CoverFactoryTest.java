package i5.las2peer.services.cdService.data.network.cover;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import i5.las2peer.services.cdService.data.network.NetworkStructure;

@RunWith(MockitoJUnitRunner.class)
public class CoverFactoryTest {
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Spy CoverFactory factory;
	
	@Mock NetworkStructure structure;
	
	@Test
	public void buildCommunityTest() {
		
		Cover cover = new Cover();
		
		int edgeCount = 5;
		int nodeCount = 4;

		Mockito.doReturn(edgeCount).when(structure).edgeCount();
		Mockito.doReturn(nodeCount).when(structure).nodeCount();
		
		List<Integer> memberList = new ArrayList<>();
		memberList.add(0);
		memberList.add(3);
		
		factory = new CoverFactory();
		Community community = factory.buildCommunity(structure, cover, memberList);
		assertNotNull(community);
		assertNotNull(community.getProperties());
		
		assertNotNull(community.getMembers());
		assertEquals(2, community.getMembers().size());
		assertEquals(0, (int) community.getMembers().get(0));
		assertEquals(3, (int) community.getMembers().get(1));
		
		assertEquals(cover, community.getCover());
		
	}
	
	@Test
	public void buildCommunityInvalidMembers() {
		
		thrown.expect(IllegalArgumentException.class);		

		Mockito.doReturn(4).when(structure).edgeCount();
		Mockito.doReturn(5).when(structure).nodeCount();
		
		Cover cover = new Cover();
		List<Integer> memberList = new ArrayList<>();
		memberList.add(2);
		memberList.add(5);
		
		factory = new CoverFactory();
		factory.buildCommunity(structure, cover, memberList);
	}
	
	@Test
	public void buildCommunityNegativeMembers() {
		
		thrown.expect(IllegalArgumentException.class);		

		Mockito.doReturn(4).when(structure).edgeCount();
		Mockito.doReturn(5).when(structure).nodeCount();
		
		Cover cover = new Cover();
		List<Integer> memberList = new ArrayList<>();
		memberList.add(-3);
		memberList.add(2);
		
		factory = new CoverFactory();
		factory.buildCommunity(structure, cover, memberList);
	}
	
}
