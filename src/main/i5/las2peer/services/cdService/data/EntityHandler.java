package i5.las2peer.services.cdService.data;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import i5.las2peer.api.Context;
import i5.las2peer.api.exceptions.RemoteServiceException;
import i5.las2peer.api.exceptions.ServiceNotAvailableException;
import i5.las2peer.api.exceptions.ServiceNotFoundException;
import i5.las2peer.api.exceptions.StorageException;
import i5.las2peer.services.cdService.data.network.Cover;
import i5.las2peer.services.cdService.data.network.Graph;
import i5.las2peer.services.cdService.data.network.NetworkAdapter;
import i5.las2peer.services.cdService.data.simulation.Parameters;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;

@Singleton
public class EntityHandler {

	private static EntityHandler databaseManager;
	private EntityManagerFactory factory;
	private static final String PERSISTENCE_UNIT_NAME = "Simulation";

	private EntityHandler(String persistenceUnitName) {

		factory = Persistence.createEntityManagerFactory(persistenceUnitName);
	}

	protected static synchronized EntityHandler getInstance() {
		if (databaseManager == null) {
			databaseManager = new EntityHandler(PERSISTENCE_UNIT_NAME);
		}
		return databaseManager;
	}

	public static synchronized EntityHandler getTestInstance() {
		if (databaseManager == null) {
			databaseManager = new EntityHandler(PERSISTENCE_UNIT_NAME + "Test");
		}
		return databaseManager;
	}

	///////////// Simulation /////////////////

	protected SimulationSeries getSimulationSeries(long seriesId) {

		EntityManager em = factory.createEntityManager();
		TypedQuery<SimulationSeries> query = em.createQuery("SELECT s FROM SimulationSeries AS s WHERE s.seriesId =:id",
				SimulationSeries.class);
		query.setParameter("id", seriesId);
		SimulationSeries series = query.getSingleResult();
		return series;
	}

	protected long storeSimulationSeries(SimulationSeries series) {

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(series);
		em.flush();
		em.getTransaction().commit();

		long seriesId = series.getSeriesId();
		em.close();

		return seriesId;
	}

	protected Parameters getSimulationParameters(long seriesId) {

		EntityManager em = factory.createEntityManager();
		TypedQuery<Parameters> query = em.createQuery("SELECT p FROM Parameters AS p WHERE s.series_seriesId =:id",
				Parameters.class);
		query.setParameter("id", seriesId);
		Parameters parameters = query.getSingleResult();
		return parameters;
	}

	protected List<Long> getSimulationSeriesIds() {

		EntityManager em = factory.createEntityManager();
		long userId = Context.getCurrent().getMainAgent().getId();

		TypedQuery<Long> query = em.createQuery("SELECT s.seriesId FROM SimulationSeries AS s WHERE s.userId =:id",
				Long.class);
		query.setParameter("id", userId);
		List<Long> seriesIds = query.getResultList();
		return (seriesIds);
	}

	protected void deleteSimulationSeries(SimulationSeries series) {

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.remove(series);
		em.getTransaction().commit();

	}

	public List<SimulationSeries> getSimulationSeries(Parameters parameters) {

		if (parameters == null)
			return getSimulationSeries();

		EntityManager em = factory.createEntityManager();

		TypedQuery<SimulationSeries> query = em.createQuery(
				"SELECT s FROM SimulationSeries s JOIN Parameters p WHERE p.graphId =:graphId AND p.dynamic =:dynamic",
				SimulationSeries.class);
		query.setParameter("graphId", parameters.getGraphId());
		query.setParameter("dynamic", parameters.getDynamic());
		List<SimulationSeries> seriesList = query.getResultList();
		return seriesList;
	}

	public List<SimulationSeries> getSimulationSeries() {

		EntityManager em = factory.createEntityManager();

		TypedQuery<SimulationSeries> query = em.createQuery("SELECT s FROM SimulationSeries s", SimulationSeries.class);
		List<SimulationSeries> seriesList = query.getResultList();
		return seriesList;
	}

	//////////////// Network ///////////////////////

	protected Graph getNetwork(long networkId) {

		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<Graph> query = em.createQuery("SELECT n FROM Networks AS n WHERE n.ocdId =:id", Graph.class);
			query.setParameter("id", networkId);
			Graph network = query.getSingleResult();
			return network;
		} catch (Exception e) {
			return null;
		}
	}

	protected long storeNetwork(Graph network) {

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(network);
		em.flush();
		em.getTransaction().commit();

		long networkId = network.getNetworkId();
		em.close();
		return networkId;
	}

	protected List<Graph> getNetworks(List<Long> networkIds) {

		EntityManager em = factory.createEntityManager();
		TypedQuery<Graph> query = em.createQuery("SELECT n FROM Networks AS n WHERE n.ocdId IN :ids", Graph.class);
		query.setParameter("ids", networkIds);
		List<Graph> networks = query.getResultList();
		return networks;
	}

	protected List<Graph> getAllNetworks() {

		EntityManager em = factory.createEntityManager();
		TypedQuery<Graph> query = em.createQuery("SELECT n FROM Networks AS n", Graph.class);
		List<Graph> networks = query.getResultList();
		return networks;
	}

	//////////////// Cover //////////////////

	protected Cover getCover(long coverId) {

		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<Cover> query = em.createQuery("SELECT n FROM Cover AS n WHERE n.ocdId =:id", Cover.class);
			query.setParameter("id", coverId);
			Cover cover = query.getSingleResult();
			return cover;
		} catch (Exception e) {
			return null;
		}
	}

	protected long storeCover(Cover cover) {

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(cover);
		em.flush();
		em.getTransaction().commit();
		long coverId = cover.getCoverId();
		em.close();
		return coverId;
	}

}
