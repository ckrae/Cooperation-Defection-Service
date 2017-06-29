package i5.las2peer.services.cdService.data.simulation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * Simulation Data
 * 
 * Objects of this class represent the data collected by simulation series The
 * objects can be stored
 * 
 */
@Entity
public class DataSet implements Serializable {

	/////////////// Attributes ///////////////

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long dataId;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn
	private List<AgentData> agentData;

	@ElementCollection
	private List<Double> cooperationValues;

	@ElementCollection
	private List<Double> payoffValues;

	@Basic
	private boolean stable;

	/////////////// Constructor ///////////////

	public DataSet() {

	}

	public DataSet(ArrayList<Double> cooperationValues, ArrayList<Double> payoffValues,
			ArrayList<ArrayList<Boolean>> nodeStrategies, ArrayList<ArrayList<Double>> nodePayoff, boolean stable) {

		this.cooperationValues = cooperationValues;
		this.payoffValues = payoffValues;
		this.agentData = new ArrayList<AgentData>();
		for (int i = 0; i < nodeStrategies.size(); i++) {
			agentData.add(new AgentData(nodeStrategies.get(i), nodePayoff.get(i)));
		}

		this.stable = stable;
	}

	/////////////// Getter ///////////////

	public List<Double> getCooperationValues() {
		return this.cooperationValues;
	}

	public List<Double> getPayoffValues() {
		return this.payoffValues;
	}

	public List<AgentData> getAgentData() {
		return this.agentData;
	}

	public double getLastCooperationValue() {
		return cooperationValues.get(cooperationValues.size() - 1);
	}

	public boolean isStable() {
		return stable;
	}

	public long getDataId() {
		return dataId;
	}

}
