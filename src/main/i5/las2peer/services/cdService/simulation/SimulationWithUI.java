package i5.las2peer.services.cdService.simulation;

import java.awt.Color;

import javax.swing.JFrame;

import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.Inspector;
import sim.portrayal.network.NetworkPortrayal2D;
import sim.portrayal.network.SimpleEdgePortrayal2D;

public class SimulationWithUI extends GUIState {
	public Display2D display;
	public JFrame displayFrame;

	NetworkPortrayal2D networkPortrayal = new NetworkPortrayal2D();

	public static void main(String[] args) {
		SimulationWithUI vid = new SimulationWithUI();
		Console c = new Console(vid);
		c.setVisible(true);
	}

	public SimulationWithUI() {
		super(new Simulation(System.currentTimeMillis()));
	}

	public SimulationWithUI(SimState state) {
		super(state);
	}

	@Override
	public Object getSimulationInspectedObject() {
		return state;
	}

	@Override
	public Inspector getInspector() {
		Inspector i = super.getInspector();
		i.setVolatile(true);
		return i;
	}

	public static String getName() {
		return "Cooperation & Defection";
	}

	@Override
	public void start() {
		super.start();
		setupPortrayals();
	}

	@Override
	public void load(SimState state) {
		super.load(state);
		setupPortrayals();
	}

	public void setupPortrayals() {
		Simulation simulation = (Simulation) state;

		// networkPortrayal.setField( new SpatialNetwork2D(
		// simulation.network ) );
		networkPortrayal.setPortrayalForAll(new SimpleEdgePortrayal2D());

		// reschedule the displayer
		display.reset();
		display.setBackdrop(Color.white);

		// redraw the display
		display.repaint();

	}

	@Override
	public void init(Controller c) {
		super.init(c);

		// make the displayer
		display = new Display2D(600, 600, this);
		// turn off clipping
		display.setClipping(false);

		displayFrame = display.createFrame();
		displayFrame.setTitle("Network Cooperation Display");
		c.registerFrame(displayFrame); // register the frame so it appears in
										// the "Display" list
		displayFrame.setVisible(true);
		display.attach(networkPortrayal, "Agents");

	}

	@Override
	public void quit() {
		super.quit();

		if (displayFrame != null)
			displayFrame.dispose();
		displayFrame = null;
		display = null;
	}

}
