

package i5.las2peer.services.cdService.simulation.dynamics;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import i5.las2peer.services.cdService.simulation.Agent;
import i5.las2peer.services.cdService.simulation.Game;
import i5.las2peer.services.cdService.simulation.Simulation;
import sim.field.network.Edge;
import sim.field.network.Network;
import sim.util.Bag;

public class DynamicTest 
{
	   

	public Network getTestNetwork() {
		
		Network network = new Network(false);
		ArrayList<Agent> agents = new ArrayList<Agent>(10);
        for(int i = 0; i < 10; i++)
        {
            Agent agent = new Agent();
            agent.setStrategy(false);
            agent.setPayoff(1.0);
            network.addNode(agent);   
            agents.add(agent);
        }    
        
        network.addEdge(agents.get(3), agents.get(5), 1);
        network.addEdge(agents.get(3), agents.get(6), 1);
        network.addEdge(agents.get(3), agents.get(7), 1);
        

		
        
        
        
		return network;
	}

	
	
	//@Test
	public void ImitationTest() {
		
		Dynamic imitation = DynamicFactory.build(DynamicType.UNCONDITIONAL_IMITATION);
		Simulation simulation = new Simulation(System.currentTimeMillis(), getTestNetwork(), Game.build(2, 1), imitation);
	
		
		
	}


} 
    
