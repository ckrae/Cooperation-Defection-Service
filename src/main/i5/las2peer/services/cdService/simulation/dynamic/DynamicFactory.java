package i5.las2peer.services.cdService.simulation.dynamic;

public class DynamicFactory {
	
	public static DynamicFactory getInstance() {
		return new DynamicFactory();
	}
	
	public Dynamic build(String dyn, double[] ds) {

		Dynamic dynamic = null;
		for (DynamicType t : DynamicType.values()) {
			if (dyn.equalsIgnoreCase(t.name())) {
				dynamic = this.build(t, ds);
			}
		}
		return dynamic;
	}

	public Dynamic build(DynamicType dyn, double[] value) {

		switch (dyn) {
		case REPLICATOR:
			return (new Replicator(value));

		case UNCONDITIONAL_IMITATION:
			return (new UnconditionalImitation());
		case MORAN:
			return (new Moran());

		default:
			return null;
		}

	}

	public Dynamic build(DynamicType type) {
		return build(type, new double[]{1.5});
	}
	
	public Dynamic build(DynamicType type, double value) {
		return build(type, new double[]{value});
	}

}
