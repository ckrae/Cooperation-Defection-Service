package i5.las2peer.services.cdService.simulation.game;

import static org.junit.Assert.*;

import org.junit.Test;

public class GameFactoryTest {
	
	@Test
	public void testBuild() {
		
		GameFactory factory = new GameFactory();
		Game game;
		double AA = 0.0;
		double AB = 1.0;
		double BA = 2.0;
		double BB = 3.0;
		game = factory.build(AA, AB, BA, BB);
		
		assertEquals(Game.class, game.getClass());
		
	}
	
}
