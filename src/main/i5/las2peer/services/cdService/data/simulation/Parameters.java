package i5.las2peer.services.cdService.data.simulation;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import i5.las2peer.services.cdService.data.util.TableInterface;
import i5.las2peer.services.cdService.data.util.TableRow;
import i5.las2peer.services.cdService.simulation.dynamic.DynamicType;
import i5.las2peer.services.cdService.simulation.game.GameType;

@Entity
public class Parameters implements Serializable, TableInterface {

	private static final long serialVersionUID = 1L;

	////////// Entity Fields //////////

	@Id
	@OneToOne(fetch = FetchType.LAZY)
	SimulationSeries series;

	@Basic
	private long graphId;

	@Enumerated(EnumType.STRING)
	private GameType game;

	@Basic
	private double payoffCC;

	@Basic
	private double payoffCD;

	@Basic
	private double payoffDD;

	@Basic
	private double payoffDC;

	@Basic
	private double cost;

	@Basic
	private double benefit;

	@Enumerated(EnumType.STRING)
	private DynamicType dynamic;

	@Basic
	private double dynamicValue;

	@Basic
	private int iterations;

	////////// Constructor //////////

	public Parameters() {

	}

	public Parameters(SimulationSeries series, long graphId, GameType game, double payoffCC, double payoffCD,
			double payoffDD, double payoffDC, DynamicType dynamic, double dynamicValue, int iterations) {

		this.series = series;
		this.setGraphId(graphId);
		this.payoffCC = payoffCC;
		this.payoffCD = payoffCD;
		this.payoffDC = payoffDC;
		this.payoffDD = payoffDD;
		this.setDynamic(dynamic);
		this.setDynamicValue(dynamicValue);
		this.setIterations(iterations);
	}

	////////// Getter //////////

	@JsonProperty
	public long getGraphId() {
		return graphId;
	}

	@JsonProperty
	public DynamicType getDynamic() {
		return dynamic;
	}

	@JsonProperty
	public int getIterations() {
		return iterations;
	}

	@JsonProperty
	public GameType getGame() {
		if (game == null) {
			this.game = GameType.getGameType(payoffCC, payoffCD, payoffDC, payoffDD);
		}
		return this.game;
	}

	@JsonProperty
	public double getPayoffCC() {
		return payoffCC;
	}

	@JsonProperty
	public double getPayoffCD() {
		return payoffCD;
	}

	@JsonProperty
	public double getPayoffDD() {
		return payoffDD;
	}

	@JsonProperty
	public double getPayoffDC() {
		return payoffDC;
	}

	@JsonProperty
	public double getCost() {
		return cost;
	}

	@JsonProperty
	public double getBenefit() {
		return benefit;
	}

	@JsonIgnore
	public double[] getPayoffValues() {
		return new double[] { payoffCC, payoffCD, payoffDC, payoffDD };
	}

	@JsonIgnore
	public double[] getDynamicValues() {
		return new double[] { dynamicValue };
	}

	@JsonIgnore
	public SimulationSeries getSimulationSeries() {
		return this.series;
	}
	////////// Setter //////////

	@JsonIgnore
	public void setSeries(SimulationSeries series) {
		this.series = series;
	}

	@JsonSetter
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	@JsonSetter
	public void setGraphId(long graphId) {
		this.graphId = graphId;
	}

	@JsonSetter
	public void setDynamic(String dynamic) {
		this.dynamic = DynamicType.fromString(dynamic);
	}

	@JsonSetter
	public void setGame(String game) {
		this.game = GameType.fromString(game);
	}

	@JsonIgnore
	public void setDynamic(DynamicType dynamic) {
		this.dynamic = dynamic;
	}

	@JsonIgnore
	public void setGame(GameType game) {
		this.game = game;
	}

	@JsonSetter
	public void setDynamicValue(double dynamicValue) {
		this.dynamicValue = dynamicValue;
	}

	@JsonSetter
	public void setPayoffCC(double payoffCC) {
		this.payoffCC = payoffCC;
	}

	@JsonSetter
	public void setPayoffCD(double payoffCD) {
		this.payoffCD = payoffCD;
	}

	@JsonSetter
	public void setPayoffDD(double payoffDD) {
		this.payoffDD = payoffDD;
	}

	@JsonSetter
	public void setPayoffDC(double payoffDC) {
		this.payoffDC = payoffDC;
	}

	@JsonSetter
	public void setBenefit(double benefit) {
		this.benefit = benefit;
	}

	@JsonSetter
	public void setCost(double cost) {
		this.cost = cost;
	}

	///////////// Methods /////////////

	public void normalize() {

		// normalize payoff values
		double total = Math.abs(payoffCC) + Math.abs(payoffCD) + Math.abs(payoffDC) + Math.abs(payoffDD);
		if (total != 0.0) {
			payoffCC = payoffCC / total;
			payoffCD = payoffCD / total;
			payoffDC = payoffDC / total;
			payoffDD = payoffDD / total;
		}

	}

	///////// Print /////////

	@Override
	public TableRow toTableLine() {

		String format = "%.2f";
		TableRow line = new TableRow();
		line.add(getGame().shortcut()).add(String.format(format, getPayoffCC())).add(String.format(format, getPayoffCD()))
				.add(String.format(format, getPayoffDC())).add(String.format(format, getPayoffDD())).add(getDynamic().shortcut());
		return line;

	}

	public TableRow toHeadLine() {

		TableRow line = new TableRow();
		line.add("Game").add("CC").add("CD").add("CD").add("DC").add("Dynamic");
		return line;

	}

}
