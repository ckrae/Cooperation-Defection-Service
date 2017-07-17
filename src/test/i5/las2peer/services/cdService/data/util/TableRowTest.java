package i5.las2peer.services.cdService.data.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import i5.las2peer.services.cdService.data.util.TableRow;

public class TableRowTest {
	
	String result;
	
	@Before
	public void init() {
		result="";
	}
	
	@Test
	public void tableRowTest() {		

		TableRow row = new TableRow();
		
		row.add(24);
		result = row.print();
		assertEquals("24", result);
		row.clear();
		result = row.print();
		assertEquals("", result);
		
		row.add("hello").add(2.0).add("abc");
		result = row.print();
		assertEquals("hello\t2.0\tabc", result);
		
		TableRow row2 = new TableRow();
		row2.add("qwer");
		
		row2.add(row);
		result = row2.print();
		assertEquals("qwer\thello\t2.0\tabc", result);
		
	}
	
	
}
