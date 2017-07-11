package i5.las2peer.services.cdService.data.simulation;

import java.io.Serializable;
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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Simulation Data
 * 
 * Objects of this class represent the data collected by simulation series The
 * objects can be stored
 * 
 */
@Entity
public class DataSet implements Serializable {

	/////////////// Entity Fields ///////////////

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

	public DataSet(List<Double> cooperationValues, List<Double> payoffValues, List<AgentData> agentDataList,
			boolean stable) {

		this.cooperationValues = cooperationValues;
		this.payoffValues = payoffValues;
		this.agentData = agentDataList;
		this.stable = stable;
	}

	/////////////// Getter ///////////////

	@JsonProperty
	public List<Double> getCooperationValues() {
		return this.cooperationValues;
	}

	@JsonProperty
	public List<Double> getPayoffValues() {
		return this.payoffValues;
	}

	@JsonProperty
	public List<AgentData> getAgentData() {
		return this.agentData;
	}

	@JsonProperty
	public double getLastCooperationValue() {
		return cooperationValues.get(cooperationValues.size() - 1);
	}

	@JsonProperty
	public boolean isStable() {
		return stable;
	}

	@JsonProperty
	public long getDataId() {
		return dataId;
	}

	@JsonIgnore
	public Evaluation getCooperationEvaluation() {
		return new Evaluation(getCooperationValues());
	}
	
	@JsonIgnore
	public Evaluation getPayoffEvaluation() {
		return new Evaluation(getPayoffValues());
	}

	////////////// Print Data /////////////

	public String toTableLine() {
		
		Evaluation coopEvaluation = getCooperationEvaluation();
		Evaluation payoffEvaluation = getPayoffEvaluation();
		
		StringBuilder line = new StringBuilder();
		line.append(getLastCooperationValue()).append("\t");
		line.append(coopEvaluation.toTableLine()).append("\t");
		line.append(payoffEvaluation.toTableLine()).append("\t");	
		
		return line.toString();
	}
	
	public String toHeadLine() {
		
		Evaluation coopEvaluation = getCooperationEvaluation();
		Evaluation payoffEvaluation = getPayoffEvaluation();
	
		StringBuilder line = new StringBuilder();
		line.append("final#C").append("\t");
		line.append(coopEvaluation.toHeadLine("", "#C"));
		line.append(payoffEvaluation.toHeadLine("", "#P"));
		
		return line.toString();
		
	}
}


