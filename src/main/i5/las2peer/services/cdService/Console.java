package i5.las2peer.services.cdService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import i5.las2peer.services.cdService.data.SimulationDataProvider;
import i5.las2peer.services.cdService.data.network.NetworkStructure;
import i5.las2peer.services.cdService.data.simulation.Parameters;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;
import i5.las2peer.services.cdService.data.simulation.SimulationSeriesGroup;
import i5.las2peer.services.cdService.data.util.NetworkParser;
import i5.las2peer.services.cdService.data.util.TablePrinter;
import i5.las2peer.services.cdService.data.util.TableType;
import i5.las2peer.services.cdService.simulation.SimulationManager;
import i5.las2peer.services.cdService.simulation.dynamic.DynamicType;
import i5.las2peer.services.cdService.simulation.game.GameType;
import sim.field.network.Network;

public class Console {

	public static void main(String[] args) {
		
		NetworkParser parser = new NetworkParser();
		SimulationDataProvider provider = SimulationDataProvider.getInstance();
		SimulationSeriesGroup group = new SimulationSeriesGroup();
		List<Long> ids = new ArrayList<>();
		try {
			SimulationManager simulationManager = new SimulationManager();
			NetworkStructure network = parser.readNetwork();
			TablePrinter latexPrinter = new TablePrinter("latex_", TableType.LATEX);
			TablePrinter tabPrinter = new TablePrinter("tab_", TableType.TAB);
			ObjectMapper mapper = new ObjectMapper();
			
			double scale = 0.0;
			Parameters parameters;
			for (int i = 0; i < 11; i++) {
								
				parameters = new Parameters();
				parameters.setDynamic(DynamicType.REPLICATOR);
				parameters.setDynamicValue(1.5);
				parameters.setIterations(200);
				parameters.setGame(GameType.PRISONERS_DILEMMA);
				parameters.setPayoffCD(0.0);
				parameters.setPayoffCC(scale);
				parameters.setPayoffDC(1.0);
				parameters.setPayoffDD(0.0);
				System.out.println("start simulation " + i);
				SimulationSeries series = simulationManager.simulate(parameters, network);
				String name = "BioJava_R_PD";
				group.setName("name");
				if(i<10) {
				name = name + "_0.0" + i;
				} else {
				name = name + "_0." + i;
				}
				series.setName(name);
				series.setSeriesId(i);
				series.normalize();				
				group.add(series);
				latexPrinter.printTable(series);
				tabPrinter.printTable(series);
				
			
				mapper.writeValue(new File("data\\json\\simulation\\" + name), series);				
				scale = (i+1)*0.1;
			}
			latexPrinter.printTable(group);
			tabPrinter.printTable(group);
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File("data\\json\\simulation\\name"), group);			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
