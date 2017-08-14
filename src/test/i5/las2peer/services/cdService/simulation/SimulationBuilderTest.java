package i5.las2peer.services.cdService.simulation;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import i5.las2peer.services.cdService.data.network.NetworkStructure;
import i5.las2peer.services.cdService.data.network.NetworkStructureBuilder;
import i5.las2peer.services.cdService.data.simulation.Parameters;
import i5.las2peer.services.cdService.simulation.dynamic.Dynamic;
import i5.las2peer.services.cdService.simulation.dynamic.DynamicType;
import i5.las2peer.services.cdService.simulation.game.Game;
import i5.las2peer.services.cdService.simulation.game.GameType;
import sim.field.network.Network;
import sim.util.Bag;

public class SimulationBuilderTest {
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	SimulationBuilder simulationBuilder;
	Parameters parameters;
	
	@Test 
	public void setGameInvalidParameters() {
		
		thrown.expect(IllegalArgumentException.class);		
		
		simulationBuilder = new SimulationBuilder();		
		parameters = new Parameters();
		parameters.setPayoffCC(0);
		parameters.setPayoffCD(0);
		parameters.setPayoffDC(0);
		parameters.setPayoffDD(0);
		
		simulationBuilder.setGameParameters(parameters);		
	}
	
	@Test 
	public void setGameTest() {
		
		Game resultGame;
		double aa = 2;
		double ab = 0;
		double ba = 3;
		double bb = 1;		
		
		simulationBuilder = new SimulationBuilder();		
		parameters = new Parameters();
		parameters.setPayoffCC(aa);
		parameters.setPayoffCD(ab);
		parameters.setPayoffDC(ba);
		parameters.setPayoffDD(bb);
		
		resultGame = simulationBuilder.setGameParameters(parameters);
		assertEquals(GameType.PRISONERS_DILEMMA, resultGame.getGameType());
		assertEquals(aa, resultGame.getPayoffAA(), 0.1);
		assertEquals(ab, resultGame.getPayoffAB(), 0.1);
		assertEquals(ba, resultGame.getPayoffBA(), 0.1);
		assertEquals(bb, resultGame.getPayoffBB(), 0.1);
	}
	
	@Test
	public void setDynamicUnknownDynamic() {
		
		thrown.expect(IllegalArgumentException.class);		
		
		simulationBuilder = new SimulationBuilder();		
		parameters = new Parameters();
		parameters.setDynamic(DynamicType.UNKNOWN);
		
		simulationBuilder.setDynamicParameters(parameters);				
	}
	
	@Test
	public void setDynamicTest() {
		
		Dynamic resultDynamic;		
		
		simulationBuilder = new SimulationBuilder();		
		parameters = new Parameters();
		parameters.setDynamic(DynamicType.REPLICATOR);
		parameters.setDynamicValue(1.2);
		
		resultDynamic = simulationBuilder.setDynamicParameters(parameters);		
		assertEquals(DynamicType.REPLICATOR, resultDynamic.getDynamicType());		
	}
	
		
	
	@Test
	public void buildNetworkTest() {

		SimulationBuilder simulationBuilder = new SimulationBuilder();
		NetworkStructureBuilder networkBuilder = new NetworkStructureBuilder();
		networkBuilder.addEdge(0, 1);
		networkBuilder.addEdge(1, 2);
		networkBuilder.addEdge(3, 2);
		networkBuilder.addEdge(4, 1);
		
		NetworkStructure structure = networkBuilder.build();
		Network network = simulationBuilder.buildNetwork(structure);		
		assertNotNull(network);		
		Bag agents = network.getAllNodes();			
		assertEquals(5, agents.size());
		assertEquals(Agent.class, agents.get(0).getClass());
		assertEquals(Agent.class, agents.get(1).getClass());
		assertEquals(Agent.class, agents.get(2).getClass());
		assertEquals(Agent.class, agents.get(3).getClass());
		assertEquals(Agent.class, agents.get(4).getClass());		
		assertEquals(0, ((Agent) agents.get(0)).getNodeId());
		assertEquals(1, ((Agent) agents.get(1)).getNodeId());
		assertEquals(2, ((Agent) agents.get(2)).getNodeId());
		assertEquals(3, ((Agent) agents.get(3)).getNodeId());	
		assertEquals(4, ((Agent) agents.get(4)).getNodeId());
		assertEquals(1, network.getEdgesIn(agents.get(0)).size());
		assertEquals(3, network.getEdgesIn(agents.get(1)).size());
		assertEquals(2, network.getEdgesIn(agents.get(2)).size());
		assertEquals(1, network.getEdgesIn(agents.get(3)).size());
		assertEquals(1, network.getEdgesIn(agents.get(4)).size());
		
	}
	
	
}
