package i5.las2peer.services.cdService.data.mapping;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import i5.las2peer.services.cdService.data.network.Community;
import i5.las2peer.services.cdService.data.simulation.DataSet;

@Entity
public class CommunityDataSetMapping {
	
	@Id
	@GeneratedValue
	private long id;

	@ElementCollection
	private List<Double> cooperationValues;

	@ElementCollection
	private List<Double> payoffValues;

	@ManyToOne
	private Community community;

	@ManyToOne
	private DataSet dataSet;

	@Basic
	private double cooperationValue;

	public CommunityDataSetMapping() {

	}
	
	////// Getter //////
	
	@JsonProperty
	public List<Double> getCooperationValues() {
		return cooperationValues;
	}

	@JsonIgnore
	public Community getCommunity() {
		return community;
	}

	@JsonIgnore
	public DataSet getDataSet() {
		return dataSet;
	}

	@JsonProperty
	public double getCooperationValue() {
		return cooperationValue;
	}

	////// Setter //////	
	
	@JsonSetter
	public void setCooperationValues(List<Double> list) {
		this.cooperationValues = list;
	}
	
	@JsonSetter
	public void setCommunity(Community community) {
		this.community = community;
	}
	
	@JsonSetter
	public void setDataSet(DataSet dataSet) {
		this.dataSet = dataSet;
	}
	
	@JsonSetter
	public void setCooperationValue(double cooperationValue) {
		this.cooperationValue = cooperationValue;
	}

}
