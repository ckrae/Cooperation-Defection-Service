package i5.las2peer.services.cdService.data.util;

import org.junit.Test;

import i5.las2peer.services.cdService.data.util.Table;
import i5.las2peer.services.cdService.data.util.TableRow;

public class TableTest {
	
	@Test
	public void printTableTest() {
		
		Table table = new Table();
		
		TableRow row1 = new TableRow();
		TableRow row2 = new TableRow();
		TableRow row3 = new TableRow();
		
		row1.add("aaa").add("1234").add("yxcv").add("vvv");
		row2.add("bbb").add("125").add("qwer").add("www");
		row3.add("ccc").add("654").add("asdf").add("zzz");
		
		table.add(row1);
		table.add(row2);
		table.add(row3);		
		System.out.println(table.print());		
	}	
	
}
