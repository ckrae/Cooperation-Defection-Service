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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import i5.las2peer.api.exceptions.RemoteServiceException;
import i5.las2peer.api.exceptions.ServiceNotAvailableException;
import i5.las2peer.api.exceptions.ServiceNotFoundException;
import i5.las2peer.services.cdService.data.network.cover.Cover;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;
import i5.las2peer.services.cdService.data.util.TableRow;

@Entity(name = "Networks")
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
	private Properties properties;

	@OneToOne
	NetworkStructure structure;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)	
	private List<Cover> covers;

	@ManyToMany(fetch = FetchType.LAZY)
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
	public Properties getProperties() {
		if (properties == null) {
			Properties properties = new Properties();

			NetworkStructure network = null;
			try {
				network = getNetworkStructure();
			} catch (ServiceNotFoundException | ServiceNotAvailableException | RemoteServiceException e) {
				e.printStackTrace();
			}

			if (network == null) {
				return new Properties();
			}

			int size = network.nodeCount();
			int edges = network.edgeCount();
			double[] degrees = new double[size];
			for (int id = 0; id < size; id++) {				
				degrees[id] = network.getEdges(id).size();
			}
			properties.setNodes(size);
			properties.setEdges(edges);
			properties.setDegreeDeviation(properties.calculateDegreeDeviation(degrees));

			this.properties = properties;
		}

		return properties;
	}

	@JsonIgnore
	public List<Cover> getCovers() {
		return covers;
	}

	@JsonIgnore
	public NetworkStructure getNetworkStructure()
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {
		if (structure == null) {
			switch (origin) {
			case OCD_Service:
				structure = new GraphAdapter().inovkeGraphStructure(originId);
				break;
			case CLIENT:
			default:
				break;
			}
		}
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

	@Override
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/// Print ///

	public TableRow toTableLine() {

		Properties properties = getProperties();

		TableRow line = new TableRow();
		line.add(getName()).add(properties.toTableLine());
		return line;
	}

}
