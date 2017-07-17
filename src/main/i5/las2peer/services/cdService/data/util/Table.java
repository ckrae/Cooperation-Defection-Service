package i5.las2peer.services.cdService.data.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import i5.las2peer.services.cdService.data.util.TableInterface;
import i5.las2peer.services.cdService.data.util.TableRow;

public class Table {

	private List<TableRow> rows;	
	private String caption;
	
	public Table() {
		rows = new ArrayList<>();		
	}
	
	public String getCaption() {
		return caption;		
	}
	
	public List<TableRow> getTableRows() {
		return rows;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public void add(TableRow row) {
		rows.add(row);
	}
	
	public void add(TableInterface row) {
		add(row.toTableLine());
	}
		
	public int rows() {
		if(rows == null) 
			return 0;		
		return rows.size();
	}
	
	public int columns() {
		if(rows == null || rows.size() == 0)
			return 0;
		return rows.get(0).size();
	}
	
	public String print() {

		StringJoiner table = new StringJoiner("\n");
		for (TableRow row : rows) {
			table.add(row.print());
		}
		return table.toString();
	}
}
