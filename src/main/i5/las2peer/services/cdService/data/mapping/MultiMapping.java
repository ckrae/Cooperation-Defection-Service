package i5.las2peer.services.cdService.data.mapping;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import i5.las2peer.services.cdService.data.network.GraphInterface;
import i5.las2peer.services.cdService.data.simulation.SimulationInterface;
import i5.las2peer.services.cdService.data.util.Table;
import i5.las2peer.services.cdService.data.util.TableRow;

public class MultiMapping extends MappingAbstract {

	///// Entity Fields /////

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private GraphInterface graph;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private SimulationInterface simulation;

	@OneToMany(fetch = FetchType.LAZY)
	private List<MappingInterface> mappings;

	@ElementCollection
	List<Double> cooperationValues;

	@Embedded
	Correlation correlation;

	///// Constructor /////
	
	public MultiMapping() {		
		
	}
	
	public MultiMapping(SimulationInterface simulation) {		
		this.simulation = simulation;
	}
	
	///// Getter /////
	

	
	
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

}
