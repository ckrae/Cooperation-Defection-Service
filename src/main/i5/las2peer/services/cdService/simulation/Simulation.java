
package i5.las2peer.services.cdService.simulation;

import java.util.ArrayList;
import java.util.List;

import ec.util.MersenneTwisterFast;
import i5.las2peer.services.cdService.data.simulation.AgentData;
import i5.las2peer.services.cdService.data.simulation.SimulationDataset;
import i5.las2peer.services.cdService.simulation.dynamic.Dynamic;
import i5.las2peer.services.cdService.simulation.dynamic.DynamicFactory;
import i5.las2peer.services.cdService.simulation.dynamic.DynamicType;
import i5.las2peer.services.cdService.simulation.game.Game;
import i5.las2peer.services.cdService.simulation.game.GameFactory;
import i5.las2peer.services.cdService.simulation.game.GameType;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.engine.Stoppable;
import sim.field.network.Network;
import sim.util.Bag;

/**
 * the main class 
 */
public class Simulation extends SimState {
	private static final long serialVersionUID = 1;

	////// Simulations Settings //////
		
	/**
	 * the network, one agent on every node.
	 */
	private Network network;
	
	/**
	 * the game, determines the payoff updating
	 */
	private Game game;
	
	/**
	 * the dynamic, determines the strategy updating
	 */
	private Dynamic dynamic;
	
	/**
	 * record data
	 */
	private DataRecorder recorder;
	
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
		this.breakCondition = new BreakCondition();
		this.recorder = new DataRecorder(breakCondition.getMaxIterations());
		
	}

	public Simulation(long seed) {
		super(seed);

		Network netw = new Network(false);

		this.network = netw;
		this.game = GameFactory.getInstance().build(GameType.PRISONERS_DILEMMA, 1, 2);
		this.dynamic = DynamicFactory.getInstance().build(DynamicType.REPLICATOR, 1.5);
		this.breakCondition = new BreakCondition();
		this.recorder = new DataRecorder(breakCondition.getMaxIterations());


		ArrayList<Agent> agentList = new ArrayList<Agent>(20);
		for (int i = 0; i < 20; i++) {
			agentList.add(i, new Agent(i));
			netw.addNode(agentList.get(i));
		}
		for (int i = 0; i < 20; i++) {
			netw.addEdge(agentList.get(random.nextInt(19)), agentList.get(random.nextInt(19)), 1);
		}

	}
	
	/**
	 * the main loop. Used in console mode.
	 */
	public static void main(String[] args) {
		doLoop(Simulation.class, args);
		System.exit(0);
	}
	
	///////// Initialize /////////	
	
	/**
	 * Prepare for a new simulation run. Called before each simulation.
	 */
	@Override
	public void start() {
		super.start();
		
		recorder.clear();
		List<Stoppable> stopper = new ArrayList<>(4);
		stopper.add(schedule.scheduleRepeating(1, 3, recorder));
		initAgents(stopper);

		breakCondition = new BreakCondition();
		breakCondition.add(schedule.scheduleRepeating(1, 4, breakCondition));
	}
	
	/**
	 * Initialize the network agents. 
	 */
	protected void initAgents(List<Stoppable> stopper) {

		// Set random strategies 50/50
		Bag agents = this.getAgents();
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
			agent.initialize(strategy, getNetwork());
			if (strategy)
				cooperation++;

			stopper.add(schedule.scheduleRepeating(1, 2, agent));
			stopper.add(schedule.scheduleRepeating(2, 1, new Steppable() {
				private static final long serialVersionUID = 1L;
				@Override
				public void step(SimState state) {
					agent.updateDynamicStep(state);
				}
			}));
		}
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
	
	/**
	 * Average Payoff
	 * 
	 * @return average payoff value
	 */
	public double getAveragePayoff() {

		double totalPayoff = getTotalPayoff();
		double totalAgents = network.getAllNodes().size();
		double value = 0.0;
		if (totalAgents > 0) {
			value = (totalPayoff / totalAgents);
		}
		return value;
	}
	
	/**
	 * @return current simulation round
	 */
	public int getRound() {
		return  (int) schedule.getSteps();
	}
	
	/**
	 * @return random generator
	 */
	public MersenneTwisterFast getRandom() {
		return  this.random;
	}

	/**
	 * Returns the results of this simulation as {@link SimulationDataset}
	 * 
	 * @return SimulationData
	 */
	public SimulationDataset getSimulationData() {
		
		Bag agents = getAgents();
		int size = agents.size();
		List<AgentData> agentDataList = new ArrayList<AgentData>(size);
		for(int agentId=0; agentId<size; agentId++) {
			agentDataList.add(((Agent) agents.get(agentId)).getAgentData());
		}
		
		SimulationDataset simulationData = new SimulationDataset();
		simulationData.setCooperationValues(recorder.getCooperationValues());
		simulationData.setPayoffValues(recorder.getPayoffValues());
		simulationData.setAgentData(agentDataList);
		return simulationData;
	}
	
	/**
	 * Hides SimulationData UI element. Used only in UI mode.
	 */
	public boolean hideSimulationData() {
		return true;
	}
	
	protected Bag getAgents() {
		return new Bag(getNetwork().getAllNodes());
	}

	/////// Get Simulation Settings /////////	
		
	/**
	 * @return the network
	 */
	public Network getNetwork() {
		return network;
	}
	
	/**
	 * Hides network UI element. Used only in UI mode.
	 */
	public boolean hideNetwork() {
		return true;
	}

	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}
	
	/**
	 * Hides Game UI element. Used only in UI mode.
	 */
	public boolean hideGame() {
		return true;
	}

	/**
	 * @return the dynamic
	 */
	public Dynamic getDynamic() {
		return dynamic;
	}
	
	/**
	 * Hides Dynamic UI element. Used only in UI mode.
	 */
	public boolean hideDynamic() {
		return true;
	}
	
	/**
	 * @return the data recorder
	 */
	protected DataRecorder getDataRecorder() {
		return recorder;
	}
	
	/**
	 * @return the data recorder
	 */
	protected BreakCondition getBreakCondition() {
		return breakCondition;
	}
	

}
