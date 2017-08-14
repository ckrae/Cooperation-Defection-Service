package i5.las2peer.services.cdService.data.util;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import i5.las2peer.services.cdService.data.mapping.CoverSimulationGroupMapping;
import i5.las2peer.services.cdService.data.mapping.CoverSimulationSeriesMapping;
import i5.las2peer.services.cdService.data.network.NetworkMeta;
import i5.las2peer.services.cdService.data.network.cover.Cover;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;
import i5.las2peer.services.cdService.data.simulation.SimulationSeriesGroup;

public class JsonAdapter {

	String simulationSeries = "data\\json\\simulation\\series\\";
	String simulationGroup = "data\\json\\simulation\\group\\";
	String network = "data\\json\\network\\";
	String cover = "data\\json\\cover";
	String coverSeriesMapping = "data\\json\\mapping\\coverSimulationSeries";
	String coverGroupMapping = "data\\json\\mapping\\coverSimulationGroup";

	///// Write /////

	public void write(Object object, String path, String name) {

		ObjectMapper mapper = new ObjectMapper();
		File directory = new File(path);
		directory.mkdirs();
		File file = new File(path, name + ".json");
		try {
			mapper.writeValue(file, object);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(SimulationSeries object) {
		String path = simulationSeries;
		String name = object.getName();
		write(object, path, name);
	}

	public void write(SimulationSeriesGroup object) {
		String path = simulationGroup;
		String name = object.getName();
		write(object, path, name);
	}

	public void write(NetworkMeta object) {
		String path = network;
		String name = object.getName();
		write(object, path, name);
	}

	public void write(Cover object) {
		String path = cover;
		String name = object.getName();
		write(object, path, name);
	}

	public void write(CoverSimulationSeriesMapping object) {
		String path = coverSeriesMapping;
		String name = object.getName();
		write(object, path, name);
	}

	public void write(CoverSimulationGroupMapping object) {
		String path = coverGroupMapping;
		String name = object.getName();
		write(object, path, name);
	}

	///// Read //////

	public NetworkMeta readNetwork(String name) {
		ObjectMapper mapper = new ObjectMapper();		
		String path = network;		
		NetworkMeta object = null;
		try {
			object = mapper.readValue(new File(path + name), NetworkMeta.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public Cover readCover(String name) {
		ObjectMapper mapper = new ObjectMapper();		
		String path = cover;		
		Cover object = null;
		try {
			object = mapper.readValue(new File(path + name), Cover.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public SimulationSeries readSimulationSeries(String name) {
		ObjectMapper mapper = new ObjectMapper();		
		String path = simulationSeries;		
		SimulationSeries object = null;
		try {
			object = mapper.readValue(new File(path + name), SimulationSeries.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public SimulationSeriesGroup readSimulationSeriesGroup(String name) {
		ObjectMapper mapper = new ObjectMapper();		
		String path = simulationGroup;		
		SimulationSeriesGroup object = null;
		try {
			object = mapper.readValue(new File(path + name), SimulationSeriesGroup.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public CoverSimulationSeriesMapping readCoverSeriesMapping(String name) {
		ObjectMapper mapper = new ObjectMapper();		
		String path = coverSeriesMapping;		
		CoverSimulationSeriesMapping object = null;
		try {
			object = mapper.readValue(new File(path + name), CoverSimulationSeriesMapping.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public CoverSimulationGroupMapping readCoverGroupMapping(String name) {
		ObjectMapper mapper = new ObjectMapper();		
		String path = coverGroupMapping;		
		CoverSimulationGroupMapping object = null;
		try {
			object = mapper.readValue(new File(path + name), CoverSimulationGroupMapping.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	
}
