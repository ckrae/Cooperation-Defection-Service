package i5.las2peer.services.cdService.simulation.game;

public enum GameType {

	PRISONERS_DILEMMA("Prisoner's dilemma"), CHICKEN("Chicken"), CUSTOM("Custom");
	
	public final String string;
	
	GameType(String str) {
		this.string = str;
	}
	
	public static GameType fromString(String string) {
		
		for (GameType type : GameType.values()) {
			if (string.equalsIgnoreCase(type.name())) {
				return type;
			}
		}
		return null;
	}

	public static GameType getGameType(double payoffCC, double payoffCD, double payoffDC, double payoffDD) {
		if (payoffDC >= payoffCC) {
			if (payoffCC + payoffCC >= payoffDC + payoffCD) {
				if (payoffDD >= payoffCD) {
					return GameType.PRISONERS_DILEMMA;
				}
				if (payoffCD >= payoffDD) {
					return GameType.CHICKEN;
				}
			}
		}
		return GameType.CUSTOM;
	}
}
