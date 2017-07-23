package i5.las2peer.services.cdService.data.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class PlotTikz {

	private static final String FILENAME = "filename.txt";

	public static void main(String[] args) {

		FileWriter fw = null;
		BufferedWriter writer = null;
		try {

			fw = new FileWriter(FILENAME);
			writer = new BufferedWriter(fw);

			writer.append("\\begin{tikzpicture}").append("\n");
			
			
			
			
			writer.append("\\end{tikzpicture}");
			
		} catch (IOException e) {

			e.printStackTrace();

		} finally {
			try {
				if (writer != null)
					writer.close();
				if (fw != null)
					fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
		

	}

}
