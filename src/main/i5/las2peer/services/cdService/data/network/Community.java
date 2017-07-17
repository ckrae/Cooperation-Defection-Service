package i5.las2peer.services.cdService.data.network;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Community implements PropertyInterface {
	
	///// Entity Fields /////
	
	@Id
	@GeneratedValue
	private long communityId;

	@ManyToOne(fetch = FetchType.LAZY)
	private Cover cover;

	@ElementCollection
	private List<Integer> members;

	@Embedded
	private Properties properties;
	
	public Community() {
		
	}
	
	///// Getter /////
	
	@JsonProperty
	public long getCommunityId() {
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
	public Properties getProperties() {
		return properties;
	}
	
	///// Setter /////
	
	public void setCommunityId(long communityId) {
		this.communityId = communityId;
	}

	public void setCover(Cover cover) {
		this.cover = cover;
	}

	public void setMembers(List<Integer> members) {
		this.members = members;
	}

	@Override
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	


	

}
