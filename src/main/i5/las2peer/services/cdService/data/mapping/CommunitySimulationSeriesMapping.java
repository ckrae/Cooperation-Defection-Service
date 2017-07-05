package i5.las2peer.services.cdService.data.mapping;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import i5.las2peer.services.cdService.data.simulation.SimulationSeries;

@Entity
public class CommunitySimulationSeriesMapping {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	SimulationSeries series;

	@OneToMany(fetch = FetchType.LAZY)
	private List<CommunityDataSetMapping> mappings;
	
	@Basic
	private double cooperationValue;

	public CommunitySimulationSeriesMapping() {

	}
	
	////// Getter //////
	
	@JsonIgnore
	public SimulationSeries getSeries() {
		return series;
	}
	
	@JsonProperty
	public List<CommunityDataSetMapping> getMappings() {
		return mappings;
	}
	
	@JsonProperty
	public double getCooperationValue() {
		return cooperationValue;
	}
	
	///// Setter /////
	
	public void setSeries(SimulationSeries series) {
		this.series = series;
	}

	public void setMappings(List<CommunityDataSetMapping> list) {
		this.mappings = list;
	}

	public void setCooperationValue(double cooperationValue) {
		this.cooperationValue = cooperationValue;
	}


}
