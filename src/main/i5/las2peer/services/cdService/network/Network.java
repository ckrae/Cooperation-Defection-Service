package i5.las2peer.services.cdService.network;

import java.util.HashMap;
import java.util.HashSet;

public class Network extends sim.field.network.Network {

	private static final long serialVersionUID = 1L;

	private long networkId;
	private final long ocdsId;
	private String graphName;
	private HashSet<Long> series;
	private HashMap<Integer, Cover> covers;

	public Network(long ocdsId) {

		super(false);

		this.networkId = ocdsId;
		this.ocdsId = ocdsId;
		this.series = new HashSet<Long>();		
	}

	public long getNetworkId() {
		return this.networkId;
	}

	public long getocdId() {
		return this.ocdsId;
	}

	public HashSet<Long> getSeries() {
		return series;
	}

	public void addSeries(long seriesId) {
		series.add(seriesId);
	}

	public HashMap<Integer, Cover> getCovers() {
		return covers;
	}

	public void addCover(HashMap<Integer, Cover> covers) {
		this.covers = covers;
	}

	public String getGraphName() {
		return graphName;
	}

	public void setGraphName(String graphName) {
		this.graphName = graphName;
	}
	

}
