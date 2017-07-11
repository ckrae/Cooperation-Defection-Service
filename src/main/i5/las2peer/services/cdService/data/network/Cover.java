package i5.las2peer.services.cdService.data.network;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
public class Cover {

	@Id
	@GeneratedValue
	private long coverId;
	
	@Basic
	private long ocdId;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn
	private List<Community> communities;

	@OneToOne(fetch = FetchType.LAZY)
	private Graph network;

	@Basic
	private String algorithm;
	
	public Cover() {
		
	}
	
	public Cover(long coverId, String algorithm) {

		this.coverId = coverId;
		this.algorithm = algorithm;
	}
	
	///// Getter /////
	
	@JsonProperty
	public long getCoverId() {
		return coverId;
	}
	
	@JsonProperty
	public long getOcdId() {
		return this.ocdId;
	}
	
	@JsonProperty
	public List<Community> getCommunities() {
		return this.communities;
	}
	
	@JsonProperty
	public String getAlgorithm() {
		return this.algorithm;
	}
	
	///// Setter /////
	
	@JsonSetter
	public void setOcdId(long id) {
		this.ocdId = id;
	}
	
	@JsonSetter
	protected void setCommunities(List<Community> communities) {
		this.communities = communities;
	}
	
	@JsonSetter
	protected void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}
	
}
