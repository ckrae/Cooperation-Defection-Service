package i5.las2peer.services.cdService.simulation.game;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameTest {
	
	@Test
	public void getPayoffTest() {
		
		Game game;
		double payoff;
		boolean myStrategy;
		boolean otherStrategy;		
		
		double trueTrue = 1.0;
		double trueFalse = 2.0;
		double falseTrue = 3.0;
		double falseFalse = 4.0;
		game = new Game(trueTrue, trueFalse, falseTrue, falseFalse);
		
		myStrategy = true;
		otherStrategy = true;		
		payoff = game.getPayoff(myStrategy, otherStrategy);
		assertEquals(trueTrue, 1.0, payoff);
		
		myStrategy = true;
		otherStrategy = false;		
		payoff = game.getPayoff(myStrategy, otherStrategy);
		assertEquals(trueFalse, 2.0, payoff);
		
		myStrategy = false;
		otherStrategy = true;		
		payoff = game.getPayoff(myStrategy, otherStrategy);
		assertEquals(falseTrue, 3.0, payoff);
		
		myStrategy = false;
		otherStrategy = false;		
		payoff = game.getPayoff(myStrategy, otherStrategy);
		assertEquals(falseFalse, 4.0, payoff);
		
	}
	
	
}
