package i5.las2peer.services.cdService.data.simulation;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import i5.las2peer.services.cdService.data.network.cover.Community;
import i5.las2peer.services.cdService.data.util.table.Table;
import i5.las2peer.services.cdService.data.util.table.TableRow;

/**
 * A SimulationSeriesGroup serves as a container for multiple SimulationSeries.
 *
 * Sometimes you want to do multiple simulations with a scaling parameter. This class
 * is meant to group the resulting SimulatonSeries together.
 */

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class SimulationSeriesGroup extends SimulationAbstract {

	////////// Entity Fields //////////

	
	/**
	 * the persistence id
	 */
	@Id
	@GeneratedValue
	private long groupId;

	@Basic
	private String name;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<SimulationSeries> seriesList;

	@Embedded
	private Evaluation cooperationEvaluation;

	@Transient
	private Evaluation payoffEvaluation;

	////////// Constructor //////////

	public SimulationSeriesGroup() {
		this.seriesList = new ArrayList<>();
	}

	public SimulationSeriesGroup(List<SimulationSeries> list) {
		this.seriesList = list;
	}

	////////// Getter //////////

	@JsonProperty
	@Override
	public long getId() {
		return this.groupId;
	}

	@Override
	@JsonProperty
	public String getName() {
		return this.name;
	}

	@JsonProperty
	public List<SimulationSeries> getSimulationSeries() {
		return this.seriesList;
	}

	@JsonProperty
	public Evaluation getCooperationEvaluation() {
		if (cooperationEvaluation == null)
			cooperationEvaluation = new Evaluation(getAverageFinalCooperationValues());
		return cooperationEvaluation;
	}

	@JsonProperty
	public Evaluation getPayoffEvaluation() {
		if (payoffEvaluation == null)
			payoffEvaluation = new Evaluation(getAverageFinalPayoffValues());
		return payoffEvaluation;
	}
	
	public List<SimulationSeries> getSeriesList() {
		return seriesList;
	}
	
	/**
	 * @return the network ids used in the simulation series
	 */
	@JsonIgnore
	public List<Long> getNetworkIds() {

		List<Long> networkIds = new ArrayList<Long>();
		for (SimulationSeries series : seriesList) {
			networkIds.add(series.getParameters().getGraphId());
		}
		return networkIds;
	}

	
	/**
	 * Creates the metaData object of this SimulationSeriesGroup. The metaData object is used to be sent to the web client.
	 * 
	 * @return MetaData
	 */
	@JsonIgnore
	public List<MetaData> getMetaData() {
		int size = seriesList.size();
		List<MetaData> metaList = new ArrayList<MetaData>(size);
		for (int i = 0; i < size; i++) {
			metaList.add(new MetaData(seriesList.get(i)));
		}
		return metaList;
	}

	/////////// Setter ////////////

	/**
	 * Adds a SimulationSeries to this group
	 * 
	 * @param series SimulationSeries
	 */
	public void add(SimulationSeries series) {
		this.seriesList.add(series);
	}

	public void setSeriesList(List<SimulationSeries> seriesList) {
		this.seriesList = seriesList;
	}

	public void setCooperationEvaluation(Evaluation cooperationEvaluation) {
		this.cooperationEvaluation = cooperationEvaluation;
	}

	public void setPayoffEvaluation(Evaluation payoffEvaluation) {
		this.payoffEvaluation = payoffEvaluation;
	}

	/////////////////// Methods ///////////////////////

	/**
	 * @return the number of contained SimulationSeries 
	 */
	public int size() {
		return seriesList.size();
	}

	public int generations() {
		int maxSize = 0;
		for (SimulationSeries simulation : seriesList) {
			if (simulation.generations() > maxSize)
				maxSize = simulation.generations();
		}
		return maxSize;
	}

	/**
	 * Return the average community cooperation values of all SimulationSeries
	 * 
	 * @param communityList
	 *            the list of communities
	 * @return the values
	 */
	public double[] getAverageCommunityCooperationValues(List<Community> communityList) {
		int datasetCount = size();

		if (datasetCount < 1)
			throw new IllegalStateException("this simulation series group is empty");

		int communityCount = communityList.size();
		double[] averageValues = new double[communityCount];

		for (int communityId = 0; communityId < communityCount; communityId++) {
			averageValues[communityId] = getAverageCommunityCooperationValue(communityList.get(communityId));
		}
		return averageValues;
	}

	/**
	 * Returns the average community cooperation value of all SimulationSeries
	 * 
	 * @param community
	 *            the Community
	 * @return average cooperation value
	 */
	public double getAverageCommunityCooperationValue(Community community) {
		int datasetCount = size();

		if (datasetCount < 1)
			throw new IllegalStateException("this simulation series group is empty");

		if (community.getMembers() == null)
			throw new IllegalArgumentException("community has no memberlist");

		double total = 0.0;
		for (int datasetId = 0; datasetId < datasetCount; datasetId++) {
			total += getSimulationSeries().get(datasetId).getAverageCommunityCooperationValue(community);
		}

		return total / datasetCount;
	}

	///// final

	@JsonIgnore
	public double[] getAverageFinalCooperationValues() {

		int size = seriesList.size();
		double[] values = new double[size];
		for (int i = 0; i < size; i++) {
			values[i] = seriesList.get(i).averageCooperationValue();
		}
		return values;
	}

	@JsonIgnore
	public double[] getAverageFinalPayoffValues() {

		int size = seriesList.size();
		double[] values = new double[size];
		for (int i = 0; i < size; i++) {
			values[i] = seriesList.get(i).averagePayoffValue();
		}
		return values;
	}

	////////// Print //////////

	@Override
	public Table toTable() {

		List<SimulationSeries> simulations = getSimulationSeries();
		Table table = new Table();

		// headline
		TableRow headline = new TableRow();
		headline.add("data").add(simulations.get(0).toHeadLine());
		table.add(headline);

		// average
		TableRow averageLine = new TableRow();
		averageLine.add("average").add(toTableLine());
		table.add(averageLine);

		// series
		for (int i = 0; i < simulations.size(); i++) {
			SimulationSeries series = simulations.get(i);
			table.add(series.toTableLine().addFront(i + 1));
		}
		return table;
	}

	@Override
	public TableRow toTableLine() {

		Evaluation coopEvaluation = getCooperationEvaluation();
		Evaluation payoffEvaluation = getPayoffEvaluation();

		TableRow line = new TableRow();
		line.add(" ").add(" ").add(" ").add(" ").add(" ").add(" ");
		line.add(coopEvaluation.toTableLine());
		line.add(payoffEvaluation.toTableLine());
		return line;
	}




}
