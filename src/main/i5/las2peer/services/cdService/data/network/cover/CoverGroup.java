package i5.las2peer.services.cdService.data.network.cover;

import java.util.ArrayList;
import java.util.List;

import i5.las2peer.services.cdService.data.util.table.Table;

public class CoverGroup {
	
	private List<Cover> covers;
	
	private String name;
	
	public CoverGroup() {
		this.covers = new ArrayList<>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addCover(Cover cover) {
		this.covers.add(cover);
	}
	
	public Table toTable() {
		
		Table table = new Table();
		table.add(covers.get(0).toHeadLine());
		for(Cover cover: covers) {
			table.add(cover.toTableLine());
		}
		return table;
	}
	
}
