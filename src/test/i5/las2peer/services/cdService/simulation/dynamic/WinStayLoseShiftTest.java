package i5.las2peer.services.cdService.simulation.dynamic;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

public class WinStayLoseShiftTest {

	@Test
	public void getNewStrategyTest() {
		
		WinStayLoseShift dynamic = new WinStayLoseShift();	
		boolean strategy;
		boolean myStrategy;
		double myPayoff;
		double myLastPayoff;		
		
		myStrategy = true;	
		
		myPayoff = 2.0;
		myLastPayoff = 1.0;
		strategy = dynamic.getNewStrategy(myStrategy, myPayoff, myLastPayoff);
		assertEquals(true, strategy);
		
		myPayoff = 1.0;
		myLastPayoff = 3.0;
		strategy = dynamic.getNewStrategy(myStrategy, myPayoff, myLastPayoff);
		assertEquals(false, strategy);
		
		myPayoff = 2.2;
		myLastPayoff = 2.2;
		strategy = dynamic.getNewStrategy(myStrategy, myPayoff, myLastPayoff);
		assertEquals(true, strategy);
		
		myStrategy = false;	
		
		myPayoff = 2.0;
		myLastPayoff = 1.0;
		strategy = dynamic.getNewStrategy(myStrategy, myPayoff, myLastPayoff);
		assertEquals(false, strategy);
		
		myPayoff = 1.0;
		myLastPayoff = 3.0;
		strategy = dynamic.getNewStrategy(myStrategy, myPayoff, myLastPayoff);
		assertEquals(true, strategy);
		
	}
	
}
