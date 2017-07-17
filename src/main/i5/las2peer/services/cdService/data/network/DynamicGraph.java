package i5.las2peer.services.cdService.data.network;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;

import i5.las2peer.services.cdService.data.simulation.SimulationDataset;
import i5.las2peer.services.cdService.data.util.Table;
import i5.las2peer.services.cdService.data.util.TableRow;

@Entity
public class DynamicGraph implements GraphInterface {
	
	///// Entity Fields /////
	
	@Id	
	@GeneratedValue
	private long networkId;
	
	@Basic
	private String graphName;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn
	private List<Graph> graphs;
	
	

	///// Getter /////
	
	@JsonProperty
	public long getId() {
		return networkId;
	}
	
	@JsonProperty
	public String getGraphName() {
		return graphName;
	}
	
	@JsonProperty
	public List<Graph> getGraphs() {
		return graphs;
	}

	///// Setter /////
	
	public void setGraphName(String graphName) {
		this.graphName = graphName;
	}

	public void setGraphs(List<Graph> graphs) {
		this.graphs = graphs;
	}
	
	///// Methods //////
	
	public int size() {
		return graphs.size();
	}
	
	
	////// Print /////
	
	public Table toTable() {

		Table table = new Table();
		
		// Networks
		for (int i = 0; i < size(); i++) {
			Graph network = graphs.get(i);
			table.add(network.toTableLine().addFront(i));
		}
		return table;
	}
	
}
