package i5.las2peer.services.cdService.simulation.game;

import java.util.List;

public class GameFactory {
	
	private GameFactory() {
		
	}

	public static Game build(double a, double b, double c, double d) {

		return (new Game(a, b, c, d));
	}

	public static Game build(double cost, double benefit) {

		cost = Math.abs(cost);
		double a = benefit - cost;
		double b = benefit;
		double c = -cost;
		double d = 0.0;
		return (build(a, b, c, d));
	}

	public static Game build(double[] payoffList) {

		if (payoffList.length == 2) {
			return (build((payoffList[0]), payoffList[1]));
		}
		if (payoffList.length == 4) {
			return (build((payoffList[0]), payoffList[1], payoffList[2], payoffList[3]));
		}
		return null;
	}

	public static Game build(List<String> payoffList) {

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
