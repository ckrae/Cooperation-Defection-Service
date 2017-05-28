package i5.las2peer.services.cdService.simulation.dynamics;


public class DynamicFactory {

	/////////////// Methods  /////////////
		
	public static Dynamic build(String dyn, double value) {
		
		Dynamic dynamic = null;
		for (DynamicType t : DynamicType.values()) {
			 if (dyn.equalsIgnoreCase(t.name())) {
				dynamic = DynamicFactory.build(t, value);
			 }
		}
		return dynamic;
		}
	
	
	public static Dynamic build(DynamicType dyn, double value) {
	
		switch (dyn) {
			case REPLICATOR: 
				return(new Replicator(value));
				
			case UNCONDITIONAL_IMITATION:
				return(new UnconditionalImitation());
			case MORAN: 
				return null;  
			
			default: 
				return null;
			}	
		
		}
	
}
