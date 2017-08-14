package i5.las2peer.services.cdService.data.mapping;

import javax.persistence.Basic;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import i5.las2peer.services.cdService.data.network.PropertyType;

import i5.las2peer.services.cdService.data.util.table.Table;
import i5.las2peer.services.cdService.data.util.table.TableInterface;

@MappedSuperclass
public abstract class MappingAbstract implements TableInterface {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Basic
	String name;
	
	@Embedded
	Correlation sizeCorrelation;

	@Embedded
	Correlation densityCorrelation;

	@Embedded
	Correlation averageDegreeCorrelation;

	@Embedded
	Correlation degreeDeviationCorrelation;

	private double[] cooperationValues;

	private double[] payoffValues;

	///// Getter /////
	
	@JsonIgnore
	public long getId() {
		return this.id;
	}
	
	@Override
	@JsonGetter
	public String getName() {
		return this.name;
	}
	
	@JsonProperty
	public Correlation getSizeCorrelation() {
		if (sizeCorrelation == null)
			sizeCorrelation = new Correlation(getCooperationValues(), getPropertyValues(PropertyType.SIZE));
		return sizeCorrelation;
	}

	@JsonProperty
	public Correlation getDensityCorrelation() {
		if (densityCorrelation == null)
			densityCorrelation = new Correlation(getCooperationValues(), getPropertyValues(PropertyType.DENSITY));
		return densityCorrelation;
	}

	@JsonProperty
	public Correlation getAverageDegreeCorrelation() {
		if (averageDegreeCorrelation == null)
			averageDegreeCorrelation = new Correlation(getCooperationValues(),
					getPropertyValues(PropertyType.AVERAGE_DEGREE));
		return averageDegreeCorrelation;
	}

	@JsonProperty
	public Correlation getDegreeDeviationCorrelation() {
		if (degreeDeviationCorrelation == null)
			degreeDeviationCorrelation = new Correlation(getCooperationValues(),
					getPropertyValues(PropertyType.DEGREE_DEVIATION));
		return degreeDeviationCorrelation;
	}

	public abstract double[] getPropertyValues(PropertyType property);

	public abstract double[] getCooperationValues();

	///// Setter /////
	
	@JsonSetter
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonSetter
	public void setSizeCorrelation(Correlation sizeCorrelation) {
		this.sizeCorrelation = sizeCorrelation;
	}

	@JsonSetter
	public void setDensityCorrelation(Correlation densityCorrelation) {
		this.densityCorrelation = densityCorrelation;
	}

	@JsonSetter
	public void setAverageDegreeCorrelation(Correlation averageDegreeCorrelation) {
		this.averageDegreeCorrelation = averageDegreeCorrelation;
	}

	@JsonSetter
	public void setDegreeDeviationCorrelation(Correlation degreeDeviationCorrelation) {
		this.degreeDeviationCorrelation = degreeDeviationCorrelation;
	}

	@JsonSetter
	public void setCooperationValues(double[] values) {
		this.cooperationValues = values;
	}

	@JsonSetter
	public void setPayoffValues(double[] values) {
		this.payoffValues = values;
	}

	///// Methods /////

	public void correlate() {
		
		double[] cooperatioValues = null;
		try {
		cooperationValues = getCooperationValues();
		
		if(cooperationValues == null || cooperationValues.length < 1)
			return;
		
		sizeCorrelation = new Correlation(cooperatioValues, getPropertyValues(PropertyType.SIZE));

		densityCorrelation = new Correlation(cooperatioValues, getPropertyValues(PropertyType.DENSITY));

		averageDegreeCorrelation = new Correlation(cooperatioValues,
				getPropertyValues(PropertyType.AVERAGE_DEGREE));

		degreeDeviationCorrelation = new Correlation(cooperatioValues,
				getPropertyValues(PropertyType.DEGREE_DEVIATION));
		} catch (Exception e) {
			e.printStackTrace();		
		}
	}

	@Override
	public abstract Table toTable();

}
