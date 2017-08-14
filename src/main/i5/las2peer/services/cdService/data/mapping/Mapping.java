package i5.las2peer.services.cdService.data.mapping;

import java.util.List;

import i5.las2peer.services.cdService.data.network.GraphInterface;
import i5.las2peer.services.cdService.data.network.PropertyType;
import i5.las2peer.services.cdService.data.simulation.SimulationAbstract;
import i5.las2peer.services.cdService.data.util.table.Table;
import i5.las2peer.services.cdService.data.util.table.TableRow;

public class Mapping extends MappingAbstract {

	///// Attributes /////

	private GraphInterface graph;

	private SimulationAbstract simulation;

	private List<MappingAbstract> mappings;
	
	Correlation correlation;

	///// Constructor /////
	
	public Mapping() {		
		
	}
	
	public Mapping(SimulationAbstract simulation) {		
		this.simulation = simulation;
	}
	
	///// Getter /////
	
	public GraphInterface getGraph() {
		return graph;
	}

	public SimulationAbstract getSimulation() {
		return simulation;
	}

	public List<MappingAbstract> getMappings() {
		return mappings;
	}
	
	///// Setter /////
	
	public void setGraph(GraphInterface graph) {
		this.graph = graph;
	}

	public void setSimulation(SimulationAbstract simulation) {
		this.simulation = simulation;
	}

	public void setMappings(List<MappingAbstract> mappings) {
		this.mappings = mappings;
	}
	
	///// Print /////
	
	@Override
	public TableRow toTableLine() {

		return null;
	}

	@Override
	public Table toTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getPropertyValues(PropertyType property) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getCooperationValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	

	

}
