package i5.las2peer.services.cdService.data.network;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Community {

	@Id
	@GeneratedValue
	private long communityId;

	@ManyToOne(fetch = FetchType.LAZY)
	private Cover cover;

	@ElementCollection
	private List<Integer> members;

	@Basic
	private int nodes;

	@Basic
	private int edges;

	@Basic
	double density;

	@Basic
	double averageDegree;

	@Basic
	double clusteringCoefficient;
	
	public Community() {
		
	}
	
	protected Community(Cover cover, ArrayList<Integer> members) {
		this.members = members;
	}

	public List<Integer> getMembers() {
		return this.members;
	}

	public long getCommunityId() {
		return communityId;
	}

	//// Properties ////

	public double getDensity() {

		return this.density;
	}

	public double getAverageDegree() {

		return this.averageDegree;
	}

	public double getSize() {

		return this.nodes;
	}

	public double getClusteringCoefficient() {

		return this.clusteringCoefficient;
	}

}
