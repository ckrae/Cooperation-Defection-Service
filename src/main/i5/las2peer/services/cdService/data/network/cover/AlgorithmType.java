package i5.las2peer.services.cdService.data.network.cover;

public enum AlgorithmType {
	
	UNKNOWN ("", ""),
	SLPA ("SPEAKER_LISTENER_LABEL_PROPAGATION_ALGORITHM", ""),
	CLIZZ ("CLIZZ_ALGORITHM", ""),
	DMID ("SIGNED_DMID_ALGORITHM", ""),
	LC ("LINK_COMMUNITIES_ALGORITHM", ""),
	SSK ("SSK_ALGORITHM", ""),
	Spinglass ("", ""),
	LE ("", ""),
	Walktrap ("", ""),
	EB ("", ""),
	FGMod ("", ""),
	MLMod ("", ""),
	LP ("", ""),
	InfoMap ("", "");
	
	String  compatibility;
	String humanRead;
	
	AlgorithmType(String compatibility, String humanRead) {
		this.compatibility = compatibility;
		this.humanRead = humanRead;
	}
	
	public String humanRead() {
		return humanRead;
	}
	
	public String compatibilityString() {
		return compatibility;
	}


public static AlgorithmType fromString(String string) {

		for (AlgorithmType type : AlgorithmType.values()) {
			if (string.equalsIgnoreCase(type.name()) || string.equalsIgnoreCase(type.compatibilityString()) || string.equalsIgnoreCase(type.humanRead())) {
				return type;
			}
		}
		return AlgorithmType.UNKNOWN;
	}
}

