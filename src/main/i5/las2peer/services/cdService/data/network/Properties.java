package i5.las2peer.services.cdService.data.network;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Embeddable
public class Properties {

	///// Entity Fields /////

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

	///// Getter /////

	@JsonProperty
	public int getNodes() {
		if (this.nodes == 0.0)
			return -1;
		return nodes;
	}

	@JsonProperty
	public int getEdges() {
		return edges;
	}

	@JsonProperty
	public double getDensity() {
		if (this.density == 0.0)
			calculateDensity();
		return density;
	}

	@JsonProperty
	public double getAverageDegree() {
		if (this.averageDegree == 0.0)
			calculateAverageDegree();
		return averageDegree;
	}

	@JsonProperty
	public double getClusteringCoefficient() {
		return clusteringCoefficient;
	}

	///// Setter /////

	public void setNodes(int nodes) {
		this.nodes = nodes;
	}

	public void setEdges(int edges) {
		this.edges = edges;
	}

	public void setDensity(double density) {
		this.density = density;
	}

	public void setAverageDegree(double averageDegree) {
		this.averageDegree = averageDegree;
	}

	public void setClusteringCoefficient(double clusteringCoefficient) {
		this.clusteringCoefficient = clusteringCoefficient;
	}

	///// Methods /////

	@JsonIgnore
	public double getProperty(PropertyType property) {

		switch (property) {
		case SIZE:
			return getNodes();

		case DENSITY:
			return getDensity();

		case AVERAGE_DEGREE:
			return getAverageDegree();

		case CLUSTERING_COEFFICIENT:
			return getClusteringCoefficient();

		default:
			break;
		}
		return 0.0;
	}

	//////// Computations ///////

	public void calculateDensity() {
		int n = this.getNodes();
		int e = this.getEdges();
		this.density = ((2 * e) / (n * (n - 1)));
	}

	public void calculateAverageDegree() {
		int n = this.getNodes();
		int e = this.getEdges();
		this.averageDegree = ((2 * e) / (n));
	}

}
