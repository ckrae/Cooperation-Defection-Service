package i5.las2peer.services.cdService.simulation.dynamic;

import static org.junit.Assert.*;

import org.junit.Test;

public class DynamicFactoryTest {

	@Test
	public void buildTest() {
		
		Dynamic dynamic;
		DynamicType type;
		DynamicFactory factory = new DynamicFactory();
		
		type = DynamicType.REPLICATOR;
		dynamic = factory.build(type, 1.5);
		assertEquals(Replicator.class, dynamic.getClass());
		
		type = DynamicType.MORAN;
		dynamic = factory.build(type);
		assertEquals(Moran.class, dynamic.getClass());
		
		type = DynamicType.UNCONDITIONAL_IMITATION;
		dynamic = factory.build(type);
		assertEquals(UnconditionalImitation.class, dynamic.getClass());
		
	}
	
}
