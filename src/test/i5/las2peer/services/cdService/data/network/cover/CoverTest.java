package i5.las2peer.services.cdService.data.network.cover;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class CoverTest {

	@Test
	public void getAlgorithmTypeNull() {
		
		Cover cover = new Cover();
		AlgorithmType result = cover.getAlgorithmType();
		assertEquals(AlgorithmType.UNKNOWN, result);
	}
	
	@Test
	public void communityCount() {
		
		Cover cover = new Cover();
		List<Community> communityList = new ArrayList<>();
		communityList.add(new Community());
		communityList.add(new Community());
		cover.setCommunities(communityList);
		
		int result = cover.communityCount();
		assertEquals(2, result);
		
	}
	
}
