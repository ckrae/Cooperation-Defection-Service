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
import com.fasterxml.jackson.annotation.JsonProperty;

import i5.las2peer.services.cdService.data.util.Table;
import i5.las2peer.services.cdService.data.util.TableRow;

@Entity
public class SimulationSeriesGroup extends SimulationAbstract {

	////////// Entity Fields //////////

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
	public List<Long> getNetworkIds() {

		List<Long> networkIds = new ArrayList<Long>();
		for (SimulationSeries series : seriesList) {
			networkIds.add(series.getParameters().getGraphId());
		}
		return networkIds;
	}

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
	
	public void add(SimulationSeries series) {
		this.seriesList.add(series);
	}
	
	public void setName(String name) {
		this.name = name;
	}

	/////////////////// Methods ///////////////////////

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

	///// final

	@JsonIgnore
	public double[] getFinalCooperationValues() {

		int size = seriesList.size();
		double[] values = new double[size];
		for (int i = 0; i < size; i++) {
			values[i] = seriesList.get(i).getCooperationEvaluation().getAverageValue();
		}
		return values;
	}

	@JsonIgnore
	public double[] getFinalPayoffValues() {

		int size = seriesList.size();
		double[] values = new double[size];
		for (int i = 0; i < size; i++) {
			values[i] = seriesList.get(i).getPayoffEvaluation().getAverageValue();
		}
		return values;
	}

	///// over time

	@JsonIgnore
	public List<Double> getCooperationValues() {

		int size = generations();
		List<Double> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			for (SimulationSeries simulation : seriesList) {
				list.add(simulation.getCooperationValues().get(i));
			}
		}
		return list;
	}

	@JsonIgnore
	public List<Double> getPayoffValues() {

		int size = generations();
		List<Double> list = new ArrayList<>(size);
		for (int i = 0; i < size; i++) {
			for (SimulationSeries simulation : seriesList) {
				list.add(simulation.getPayoffValues().get(i));
			}
		}
		return list;
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
		
		//average
		TableRow averageLine = new TableRow();
		averageLine.add("average").add(toTableLine());
		table.add(averageLine);
		
		//series
		for (int i = 0; i < simulations.size(); i++) {
			SimulationSeries series = simulations.get(i);
			table.add(series.toTableLine().addFront(i+1));
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
