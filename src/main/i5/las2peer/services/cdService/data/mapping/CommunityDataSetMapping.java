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

import i5.las2peer.services.cdService.data.network.cover.Community;
import i5.las2peer.services.cdService.data.simulation.SimulationDataset;
import i5.las2peer.services.cdService.data.util.Table;
import i5.las2peer.services.cdService.data.util.TableRow;

@Entity
public class CommunityDataSetMapping extends MappingAbstract {
	
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
	private SimulationDataset dataSet;

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
	public SimulationDataset getDataSet() {
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
	public void setDataSet(SimulationDataset dataSet) {
		this.dataSet = dataSet;
	}
	
	@JsonSetter
	public void setCooperationValue(double cooperationValue) {
		this.cooperationValue = cooperationValue;
	}

	@Override
	public TableRow toTableLine() {		

		TableRow line = new TableRow();		
		line.add("");		
		return line;
	}

	@Override
	public Table toTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String tableName() {
		// TODO Auto-generated method stub
		return null;
	}

}
