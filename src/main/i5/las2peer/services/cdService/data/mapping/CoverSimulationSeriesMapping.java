package i5.las2peer.services.cdService.data.mapping;

import java.io.Serializable;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import i5.las2peer.services.cdService.data.network.Cover;

@Entity
public class CoverSimulationSeriesMapping implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Cover cover;

	@OneToMany(fetch = FetchType.LAZY)
	private List<CommunitySimulationSeriesMapping> mappings;

	@ElementCollection
	List<Double> cooperationValues;

	public CoverSimulationSeriesMapping() {

	}
	
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

	public void setCover(Cover cover) {
		this.cover = cover;
	}

	public void setMappings(List<CommunitySimulationSeriesMapping> mappings) {
		this.mappings = mappings;
	}

	public void setCooperationValues(List<Double> cooperationValues) {
		this.cooperationValues = cooperationValues;
	}

}
