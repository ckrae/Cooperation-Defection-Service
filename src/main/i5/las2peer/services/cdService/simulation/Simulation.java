
package i5.las2peer.services.cdService.simulation;

import java.util.ArrayList;

import i5.las2peer.services.cdService.data.SimulationData;
import i5.las2peer.services.cdService.simulation.dynamics.Dynamic;
import i5.las2peer.services.cdService.simulation.dynamics.DynamicFactory;
import sim.engine.*;
import sim.util.*;
import sim.field.network.*;


public class Simulation extends SimState
    {
    private static final long serialVersionUID = 1;

    //// Simulations Settings
    
    private final static int MAX_ITERATIONS = 1000;
    /**
     * the network, a agent on every node.
     */
    private final Network network;
    /**
     * the game, determines the payoff updating
     */
    private final Game game; 
    /**
     * the dynamic, determines the strategy updating
     */
    private final Dynamic dynamic;    
    
    //// Recorded Data    
    private DataRecorder recorder; 
    

    //// Constructor
    public Simulation(long seed) {
    	super(seed);
    	
        this.network = new Network(false);        
        this.game = Game.build(2, 4);
        this.dynamic = DynamicFactory.build("Replicator", 1.5);        
        this.recorder = new DataRecorder(MAX_ITERATIONS);
    }
    
    
    public Simulation(long seed, Network network, Game game, Dynamic dynamic)
    {
        super(seed);
        
        this.network = network;        
        this.game = game;
        this.dynamic = dynamic;
        
        this.recorder = new DataRecorder(MAX_ITERATIONS);
        
    }         
    
    
    public static void main(String[] args)
    {
    doLoop(Simulation.class, args);
    System.exit(0);
    }    
    
    
    
    /**
     * prepare for a new simulation run
     */
    public void start() {
    	
        super.start();     
        
        // set random strategy to every agent
        Bag agents = new Bag(network.getAllNodes());
        for(int i = 0, size = agents.size(); i < size; i++) {
        	
            Agent agent = (Agent) agents.get(i);            
            agent.setStrategy(random.nextBoolean());
        }
        
        //event schedule
        ArrayList<Stoppable> stopper = new ArrayList<Stoppable>(4);        
        stopper.add(schedule.scheduleRepeating(game, 1, 1));
        stopper.add(schedule.scheduleRepeating(dynamic, 2, 1));        
        stopper.add(schedule.scheduleRepeating(recorder, 3, 1));
        BreakCondition breakCondition = new BreakCondition(stopper);
        breakCondition.add(schedule.scheduleRepeating(breakCondition, 4, 1));
     }
    
    
    public void finish() {    	
    	
    	super.finish();
    	SimulationData data = recorder.createData(this);
    	
    	
    	
    }
   
    
    public boolean isBreakCondition() {    	

    	if(this.getRound() >= MAX_ITERATIONS) {    		
    		return true;
    	}
    	if(false) {
    		return true;
    	}    	
    	return false;
    }
    
    
    
    //////// Network Utility /////////
    
    public Bag getNeighbourhood(Agent agent) {    	
 
    	Bag nodes = new Bag(network.getAllNodes());
    	int index = network.getNodeIndex(this);
    	Agent node = (Agent) nodes.get(index);
    	Bag edges = new Bag(network.getEdges(node, nodes));
    	Bag neighbours = new Bag();
    	for(int i = 0; i < edges.size(); i++)
        {
            Edge edge = (Edge)(edges.get(i));	            
            Agent from = (Agent) edge.getFrom();
            Agent to = (Agent) edge.getTo();
            
            if(from.equals(node) && !to.equals(node)) {
            	neighbours.add(to);
            } else {
            if(to.equals(node) && !from.equals(node))
            	neighbours.add(from);
            }          
        }    	
    	return neighbours;    	
    }
    
    /**
	* Get a random neighbour agent
	* 
	* @return agent 
	*/
    public Agent getRandomNeighbour(Agent agent) {
    	
    	Bag agents = new Bag(getNeighbourhood(agent));
    	final int index = random.nextInt(agents.size());	    
    	
		return (Agent) agents.get(index);	    	
    }
        
    
    
   /////// Simulation Data ////////
    
   /**
   * Number of Cooperators
   * @return total Number of Cooperators
   */         
   public long getCooperationNumber() {
	   
	   long number = 0;
	   Bag agents = network.getAllNodes();
       for(int i = 0; i < agents.size(); i++)
       {
           Agent agent = (Agent) agents.get(i);            
           if(agent.getStrategy()) {
        	   number++;
       		}
       }
	   return number;	   
   }
   
   /**
    * Cooperation Value
	* @return value Cooperator / all agents
	*/         
	   public double getCooperationValue() {		   
		   
		   long cooperators = getCooperationNumber();
		   long totalAgents = network.getAllNodes().size();
		   double value = 0;
		   if(totalAgents > 0) {
			   value = (cooperators / totalAgents);
		   }
		   return value;	   
	   }   
   	
   
   /**
   * The total payoff summed up by all agents
   * @return total payoff number
   */  
	public double getTotalPayoff() {
		   
		double totalPayoff = 0;
		Bag agents = network.getAllNodes();
	    for(int i = 0; i < agents.size(); i++)
	    {
	    	Agent agent = (Agent) agents.get(i);            
	        totalPayoff += agent.getPayoff();
	    }
	    return totalPayoff;		   
	}
	
	
	
	/**
	   * Current Generation 
	   * increases after each Strategy updating
	   * @return generation number
	   */         
	   public long getGenerationCount() {		   
		   
		   return dynamic.generationCount();  
	   }
	  
   
   
   
   public long getGameSteps() {
	   return game.stepped;
   }
   

   
   public int getRound() {
	   return (int) schedule.getTime();
   }
   
    
    ///// Getter
	/**
	 * @return the network
	 */
	public Network getNetwork() {
		return network;
	}

	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}	

	/**
	 * @return the dynamic
	 */
	public Dynamic getDynamic() {
		return dynamic;
	}

	
	public double[] getPayoffScheme() {
		
		return this.getGame().toDoubleArray();
		
	}
	


    }
