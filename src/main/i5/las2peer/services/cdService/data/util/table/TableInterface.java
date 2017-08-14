package i5.las2peer.services.cdService.data.util.table;

import i5.las2peer.services.cdService.data.util.table.TableRow;

public interface TableInterface  {
	
	public TableRow toTableLine();
	
	public Table toTable();
	
	public String getName();
}
	
