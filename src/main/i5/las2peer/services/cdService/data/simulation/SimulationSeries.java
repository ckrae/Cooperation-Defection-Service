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
	private final ArrayList<SimulationData> datasets;

	////////////////////////////////////////////

	public SimulationSeries(long seriesId, long userId, SimulationParameters parameters,
			ArrayList<SimulationData> datasets) {

		this.parameters = parameters;
		this.datasets = datasets;
		this.seriesId = seriesId;
		this.userId = userId;

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

	public long getUserId() {
		return userId;
	}

}
