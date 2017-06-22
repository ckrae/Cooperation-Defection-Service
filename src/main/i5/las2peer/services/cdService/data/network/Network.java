package i5.las2peer.services.cdService.data.network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import i5.las2peer.services.cdService.simulation.Agent;
import sim.util.Bag;

public class Network extends sim.field.network.Network {

	private static final long serialVersionUID = 1L;

	private long networkId;
	private final long ocdsId;
	private String graphName;
	private HashSet<Long> series;
	private ArrayList<Cover> covers;

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

	public ArrayList<Cover> getCovers() {
		return covers;
	}

	public void addCover(ArrayList<Cover> covers) {
		this.covers = covers;
	}

	public String getGraphName() {
		return graphName;
	}

	public void setGraphName(String graphName) {
		this.graphName = graphName;
	}
		

	public ArrayList<Agent> getAgents(ArrayList<Long> nodes) {

		Bag agents = new Bag(this.getAllNodes());
		int size = nodes.size();
		ArrayList<Agent> result = new ArrayList<Agent>(size);
		for (int i = 0; i < size; i++) {
			result.add((Agent) agents.get((nodes.get(i).intValue())));
		}
		return result;

	}
	
	public sim.field.network.Network getSubNetwork(ArrayList<Long> nodes) {
		
		sim.field.network.Network subNetwork = new sim.field.network.Network(false);
		Bag bag = new Bag(this.getAllNodes());
		
		for(int i=0, si=nodes.size(); i<si; i++) {
			Agent node = (Agent) bag.get(nodes.get(i).intValue());
			subNetwork.addNode(node);
		}
		
		for(int i=0, si=nodes.size(); i<si; i++) {
			Agent node = (Agent) bag.get(nodes.get(i).intValue());
			subNetwork.addNode(node);
		}
		
		
		return null;		
		
	}
}
