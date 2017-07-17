package i5.las2peer.services.cdService.data.simulation;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import i5.las2peer.services.cdService.data.util.Table;
import i5.las2peer.services.cdService.data.util.TableRow;
import sim.field.network.Network;

/**
 * SimulationSeries
 *
 */

@Entity
public class SimulationSeries extends SimulationAbstract {

	/////////////// Entity Fields ///////////////

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long seriesId;

	@Basic
	private long userId;

	@Basic
	private String name;

	@Basic
	private int generations;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Parameters parameters;

	@Embedded
	private Evaluation cooperationEvaluation;

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

	@JsonProperty
	public long getSeriesId() {
		return seriesId;
	}

	@JsonIgnore
	@Override
	public long getId() {
		return seriesId;
	}

	@JsonIgnore
	public long getUserId() {
		return userId;
	}

	@JsonProperty
	public String getName() {
		if (name == null)
			return String.valueOf(getId());
		return name;
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
		if (cooperationEvaluation == null)
			cooperationEvaluation = new Evaluation(getFinalCooperationValues());
		return cooperationEvaluation;
	}

	@JsonProperty
	public Evaluation getPayoffEvaluation() {
		if (payoffEvaluation == null)
			payoffEvaluation = new Evaluation(getFinalPayoffValues());
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

	public void setSeriesId(long seriesId) {
		this.seriesId = seriesId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public void setName(String name) {
		this.name = name;
	}

	/////////////////// Methods ///////////////////////

	public int size() {
		return simulationDatasets.size();
	}

	public int generations() {

			int maxSize = 0;
			for (SimulationDataset dataset : simulationDatasets) {
				if (dataset.getCooperationValues().size() > maxSize)
					maxSize = dataset.getCooperationValues().size();
			}			
			return maxSize;	
	}

	public void normalize() {
		int maxSize = generations();
		for (SimulationDataset dataset : simulationDatasets) {
			dataset.fill(maxSize);
		}
	}

	///// Final

	@JsonIgnore
	public double[] getFinalCooperationValues() {

		int size = size();
		double[] values = new double[size];
		for (int i = 0; i < size; i++) {
			values[i] = simulationDatasets.get(i).getFinalCooperationValue();
		}
		return values;
	}

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
	public List<Double> getCooperationValues() {

		int size = generations();
		List<Double> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			for (SimulationDataset dataset : simulationDatasets) {
				list.add(dataset.getCooperationValues().get(i));
			}
		}
		return list;
	}

	@JsonIgnore
	public List<Double> getPayoffValues() {

		int size = generations();
		List<Double> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			for (SimulationDataset dataset : simulationDatasets) {
				list.add(dataset.getPayoffValues().get(i));
			}
		}
		return list;
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
			table.add(data.toTableLine().addFront(i+1));
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
