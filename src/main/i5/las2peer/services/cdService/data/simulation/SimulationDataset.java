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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import i5.las2peer.services.cdService.data.network.cover.Community;
import i5.las2peer.services.cdService.data.util.table.Table;
import i5.las2peer.services.cdService.data.util.table.TableRow;

/**
 * Simulation Data
 * 
 * Objects of this class represent the data collected by simulation series The
 * objects can be stored
 * 
 */
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimulationDataset extends SimulationAbstract {

	/////////////// Entity Fields ///////////////

	@Id
	@GeneratedValue
	private long Id;

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

	@Override
	@JsonIgnore
	public long getId() {
		return Id;
	}

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

	@JsonIgnore
	public Evaluation getCooperationEvaluation() {
		return new Evaluation(getCooperationValues());
	}

	@JsonIgnore
	public Evaluation getPayoffEvaluation() {
		return new Evaluation(getPayoffValues());
	}

	////// Setter /////

	public void setAgentData(List<AgentData> agentList) {
		this.agentData = agentList;
	}

	public void setCooperationValues(List<Double> cooperationValues) {
		this.cooperationValues = cooperationValues;
	}

	public void setPayoffValues(List<Double> payoffValues) {
		this.payoffValues = payoffValues;
	}

	public void setStable(boolean stable) {
		this.stable = stable;
	}

	//////////// Methods ////////////

	public int generations() {
		return getCooperationValues().size();
	}
	
	public int fill(int newSize) {
		int oldSize = getCooperationValues().size();
		if (newSize > oldSize) {
			double last = getFinalCooperationValue();
			for (int i = oldSize; i < newSize; i++) {
				cooperationValues.add(last);
			}
			last = getFinalPayoffValue();
			for (int i = payoffValues.size(); i < newSize; i++) {
				payoffValues.add(last);
			}
		}
		return newSize;
	}

	public double[] getCommunityCooperationValues(List<Community> communityList) {

		int communityCount = communityList.size();
		double[] values = new double[communityCount];

		for (int communityId = 0; communityId < communityCount; communityId++) {
			List<Integer> memberList = communityList.get(communityId).getMembers();

			try {
				values[communityId] = getCommunityCooperationValue(memberList);
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException("invalid communityList");
			}
		}
		return values;
	}

	public double getCommunityCooperationValue(List<Integer> memberList) {

		int agentCount = agentCount();
		int memberCount = memberList.size();

		int cooperators = 0;
		for (int i = 0; i < memberCount; i++) {
			int memberId = memberList.get(i);

			if (memberId >= agentCount || memberId < 0)
				throw new IllegalArgumentException("invalid memberList");

			if (getAgentStrategy(memberId))
				cooperators++;
		}
		return (double) cooperators / memberCount;
	}

	@JsonIgnore
	public boolean getAgentStrategy(int agentId) {
		return getAgentData().get(agentId).getFinalStrategy();
	}

	public int agentCount() {
		if (this.getAgentData() == null)
			return 0;
		return this.getAgentData().size();
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
