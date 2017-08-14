package i5.las2peer.services.cdService.data.simulation;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class AgentData {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private SimulationDataset dataset;

	@Transient
	private List<Boolean> strategies;

	@Transient
	private List<Double> payoff;
	
	@Basic 
	private boolean finalStrategy;
	
	@Basic
	private double finalPayoff;
	
	///// Constructor /////
	
	public AgentData() {

	}

	public AgentData(List<Boolean> strategies, List<Double> payoff) {
		setStrategies(strategies);
		setPayoff(payoff);
	}
	
	///// Getter /////
	
	@JsonIgnore
	public List<Boolean> getStrategies() {
		return strategies;
	}
	
	@JsonProperty
	public boolean getFinalStrategy() {
		return this.finalStrategy;
	}
	
	@JsonIgnore
	public List<Double> getPayoff() {
		return payoff;
	}
	
	@JsonProperty
	public double getFinalPayoff() {
		return this.finalPayoff;
	}	
	
	///// Setter //////
	
	public void setStrategies(List<Boolean> strategies) {
		this.strategies = strategies;
		finalStrategy = strategies.get(strategies.size()-1);
	}
	
	public void setPayoff(List<Double> payoff) {
		this.payoff = payoff;
		finalPayoff = payoff.get(payoff.size()-1);
	}
	
	public void setFinalStrategy(boolean strategy) {
		this.finalStrategy = strategy;
	}
	
	public void setFinalPayoff(double payoff) {
		this.finalPayoff = payoff;
	}

}
