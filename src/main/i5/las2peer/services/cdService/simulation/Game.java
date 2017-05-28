package i5.las2peer.services.cdService.simulation;



import java.util.List;

import sim.engine.*;
import sim.field.network.Network;
import sim.util.Bag;

/**
 * The Game
 */

public class Game implements Steppable {

/////////////// Attributes ///////////////		

	/** Payoff Matrix Values
	 * ( cc, cd )	( R , S )
	 * ( dc, dd )	( T , P )	
	 */
	private final double cc;
	private final double cd;
	private final double dd;
	private final double dc;
	
	private final boolean StrategyA = true; // cooperate
	private final boolean StrategyB = false; // defect
	
	public int stepped = 0;	
	
/////////////// Constructor  ///////////////	
		
	private Game (double a, double b, double c, double d) {
			
			this.cc = a;
			this.dc = b;
			this.cd = c;
			this.dd = d;
	}	

/////////////// Fabric Methods  ///////////////////		
		
	public static Game build(double a, double b, double c, double d) {
		
		return(new Game(a, b, c, d));
	}
	
	public static Game build(double cost, double benefit) {
		
		cost = Math.abs(cost);			
		double a = benefit-cost;
		double b = benefit;
		double c = -cost;
		double d = 0.0;
		return (Game.build(a, b, c, d));
	}	
	
	public static Game build(double[] payoffList) {
		
		if(payoffList.length == 2) {
			return(Game.build((payoffList[0]), payoffList[1]));
		} 
		if(payoffList.length == 4) {
			return(Game.build((payoffList[0]), payoffList[1], payoffList[2], payoffList[3]));
		}
		return null;
	}
	
	
	public static Game build(List<String> payoffList) {
		
		if(payoffList.size() == 2) {
			return(Game.build(Double.parseDouble(payoffList.get(0)), Double.parseDouble(payoffList.get(1))));
		} 
		if(payoffList.size() == 4) {
			return(Game.build(Double.parseDouble(payoffList.get(0)), Double.parseDouble(payoffList.get(1)),  Double.parseDouble(payoffList.get(2)),  Double.parseDouble(payoffList.get(3))));
		}
		return null;
	}

	
/////////////// Methods  ///////////////////	
	
	/**
	 * Determine the payoff values of every node
	 */
	@Override
	public void step(SimState state) {
		
	Simulation simulation = (Simulation) state;
	updatePayoff(simulation);
	stepped++;	

    
    }
	
	
	/**
	 * Updates the payoff Values for all nodes of a given network
	 * @param agent
	 * @param neighbours
	 * @return payoff
	 */
	public void updatePayoff(Simulation simulation) {
		
		Bag agents = new Bag(simulation.getNetwork().getAllNodes());
		double[] newValues = new double[agents.size()]; 
	   
		// determine new values 
		for(int i = 0; i < agents.size(); i++)
	    {
	    	Agent agent = (Agent) agents.get(i);
	    	newValues[i] = getPayoff(agent, simulation.getNeighbourhood(agent));
	    }
	    
		// adopt new values
		for(int i = 0; i < agents.size(); i++)
	    {
			Agent agent = (Agent) agents.get(i);
			agent.setPayoff(newValues[i]);
	    }
		
	}
	
	
	

	/**
	 * Determine the total Payoff for a agent of all neighbour games
	 * @param agent
	 * @param neighbours
	 * @return payoff
	 */
	public double getPayoff(Agent agent, Bag neighbours) {		
		
		double payoff = 0.0;
		for(int j = 0; j < neighbours.size(); j++) {
    		Agent neighbour = (Agent) neighbours.get(j);
    		payoff += getPayoff(agent.getStrategy(), neighbour.getStrategy());    		
    	}
		return payoff;
		
	}	
	
	
	/**
	 * Determine the Payoff between two Strategies
	 * @param myStrategy
	 * @param otherStrategy
	 * @return payoff
	 */
	public double getPayoff(boolean myStrategy, boolean otherStrategy) {		
		
		double payoff = 0.0;
		if (myStrategy == StrategyA) {
			if(otherStrategy == StrategyA) {
				//cooperate cooperate - reward
				payoff += cc;
				
			} else if (otherStrategy == StrategyB) {
				//cooperate defect - sucker 
				payoff += cd;
			}				
		} else if(myStrategy == StrategyB) {
			if(otherStrategy == StrategyA) {
				//defect cooperate - temptation
				payoff += dc;
				
			} else if (otherStrategy == StrategyB) {
				//defect defect - punishment
				payoff += dd;
			}	
		}				
		return payoff;	
		
	}
	
	
	/**
	 * Get the Payoff Scheme as Double Array {cc, cd, dc, dd}
	 * 
	 * @return double array
	 */
	public double[] toDoubleArray () {
		
		double[] result = {cc, cd, dc, dd};
		return result;
	}

	

	
	
	
}
