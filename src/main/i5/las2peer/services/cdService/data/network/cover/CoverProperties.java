package i5.las2peer.services.cdService.data.network.cover;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import i5.las2peer.services.cdService.data.network.Properties;
import i5.las2peer.services.cdService.data.network.PropertyType;
import i5.las2peer.services.cdService.data.util.table.TableLineInterface;
import i5.las2peer.services.cdService.data.util.table.TableRow;

@Embeddable
public class CoverProperties extends Properties implements TableLineInterface {

	///// Entity Fields /////

	@Basic
	private double totalCommunities;

	@Basic
	private double averageCommunitySize;

	@Basic
	private double CommunitySizeDeviation;

	///// Getter /////
	
	public double getTotalCommunities() {
		return totalCommunities;
	}

	public double getAverageCommunitySize() {
		return averageCommunitySize;
	}

	public double getCommunitySizeDeviation() {
		return CommunitySizeDeviation;
	}

	///// Setter /////

	public void setTotalCommunities(double totalCommunities) {
		this.totalCommunities = totalCommunities;
	}

	public void setAverageCommunitySize(double averageCommunitySize) {
		this.averageCommunitySize = averageCommunitySize;
	}

	public void setCommunitySizeDeviation(double communitySizeDeviation) {
		CommunitySizeDeviation = communitySizeDeviation;
	}

	///// Methods /////

	@Override
	@JsonIgnore
	public double getProperty(PropertyType property) {

		switch (property) {
		case COMMUNITIES_TOTAL:
			return getTotalCommunities();

		case COMMUNITY_SIZE_AVERAGE:
			return getAverageCommunitySize();

		case COMMUNITY_SIZE_DEVIATION:
			return getCommunitySizeDeviation();		

		default:
			break;
		}
		return 0.0;
	}

	/////////// Print ///////////

	@Override
	public TableRow toTableLine() {

		TableRow line = new TableRow();
		line.add(getTotalCommunities()).add(getAverageCommunitySize()).add(getCommunitySizeDeviation());
		return line;
	}

	public TableRow toHeadLine() {

		TableRow line = new TableRow();
		line.add("Communities").add("Average Community Size").add("Community Size Deviation");
		return line;

	}

}
