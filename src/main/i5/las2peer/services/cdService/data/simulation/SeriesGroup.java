package i5.las2peer.services.cdService.data.simulation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class SeriesGroup implements Serializable {

	private static final long serialVersionUID = 1L;

	////////// Entity Fields //////////

	@Id
	@GeneratedValue
	private long groupId;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn
	private List<SimulationSeries> seriesList;

	////////// Constructor //////////

	public SeriesGroup() {

	}

	public SeriesGroup(List<SimulationSeries> list) {
		this.seriesList = list;
	}

	////////// Getter //////////

	public List<SimulationSeries> getSimulationSeries() {
		return this.seriesList;
	}

	public List<Long> getNetworkIds() {

		List<Long> networkIds = new ArrayList<Long>();
		for (SimulationSeries series : seriesList) {
			networkIds.add(series.getParameters().getGraphId());
		}
		return networkIds;
	}

	public List<SimulationMeta> getSimulationMeta() {
		int size = seriesList.size();
		List<SimulationMeta> metaList = new ArrayList<SimulationMeta>(size);
		for (int i = 0; i < size; i++) {
			metaList.add(new SimulationMeta(seriesList.get(i)));
		}
		return metaList;
	}
}
