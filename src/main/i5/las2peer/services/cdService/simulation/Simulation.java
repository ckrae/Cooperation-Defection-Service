
package i5.las2peer.services.cdService.simulation;

import java.util.ArrayList;
import java.util.List;

import i5.las2peer.services.cdService.data.simulation.SimulationDataset;
import i5.las2peer.services.cdService.simulation.dynamic.Dynamic;
import i5.las2peer.services.cdService.simulation.dynamic.DynamicFactory;
import i5.las2peer.services.cdService.simulation.dynamic.DynamicType;
import i5.las2peer.services.cdService.simulation.game.Game;
import i5.las2peer.services.cdService.simulation.game.GameFactory;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.field.network.Network;
import sim.util.Bag;

public class Simulation extends SimState {
	private static final long serialVersionUID = 1;

	//// Simulations Settings

	private final int MAX_ITERATIONS = 1000;
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
	/**
	 * record data
	 */
	private final DataRecorder recorder;
	/**
	 * break condition
	 */
	private BreakCondition breakCondition;

	////// Constructor ///////

	public Simulation() {
		this(System.currentTimeMillis());
	}

	public Simulation(long seed, Network network, Game game, Dynamic dynamic) {
		super(seed);

		this.network = network;
		this.game = game;
		this.dynamic = dynamic;
		this.recorder = new DataRecorder(this);

	}

	public Simulation(long seed) {
		super(seed);

		Network netw = new Network(false);

		this.network = netw;
		this.game = GameFactory.getInstance().build(2, 4);
		this.dynamic = DynamicFactory.getInstance().build(DynamicType.REPLICATOR, 1.5);
		this.recorder = new DataRecorder(this);

		ArrayList<Agent> agentList = new ArrayList<Agent>(20);
		for (int i = 0; i < 20; i++) {
			agentList.add(i, new Agent(i));
			netw.addNode(agentList.get(i));
		}
		for (int i = 0; i < 20; i++) {
			netw.addEdge(agentList.get(random.nextInt(19)), agentList.get(random.nextInt(19)), 1);
		}

	}

	public static void main(String[] args) {
		doLoop(Simulation.class, args);
		System.exit(0);
	}
	
	///////// Initialize /////////	
	
	/**
	 * prepare for a new simulation run
	 */
	@Override
	public void start() {
		super.start();
		
		recorder.clear();
		List<Stoppable> stopper = new ArrayList<>(4);
		stopper.add(schedule.scheduleRepeating(1, 3, recorder));
		initAgents(stopper);

		breakCondition = new BreakCondition(stopper);
		breakCondition.add(schedule.scheduleRepeating(1, 4, breakCondition));
	}

	protected void initAgents(List<Stoppable> stopper) {

		// Set random strategies 50/50
		Bag agents = new Bag(network.getAllNodes());
		int size = agents.size();

		int cooperation = 0;
		for (int i = 0; i < size; i++) {
			boolean strategy;

			// already 50% cooperation
			if (cooperation * 2 >= size) {
				strategy = false;
			} else
			// already 50% defection
			if ((i - cooperation) * 2 >= size) {
				strategy = true;
			} else {
				// random
				strategy = random.nextBoolean();
			}

			// Initialize Agent
			Agent agent = (Agent) agents.get(i);
			agent.initialize(strategy, this);
			if (strategy)
				cooperation++;

			stopper.add(schedule.scheduleRepeating(1, 2, agent));
			stopper.add(schedule.scheduleRepeating(2, 1, new Steppable() {
				@Override
				public void step(SimState state) {
					agent.updateDynamicStep(state);
				}
			}));

		}

	}

	public boolean isBreakCondition() {

		if (this.getRound() > 4) {
			return breakCondition.isBreakCondition(this);
		}
		return false;

	}

	//////// Simulation Data /////////

	/**
	 * Number of Cooperators
	 * 
	 * @return total Number of Cooperators
	 */
	public int getCooperationNumber() {
		int number = 0;
		Bag agents = network.getAllNodes();
		for (int i = 0, si = agents.size(); i < si; i++) {
			Agent agent = (Agent) agents.get(i);
			if (agent.getStrategy()) {
				number++;
			}
		}
		return number;
	}

	/**
	 * Cooperation Value
	 * 
	 * @return value Cooperator / all agents
	 */
	public double getCooperationValue() {

		double cooperators = getCooperationNumber();
		double totalAgents = network.getAllNodes().size();
		double value = 0;
		if (totalAgents > 0) {
			value = (cooperators / totalAgents);
		}
		return value;
	}

	/**
	 * The total payoff summed up by all agents
	 * 
	 * @return total payoff number
	 */
	public double getTotalPayoff() {

		double totalPayoff = 0.0;
		Bag agents = network.getAllNodes();
		for (int i = 0; i < agents.size(); i++) {
			Agent agent = (Agent) agents.get(i);
			totalPayoff += agent.getPayoff();
		}
		return totalPayoff;
	}

	public double getAveragePayoff() {

		double totalPayoff = getTotalPayoff();
		double totalAgents = network.getAllNodes().size();
		double value = 0.0;
		if (totalAgents > 0) {
			value = (totalPayoff / totalAgents);
		}
		return value;
	}

	public int getRound() {
		return (int) schedule.getSteps();
	}

	public SimulationDataset getSimulationData() {
		return recorder.getSimulationData();
	}

	public boolean hideSimulationData() {
		return true;
	}

	/////// Get Simulation Settings /////////

	public int getMaxIterations() {
		return this.MAX_ITERATIONS;
	}

	/**
	 * @return the network
	 */
	public Network getNetwork() {
		return network;
	}

	public boolean hideNetwork() {
		return true;
	}

	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	public boolean hideGame() {
		return true;
	}

	/**
	 * @return the dynamic
	 */
	public Dynamic getDynamic() {
		return dynamic;
	}

	public boolean hideDynamic() {
		return true;
	}

	/**
	 * @return the recorder
	 */
	public DataRecorder getDataRecorder() {
		return recorder;
	}

	public boolean hideDataRecorder() {
		return true;
	}

}
