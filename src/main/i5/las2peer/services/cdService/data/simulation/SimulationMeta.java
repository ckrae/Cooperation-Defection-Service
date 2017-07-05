package i5.las2peer.services.cdService.data.simulation;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;


public class SimulationMeta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	long seriesId;	

	private Parameters parameters;	

	private Evaluation evaluation;
	
	public SimulationMeta() {
		
	}
	
	public SimulationMeta(SimulationSeries series) {
		
		this.seriesId = series.getSeriesId();
		this.parameters = series.getParameters();
		this.evaluation = series.getEvaluation();
	}
	
	@JsonProperty
	public long getSeriesId() {
		return seriesId;
	}	
	
	@JsonSetter
	public void setSeriesId(long seriesId) {
		this.seriesId = seriesId;
	}
	
	@JsonProperty
	public Parameters getParameters() {
		return parameters;
	}
	
	@JsonSetter
	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}
	
	@JsonProperty
	public Evaluation getEvaluation() {
		return evaluation;
	}
	
	@JsonSetter
	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}
	
	

}
