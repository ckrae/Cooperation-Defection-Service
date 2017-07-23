package i5.las2peer.services.cdService.data.mapping;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import i5.las2peer.services.cdService.data.network.cover.Cover;
import i5.las2peer.services.cdService.data.util.Table;
import i5.las2peer.services.cdService.data.util.TableRow;

@Entity
public class CoverSimulationSeriesMapping extends MappingAbstract {

	///// Entity Fields /////

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Cover cover;

	@OneToMany(fetch = FetchType.LAZY)
	private List<CommunitySimulationSeriesMapping> mappings;

	@ElementCollection
	List<Double> cooperationValues;

	@Embedded
	Correlation correlation;

	///// Constructor /////

	public CoverSimulationSeriesMapping() {

	}

	///// Getter /////

	@JsonProperty
	public Cover getCover() {
		return cover;
	}

	@JsonProperty
	public List<CommunitySimulationSeriesMapping> getMappings() {
		return mappings;
	}

	@JsonIgnore
	public List<Double> getCooperationValues() {
		return cooperationValues;
	}

	@JsonProperty
	public Correlation getCorrelation() {
		if(correlation == null)
			correlation = new Correlation();
		return correlation;
	}	

	///// Setter /////

	public void setCover(Cover cover) {
		this.cover = cover;
	}

	public void setMappings(List<CommunitySimulationSeriesMapping> mappings) {
		this.mappings = mappings;
	}

	public void setCooperationValues(List<Double> cooperationValues) {
		this.cooperationValues = cooperationValues;
	}

	///// Methods /////

	
	
	///// Print /////

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
