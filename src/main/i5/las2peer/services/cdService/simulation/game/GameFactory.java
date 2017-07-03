package i5.las2peer.services.cdService.simulation.game;

import java.util.List;

public class GameFactory {

	public static GameFactory getInstance() {
		return new GameFactory();
	}

	public GameFactory() {

	}

	public Game build(double AA, double AB, double BA, double BB) {

		return (new Game(AA, AB, BA, BB));
	}

	public Game build(double cost, double benefit) {

		cost = Math.abs(cost);
		double AA = benefit - cost;
		double AB = -cost;
		double BA = benefit;
		double BB = 0.0;
		return (build(AA, AB, BA, BB));
	}

	public Game build(double[] payoffList) {

		if (payoffList.length == 2) {
			return (build((payoffList[0]), payoffList[1]));
		}
		if (payoffList.length == 4) {
			return (build((payoffList[0]), payoffList[1], payoffList[2], payoffList[3]));
		}
		return null;
	}

	public Game build(List<String> payoffList) {

		if (payoffList.size() == 2) {
			return (build(Double.parseDouble(payoffList.get(0)), Double.parseDouble(payoffList.get(1))));
		}
		if (payoffList.size() == 4) {
			return (build(Double.parseDouble(payoffList.get(0)), Double.parseDouble(payoffList.get(1)),
					Double.parseDouble(payoffList.get(2)), Double.parseDouble(payoffList.get(3))));
		}
		return null;
	}

}
