package i5.las2peer.services.cdService.simulation;

import java.util.ArrayList;
import i5.las2peer.services.cdService.data.simulation.DataSet;
import i5.las2peer.services.cdService.data.simulation.Parameters;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;
import i5.las2peer.services.cdService.simulation.dynamic.Dynamic;
import i5.las2peer.services.cdService.simulation.dynamic.DynamicFactory;
import i5.las2peer.services.cdService.simulation.game.Game;
import i5.las2peer.services.cdService.simulation.game.GameFactory;
import sim.field.network.Edge;
import sim.field.network.Network;
import sim.util.Bag;

public class SimulationManager {

	DynamicFactory dynamicFactory;
	GameFactory gameFactory;

	public SimulationManager() {

		this.dynamicFactory = new DynamicFactory();
		this.gameFactory = new GameFactory();
	}

	public SimulationManager(DynamicFactory dynamicFactory, GameFactory gameFactory) {

		this.dynamicFactory = dynamicFactory;
		this.gameFactory = gameFactory;
	}

	public SimulationSeries simulate(Parameters para, Network intNetwork) {

		Network agentNetwork = createAgents(intNetwork);
		Dynamic dynamic = dynamicFactory.build(para.getDynamic(), para.getDynamicValues());
		Game game = gameFactory.build(para.getPayoffValues());

		ArrayList<DataSet> data = new ArrayList<DataSet>(para.getIterations());

		for (int i = 0, end = para.getIterations(); i < end; i++) {

			Simulation simulation = new Simulation(System.currentTimeMillis(), agentNetwork, game, dynamic);
			simulation.start();
			do
				if (!simulation.schedule.step(simulation))
					break;
			while (simulation.schedule.getSteps() < 5000);
			simulation.finish();
			data.add(simulation.getSimulationData());
		}
		
		return (this.build(para, data));

	}

	protected SimulationSeries build(Parameters para, ArrayList<DataSet> data) {

		SimulationSeries series = new SimulationSeries(para, data);
		para.setSeries(series);
		return series;
	}

	protected Network createAgents(Network intNetwork) {

		Network agentNetwork = new Network(false);
		Bag intNodes = intNetwork.getAllNodes();
		Bag agentNodes = new Bag();
		int size = intNodes.size();
		
		// Add Nodes
		for (int i = 0; i < size; i++) {
			Agent agent = new Agent((int) intNodes.get(i));
			agentNodes.add(agent);
			agentNetwork.addNode(agent);
		}

		// Add Edges
		for (int i = 0; i < size; i++) {
			Bag intEdges = intNetwork.getEdgesOut(intNodes.get(i));

			for (int j = 0, jSize = intEdges.size(); j < jSize; j++) {

				agentNetwork.addEdge(agentNodes.get((int) ((Edge) intEdges.get(j)).getFrom()),
						agentNodes.get((int) ((Edge) intEdges.get(j)).getTo()), true);
			}
		}

		return agentNetwork;
	}

}
