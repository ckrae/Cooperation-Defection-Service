package i5.las2peer.services.cdService.simulation.dynamics;

import i5.las2peer.services.cdService.simulation.Agent;
import i5.las2peer.services.cdService.simulation.Simulation;

/**
*
* Replicator
* 
*/
public class Replicator extends Dynamic  {
	
/////////////// Attributes ////////////

private static final long serialVersionUID = 1L;
	
final static DynamicType TYPE = DynamicType.REPLICATOR;	

/////////////// Constructor ////////////

public Replicator(double value) {
	
	super(value);	
}
	
/////////////// Methods /////////////////
	
	@Override
	public boolean getNewStrategy(Agent agent, Simulation simulation) {
		
		
		Agent neighbour = simulation.getRandomNeighbour(agent);
		Agent betterAgent = agent.comparePayoff(neighbour);
		
		if(!betterAgent.equals(agent)) {
			
		double probability = (neighbour.getPayoff() - agent.getPayoff()) / (getValue() * Math.max(simulation.getNeighbourhood(neighbour).size(), simulation.getNeighbourhood(agent).size()));  
		if (simulation.random.nextDouble(true, true) < probability); 	
			return neighbour.getStrategy();
		
    	}		
		return agent.getStrategy();
		
	}	
			
		
	
}
	
	

