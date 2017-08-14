package i5.las2peer.services.cdService.data.mapping;

import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import i5.las2peer.services.cdService.data.network.PropertyType;
import i5.las2peer.services.cdService.data.network.cover.Community;
import i5.las2peer.services.cdService.data.network.cover.Cover;
import i5.las2peer.services.cdService.data.simulation.SimulationSeriesGroup;
import i5.las2peer.services.cdService.data.util.table.Table;
import i5.las2peer.services.cdService.data.util.table.TableRow;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoverSimulationGroupMapping extends MappingAbstract {

	///// Entity Fields /////

	@ManyToOne(fetch = FetchType.LAZY)
	private Cover cover;

	@ManyToOne(fetch = FetchType.LAZY)
	private SimulationSeriesGroup simulation;

	///// Getter /////
		
	@JsonIgnore
	public Cover getCover() {
		return cover;
	}

	@JsonIgnore
	public SimulationSeriesGroup getSimulation() {
		return simulation;
	}
	///// Setter /////
	
	@JsonIgnore
	public void setCover(Cover cover) {
		this.cover = cover;
	}

	@JsonIgnore
	public void setSimulation(SimulationSeriesGroup simulation) {
		this.simulation = simulation;
	}
	
	///// Methods /////
		
	@Override
	@JsonProperty
	public double[] getPropertyValues(PropertyType property) {
			
		int size = getCover().communityCount();
		double[] properties = new double[size];
		
		for(int i=0; i<size; i++) {
			properties[i] = getCover().getCommunity(i).getProperty(property);
		}
		return properties;
	}
	
	@Override
	@JsonProperty
	public double[] getCooperationValues() {
		
		List<Community> communityList = getCover().getCommunities();
		double[] values = getSimulation().getAverageCommunityCooperationValues(communityList);
		
		return values;		
	}	

	///// Print /////
	
	public void printSystem() {
		Correlation correlation = getSizeCorrelation();
		System.out.println(" &" + correlation.getPearson() + " &" + correlation.getSpearman() + " &" + correlation.getKendall() + " &" + correlation.getWilcoxon() + " &" + correlation.getMannWhitneyU());
		correlation = getDensityCorrelation();
		System.out.println(" &" + correlation.getPearson() + " &" + correlation.getSpearman() + " &" + correlation.getKendall() + " &" + correlation.getWilcoxon() + " &" + correlation.getMannWhitneyU());
		correlation = getDensityCorrelation();
		System.out.println(" &" + correlation.getPearson() + " &" + correlation.getSpearman() + " &" + correlation.getKendall() + " &" + correlation.getWilcoxon() + " &" + correlation.getMannWhitneyU());
		correlation = getDensityCorrelation();
		System.out.println(" &" + correlation.getPearson() + " &" + correlation.getSpearman() + " &" + correlation.getKendall() + " &" + correlation.getWilcoxon() + " &" + correlation.getMannWhitneyU());

	}
	
	@Override
	public TableRow toTableLine() {

		TableRow line = new TableRow();
		line.add(getSizeCorrelation().toTableLine()).add(getDensityCorrelation().toTableLine()).add(getAverageDegreeCorrelation().toTableLine()).add(getDegreeDeviationCorrelation().toTableLine());
		return line;
	}

	@Override
	public Table toTable() {
		// TODO Auto-generated method stub
		return null;
	}




}
