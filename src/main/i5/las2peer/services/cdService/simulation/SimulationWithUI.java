
package i5.las2peer.services.cdService.simulation;
import sim.portrayal.network.*;
import sim.portrayal.continuous.*;
import sim.engine.*;
import sim.display.*;
import sim.portrayal.simple.*;
import sim.portrayal.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.*;


public class SimulationWithUI extends GUIState
    {
    public Display2D display;
    public JFrame displayFrame;

    ContinuousPortrayal2D yardPortrayal = new ContinuousPortrayal2D();
    NetworkPortrayal2D buddiesPortrayal = new NetworkPortrayal2D();
           



    public static void main(String[] args)
        {
        SimulationWithUI vid = new SimulationWithUI();
        Console c = new Console(vid);
        c.setVisible(true);
        }

    public SimulationWithUI() { super(new Simulation( System.currentTimeMillis())); }
    public SimulationWithUI(SimState state) { super(state); }

    public Object getSimulationInspectedObject() { return state; }

    public Inspector getInspector()
        {
        Inspector i = super.getInspector();
        i.setVolatile(true);
        return i;
        }

    public static String getName() { return "WCSS Tutorial 14: Student Cliques (in 3D)"; }
    
    public void start()
        {
        super.start();
        setupPortrayals();
        }

    public void load(SimState state)
        {
        super.load(state);
        setupPortrayals();
        }

    public void setupPortrayals()
        {
        Simulation students = (Simulation) state;
        
        // tell the portrayals what to portray and how to portray them
       // yardPortrayal.setField( students.yard );
        yardPortrayal.setPortrayalForAll(
            new MovablePortrayal2D(
                new CircledPortrayal2D(
                    new LabelledPortrayal2D(
                        new OvalPortrayal2D()
                            {
                            public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
                                {
                                Agent student = (Agent)object;

                               // int agitationShade = (int) (student.getAgitation() * 255 / 10.0);
                                //if (agitationShade > 255) agitationShade = 255;
                                //paint = new Color(agitationShade, 0, 255 - agitationShade);
                                //super.draw(object, graphics, info);
                                }
                            }, 
                        5.0, null, Color.black, true),
                    0, 5.0, Color.green, true)));
                                                

        
       // buddiesPortrayal.setField( new SpatialNetwork2D( students.yard, students.network ) );
        buddiesPortrayal.setPortrayalForAll(new SimpleEdgePortrayal2D());

        // reschedule the displayer
        display.reset();
        display.setBackdrop(Color.white);

        // redraw the display
        display.repaint();          
                            


        }

    public void init(Controller c)
        {
        super.init(c);

        // make the displayer
        display = new Display2D(600,600,this);
        // turn off clipping
        display.setClipping(false);

        displayFrame = display.createFrame();
        displayFrame.setTitle("Schoolyard Display");
        c.registerFrame(displayFrame);   // register the frame so it appears in the "Display" list
        displayFrame.setVisible(true);
        display.attach( buddiesPortrayal, "Buddies" );
        display.attach( yardPortrayal, "Yard" );

        }

    public void quit()
        {
        super.quit();

        if (displayFrame!=null) displayFrame.dispose();
        displayFrame = null;
        display = null;
        }         

    }
