package i5.las2peer.services.cdService.data.util;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;

public class Charter {

	public Charter() {
		
	}
	
	public void test() {
		
	
		
		
	}
	
	
	public JFreeChart createScatterChart(String title, String xAxisLabel, String yAxisLabel, XYDataset dataset) {
		
		JFreeChart chart = ChartFactory.createScatterPlot(title, xAxisLabel, yAxisLabel, dataset);
		return chart;
		
	}
	
}
