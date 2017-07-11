package i5.las2peer.services.cdService.data.simulation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * SimulationSeries
 *
 */

@Entity
public class SimulationSeries implements Serializable {

	private static final long serialVersionUID = 1L;

	/////////////// Entity Fields ///////////////

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long seriesId;

	@Basic
	private long userId;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Parameters parameters;

	@Embedded
	private Evaluation evaluation;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn
	private List<DataSet> datasets;

	///////////////// Constructor //////////////////

	public SimulationSeries() {

	}

	public SimulationSeries(Parameters parameters, ArrayList<DataSet> datasets) {

		this.parameters = parameters;		
		this.datasets = datasets;
		
		double[] values = this.getLastCooperationValues();
		this.evaluation = new Evaluation(values);

	}
	
	////////////////// Getter /////////////////////
	
	@JsonProperty
	public long getSeriesId() {
		return seriesId;
	}
	
	@JsonProperty
	public long getUserId() {
		return userId;
	}
	
	@JsonProperty
	public Parameters getParameters() {

		return parameters;
	}
	
	@JsonProperty
	public List<DataSet> getDatasets() {
		return datasets;
	}
	
	@JsonProperty
	public Evaluation getEvaluation() {
		return evaluation;
	}
	
	@JsonIgnore
	public SimulationMeta getSimulationMeta() {		
		return new SimulationMeta(this);		
	}
		
	public void setSeriesId(long seriesId) {
		this.seriesId = seriesId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	/////////////////// Methods ///////////////////////
	
	public double[] getLastCooperationValues() {

		int size = datasets.size();
		double[] values = new double[size];
		for (int i = 0; i < size; i++) {
			values[i] = datasets.get(i).getLastCooperationValue();
		}
		return values;
	}

	////////////// Print Data /////////////
	
	public String toTable() {
		
		List<DataSet> datasets = getDatasets();		
		StringBuilder table = new StringBuilder();
		
		// headline
		table.append("data").append("\t");
		table.append(datasets.get(0).toHeadLine());
		table.append("\n");
		
		//average
		table.append("average").append("\t");
		table.append("final#C").append("\t");
		table.append(getEvaluation().toTableLine()).append("\t");
		
		table.append("\n");
		//datasets
		for(int i=0; i<datasets.size(); i++) {
			
			DataSet data = datasets.get(i);
			table.append(i).append("\t");
			table.append(data.toTableLine());
			table.append("\n");			
		}		
		return table.toString();	
	}
	
	public String toTableLine() {
		
		StringBuilder line = new StringBuilder();
		
		return line.toString();
	}

	
}
