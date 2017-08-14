package i5.las2peer.services.cdService.data.util.table;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import i5.las2peer.services.cdService.data.util.table.TableInterface;
import i5.las2peer.services.cdService.data.util.table.TableRow;

/**
 * A Table consisting of {@link TableRow}s. A {@link TablePrinter} can write the
 * table into a file.
 */
public class Table {

	private List<TableRow> rows;

	public Table() {
		rows = new ArrayList<>();
	}

	public List<TableRow> getTableRows() {
		return rows;
	}

	///// add /////

	/**
	 * Add a {@link TableRow} to the end of the Table
	 * 
	 * @param tableRow
	 *            the TableRow
	 */
	public void add(TableRow tableRow) {
		rows.add(tableRow);
	}

	/**
	 * Add a object that implements the {@link TableInterface} to the end of the
	 * Table.
	 * 
	 * @param tableRow
	 *            the TableRow
	 */
	public void add(TableInterface row) {
		add(row.toTableLine());
	}

	/**
	 * Creates a new TableRow consisting of one column of the given string
	 * 
	 * @param tableRow
	 *            the TableRow
	 */
	public void add(String string) {
		rows.add(new TableRow().add(string));
	}

	///// append /////

	public void append(int rowId, TableRow tableRow) {

		if (rowId < 0 || rowId > rows())
			throw new IllegalArgumentException("invalid row id");

		getTableRows().get(rowId).add(tableRow);
	}
	
	public void append(int rowId, TableInterface tableInterface) {

		if (rowId < 0 || rowId > rows())
			throw new IllegalArgumentException("invalid row id");

		getTableRows().get(rowId).add(tableInterface);
	}
	
	public void append(int rowId, String row) {

		if (rowId < 0 || rowId > rows())
			throw new IllegalArgumentException("invalid row id");

		getTableRows().get(rowId).add(row);
	}
	
	///// size /////
	
	public int rows() {
		if (rows == null)
			return 0;
		return rows.size();
	}

	public int columns() {
		if (rows == null || rows.size() == 0)
			return 0;
		return rows.get(0).size();
	}
	
	///// format /////
	
	public void format(Formatter formatter) {
		
		for (TableRow row : rows) {
			row.format(formatter);
		}
	}
	
	///// print /////
	
	public String print() {

		StringJoiner table = new StringJoiner("\n");
		for (TableRow row : rows) {
			table.add(row.print());
		}
		return table.toString();
	}
}
