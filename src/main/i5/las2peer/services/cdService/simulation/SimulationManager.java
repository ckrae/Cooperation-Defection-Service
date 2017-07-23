package i5.las2peer.services.cdService.simulation;

import java.util.ArrayList;
import java.util.List;
import i5.las2peer.services.cdService.data.simulation.SimulationDataset;
import i5.las2peer.services.cdService.data.simulation.Parameters;
import i5.las2peer.services.cdService.data.network.NetworkStructure;
import i5.las2peer.services.cdService.data.network.NetworkStructureEdge;
import i5.las2peer.services.cdService.data.network.NetworkStructureNode;
import i5.las2peer.services.cdService.data.simulation.GroupParameters;
import i5.las2peer.services.cdService.data.simulation.GroupType;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;
import i5.las2peer.services.cdService.data.simulation.SimulationSeriesGroup;
import i5.las2peer.services.cdService.simulation.dynamic.Dynamic;
import i5.las2peer.services.cdService.simulation.dynamic.DynamicFactory;
import i5.las2peer.services.cdService.simulation.dynamic.DynamicType;
import i5.las2peer.services.cdService.simulation.game.Game;
import i5.las2peer.services.cdService.simulation.game.GameFactory;
import i5.las2peer.services.cdService.simulation.game.GameType;
import sim.field.network.Edge;
import sim.field.network.Network;
import sim.util.Bag;

public class SimulationManager {

	DynamicFactory dynamicFactory;
	GameFactory gameFactory;

	/////// Constructor ////////

	public SimulationManager() {

		this.dynamicFactory = new DynamicFactory();
		this.gameFactory = new GameFactory();
	}

	public SimulationManager(DynamicFactory dynamicFactory, GameFactory gameFactory) {

		this.dynamicFactory = dynamicFactory;
		this.gameFactory = gameFactory;
	}

	/////// Simulation Series ////////

	public SimulationSeries simulate(Parameters parameters, NetworkStructure networkStructure) {

		Network network = buildNetwork(networkStructure);		
		return (simulate(parameters, network));
	}
	
	public SimulationSeries simulate(Parameters parameters, Network network) {

		Dynamic dynamic = dynamicFactory.build(parameters.getDynamic(), parameters.getDynamicValues());
		Game game = gameFactory.build(parameters.getPayoffValues());

		List<SimulationDataset> datasets = new ArrayList<>(parameters.getIterations());
		Simulation simulation = new Simulation(System.currentTimeMillis(), network, game, dynamic);

		for (int i = 0, end = parameters.getIterations(); i < end; i++) {

			simulation.start();
			do
				if (!simulation.schedule.step(simulation))
					break;
			while (simulation.schedule.getSteps() < 5000);
			datasets.add(simulation.getSimulationData());
			simulation.finish();
		}

		return (this.build(parameters, datasets));
	}

	protected SimulationSeries build(Parameters para, List<SimulationDataset> data) {

		SimulationSeries series = new SimulationSeries(para, data);
		para.setSeries(series);
		return series;
	}

	/////// Simulation Series Group ////////

	public SimulationSeriesGroup simulate(GroupParameters parameters, NetworkStructure networkStructure) {

		if (!parameters.validate())
			throw new IllegalArgumentException();

		SimulationSeriesGroup group = new SimulationSeriesGroup();
		Network network = buildNetwork(networkStructure);

		int scaling = parameters.getScaling();
		for (int i = 0; i < scaling; i++) {
			double scale = i / scaling;
			Parameters simulationParameters = new Parameters();
			setGameParameters(simulationParameters, parameters.getGame(), scale);
			setDynamicParameters(simulationParameters, parameters.getDynamic());

			SimulationSeries series = simulate(simulationParameters, network);
			group.add(series);
		}
		return (group);
	}	
	
	public void setGameParameters(Parameters parameters, GroupType game, double scale) {

		switch (game) {
		case Rescaled_PD:
		default:
			parameters.setGame(GameType.PRISONERS_DILEMMA);
			parameters.setPayoffCC(scale);
			parameters.setPayoffCD(1);
			parameters.setPayoffDC(0);
			parameters.setPayoffDD(0);
		}
	}

	public void setDynamicParameters(Parameters parameters, DynamicType dynamic) {

		switch (dynamic) {
		case REPLICATOR:
		default:
			parameters.setDynamic(DynamicType.REPLICATOR);
			parameters.setDynamicValue(1.5);
		}
	}

	/////// Initialize Network ////////

	protected Network buildNetwork(NetworkStructure structure) {

		Network network = new Network(false);
		List<Agent> agents = new ArrayList<>();

		for (NetworkStructureNode node : structure.getNodes()) {
			Agent agent = new Agent(node.getId());
			agents.add(node.getId(), agent);
			network.addNode(agent);
		}

		for (NetworkStructureEdge edge : structure.getEdges()) {
			int from = edge.getFrom().getId();
			int to = edge.getTo().getId();
			network.addEdge(agents.get(from), agents.get(to), true);
		}
		return network;
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
