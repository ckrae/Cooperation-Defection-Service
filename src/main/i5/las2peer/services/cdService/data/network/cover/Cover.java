package i5.las2peer.services.cdService.data.network.cover;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import i5.las2peer.services.cdService.data.network.NetworkMeta;
import i5.las2peer.services.cdService.data.util.table.TableLineInterface;
import i5.las2peer.services.cdService.data.util.table.TableRow;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Cover implements TableLineInterface {
	
	///// Entity Fields /////
	
	@Id
	@GeneratedValue
	private long coverId;
		
	@Basic
	private long ocdId;
	
	@Basic
	private String name;
	
	@Enumerated
	private CoverOrigin origin;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn
	private List<Community> communities;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn
	private NetworkMeta network;

	@Enumerated
	private AlgorithmType algorithm;
	
	@Embedded
	private CoverProperties properties;
	
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
	public String getName() {
		return this.name;
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
	
	@JsonProperty
	public CoverProperties getProperties() {
		return this.properties;
	}
	
	///// Setter /////
	
	@JsonSetter
	public void setProperties(CoverProperties properties) {
		this.properties = properties;
	}
	
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
	
	@JsonIgnore
	public Community getCommunity(int id) {
		return getCommunities().get(id);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	//////
	
	public int communityCount() {
		return this.getCommunities().size();
	}
	
	///// Print /////
	
	@Override
	public TableRow toTableLine() {
		TableRow line = new TableRow();
		line.add(algorithm.humanRead()).add(getProperties().toTableLine());
		return line;
	}
	
	public TableRow toHeadLine() {
		
		TableRow line = new TableRow();
		line.add("algorithm").add(getProperties().toHeadLine());
		return line;
		
	}


	
}
