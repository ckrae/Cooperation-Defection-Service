package i5.las2peer.services.cdService.simulation;

import java.util.ArrayList;
import java.util.List;
import i5.las2peer.services.cdService.data.simulation.SimulationDataset;
import i5.las2peer.services.cdService.data.simulation.Parameters;
import i5.las2peer.api.exceptions.ServiceInvocationException;
import i5.las2peer.services.cdService.data.NetworkDataProvider;
import i5.las2peer.services.cdService.data.network.NetworkMeta;
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
import sim.field.network.Network;


/**
 * Provides the interface to start simulations from the service.
 */
public class SimulationBuilder {

	DynamicFactory dynamicFactory;
	GameFactory gameFactory;

	Game game;
	Dynamic dynamic;
	NetworkMeta network;
	int graphId;

	int iterations;

	/////// Constructor ////////

	public SimulationBuilder() {

		this.dynamicFactory = new DynamicFactory();
		this.gameFactory = new GameFactory();
	}

	public SimulationBuilder(DynamicFactory dynamicFactory, GameFactory gameFactory) {

		this.dynamicFactory = dynamicFactory;
		this.gameFactory = gameFactory;
	}

	/////// Simulation Series ////////

	public void setParameters(Parameters parameters) {

		try {

			setGameParameters(parameters);
			setDynamicParameters(parameters);
			setNetwork(parameters);

			iterations = parameters.getIterations();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	protected Game setGameParameters(Parameters parameters) {

		double payoffAA = parameters.getPayoffCC();
		double payoffAB = parameters.getPayoffCD();
		double payoffBA = parameters.getPayoffDC();
		double payoffBB = parameters.getPayoffDD();

		GameType gameType = GameType.getGameType(payoffAA, payoffAB, payoffBA, payoffBB);
		if (gameType.equals(GameType.INVALID))
			throw new IllegalArgumentException("invalid game type");

		game = gameFactory.build(payoffAA, payoffAB, payoffBA, payoffBB);
		return game;
	}

	protected Dynamic setDynamicParameters(Parameters parameters) {

		DynamicType dynamicType = parameters.getDynamic();
		if (dynamicType == null || dynamicType.equals(DynamicType.UNKNOWN))
			throw new IllegalArgumentException("invalid dynamic type");

		double[] values = parameters.getDynamicValues();
		dynamic = dynamicFactory.build(dynamicType, values);
		return dynamic;
	}

	protected void setNetwork(Parameters parameters) {

		long networkId = parameters.getGraphId();
		NetworkStructure network = null;

		network = NetworkDataProvider.getInstance().getNetwork(networkId).getNetworkStructure();

		if (network == null)
			throw new IllegalArgumentException("network not found");

		this.network = network.getNetworkMeta();
	}
	
	public void setNetwork(NetworkMeta network) {

		if (network == null)
			throw new IllegalArgumentException("no network");

		this.network = network;
	}

	protected void validate() throws IllegalStateException {

		if (this.game == null)
			throw new IllegalStateException("no game defined");

		if (this.dynamic == null)
			throw new IllegalStateException("no dynamic defined");

		if (this.network == null)
			throw new IllegalStateException("no network defined");		
	}

	public SimulationSeries simulate() throws IllegalStateException, ServiceInvocationException {

		try {
			validate();
		} catch (IllegalStateException e) {
			throw e;
		}
		
		Game game = this.game;
		Dynamic dynamic = this.dynamic;
		NetworkMeta networkMeta = this.network;
		Network network = buildNetwork(networkMeta.getNetworkStructure());
		
		int iterations = this.iterations;
		if(iterations < 1)
			iterations = 1;				
		
		List<SimulationDataset> datasets = new ArrayList<>(iterations);
		Simulation simulation = new Simulation(System.currentTimeMillis(), network, game, dynamic);

		for (int i = 0; i < iterations; i++) {

			simulation.start();
			do
				if (!simulation.schedule.step(simulation))
					break;
			while (simulation.schedule.getSteps() < 5000);
			datasets.add(simulation.getSimulationData());
			simulation.finish();
		}
		
		Parameters parameters = new Parameters();
		parameters.setDynamic(dynamic.getDynamicType());
		parameters.setGame(game.getGameType());
		parameters.setPayoffCC(game.getPayoffAA());
		parameters.setPayoffCD(game.getPayoffAB());
		parameters.setPayoffDC(game.getPayoffBA());
		parameters.setPayoffDD(game.getPayoffBB());
		parameters.setGraphId(networkMeta.getNetworkId());
		
		SimulationSeries series = new SimulationSeries(parameters, datasets);
		return (series);
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

			//SimulationSeries series = simulate(simulationParameters, network);
			//group.add(series);
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

	/**
	 * Transform a service network representation into a MASON compatible
	 * network.
	 * 
	 * @param structure
	 *            network as {@link NetworkStructure}
	 * @return network as {@link Network}
	 */
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

}
