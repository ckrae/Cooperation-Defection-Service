package i5.las2peer.services.cdService.data;

import static org.junit.Assert.*;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import i5.las2peer.services.cdService.data.network.NetworkMeta;
import i5.las2peer.services.cdService.data.network.NetworkStructureBuilder;
import i5.las2peer.services.cdService.data.network.cover.AlgorithmType;
import i5.las2peer.services.cdService.data.network.cover.Cover;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;

public class EntityHandlerTest {

	private static EntityManagerFactory factory;

	///// Simulation Series ///////

	@BeforeClass
	public static void setUpPersistence() {
		factory = PersistenceUtil.getEntityManagerFactory();
	}

	@Before
	public void clearDatabase() {

		EntityManager em = factory.createEntityManager();
		EntityTransaction etx = em.getTransaction();
		etx.begin();
		Query query = em.createQuery("DELETE FROM Cover", NetworkMeta.class);
		query.executeUpdate();
		query = em.createQuery("DELETE FROM Networks", NetworkMeta.class);
		query.executeUpdate();
		query = em.createQuery("DELETE FROM AgentData", SimulationSeries.class);
		query.executeUpdate();
		query = em.createQuery("DELETE FROM SimulationDataset", SimulationSeries.class);
		query.executeUpdate();
		query = em.createQuery("DELETE FROM SimulationSeries", SimulationSeries.class);
		query.executeUpdate();
		etx.commit();
	}

