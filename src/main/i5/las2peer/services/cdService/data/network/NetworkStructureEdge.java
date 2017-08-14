package i5.las2peer.services.cdService.data.network;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class NetworkStructureEdge {
	
	@Id
	@GeneratedValue
	private int primaryId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	NetworkStructureNode from;
	
	@ManyToOne(cascade = CascadeType.ALL)
	NetworkStructureNode to;
		
	public NetworkStructureEdge() {

	}
	
	public NetworkStructureEdge(NetworkStructureNode from, NetworkStructureNode to) {
		this.from = from;
		this.to= to;
	}
	
	public NetworkStructureNode getFrom() {
		return from;
	}
	
	public NetworkStructureNode getTo() {
		return to;
	}
	
}
