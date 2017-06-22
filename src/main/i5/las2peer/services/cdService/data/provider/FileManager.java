package i5.las2peer.services.cdService.data.provider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import i5.las2peer.api.Context;
import i5.las2peer.services.cdService.data.simulation.SimulationData;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;

public class FileManager {

	public static SimulationSeries getSimulationSeries(long seriesId) {

		long userId = Context.getCurrent().getMainAgent().getId();
		SimulationSeries series = null;

		return series;
	}

	public static void storeSimulationSeries(SimulationSeries series, long seriesId) {

		long userId = Context.getCurrent().getMainAgent().getId();

		String directory = "files/" + String.valueOf(userId) + "/" + String.valueOf(seriesId);

		ArrayList<SimulationData> datasets = series.getDatasets();
		for (int i = 0, size = datasets.size(); i < size; i++) {

			String fileName = String.valueOf(i);
			File file = new File(directory, fileName);
			
			try {
				FileOutputStream fout = FileUtils.openOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(fout);
				oos.writeObject(series);

			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	
			ObjectMapper mapper = new ObjectMapper();
			try {
				mapper.writerWithDefaultPrettyPrinter().writeValue(new File(directory + ".json"), series);
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			


	
	}

	public static long getNextSimulationIndex() {

		long userId = Context.getCurrent().getMainAgent().getId();
		String directoryName = "files/" + String.valueOf(userId);
		File directory = new File(directoryName);
		String fileName = "info.txt";
		File file = new File(directory, fileName);

		if (!directory.exists()) {
			directory.mkdirs();
		}
		if (!file.exists()) {
			file.mkdir();
			try {
				FileWriter writer = new FileWriter(file);
				writer.write("0");
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return 0;
	}

}
