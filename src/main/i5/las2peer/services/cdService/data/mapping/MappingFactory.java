package i5.las2peer.services.cdService.data.mapping;

import i5.las2peer.services.cdService.data.network.GraphInterface;
import i5.las2peer.services.cdService.data.network.cover.Cover;
import i5.las2peer.services.cdService.data.simulation.SimulationAbstract;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;
import i5.las2peer.services.cdService.data.simulation.SimulationSeriesGroup;

public class MappingFactory {
	
	public Mapping build(GraphInterface graph, SimulationAbstract simulation) {
		
		Mapping mapping = new Mapping();
		mapping.setGraph(graph);
		mapping.setSimulation(simulation);
		mapping.setName("");
		
		return mapping;		
	}
	
	
	public CoverSimulationSeriesMapping build(Cover cover, SimulationSeries simulation) {
		
		CoverSimulationSeriesMapping mapping = new CoverSimulationSeriesMapping();
		mapping.setCover(cover);
		mapping.setSimulation(simulation);
		mapping.setName("mapping_" + simulation.getName() + "_" + cover.getAlgorithm());
		
		return mapping;		
	}
	
	public CoverSimulationGroupMapping build(Cover cover, SimulationSeriesGroup simulation) {
		
		CoverSimulationGroupMapping mapping = new CoverSimulationGroupMapping();
		mapping.setCover(cover);
		mapping.setSimulation(simulation);
		mapping.setName("mapping_" + simulation.getName() + "_" + cover.getAlgorithm());
		
		return mapping;		
	}
	


}
