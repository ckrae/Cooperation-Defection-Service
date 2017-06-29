package i5.las2peer.services.cdService.data.simulation;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class SimulationMeta implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@OneToOne(fetch = FetchType.LAZY)
	SimulationSeries series;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Parameters parameters;
	
	@OneToOne(fetch = FetchType.LAZY)
	private Evaluation evaluation;

	public SimulationMeta(SimulationSeries series) {
		
		this.series = series;
		this.parameters = series.getParameters();
		this.evaluation = series.getEvaluation();
	}

	public SimulationSeries getSeries() {
		return series;
	}

	public void setSeries(SimulationSeries series) {
		this.series = series;
	}

	public Parameters getParameters() {
		return parameters;
	}

	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}

	public Evaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(Evaluation evaluation) {
		this.evaluation = evaluation;
	}
	
	

}
