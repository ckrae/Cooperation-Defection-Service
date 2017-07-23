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
import com.fasterxml.jackson.annotation.JsonProperty;

import i5.las2peer.services.cdService.data.network.Properties;
import i5.las2peer.services.cdService.data.network.PropertyInterface;

@Entity
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
	private Properties properties;
	
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
	public Properties getProperties() {
		return properties;
	}
	
	///// Setter /////
	
	public void setCommunityId(int communityId) {
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
