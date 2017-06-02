package i5.las2peer.services.cdService.simulation.dynamics;

//////////////Enumeration //////////

/**
 * Lists all available Dynamics If you implement a new subclass extend it The
 * String value is used as end user representation
 */
public enum DynamicType {

	REPLICATOR(0, "Replicator"),
	UNCONDITIONAL_IMITATION(1, "Unconditional Imitation"),
	MORAN(2, "Moran-Like");

	private final String name;
	private final int number;

	DynamicType(int num, String str) {
		this.name = str;
		this.number = num;
	}

	public static boolean TypeExists(String str) {

		for (DynamicType t : DynamicType.values()) {
			if (str.equalsIgnoreCase(t.name())) {
				return true;
			}
		}
		return false;
	}

}
