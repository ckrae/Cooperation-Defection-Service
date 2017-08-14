package i5.las2peer.services.cdService.data.network;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class NetworkStructureNode {
	
	@Id
	@GeneratedValue
	private int primaryId;
	
	@Basic
	private int nodeId;
	
	public NetworkStructureNode() {

	}
	
	public NetworkStructureNode(int id) {
		this.nodeId = id;
	}
	
	@JsonGetter
	public int getId() {
		return nodeId;
	}
	
	@JsonSetter
	public void setId(int nodeId) {
		this.nodeId = nodeId;
	}

}
