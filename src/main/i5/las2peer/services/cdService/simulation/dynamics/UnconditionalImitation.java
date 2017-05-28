package i5.las2peer.services.cdService.simulation.dynamics;

import i5.las2peer.services.cdService.simulation.Agent;
import i5.las2peer.services.cdService.simulation.Simulation;
import sim.util.Bag;

/**
*
* Unconditional Imitation
* 
* Search Neighbor with best Payoff and adopt his strategy
* 
*/
public class UnconditionalImitation extends Dynamic  {
	
/////////////// Attributes ////////////

private static final long serialVersionUID = 1L;
	
final static DynamicType TYPE = DynamicType.UNCONDITIONAL_IMITATION; 	

/////////////// Constructor ////////////

public UnconditionalImitation() {
	
	super();	
}
	
/////////////// Methods ///////////////	
	
	@Override
	public boolean getNewStrategy(Agent agent, Simulation simulation) {
		
		Bag neighbours = new Bag(simulation.getNetwork().getAllNodes());
		double bestPayoff = agent.getPayoff();
		boolean bestStrategy = agent.getStrategy();
    	for(int i = 0; i < neighbours.size(); i++) {
    		Agent nei = (Agent) neighbours.get(i);
    		if(nei.getPayoff() > bestPayoff) {
    			bestStrategy = nei.getStrategy();
    		}
    	}		
		return bestStrategy;
		
	}	
			
		
	
}

