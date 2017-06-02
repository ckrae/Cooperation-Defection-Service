package i5.las2peer.services.cdService.simulation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import i5.las2peer.services.cdService.data.SimulationData;
import i5.las2peer.services.cdService.data.SimulationParameters;
import i5.las2peer.services.cdService.data.SimulationSeries;
import i5.las2peer.services.cdService.data.manager.DataManager;
import i5.las2peer.services.cdService.simulation.dynamics.Dynamic;
import i5.las2peer.services.cdService.simulation.dynamics.DynamicFactory;
import sim.field.network.Network;

public class SimulationManager {

	private static Queue<SimulationSeries> SimulationQueue = new LinkedList<SimulationSeries>();

	public SimulationManager() {

	}

	public static SimulationSeries simulate(SimulationParameters para, Network network) {

		Dynamic dynamic = DynamicFactory.build(para.getDynamic(), para.getDynamicValue());
		Game game = Game.build(para.getPayoffValues());

		ArrayList<SimulationData> data = new ArrayList<SimulationData>(para.getIterations());

		for (int i = 0, end = para.getIterations(); i <= end; i++) {

			Simulation simulation = new Simulation(System.currentTimeMillis(), network, game, dynamic);
			simulation.start();
			do
				if (!simulation.schedule.step(simulation))
					break;
			while (simulation.schedule.getSteps() < 5000);
			simulation.finish();
			data.add(simulation.getSimulationData());
		}

		return (SimulationManager.build(para, data));

	}

	private static SimulationSeries build(SimulationParameters para, ArrayList<SimulationData> data) {

		long seriesId = DataManager.getNextSeriesId();
		long userId = DataManager.getUserId();
		SimulationSeries series = new SimulationSeries(seriesId, userId, para, data);
		return series;
	}

}
