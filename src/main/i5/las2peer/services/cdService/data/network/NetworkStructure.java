package i5.las2peer.services.cdService.data.network;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class NetworkStructure {

	///// Entity Fields /////

	@Id
	@GeneratedValue
	private int primaryId;

	@OneToOne(cascade = CascadeType.ALL)
	NetworkMeta networkMeta;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<NetworkStructureNode> nodes;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<NetworkStructureEdge> edges;

	///// Constructor /////

	public NetworkStructure() {

	}

	public NetworkStructure(int size) {
		for (int i = 0; i < size; i++) {
			this.addNode();
		}
	}

	///// Getter /////

	public NetworkMeta getNetworkMeta() {
		return networkMeta;
	}

	public List<NetworkStructureEdge> getEdges() {
		if (edges == null)
			edges = new ArrayList<>();

		return edges;
	}

	public List<NetworkStructureNode> getNodes() {
		if (nodes == null)
			nodes = new ArrayList<>();

		return nodes;
	}

	///// Nodes /////

	public int nodeCount() {
		return getNodes().size();
	}

	public NetworkStructureNode getNode(int nodeId) {
		
		if(nodeId < 0)
			throw new IllegalArgumentException("invalid nodeId");
		
		return getNodes().get(nodeId);
	}

	protected int addNode() {
		int nodeId = getNodes().size();
		NetworkStructureNode node = new NetworkStructureNode(nodeId);
		getNodes().add(nodeId, node);
		return nodeId;
	}

	///// Edge /////

	public int edgeCount() {
		return getEdges().size();
	}

	public NetworkStructureEdge addEdge(int id1, int id2) {

		if (id1 == id2)
			throw new IllegalArgumentException("no loop allowed");

		if (id1 < 0 || id2 < 0)
			throw new IllegalArgumentException("no negative ids");

		if (nodeCount() < id1 || nodeCount() < id2)
			throw new IllegalArgumentException("node ids to high");

		NetworkStructureEdge edge = getEdge(getNode(id1), getNode(id2));
		if (edge != null)
			throw new IllegalArgumentException("Edge already exists.");

		edge = new NetworkStructureEdge(getNode(id1), getNode(id2));
		getEdges().add(edge);
		return edge;
	}

	public NetworkStructureEdge getEdge(NetworkStructureNode from, NetworkStructureNode to) {

		for (NetworkStructureEdge edge : getEdges()) {
			if ((edge.getFrom().equals(from) && edge.getTo().equals(to))
					|| (edge.getFrom().equals(to) && edge.getTo().equals(from))) {
				return edge;
			}
		}
		return null;
	}

	///// Methods /////

	public List<NetworkStructureEdge> getEdges(int nodeId) {

		List<NetworkStructureEdge> edgeList = new ArrayList<>();
		for (NetworkStructureEdge edge : edges) {
			if (edge.getFrom().getId() == nodeId || edge.getTo().getId() == nodeId) {
				edgeList.add(edge);
			}
		}

		return edgeList;
	}

	public List<NetworkStructureEdge> getEdges(List<Integer> nodeIds) {

		List<NetworkStructureEdge> edgeList = new ArrayList<>();
		for (NetworkStructureEdge edge : edges) {
			if (nodeIds.contains(edge.getFrom().getId()) && nodeIds.contains(edge.getTo().getId())) {
				edgeList.add(edge);
			}
		}

		return edgeList;
	}

	public NetworkStructure getSubNetwork(List<Integer> nodeIds) {

		NetworkStructureBuilder networkStructureBuilder = new NetworkStructureBuilder();
		List<NetworkStructureEdge> edgeList = getEdges(nodeIds);

		for (NetworkStructureEdge edge : edgeList) {
			networkStructureBuilder.addEdge(edge.getFrom().getId(), edge.getTo().getId());
		}
		return networkStructureBuilder.build();
	}

	public List<Integer> toEdgeList() {
		List<Integer> edgeList = new ArrayList<>();
		return edgeList;
	}

}
