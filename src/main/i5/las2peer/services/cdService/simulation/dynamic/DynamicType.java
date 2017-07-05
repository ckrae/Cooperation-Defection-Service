package i5.las2peer.services.cdService.simulation.dynamic;

/**
 * Lists all available Dynamics If you implement a new subclass extend it The
 * String value is used as end user representation
 */
public enum DynamicType {

	UNKNOWN(0, "Unknown"), REPLICATOR(1, "Replicator"), UNCONDITIONAL_IMITATION(2, "Imitation"), MORAN(3, "Moran"), WS_LS(4, "Win-Stay Lose-Shift");

	public final String string;
	public final int number;

	DynamicType(int num, String str) {
		this.string = str;
		this.number = num;
	}

	public static boolean TypeExists(String str) {

		for (DynamicType t : DynamicType.values()) {
			if (str.equalsIgnoreCase(t.name()) || str.equalsIgnoreCase(t.string)) {
				return true;
			}
		}
		return false;
	}

	public static DynamicType fromString(String string) {

		for (DynamicType type : DynamicType.values()) {
			if (string.equalsIgnoreCase(type.name()) || string.equalsIgnoreCase(type.string)) {
				return type;
			}
		}
		return DynamicType.UNKNOWN;
	}
	
	public static DynamicType[] getValues() {
		return DynamicType.values();
	}

}
