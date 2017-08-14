package i5.las2peer.services.cdService.simulation.dynamic;

/**
 * Lists all available Dynamics If you implement a new subclass extend it The
 * String value is used as end user representation
 */
public enum DynamicType {

	UNKNOWN(0, "Unknown", "UK"),
	REPLICATOR(1, "Replicator", "REP"),
	UNCONDITIONAL_IMITATION(2, "Imitation", "IM"),
	MORAN(3, "Moran", "MOR"),
	WS_LS(4, "Win-Stay Lose-Shift", "WSLS");

	public final String string;
	public final String shortcut;
	public final int number;

	DynamicType(int num, String str, String sht) {
		this.string = str;
		this.number = num;
		this.shortcut = sht;
	}
	
	public String shortcut() {
		return this.shortcut;
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
