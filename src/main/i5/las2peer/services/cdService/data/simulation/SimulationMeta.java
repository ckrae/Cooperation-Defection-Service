package i5.las2peer.services.cdService.data.simulation;

import java.io.Serializable;

public class SimulationMeta implements Serializable {

	private static final long serialVersionUID = 1L;

	private long seriesId;
	private SimulationParameters parameters;
	private SimulationEvaluation evaluation;

	public SimulationMeta(SimulationSeries series) {
		
		this.setSeriesId(series.getSeriesId());
		this.setParameters(series.getParameters());
		this.setEvaluation(series.getEvaluation());
	}

	public long getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(long seriesId) {
		this.seriesId = seriesId;
	}

	public SimulationParameters getParameters() {
		return parameters;
	}

	public void setParameters(SimulationParameters parameters) {
		this.parameters = parameters;
	}

	public SimulationEvaluation getEvaluation() {
		return evaluation;
	}

	public void setEvaluation(SimulationEvaluation evaluation) {
		this.evaluation = evaluation;
	}
	
	

}
