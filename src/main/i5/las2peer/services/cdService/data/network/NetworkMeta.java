package i5.las2peer.services.cdService.data.network;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import i5.las2peer.services.cdService.data.network.cover.Cover;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;
import i5.las2peer.services.cdService.data.util.table.TableRow;

@Entity(name = "Networks")
@JsonIgnoreProperties(ignoreUnknown = true)
public class NetworkMeta implements GraphInterface {

	@Id
	@GeneratedValue
	private long networkId;

	@Enumerated
	private NetworkOrigin origin;

	@Basic
	private long originId;

	@Basic
	private long userId;

	@Basic
	private String name;
	
	@Basic
	private int size;

	@Embedded
	private NetworkProperties networkProperties;
		
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)	
	NetworkStructure structure;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)	
	private List<Cover> covers;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SimulationSeries> simulations;

	public NetworkMeta() {

	}

	public NetworkMeta(long ocdId) {

		this.originId = ocdId;
	}

	///// Getter /////

	@JsonProperty
	public long getNetworkId() {
		return this.networkId;
	}

	@JsonProperty
	public long getOriginId() {
		return this.originId;
	}

	@JsonIgnore
	public long getUserId() {
		return this.userId;
	}

	@JsonProperty
	public String getName() {
		return name;
	}
	
	@JsonProperty
	public int getSize() {
		return size;
	}

	@JsonProperty
	public String getOrigin() {
		if(origin == null) 
			origin = NetworkOrigin.UNKNOWN;
		return origin.name();
	}

	@Override
	@JsonProperty
	public NetworkProperties getProperties() {
		if (networkProperties == null) {
			NetworkProperties networkProperties = new NetworkProperties();

			NetworkStructure network = getNetworkStructure();
		
			if (network == null) {
				return new NetworkProperties();
			}

			int size = network.nodeCount();
			int edges = network.edgeCount();
			double[] degrees = new double[size];
			for (int id = 0; id < size; id++) {				
				degrees[id] = network.getEdges(id).size();
			}
			networkProperties.setNodes(size);
			networkProperties.setEdges(edges);
			networkProperties.setDegreeDeviation(networkProperties.calculateDegreeDeviation(degrees));

			this.networkProperties = networkProperties;
		}

		return networkProperties;
	}

	@JsonIgnore
	public List<Cover> getCovers() {
		return covers;
	}

	@JsonProperty
	public NetworkStructure getNetworkStructure() {		
		return structure;
	}

	@JsonIgnore
	public List<SimulationSeries> getSimulations() {
		return simulations;
	}

	/// Setter ///

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public void setSize(int size) {
		this.size = size;
	}

	public void setGraphName(String graphName) {
		this.name = graphName;
	}

	public void addCover(ArrayList<Cover> covers) {
		this.covers = covers;
	}
	
	public void setOrigin(NetworkOrigin origin) {
		this.origin = origin;
	}
	
	public void setOriginId(long originId) {
		this.originId = originId;
	}
	
	public void setNetworkStructure(NetworkStructure network) {
		this.structure = network;
	}

	@Override
	public void setProperties(NetworkProperties networkProperties) {
		this.networkProperties = networkProperties;
	}
	

	/// Print ///

	public TableRow toTableLine() {

		NetworkProperties networkProperties = getProperties();

		TableRow line = new TableRow();
		line.add(getName()).add(networkProperties.toTableLine());
		return line;
	}

}
