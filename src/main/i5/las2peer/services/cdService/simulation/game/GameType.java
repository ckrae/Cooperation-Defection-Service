package i5.las2peer.services.cdService.simulation.game;

public enum GameType {

	PRISONERS_DILEMMA("Prisoner's dilemma", "PD"), CHICKEN("Chicken", "SD"), CUSTOM("Custom", "CTM");
	
	public final String string;
	public final String shortcut;
	
	GameType(String str, String sht) {
		this.string = str;
		this.shortcut = sht;
	}
	
	public String humanRead() {
		return this.string;
	}
	
	public String shortcut() {
		return this.shortcut;
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
