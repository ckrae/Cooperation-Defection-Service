package i5.las2peer.services.cdService.simulation;

import java.util.ArrayList;
import i5.las2peer.services.cdService.data.simulation.SimulationData;
import i5.las2peer.services.cdService.data.simulation.SimulationParameters;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;
import i5.las2peer.services.cdService.simulation.dynamic.Dynamic;
import i5.las2peer.services.cdService.simulation.dynamic.DynamicFactory;
import i5.las2peer.services.cdService.simulation.game.Game;
import i5.las2peer.services.cdService.simulation.game.GameFactory;
import sim.field.network.Network;

public class SimulationManager {


	public SimulationManager() {

	}

	public static SimulationSeries simulate(SimulationParameters para, Network network) {

		Dynamic dynamic = DynamicFactory.build(para.getDynamic(), para.getDynamicValues());
		Game game = GameFactory.build(para.getPayoffValues());

		ArrayList<SimulationData> data = new ArrayList<SimulationData>(para.getIterations());

		for (int i = 0, end = para.getIterations(); i < end; i++) {

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

		SimulationSeries series = new SimulationSeries(para, data);
		return series;
	}

}
