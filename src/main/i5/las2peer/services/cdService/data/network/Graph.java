package i5.las2peer.services.cdService.data.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import i5.las2peer.api.exceptions.RemoteServiceException;
import i5.las2peer.api.exceptions.ServiceNotAvailableException;
import i5.las2peer.api.exceptions.ServiceNotFoundException;
import i5.las2peer.services.cdService.data.simulation.Evaluation;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;
import i5.las2peer.services.cdService.data.util.TableRow;
import sim.field.network.Network;
import sim.util.Bag;

@Entity(name = "Networks")
public class Graph implements Serializable, GraphInterface, PropertyInterface {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long networkId;

	@Basic
	private long ocdId;

	@Basic
	private long userId;

	@Basic
	private String graphName;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<SimulationSeries> seriesList;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn
	private List<Cover> covers;

	@Transient
	private Properties properties;

	@Transient
	Network network;

	public Graph() {

	}

	public Graph(long ocdId) {

		this.ocdId = ocdId;
	}

	//// Getter /////

	@JsonProperty
	public long getNetworkId() {
		return this.networkId;
	}

	@JsonProperty
	public long getOCDId() {
		return this.ocdId;
	}

	@JsonProperty
	public long getUserId() {
		return this.userId;
	}

	@JsonProperty
	public String getGraphName() {
		return graphName;
	}

	@Override
	@JsonProperty
	public Properties getProperties() {
		if (properties == null) {
			Properties properties = new Properties();

			Network network = null;
			try {
				network = getGraph();
			} catch (ServiceNotFoundException | ServiceNotAvailableException | RemoteServiceException e) {
				e.printStackTrace();
			}

			if (network == null) {
				return new Properties();
			}

			Bag nodes = new Bag(network.getAllNodes());
			int size = nodes.size();
			int edges = NetworkAdapter.countEdges(network);
			double[] degrees = new double[size];
			for (int i = 0; i < size; i++) {
				degrees[i] = network.getEdgesIn(nodes.get(i)).size();
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
	public Network getGraph() throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {
		if (network == null)
			return NetworkAdapter.inovkeGraphData(this.ocdId);
		return network;
	}

	/// Setter ///

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setGraphName(String graphName) {
		this.graphName = graphName;
	}

	public void addCover(ArrayList<Cover> covers) {
		this.covers = covers;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/// Print ///

	public TableRow toTableLine() {

		Properties properties = getProperties();

		TableRow line = new TableRow();
		line.add(getGraphName()).add(properties.toTableLine());
		return line;
	}

}
