package i5.las2peer.services.cdService.data.network;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class NetworkStructureNode {
	
	@Id
	@GeneratedValue
	private int primaryId;
	
	@Basic
	private int innerId;
	
	public NetworkStructureNode() {

	}
	
	public NetworkStructureNode(int id) {
		this.innerId = id;
	}
	
	public int getId() {
		return innerId;
	}

}
