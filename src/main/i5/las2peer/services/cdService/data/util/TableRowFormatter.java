package i5.las2peer.services.cdService.data.util;

import java.util.List;

public class TableRowFormatter extends Formatter {

	private int digits;

	public TableRowFormatter() {

	}

	public TableRowFormatter(int i) {
		this.digits = i;
	}

	public TableRow decimals(TableRow row) {

		return decimals(row, digits);
	}

	public TableRow decimals(TableRow row, int digits) {

		List<String> cells = row.getCells();
		for (int i = 0; i < cells.size(); i++) {
			String cell = cells.get(i);
			if (cell.length() > digits - 2) {
				cell = decimals(cell, digits);
				cells.set(i, cell);
			}
		}
		return row;
	}

}
