package i5.las2peer.services.cdService.data;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.jpa.PersistenceProvider;
import org.junit.Before;
import org.junit.Test;

import i5.las2peer.services.cdService.data.network.Graph;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;

public class EntityHandlerTest {

	private static final String PERSISTENCE_UNIT_NAME = "SimulationTest";
	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

	///// Simulation Series ///////

	@Before
	public void clearDatabase() {

		EntityManager em = factory.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		Query networkQuery = em.createQuery("DELETE FROM Networks", Graph.class);
		networkQuery.executeUpdate();
		Query simulationQuery = em.createQuery("DELETE FROM SimulationSeries", SimulationSeries.class);
		simulationQuery.executeUpdate();
		etx.commit();
	}

	@Test
	public void testStoreSimulationSeries() {

		EntityHandler databaseManager = EntityHandler.getTestInstance();
		SimulationSeries series = new SimulationSeries();
		long userId = 7;
		series.setUserId(userId);

		long seriesId = 0;
		try {
			seriesId = databaseManager.storeSimulationSeries(series);
		} catch (Exception e) {
			e.printStackTrace();
		}

		EntityManager em = factory.createEntityManager();

		TypedQuery<SimulationSeries> query = em.createQuery("SELECT s FROM SimulationSeries AS s WHERE s.seriesId =:id",
				SimulationSeries.class);
		query.setParameter("id", seriesId);
		SimulationSeries resultSeries = query.getSingleResult();

		assertNotNull(resultSeries);
		assertEquals(userId, resultSeries.getUserId());
		assertEquals(seriesId, resultSeries.getSeriesId());
	}

	@Test
	public void testGetSimulationSeries() {

		SimulationSeries series = new SimulationSeries();
		long userId = 7;
		series.setUserId(7);

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(series);
		em.flush();
		em.getTransaction().commit();
		long seriesId = series.getSeriesId();
		em.close();

		SimulationSeries resultSeries = null;
		try {
			resultSeries = EntityHandler.getTestInstance().getSimulationSeries(seriesId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertNotNull(resultSeries);
		assertEquals(userId, resultSeries.getUserId());
		assertEquals(seriesId, resultSeries.getSeriesId());

	}

	//////// Networks ////////////

	@Test
	public void getNetworkTest() {

		String graphName = "TestGraph";
		Graph network = new Graph();
		network.setGraphName(graphName);

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(network);
		em.flush();
		long networkId = network.getNetworkId();
		em.getTransaction().commit();

		Graph resultNetwork = EntityHandler.getTestInstance().getNetwork(networkId);
		assertNotNull(resultNetwork);
		assertEquals(networkId, resultNetwork.getNetworkId());
		assertEquals(graphName, resultNetwork.getGraphName());

	}

	@Test
	public void storeNetworkTest() {

		long networkId = 0;
		String graphName = "TestGraph";
		Graph network = new Graph(networkId);
		network.setGraphName(graphName);

		long resultId = EntityHandler.getTestInstance().storeNetwork(network);
		EntityManager em = factory.createEntityManager();
		TypedQuery<Graph> query = em.createQuery("SELECT n FROM Networks AS n WHERE n.networkId = :id", Graph.class);
		query.setParameter("id", resultId);
		List<Graph> networks = query.getResultList();
		Graph resultNetwork = networks.get(0);

		assertEquals(1, networks.size());
		assertEquals(graphName, resultNetwork.getGraphName());
	}

	@Test
	public void getAllNetworks() {

		EntityManager em = factory.createEntityManager();

		long[] networkIds = new long[] { 2, 4, 7 };
		for (long id : networkIds) {
			Graph network = new Graph(id);
			em.getTransaction().begin();
			em.persist(network);
			em.getTransaction().commit();
		}

		List<Graph> resultNetworks = EntityHandler.getTestInstance().getAllNetworks();
		assertNotNull(resultNetworks);
		assertEquals(networkIds.length, resultNetworks.size());


	}

}
