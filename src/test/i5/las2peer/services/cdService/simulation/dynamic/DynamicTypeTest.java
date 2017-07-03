package i5.las2peer.services.cdService.simulation.dynamic;

import static org.junit.Assert.*;

import org.junit.Test;

public class DynamicTypeTest {
	
	@Test
	public void typeExistsTest() {
		
		boolean result;
		
		result = DynamicType.TypeExists("rePliCator");
		assertEquals(true, result);
		
		result = DynamicType.TypeExists("imitation");
		assertEquals(true, result);
		
		result = DynamicType.TypeExists("was35hkld");
		assertEquals(false, result);
		
	}
	
	@Test
	public void fromStringTest() {
		
		DynamicType result;
		
		result = DynamicType.fromString("RePlicaTor");
		assertEquals(DynamicType.REPLICATOR, result);
		
		result = DynamicType.fromString("Ws_lS");
		assertEquals(DynamicType.WS_LS, result);
		
		result = DynamicType.fromString("sdk239wlk");
		assertEquals(DynamicType.UNKNOWN, result);
		
		
	}
	
}
