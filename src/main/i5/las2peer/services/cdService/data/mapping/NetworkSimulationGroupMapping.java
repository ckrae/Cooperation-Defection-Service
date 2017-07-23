package i5.las2peer.services.cdService.data.mapping;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import i5.las2peer.services.cdService.data.network.NetworkMeta;
import i5.las2peer.services.cdService.data.network.PropertyType;
import i5.las2peer.services.cdService.data.simulation.SimulationSeriesGroup;
import i5.las2peer.services.cdService.data.util.Table;
import i5.las2peer.services.cdService.data.util.TableRow;

public class NetworkSimulationGroupMapping extends MappingAbstract {
	
///// Entity Fields /////

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private NetworkMeta network;

	@ManyToOne(fetch = FetchType.LAZY)
	private SimulationSeriesGroup simulation;

	@ElementCollection
	List<Double> cooperationValues;

	@Embedded
	Correlation correlation;

	///// Constructor /////

	public NetworkSimulationGroupMapping() {

	}

	///// Getter /////
	
	public long getId() {
		return this.id;
	}

	@JsonProperty
	public NetworkMeta getNetwork() {
		return network;
	}

	@JsonProperty
	public SimulationSeriesGroup getSimulation() {
		return simulation;
	}

	@JsonIgnore
	public List<Double> getCooperationValues() {
		return cooperationValues;
	}

	@JsonProperty
	public Correlation getCorrelation() {
		if(correlation == null)
			correlation = new Correlation();
		return correlation;
	}	

	///// Setter /////

	public void setNetwork(NetworkMeta network) {
		this.network = network;
	}

	public void setSimulation(SimulationSeriesGroup simulation) {
		this.simulation = simulation;
	}

	public void setCooperationValues(List<Double> cooperationValues) {
		this.cooperationValues = cooperationValues;
	}

	///// Methods /////
	
	@JsonIgnore
	public double[] getPropertyValues(PropertyType property) {
		
		return null;
	}
	
	
	///// Print /////

	@Override
	public TableRow toTableLine() {

		TableRow line = new TableRow();
		line.add("");
		return line;
	}

	@Override
	public Table toTable() {
		// TODO Auto-generated method stub
		return null;
	}


	
}
