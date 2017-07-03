package i5.las2peer.services.cdService.data.network;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import i5.las2peer.services.cdService.data.ConsistencyException;

@Entity
public class Cover {

	@Id
	private long coverId;

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
	
	public List<Community> getCommunities() {

		return this.communities;
	}
	
	protected void setCommunities(List<Community> communities) {
		this.communities = communities;
	}
	

	public Community getCommunity(int communityId) throws ConsistencyException {

		if (communityId < communities.size()) {
			Community community = communities.get(communityId);
			if (communityId != community.getCommunityId()) {
				throw new ConsistencyException();
			}
			return community;
		}
		return null;
	}

	public long getCoverId() {
		return coverId;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public long getGraphId() {
		// TODO Auto-generated method stub
		return 0;
	}

}
