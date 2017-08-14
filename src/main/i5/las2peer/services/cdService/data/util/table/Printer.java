package i5.las2peer.services.cdService.data.util.table;

import java.io.File;
import java.util.List;

import i5.las2peer.services.cdService.data.mapping.CoverSimulationGroupMapping;
import i5.las2peer.services.cdService.data.mapping.NetworkSimulationGroupMapping;
import i5.las2peer.services.cdService.data.network.DynamicGraph;
import i5.las2peer.services.cdService.data.network.PropertyType;
import i5.las2peer.services.cdService.data.network.cover.CoverGroup;
import i5.las2peer.services.cdService.data.simulation.AgentData;
import i5.las2peer.services.cdService.data.simulation.SimulationDataset;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;
import i5.las2peer.services.cdService.data.simulation.SimulationSeriesGroup;

public class Printer extends TablePrinter {

	String simulationSeries = "data\\table\\simulation\\series\\";
	String simulationGroup = "data\\table\\simulation\\group\\";
	String network = "data\\table\\network\\";
	String cover = "data\\table\\cover";
	String coverSeriesMapping = "data\\table\\mapping\\coverSimulationSeries";
	String coverGroupMapping = "data\\table\\mapping\\coverSimulationGroup";
	
	void printTable(NetworkSimulationGroupMapping mapping) {

		Table table = mapping.toTable();
		File path = new File(simulationGroup);
		path.mkdirs();
		File file = buildFile(path, mapping.getName());
		printTable(table, file);

	}

	void printTable(DynamicGraph network) {

		Table table = network.toTable();
		File path = new File("data\\table\\network\\dynamic");
		path.mkdirs();
		File file = buildFile(path, String.valueOf(network.getId()));
		printTable(table, file);

	}
	
	public void printTable(SimulationSeries simulation) {

		Table table = simulation.toTable();
		File path = new File(simulationSeries);
		path.mkdirs();
		File file = buildFile(path, simulation.getName());
		printTable(table, file);
	}
	
	public void printTable(SimulationSeriesGroup simulation) {

		Table table = simulation.toTable();
		File path = new File(simulationGroup);
		path.mkdirs();
		File file = buildFile(path, simulation.getName());
		printTable(table, file);
	}

	public void printTable(CoverGroup object) {

		Table table = object.toTable();
		File path = new File(cover);
		path.mkdirs();
		File file = buildFile(path, object.getName());
		printTable(table, file);
	}

	public void printCorrelations(CoverSimulationGroupMapping mapping) {

		DataTable table;
		File file;
		File path = new File("data\\table\\mapping\\properties\\" + mapping.getCover().getAlgorithm() + "\\");
		path.mkdirs();

		double[] cooperation = mapping.getCooperationValues();
		double[] property;

		file = buildFile(path, "size");
		property = mapping.getPropertyValues(PropertyType.SIZE);
		table = new DataTable();
		table.add(cooperation, property);
		printTable(table, file);

		file = buildFile(path, "density");
		property = mapping.getPropertyValues(PropertyType.DENSITY);
		table = new DataTable();
		table.add(cooperation, property);
		printTable(table, file);

		file = buildFile(path, "averageDegree");
		property = mapping.getPropertyValues(PropertyType.AVERAGE_DEGREE);
		table = new DataTable();
		table.add(cooperation, property);
		printTable(table, file);

	}
	
	public void printFinalState(SimulationSeries simulation) {

		List<SimulationDataset> list = simulation.getSimulationDatasets();
		int size = list.size();

		for (int i = 0; i < size; i++) {

			List<AgentData> agentList = list.get(i).getAgentData();
			Table table = new Table();
			for (int j = 0, jsize = agentList.size(); j < jsize; j++) {
				table.add(new TableRow().add(String.valueOf(agentList.get(j).getFinalStrategy())));
			}
			File path = new File("data\\table\\simulation\\series\\" + simulation.getName() + "\\finalState");
			path.mkdirs();
			File file = buildFile(path, i + "_" + simulation.getName());
			printTable(table, file);

		}

	}

}
