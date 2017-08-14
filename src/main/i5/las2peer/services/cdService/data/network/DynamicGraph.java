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

import i5.las2peer.services.cdService.data.util.table.Table;

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
	private List<NetworkMeta> networkMetas;	
	

	///// Getter /////
	
	@JsonProperty
	public long getId() {
		return networkId;
	}
	
	@JsonProperty
	public String getName() {
		return graphName;
	}
	
	@JsonProperty
	public List<NetworkMeta> getGraphs() {
		return networkMetas;
	}

	///// Setter /////
	
	public void setGraphName(String graphName) {
		this.graphName = graphName;
	}

	public void setGraphs(List<NetworkMeta> networkMetas) {
		this.networkMetas = networkMetas;
	}
	
	///// Methods //////
	
	public int size() {
		return networkMetas.size();
	}
	

	@Override
	public NetworkProperties getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProperties(NetworkProperties networkProperties) {
		// TODO Auto-generated method stub
		
	}
	
	////// Print /////
	
	public Table toTable() {

		Table table = new Table();
		
		// Networks
		for (int i = 0; i < size(); i++) {
			NetworkMeta network = networkMetas.get(i);
			table.add(network.toTableLine().addFront(i));
		}
		return table;
	}

	
}
