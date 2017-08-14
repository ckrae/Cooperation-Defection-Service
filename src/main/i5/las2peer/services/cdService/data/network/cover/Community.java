package i5.las2peer.services.cdService.data.network.cover;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import i5.las2peer.services.cdService.data.network.NetworkProperties;
import i5.las2peer.services.cdService.data.network.PropertyInterface;
import i5.las2peer.services.cdService.data.network.PropertyType;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Community implements PropertyInterface {
	
	///// Entity Fields /////
	
	@Id
	@GeneratedValue
	private long Id;
	
	@Basic
	private int communityId;

	@ManyToOne(fetch = FetchType.LAZY)
	private Cover cover;

	@ElementCollection
	private List<Integer> members;

	@Embedded
	private NetworkProperties networkProperties;
	
	public Community() {
		
	}
	
	///// Getter /////
	
	@JsonIgnore
	public long getId() {
		return Id;
	}
	
	@JsonProperty
	public int getCommunityId() {
		return communityId;
	}
	
	@JsonIgnore
	public Cover getCover() {
		return cover;
	}
	
	@JsonProperty
	public List<Integer> getMembers() {
		return members;
	}
	
	@Override
	@JsonProperty
	public NetworkProperties getProperties() {
		return networkProperties;
	}
	
	@JsonIgnore
	public double getProperty(PropertyType property) {
		return getProperties().getProperty(property);
		
	}
	
	///// Setter /////
	
	@JsonSetter
	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}
	
	@JsonSetter
	public void setCover(Cover cover) {
		this.cover = cover;
	}
	
	@JsonSetter
	public void setMembers(List<Integer> members) {
		this.members = members;
	}

	@Override
	@JsonSetter
	public void setProperties(NetworkProperties networkProperties) {
		this.networkProperties = networkProperties;
	}
	


	

}
