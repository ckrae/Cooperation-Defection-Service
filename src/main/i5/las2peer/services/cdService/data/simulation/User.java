package i5.las2peer.services.cdService.data.simulation;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	long id;
	
	public User() {
		
	}
	
	public long getUserId() {
		return this.id;
	}


}
