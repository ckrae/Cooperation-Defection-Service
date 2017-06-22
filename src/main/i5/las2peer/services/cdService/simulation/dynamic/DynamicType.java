package i5.las2peer.services.cdService.simulation.dynamic;

/**
 * Lists all available Dynamics If you implement a new subclass extend it The
 * String value is used as end user representation
 */
public enum DynamicType {

	REPLICATOR(0, "Replicator"),
	UNCONDITIONAL_IMITATION(1, "Imitation"),
	MORAN(2, "Moran");

	private final String string;
	private final int number;

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
		      if (type.string.equalsIgnoreCase(string)) {
		        return type;
		      }
		    }
		    return null;
	}

}
