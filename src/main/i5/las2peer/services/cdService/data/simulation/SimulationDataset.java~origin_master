package i5.las2peer.services.cdService.data.simulation;

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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import i5.las2peer.services.cdService.data.util.Table;
import i5.las2peer.services.cdService.data.util.TableRow;
import i5.las2peer.services.cdService.simulation.Simulation;

/**
 * Simulation Data
 * 
 * Objects of this class represent the data collected by simulation series The
 * objects can be stored
 * 
 */
@Entity
public class SimulationDataset extends SimulationAbstract {

	/////////////// Entity Fields ///////////////

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

	public SimulationDataset() {

	}

	public SimulationDataset(List<Double> cooperationValues, List<Double> payoffValues, List<AgentData> agentDataList,
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
	public double getFinalCooperationValue() {
		return cooperationValues.get(cooperationValues.size() - 1);
	}

	@JsonProperty
	public double getFinalPayoffValue() {
		return payoffValues.get(payoffValues.size() - 1);
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

	//////////// Methods ////////////

	public int size() {
		return cooperationValues.size();
	}

	public int fill(int size) {
		if (size > size()) {
			double last = getFinalCooperationValue();
			for (int i = size(); i < size; i++) {
				cooperationValues.add(last);
			}
			last = getFinalPayoffValue();
			for (int i = payoffValues.size(); i < size; i++) {
				payoffValues.add(last);
			}
		}
		return size;
	}

	////////////// Print Data /////////////

	@Override
	public TableRow toTableLine() {

		Evaluation coopEvaluation = getCooperationEvaluation();
		Evaluation payoffEvaluation = getPayoffEvaluation();

		TableRow line = new TableRow();
		line.add(getFinalCooperationValue());
		line.add(coopEvaluation.toTableLine());
		line.add(payoffEvaluation.toTableLine());

		return line;
	}

	public TableRow toHeadLine() {

		Evaluation coopEvaluation = getCooperationEvaluation();
		Evaluation payoffEvaluation = getPayoffEvaluation();

		TableRow line = new TableRow();
		line.add("final#C");
		line.add(coopEvaluation.toHeadLine().suffix("#C"));
		line.add(payoffEvaluation.toHeadLine().suffix("#P"));

		return line;
	}

	@Override
	public Table toTable() {		
		
		Table table = new Table();				
		
		List<Double> values = getCooperationValues();
		for (int i = 0; i < values.size(); i++) {
			double value = values.get(i);
			table.add(new TableRow().add(value));
		}
		return table;
	}


}
