package i5.las2peer.services.cdService.network;

import java.util.ArrayList;

public class Community {

	ArrayList<Long> members;
	private final long coverId;
	private final long communityId;

	public Community(long coverId, long communityId, ArrayList<Long> arrayList) {

		this.members = arrayList;
		this.coverId = coverId;
		this.communityId = communityId;
	}

	public ArrayList<Long> getMembers() {

		return this.members;
	}

	public long getCoverId() {
		return coverId;
	}

	public long getCommunityId() {
		return communityId;
	}
}
