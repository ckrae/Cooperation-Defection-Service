package i5.las2peer.services.cdService.data.simulation;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * SimulationSeries
 * 
 * Container for all SimulationData of the same game series. identifier
 * SERVICE_PREFIX + UserId + # + SeriesId
 */

public class SimulationSeries implements Serializable {

	/////////////// Attributes ///////////////

	private static final long serialVersionUID = 1L;

	private long seriesId;
	private long userId;
	private final SimulationParameters parameters;
	private final SimulationEvaluation evaluation;
	private final ArrayList<SimulationData> datasets;

	////////////////////////////////////////////

	public SimulationSeries(SimulationParameters parameters,
			ArrayList<SimulationData> datasets) {

		this.parameters = parameters;
		this.datasets = datasets;
		this.evaluation = new SimulationEvaluation(this);

	}
	
	public double[] getLastCooperationValues() {
		
		int size = datasets.size();
		double[] values = new double[size];
		for (int i=0; i<size; i++) {
			values[i] = datasets.get(i).getLastCooperationValue();
		}
		return values;
	}

	public SimulationParameters getParameters() {

		return parameters;
	}	

	public ArrayList<SimulationData> getDatasets() {

		return datasets;
	}

	public long getSeriesId() {
		return seriesId;
	}
	
	public void setSeriesId(long seriesId) {
		this.seriesId = seriesId;
	}

	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public void setUserId() {
		this.userId = userId;
	}

	public SimulationEvaluation getEvaluation() {
		return evaluation;
	}

}
