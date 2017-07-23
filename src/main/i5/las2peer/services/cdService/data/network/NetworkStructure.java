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
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
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

	public int addNode() {

		int nodeId = getNodes().size();
		NetworkStructureNode node = new NetworkStructureNode(nodeId);
		nodes.add(nodeId, node);
		return nodeId;
	}

	public NetworkStructureNode getNode(int nodeId) {
		return getNodes().get(nodeId);
	}
	
	///// Edge /////
	
	public int edgeCount() {
		return getEdges().size();
	}

	public NetworkStructureEdge addEdge(int id1, int id2) {

		if (id1 == id2)
			throw new IllegalArgumentException("no loop allowed");

		if (getNodes().get(id1).equals(id1) || getNodes().get(id2).equals(id2))
			throw new IllegalArgumentException("Nodes not contained");

		NetworkStructureEdge edge = getEdge(getNode(id1), getNode(id2));
		if (edge != null)
			throw new IllegalArgumentException("Edge already exists.");

		edge = new NetworkStructureEdge(getNode(id1), getNode(id2));
		edges.add(edge);
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

		NetworkStructureBuilder<Integer> networkBuilder = new NetworkStructureBuilder<Integer>();
		List<NetworkStructureEdge> edgeList = getEdges(nodeIds);

		for (NetworkStructureEdge edge : edgeList) {
			networkBuilder.addEdge(edge.getFrom().getId(), edge.getTo().getId());
		}
		return networkBuilder.build();
	}
	
	public List<Integer> toEdgeList() {
		List<Integer> edgeList = new ArrayList<>();
		return edgeList;
	}

}
