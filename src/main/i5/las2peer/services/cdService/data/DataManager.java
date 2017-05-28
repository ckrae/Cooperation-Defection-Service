package i5.las2peer.services.cdService.data;

import com.google.common.util.concurrent.Service;

import i5.las2peer.api.Context;
import i5.las2peer.p2p.ServiceList;
import i5.las2peer.services.cdService.CDService;
import i5.las2peer.services.cdService.utility.DatabaseManager;

public class DataManager {

	private final static CDService service = (CDService) Context.getCurrent().getService();
		
	@SuppressWarnings("unused")
	public static SimulationSeries getSimulationSeries(long seriesId) {
		
		if(CDService.USE_DATABASE == true) {
			
			return DatabaseManager.getSimulationSeries(seriesId);
		}
		
		if(CDService.USE_STORAGE == true) {
		
			return service.getSimulationSeries(seriesId);			
		}
	
		return null;
	}

	@SuppressWarnings("unused")
	public static SimulationParameters getSimulationParameters(long seriesId) {
		
		if(CDService.USE_DATABASE == true) {
			
			return DatabaseManager.getSimulationParameters(seriesId);
		}
		
		if(CDService.USE_STORAGE == true) {
		
			return service.getSimulationSeries(seriesId).getParameters();			
		}
		
		return null;
		
	}	
	

	@SuppressWarnings("unused")
	public static boolean storeSimulationSeries(SimulationSeries series) {
		
		SimulationContainer container = null;
		long seriesId = 0;
		
		if(CDService.USE_DATABASE == true) {
			
			return false;
		}
		
		if(CDService.USE_STORAGE == true) {
		
			container = service.getSimulationContainer();
			seriesId = container.getLastIndex()+1;
			service.storeSimulationSeries(series, seriesId);	
			return true;
		}
		return false;
	}
	
	

}
