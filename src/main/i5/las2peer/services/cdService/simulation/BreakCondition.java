package i5.las2peer.services.cdService.simulation;

import java.util.ArrayList;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;

public class BreakCondition implements Steppable {

	private static final long serialVersionUID = 1L;

	private ArrayList<Stoppable> stopper;

	public BreakCondition(ArrayList<Stoppable> stopper) {

		this.stopper = stopper;
	}
	
	public BreakCondition() {

	}

	public boolean isBreakCondition(Simulation simulation) {
		
		int round = simulation.getRound();
		if (round > 5) {

			if (round >= simulation.getMaxIterations()) {
				return true;
			}
			
			DataRecorder recorder = simulation.getDataRecorder();
			if (recorder.getCooperationValue(round-1) == recorder.getCooperationValue(round - 2)
					&& recorder.getCooperationValue(round - 2) == recorder.getCooperationValue(round - 3)
					&& recorder.getPayoffValue(round - 1) == recorder.getPayoffValue(round - 2)) {
				return true;
			}
		}
		return false;
		
	}
	
	@Override
	public void step(SimState state) {
		Simulation simulation = (Simulation) state;
				
		if (this.isBreakCondition(simulation)) {

			for (int i = 0, size = stopper.size(); i < size; i++) {
				stopper.get(i).stop();
			}
		}
	}
	
	public void add(Stoppable stoppable) {
		stopper.add(stoppable);

	}

}
