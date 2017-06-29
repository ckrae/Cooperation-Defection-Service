package i5.las2peer.services.cdService.data;

public class ConsistencyException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ConsistencyException() {
		
		super("data is not consistent");
		
	}
	

}