	@Test
	public void testStoreSimulationSeries() {

		EntityHandler databaseManager = EntityHandler.getInstance();
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
		assertEquals(seriesId, resultSeries.getId());
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
		long seriesId = series.getId();
		em.close();

		SimulationSeries resultSeries = null;
		try {
			resultSeries = EntityHandler.getInstance().getSimulationSeries(seriesId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertNotNull(resultSeries);
		assertEquals(userId, resultSeries.getUserId());
		assertEquals(seriesId, resultSeries.getId());

	}

	///// Simulation Series Group ///////

	//////// Networks ////////////

	@Test
	public void getNetworkTest() {

		String graphName = "TestGraph";
		NetworkMeta network = new NetworkMeta();
		network.setGraphName(graphName);
		
		NetworkStructureBuilder structure = new NetworkStructureBuilder();
		structure.addEdge(0, 1);
		structure.addEdge(2, 1);
		network.setNetworkStructure(structure.build());
		
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(network);
		em.flush();
		long networkId = network.getNetworkId();
		em.getTransaction().commit();

		NetworkMeta resultNetwork = EntityHandler.getInstance().getNetwork(networkId);
		assertNotNull(resultNetwork);
		assertEquals(networkId, resultNetwork.getNetworkId());
		assertEquals(graphName, resultNetwork.getName());
		assertNotNull(resultNetwork.getNetworkStructure());
		assertEquals(3, resultNetwork.getNetworkStructure().getNodes().size());

	}

	@Test
	public void storeNetworkTest() {

		long networkId = 0;
		String graphName = "TestGraph";
		NetworkMeta network = new NetworkMeta(networkId);
		network.setGraphName(graphName);
		
		NetworkStructureBuilder structure = new NetworkStructureBuilder();
		structure.addEdge(0, 1);
		structure.addEdge(2, 1);
		network.setNetworkStructure(structure.build());

		long resultId = EntityHandler.getInstance().storeNetwork(network);
		EntityManager em = factory.createEntityManager();
		TypedQuery<NetworkMeta> query = em.createQuery("SELECT n FROM Networks AS n WHERE n.networkId = :id",
				NetworkMeta.class);
		query.setParameter("id", resultId);
		List<NetworkMeta> networks = query.getResultList();
		NetworkMeta resultNetwork = networks.get(0);

		assertEquals(1, networks.size());
		assertEquals(graphName, resultNetwork.getName());
		assertNotNull(resultNetwork.getNetworkStructure());
		assertEquals(3, resultNetwork.getNetworkStructure().getNodes().size());
	}

	@Test
	public void getAllNetworks() {

		EntityManager em = factory.createEntityManager();

		long[] networkIds = new long[] { 2, 4, 7 };
		for (long id : networkIds) {
			NetworkMeta network = new NetworkMeta(id);
			em.getTransaction().begin();
			em.persist(network);
			em.getTransaction().commit();
		}

		List<NetworkMeta> resultNetworks = EntityHandler.getInstance().getAllNetworks();
		assertNotNull(resultNetworks);
		assertEquals(networkIds.length, resultNetworks.size());
	}

	//////// Covers ////////////
	
	@Test
	public void getCoverTest() {
		
		AlgorithmType type = AlgorithmType.DMID;
		Cover result;
		
		NetworkMeta network = new NetworkMeta();
		Cover cover = new Cover();
		cover.setAlgorithmType(type);
		cover.setNetwork(network);
		
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(network);
		em.persist(cover);
		em.flush();
		long coverId = cover.getCoverId();
		em.getTransaction().commit();
		
		EntityHandler entityHandler = EntityHandler.getInstance();
		result = entityHandler.getCover(coverId);
		assertNotNull(result);
		assertEquals(cover.getCoverId(), result.getCoverId());
		assertEquals(cover.getAlgorithm(), result.getAlgorithm());
		
		result = entityHandler.getCover(network, type);
		assertNotNull(result);
		assertEquals(cover.getCoverId(), result.getCoverId());
		assertEquals(cover.getAlgorithm(), result.getAlgorithm());
		assertEquals(cover.getNetwork().getNetworkId(), result.getNetwork().getNetworkId());
		
		// with more stored entities
		
		AlgorithmType type2 = AlgorithmType.CLIZZ;
		AlgorithmType type3 = AlgorithmType.SLPA;
		NetworkMeta network2 = new NetworkMeta();
		NetworkMeta network3 = new NetworkMeta();
		Cover cover2 = new Cover();
		Cover cover3 = new Cover();
		cover2.setAlgorithmType(type2);
		cover3.setAlgorithmType(type3);
		cover2.setNetwork(network2);
		cover3.setNetwork(network3);		

		em.getTransaction().begin();
		em.persist(network2);
		em.persist(network3);
		em.persist(cover2);
		em.persist(cover3);
		em.flush();
		em.getTransaction().commit();

		result = entityHandler.getCover(coverId);
		assertNotNull(result);
		assertEquals(cover.getCoverId(), result.getCoverId());
		assertEquals(cover.getAlgorithm(), result.getAlgorithm());
		
		result = entityHandler.getCover(network, type);
		assertNotNull(result);
		assertEquals(cover.getCoverId(), result.getCoverId());
		assertEquals(cover.getAlgorithm(), result.getAlgorithm());
		
		result = entityHandler.getCover(network2, type2);
		assertNotNull(result);
		assertEquals(cover2.getAlgorithm(), result.getAlgorithm());
		
		result = entityHandler.getCover(network3, type3);
		assertNotNull(result);
		assertEquals(cover3.getAlgorithm(), result.getAlgorithm());
		
		// with more covers of same network
		
		cover2.setNetwork(network);
		cover3.setNetwork(network);
		
		em.getTransaction().begin();
		em.persist(cover2);
		em.persist(cover3);
		em.flush();
		em.getTransaction().commit();
		
		result = entityHandler.getCover(network, type3);
		assertNotNull(result);
		assertEquals(cover3.getAlgorithm(), result.getAlgorithm());
		
		result = entityHandler.getCover(network, type);
		assertNotNull(result);
		assertEquals(cover.getAlgorithm(), result.getAlgorithm());
		
		// not existing entities
		
		result = entityHandler.getCover(network2, type);
		assertNull(result);
		
		result = entityHandler.getCover(network3, type2);
		assertNull(result);
		
	}	

	@Test
	public void getCoversTest() {
		
		List<Cover> result;
		
		NetworkMeta network1 = new NetworkMeta();
		Cover cover1 = new Cover();
		Cover cover2 = new Cover();
		Cover cover3 = new Cover();
		cover1.setNetwork(network1);
		cover2.setNetwork(network1);
		cover3.setNetwork(network1);
		
		NetworkMeta network2 = new NetworkMeta();
		Cover cover4 = new Cover();
		cover4.setNetwork(network2);
		
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(network1);
		em.persist(network2);
		em.persist(cover1);
		em.persist(cover2);
		em.persist(cover3);
		em.persist(cover4);
		em.flush();
		em.getTransaction().commit();
		
		EntityHandler entityHandler = EntityHandler.getInstance();
		result = entityHandler.getCovers(network1);
		assertNotNull(result);
		assertEquals(3, result.size());
		
		result = entityHandler.getCovers(network2);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(cover4.getCoverId(), result.get(0).getCoverId());

	}


	/////// Mapping /////////

}
