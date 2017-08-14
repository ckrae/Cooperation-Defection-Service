package i5.las2peer.services.cdService.data.simulation;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import i5.las2peer.services.cdService.data.network.cover.Community;
import i5.las2peer.services.cdService.data.util.table.Table;
import i5.las2peer.services.cdService.data.util.table.TableRow;

/**
 * A SimulationSeries serves as a container for multiple SimulationDatasets.
 * 
 * If you repeat the same Simulation multiple times, you will get multiple
 * SimulationDatasets with the same parameters. This class is meant to group them
 * together and allow statistical evaluation of the data sets.
 *
 */

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimulationSeries extends SimulationAbstract {

	/////////////// Entity Fields ///////////////

	/**
	 * The primary key for database persistence
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long seriesId;

	@Basic
	private long userId;

	@Basic
	private String name;

	@Basic
	private int generations;

	/**
	 * The simulation parameters used for this simulation series
	 */
	@Embedded
	private Parameters parameters;

	/**
	 * Statistical evaluation of the cooperation values of the
	 * SimulationDatasets
	 */
	@Embedded
	private Evaluation cooperationEvaluation;
	
	/**
	 * Statistical evaluation of the payoff values of the
	 * SimulationDatasets
	 */
	@Transient
	private Evaluation payoffEvaluation;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn
	private List<SimulationDataset> simulationDatasets;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SimulationSeriesGroup> simulationGroups;

	///////////////// Constructor //////////////////

	public SimulationSeries() {

	}

	public SimulationSeries(Parameters parameters, List<SimulationDataset> simulationDatasets) {

		this.parameters = parameters;
		this.simulationDatasets = simulationDatasets;
	}

	////////////////// Getter /////////////////////

	@JsonIgnore
	@Override
	public long getId() {
		return seriesId;
	}

	@JsonIgnore
	public long getUserId() {
		return userId;
	}

	@JsonIgnore
	public long getGenerations() {
		return generations;
	}

	@JsonProperty
	public Parameters getParameters() {
		return parameters;
	}

	@JsonProperty
	public List<SimulationDataset> getSimulationDatasets() {
		return simulationDatasets;
	}

	@JsonProperty
	public Evaluation getCooperationEvaluation() {
		return cooperationEvaluation;
	}

	@JsonProperty
	public Evaluation getPayoffEvaluation() {
		return payoffEvaluation;
	}

	@JsonIgnore
	public MetaData getMetaData() {
		return new MetaData(this);
	}

	@JsonIgnore
	public List<SimulationSeriesGroup> getSimulationSeriesGroups() {
		return this.simulationGroups;
	}

	////////////////// Setter /////////////////////

	@JsonSetter
	public void setId(long seriesId) {
		this.seriesId = seriesId;
	}

	@JsonSetter
	public void setUserId(long userId) {
		this.userId = userId;
	}

	@JsonSetter
	public void setGenerations(int generations) {
		this.generations = generations;
	}

	@JsonSetter
	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}

	@JsonSetter
	public void setCooperationEvaluation(Evaluation cooperationEvaluation) {
		this.cooperationEvaluation = cooperationEvaluation;
	}

	@JsonSetter
	public void setPayoffEvaluation(Evaluation payoffEvaluation) {
		this.payoffEvaluation = payoffEvaluation;
	}

	@JsonSetter
	public void setSimulationDatasets(List<SimulationDataset> simulationDatasets) {
		this.simulationDatasets = simulationDatasets;
	}

	/////////////////// Methods ///////////////////////

	/**
	 * Create {@link Evaluation} objects with the values given by
	 * {@link #getFinalCooperationValues()} and {@link #getFinalPayoffValues()}.
	 * 
	 */
	public void evaluate() {
		cooperationEvaluation = new Evaluation(getFinalCooperationValues());
		payoffEvaluation = new Evaluation(getFinalPayoffValues());
	}

	/**
	 * @return the number of SimulationDatasets
	 */
	public int size() {
		if (simulationDatasets == null)
			return 0;
		return simulationDatasets.size();
	}

	/**
	 * @return the number of generations of the longest SimulationDataset
	 */
	public int generations() {

		if (getSimulationDatasets() == null)
			return 0;

		int maxSize = 0;
		for (SimulationDataset dataset : simulationDatasets) {
			if (dataset.generations() > maxSize)
				maxSize = dataset.generations();
		}
		return maxSize;
	}

	public void normalize() {
		int maxSize = generations();
		for (SimulationDataset dataset : simulationDatasets) {
			dataset.fill(maxSize);
		}
	}
	
	public double averageCooperationValue() {
		if(getCooperationEvaluation() == null)
			setCooperationEvaluation(new Evaluation(getFinalCooperationValues()));
		return getCooperationEvaluation().getAverageValue();
	}
	
	public double averagePayoffValue() {
		if(getPayoffEvaluation() == null)
			setPayoffEvaluation(new Evaluation(getFinalPayoffValues()));
		return getPayoffEvaluation().getAverageValue();
	}

	/**
	 * Return the average community cooperation values of all SimulationDatasets
	 * *
	 * 
	 * @param communityList
	 *            the list of communities
	 * @return the values
	 */
	public double[] getAverageCommunityCooperationValues(List<Community> communityList) {
		int datasetCount = size();

		if (datasetCount < 1)
			throw new IllegalStateException("this simulation series is empty");

		int communityCount = communityList.size();
		double[] averageValues = new double[communityCount];

		for (int communityId = 0; communityId < communityCount; communityId++) {
			averageValues[communityId] = getAverageCommunityCooperationValue(communityList.get(communityId));
		}
		return averageValues;
	}

	/**
	 * Returns the average community cooperation value of the SimulationDatasets
	 * *
	 * 
	 * @param list
	 *            the Community
	 * @return average cooperation value
	 */
	public double getAverageCommunityCooperationValue(Community community) {
		int datasetCount = size();

		if (datasetCount < 1)
			throw new IllegalStateException("this simulation series is empty");

		if (community.getMembers() == null)
			throw new IllegalArgumentException("community has no memberlist");

		double total = 0.0;
		for (int datasetId = 0; datasetId < datasetCount; datasetId++) {
			total += getSimulationDatasets().get(datasetId).getCommunityCooperationValue(community.getMembers());
		}

		return total / datasetCount;
	}

	///// Final

	/**
	 * @return array of the cooperations values of the final state of all
	 *         SimulationDatasets
	 */
	@JsonIgnore
	public double[] getFinalCooperationValues() {

		int size = size();
		double[] values = new double[size];
		for (int i = 0; i < size; i++) {
			values[i] = simulationDatasets.get(i).getFinalCooperationValue();
		}
		return values;
	}

	/**
	 * @return array of the payoff values of the final state of all
	 *         SimulationDatasets
	 */
	@JsonIgnore
	public double[] getFinalPayoffValues() {

		int size = size();
		double[] values = new double[size];
		for (int i = 0; i < size; i++) {
			values[i] = simulationDatasets.get(i).getFinalPayoffValue();
		}
		return values;
	}

	///// over time

	@JsonIgnore
	public double[] getAverageCooperationValuesOverTime() {

		int size = generations();
		double[] values = new double[size];
		for (int i = 0; i < size; i++) {
			double total = 0;
			for (SimulationDataset dataset : simulationDatasets) {
				total += dataset.getCooperationValues().get(i);
			}
			values[i] = total / getSimulationDatasets().size();
		}
		return values;
	}

	@JsonIgnore
	public double[] getAveragePayoffValuesOverTime() {

		int size = generations();
		double[] values = new double[size];
		for (int i = 0; i < size; i++) {
			double total = 0;
			for (SimulationDataset dataset : simulationDatasets) {
				total += dataset.getPayoffValues().get(i);
			}
			values[i] = total / getSimulationDatasets().size();
		}
		return values;
	}

	////////////// Print Data /////////////

	@Override
	public Table toTable() {

		List<SimulationDataset> simulationDatasets = getSimulationDatasets();
		Table table = new Table();

		// headline
		TableRow headline = new TableRow();
		headline.add("data").add(simulationDatasets.get(0).toHeadLine());
		table.add(headline);

		// datasets
		for (int i = 0; i < simulationDatasets.size(); i++) {
			SimulationDataset data = simulationDatasets.get(i);
			table.add(data.toTableLine().addFront(i + 1));
		}
		return table;
	}

	@Override
	public TableRow toTableLine() {

		Parameters parameters = getParameters();
		Evaluation coopEvaluation = getCooperationEvaluation();
		Evaluation payoffEvaluation = getPayoffEvaluation();

		TableRow line = new TableRow();
		line.add(parameters.toTableLine());
		line.add(coopEvaluation.toTableLine());
		line.add(payoffEvaluation.toTableLine());
		return line;
	}

	public TableRow toHeadLine() {

		Parameters parameters = getParameters();
		Evaluation coopEvaluation = getCooperationEvaluation();
		Evaluation payoffEvaluation = getPayoffEvaluation();

		TableRow line = new TableRow();
		line.add(parameters.toHeadLine());
		line.add(coopEvaluation.toHeadLine().suffix("#C"));
		line.add(payoffEvaluation.toHeadLine().suffix("#P"));

		return line;

	}

}
