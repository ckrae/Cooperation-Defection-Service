

package i5.las2peer.services.cdService.simulation;



import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import i5.las2peer.services.cdService.data.SimulationData;
import sim.engine.*;
import sim.field.network.Network;
import sim.util.Bag;


public class DataRecorder implements Steppable
    {
	    private static final long serialVersionUID = 1;	    
	    
	    private double[] cooperationValues;
	    private double[] payoffValues;
	    private boolean[] networkCooperation;
	    private double[] networkPayoff;
	    
	    public DataRecorder(int maxIterations) {	    	
	    	
	    	this.cooperationValues = new double[maxIterations+1];
	    	this.payoffValues = new double[maxIterations+1];
	    }
	    
	   
/////////////////// Steps ///////////////////////////
	  
	    @Override       
	    public void step(SimState state)
	    {
	        Simulation simulation = (Simulation) state;
	        cooperationValues[simulation.getRound()] = simulation.getCooperationValue();
	        payoffValues[simulation.getRound()] = simulation.getTotalPayoff();	
	    }	     
	    
	    public SimulationData createData (Simulation simulation) {
	    	
	        Network network = simulation.getNetwork();
	        Bag agents = new Bag(network.getAllNodes());
	        int size = agents.size();
	        networkCooperation = new boolean[size];
	        for(int i = 0; i<size; i++) {	        	
	        	Agent agent = (Agent) agents.get(i);
	        	networkCooperation[i] = agent.getStrategy();
	        	networkPayoff[i] = agent.getPayoff();
	        }
	        
	        SimulationData simulationData = new SimulationData(cooperationValues, networkCooperation, networkPayoff);	        
			
	        printToFile(simulationData);
	        return simulationData;	        
	    }
	

	    public void printToFile(SimulationData data)  {
	    	
	    	String fileName = "test.txt";
	    	String coopFileName = "coop.txt";
	    	File dir = new File ("cds");
	    	File file = new File (dir, fileName);
	    	File coopFile = new File (dir, coopFileName);
	    	FileWriter writer = null;	    	
			try {
				writer = new FileWriter(file);	
			
				for(int i=0, size=networkCooperation.length; i<size; i++) {
					
					writer.write(i+"#"+networkCooperation[i]+"#"+networkPayoff[i]+"\n");
				}
			

			writer.flush();
			writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				writer = new FileWriter(coopFile);	
			
				for(int i=0, size=cooperationValues.length; i<size; i++) {
					
					writer.write(i+"#"+cooperationValues[i]+"\n");
				}
			

			writer.flush();
			writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	    }
	    


    } 
    
