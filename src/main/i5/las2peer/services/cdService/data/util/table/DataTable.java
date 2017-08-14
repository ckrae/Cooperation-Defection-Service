package i5.las2peer.services.cdService.data.util.table;

/**
 * A DataTable
 */
public class DataTable extends Table {

	public void add(double[] val1, double[] val2) {

		if (val1.length != val2.length)
			throw new IllegalArgumentException("array lenght do not match");

		int size = val1.length;
		for (int i = 0; i < size; i++) {
			TableRow row = new TableRow().add(val1[i]).add(val2[i]);
			this.add(row);
		}
	}

	public void add(double[] val1, double[] val2, double[] val3) {

		if (val1.length != val2.length || val1.length != val2.length)
			throw new IllegalArgumentException("array lenght do not match");

		int size = val1.length;
		for (int i = 0; i < size; i++) {
			TableRow row = new TableRow().add(val1[i]).add(val2[i]).add(val3[i]);
			this.add(row);
		}
	}

	public void append(double[] val) {

		if (val.length != rows())
			throw new IllegalArgumentException("array lenght do not match");

		int size = val.length;
		for (int i = 0; i < size; i++) {
			this.append(i, String.valueOf(val[i]));
		}

	}

}
