package i5.las2peer.services.cdService.data.simulation;

import i5.las2peer.services.cdService.data.util.table.Table;
import i5.las2peer.services.cdService.data.util.table.TableInterface;
import i5.las2peer.services.cdService.data.util.table.TableRow;

public abstract class SimulationAbstract implements TableInterface {
	
	private String name;
	
	///// Getter /////
	
	public long getId() {
		return 0;
	}
	
	@Override
	public String getName() {		
		if (name == null || name == "")
			return String.valueOf(getId());
		return name;
	}
		
	
	///// Setter /////
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public TableRow toTableLine() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Table toTable() {
		// TODO Auto-generated method stub
		return null;
	}
	






}
