package i5.las2peer.services.cdService.simulation;

import java.util.ArrayList;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;

public class BreakCondition implements Steppable {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<Stoppable> stopper;
	
	public BreakCondition(ArrayList stopper) {
		
		this.stopper = stopper;		
	}	
	
	@Override
	public void step(SimState state) {
		Simulation simulation = (Simulation) state;
		
		if(simulation.isBreakCondition()) {
			
			for(int i=0, size=stopper.size(); i<size; i++) {
				stopper.get(i).stop();				
			}			
		}		
	}

	public void add(Stoppable stoppable) {
		stopper.add(stoppable);
		
	}

}
