package i5.las2peer.services.cdService.data.manager;

import i5.las2peer.api.Context;
import i5.las2peer.api.exceptions.ArtifactNotFoundException;
import i5.las2peer.api.exceptions.StorageException;
import i5.las2peer.persistency.Envelope;
import i5.las2peer.security.L2pSecurityException;
import i5.las2peer.services.cdService.CDService;
import i5.las2peer.services.cdService.data.SimulationContainer;
import i5.las2peer.services.cdService.data.SimulationSeries;
import i5.las2peer.tools.CryptoException;
import i5.las2peer.tools.SerializationException;

public class StorageManager {

	public static SimulationContainer getSimulationContainer() {

		long userId = Context.getCurrent().getMainAgent().getId();
		String identifier = CDService.SERVICE_PREFIX + String.valueOf(userId);
		Envelope stored = null;
		SimulationContainer simCon = null;

		try {
			try {
				stored = Context.getCurrent().fetchEnvelope(identifier);
				simCon = (SimulationContainer) stored.getContent();

			} catch (ArtifactNotFoundException e) {
				simCon = new SimulationContainer();
				Envelope env = Context.getCurrent().createEnvelope(identifier, simCon);
				Context.getCurrent().storeEnvelope(env);
			}
		} catch (CryptoException | L2pSecurityException | SerializationException | StorageException e) {
			e.printStackTrace();
		}
		return simCon;
	}

	public static void storeSimulationContainer(SimulationContainer simCon) {

		long userId = Context.getCurrent().getMainAgent().getId();
		String identifier = CDService.SERVICE_PREFIX + String.valueOf(userId);

		try {

			Envelope env = Context.getCurrent().fetchEnvelope(identifier);
			env = Context.getCurrent().createEnvelope(env, simCon);
			Context.getCurrent().storeEnvelope(env);

		} catch (CryptoException | SerializationException | StorageException e) {
			e.printStackTrace();
		}

	}

	public static SimulationSeries getSimulationSeries(long seriesId) throws StorageException {

		long userId = Context.getCurrent().getMainAgent().getId();
		String identifier = CDService.SERVICE_PREFIX + String.valueOf(userId) + "#" + String.valueOf(seriesId);
		Envelope stored = null;
		SimulationSeries series = null;

		try {
			stored = Context.getCurrent().fetchEnvelope(identifier);
			series = (SimulationSeries) stored.getContent();

		} catch (CryptoException | L2pSecurityException | SerializationException e) {
			e.printStackTrace();
		}
		return series;
	}

	public static void storeSimulationSeries(SimulationSeries series, long seriesId) {

		long userId = Context.getCurrent().getMainAgent().getId();
		String identifier = CDService.SERVICE_PREFIX + String.valueOf(userId) + "#" + String.valueOf(seriesId);
		Envelope env = null;

		try {
			env = Context.getCurrent().createUnencryptedEnvelope(identifier, series);
			Context.getCurrent().storeEnvelope(env);

		} catch (CryptoException | SerializationException | StorageException e) {
			e.printStackTrace();
		}
	}

}
