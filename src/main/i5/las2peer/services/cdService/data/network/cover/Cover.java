package i5.las2peer.services.cdService.data.network.cover;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import i5.las2peer.services.cdService.data.network.NetworkMeta;

@Entity
public class Cover {
	
	///// Entity Fields /////
	
	@Id
	@GeneratedValue
	private long coverId;
	
	@Basic
	private long ocdId;
	
	@Enumerated
	private CoverOrigin origin;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn
	private List<Community> communities;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private NetworkMeta network;

	@Enumerated
	private AlgorithmType algorithm;
	
	///// Constructor /////
	
	public Cover() {
		
	}
	
	public Cover(long coverId, AlgorithmType algorithm) {

		this.coverId = coverId;
		this.algorithm = algorithm;
	}
	
	///// Getter /////
	
	@JsonProperty
	public long getCoverId() {
		return coverId;
	}
	
	@JsonProperty
	public long getOriginId() {
		return this.ocdId;
	}
	
	@JsonProperty
	public List<Community> getCommunities() {
		return this.communities;
	}
	
	@JsonIgnore
	public NetworkMeta getNetwork() {
		if(network == null)
			return null;
		return this.network;
	}
	
	
	@JsonIgnore
	public AlgorithmType getAlgorithmType() {
		if(algorithm == null)
			algorithm = AlgorithmType.UNKNOWN;
		return this.algorithm;
	}
	
	@JsonProperty
	public String getAlgorithm() {
		return getAlgorithmType().toString();
	}
	
	///// Setter /////
	
	@JsonSetter
	public void setOriginId(long id) {
		this.ocdId = id;
	}
	
	@JsonIgnore
	public void setNetwork(NetworkMeta network) {
		this.network = network;
	}
	
	@JsonSetter
	protected void setCommunities(List<Community> communities) {
		this.communities = communities;
	}
	
	@JsonIgnore
	public void setAlgorithmType(AlgorithmType algorithm) {
		this.algorithm = algorithm;
	}
	
	@JsonSetter
	protected void setAlgorithm(String algorithm) {
		setAlgorithmType(AlgorithmType.valueOf(algorithm));
	}
	
}
