package i5.las2peer.services.cdService.data.simulation;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class AgentData {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private SimulationDataset data;

	@ElementCollection
	private List<Boolean> strategies;

	@ElementCollection
	private List<Double> payoff;

	public AgentData() {

	}

	public AgentData(List<Boolean> strategies, List<Double> payoff) {
		this.strategies = strategies;
		this.payoff = payoff;
	}

	public List<Boolean> getStrategies() {
		return strategies;
	}

	public void setStrategies(List<Boolean> strategies) {
		this.strategies = strategies;
	}

	public List<Double> getPayoff() {
		return payoff;
	}

	public void setPayoff(List<Double> payoff) {
		this.payoff = payoff;
	}

}
