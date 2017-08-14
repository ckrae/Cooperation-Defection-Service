package i5.las2peer.services.cdService.simulation.game;

/**
 * Provides possible types for {@link Game}
 * 
 */
public enum GameType {
	
	/**
	 * Prisoner's Dilemma.
	 */
	PRISONERS_DILEMMA("Prisoner's dilemma", "PD"),
	
	/**
	 * Chicken / SnowDrift Game. 
	 */
	CHICKEN("Snow Drift", "SD"),
	
	/**
	 * games that can not be assigned to other GameTypes
	 */
	CUSTOM("Custom", "CTM"),
	
	/**
	 * invalid games
	 */
	INVALID("Invalid", "INVALID");

	public final String humanRead;
	public final String shortcut;

	GameType(String str, String sht) {
		this.humanRead = str;
		this.shortcut = sht;
	}

	public String humanRead() {
		return this.humanRead;
	}

	public String shortcut() {
		return this.shortcut;
	}

	/**
	 * Parse the GameType from a string
	 * 
	 * @param string 
	 * @return gameType
	 */
	public static GameType fromString(String string) {

		for (GameType type : GameType.values()) {
			if (string.equalsIgnoreCase(type.name()) || string.equalsIgnoreCase(type.shortcut()) || string.equalsIgnoreCase(type.humanRead())) {
				return type;
			}
		}
		return GameType.INVALID;
	}

	/**
	 * Determine the GameType by the game parameters
	 * 
	 * @param payoffCC
	 * @param payoffCD
	 * @param payoffDC
	 * @param payoffDD
	 * @return gameType
	 */
	public static GameType getGameType(double payoffCC, double payoffCD, double payoffDC, double payoffDD) {
		
		if (payoffCC == 0.0 && payoffCD == 0.0 && payoffDC == 0.0 && payoffDD == 0.0)
			return GameType.INVALID;
		
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
