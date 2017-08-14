package i5.las2peer.services.cdService.simulation.dynamic;

/**
 * Constructs {@link Dynamic} objects
 */
public class DynamicFactory {	

	public static DynamicFactory getInstance() {
		return new DynamicFactory();
	}

	/**
	 * Creates a {@link Dynamic} matching the specified {@link DynamicType} and parameter values.
	 * 
	 * @param dynamicType
	 * @param values parameters values
	 * @return Dynamic
	 */
	public Dynamic build(DynamicType dynamicType, double[] values) {

		switch (dynamicType) {
		
		case REPLICATOR:
			if(values == null || values.length != 1)
				throw new IllegalArgumentException("no dynamic parameters specified");
			return (new Replicator(values[0]));

		case UNCONDITIONAL_IMITATION:
			return (new UnconditionalImitation());
		
		case MORAN:
			return (new Moran());
			
		case WS_LS:
			return (new WinStayLoseShift());
		
		default:
			throw new IllegalArgumentException("unknown dynamic");
		}
	}

	/**
	 * Creates a {@link Dynamic} matching the specified {@link DynamicType} with default parameters.
	 * 
	 * @param dynamicType
	 * @return
	 */
	public Dynamic build(DynamicType dynamicType) {
		return build(dynamicType, new double[]{1.5});
	}
	
	/**
	 * Creates a {@link Dynamic} matching the specified {@link DynamicType} and the parameter value.
	 * 
	 * @param dynamicType
	 * @param value parameter value
	 * @return dynamic
	 */
	public Dynamic build(DynamicType dynamicType, double value) {
		return build(dynamicType, new double[]{value});
	}	
	
	/**
	 * Creates a {@link Dynamic} matching the specified dynamic string and parameter values.
	 * 
	 * @param dynamicString specifying dynamicType
	 * @param values dynamic parameter values
	 * @return Dynamic
	 */
	public Dynamic build(String dynamicString, double[] values) {

		DynamicType dynamicType = DynamicType.fromString(dynamicString);
		
		if(dynamicType == DynamicType.UNKNOWN) 
			throw new IllegalArgumentException("unknown dynamic");
		
		return build(dynamicType, values);
	}
	
}
