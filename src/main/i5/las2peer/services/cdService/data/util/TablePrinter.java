package i5.las2peer.services.cdService.data.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import i5.las2peer.services.cdService.data.mapping.MappingInterface;
import i5.las2peer.services.cdService.data.mapping.NetworkSimulationGroupMapping;
import i5.las2peer.services.cdService.data.network.DynamicGraph;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;
import i5.las2peer.services.cdService.data.simulation.SimulationSeriesGroup;
import i5.las2peer.services.cdService.data.util.Table;

public class TablePrinter {

	private TableType type;
	private String suffix;
	private String prefix;

	public TablePrinter() {

	}

	public TablePrinter(TableType type) {
		this("", type, "");
	}
	
	public TablePrinter(TableType type, String suffix) {
		this("", type, suffix);

	}

	public TablePrinter(String prefix, TableType type) {
		this(prefix, type, "");
	}
	
	public TablePrinter(String prefix, TableType type, String suffix) {
		this.type = type;
		this.prefix = prefix;
		this.suffix = suffix;
	}

	void printTable(MappingInterface simulation) {

	}
	
	
	protected File buildFile(File path, String name) {
		return new File(path,prefix + name + suffix);
	}
	
	protected File buildFile(String name) {
		return new File(prefix + name + suffix);
	}
	

	// Single Network - Multiple Covers ; Correletaions
	void printTable(NetworkSimulationGroupMapping mapping) {

		Table table = mapping.toTable();
		File path = new File("data\\table\\mapping\\group");
		path.mkdirs();
		File file = buildFile(path, String.valueOf(mapping.getId()));
		printTable(table, file);

	}

	// Dynamic Network
	void printTable(DynamicGraph network) {

		Table table = network.toTable();
		File path = new File("data\\table\\network\\dynamic");
		path.mkdirs();
		File file = buildFile(path, String.valueOf(network.getId()));
		printTable(table, file);

	}

	public void printTable(SimulationSeriesGroup simulation) {

		Table table = simulation.toTable();
		File path = new File("data\\table\\simulation\\group");
		path.mkdirs();
		File file = buildFile(path, String.valueOf(simulation.getName()));
		printTable(table, file);
	}

	public void printTable(SimulationSeries simulation) {

		Table table = simulation.toTable();
		File path = new File("data\\table\\simulation\\series");
		path.mkdirs();
		File file = buildFile(path, String.valueOf(simulation.getName()));
		printTable(table, file);
	}
	
	@SuppressWarnings("resource")
	public void printTable(Table table, File file) {

		Writer writer;
		try {
			writer = new FileWriter(file, false);			
			switch (type) {
			case LATEX: 
	           writer = writeLatexTable(table, writer);
	           break;
	        case TAB:	         
	        default: 
	        	writer.write(table.print()); 
	        	writer.close();
			}			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	///// table formats //////

	public Writer writeLatexTable(Table table, Writer writer) throws IOException {
		
		TableRowFormatter formatter = new TableRowFormatter(6);
		
		// begin table
		writer.append("\\begin{table}[]").append("\n");
		writer.append("\\caption{").append(table.getCaption()).append("}").append("\n");
		writer.append("\\label{}").append("\n");
		writer.append("\\centering");
		writer .append("\\begin{adjustbox}{max width=\\textwidth}");
		writer.append("\\begin{tabular}{*{");
		writer.append(String.valueOf(table.columns()));
		writer.append("}{l}}").append("\n");	

		// headline
		List<TableRow> rows = table.getTableRows();
		TableRow headline = rows.get(0);
		writer.append(headline.print("&").replace("#", "\\#"));
		writer.append("\\\\ ").append("\\hline").append("\n");

		// content
		for (int i = 1; i < rows.size(); i++) {
			TableRow row = rows.get(i);
			row = formatter.decimals(row);
			{
				writer.append(row.print("&"));
				writer.append("\\\\");
				if (i % 5 == 0)
					writer.append("\\hline");
				writer.append("\n");
			}
		}
		
		// end table
		writer.append("\\end{tabular}").append("\n");
		writer.append("\\end{adjustbox}");
		writer.append("\\end{table}").append("\n");
		writer.close();
		return writer;

	}

}
