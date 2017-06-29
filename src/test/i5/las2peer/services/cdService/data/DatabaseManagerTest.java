package i5.las2peer.services.cdService.data;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.eclipse.persistence.jpa.PersistenceProvider;
import org.junit.Test;

import i5.las2peer.services.cdService.data.simulation.SimulationSeries;

public class DatabaseManagerTest {

	private static final String PERSISTENCE_UNIT_NAME = "SimulationTest";
	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); 
	
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
	
	
	@Test
	public void testFactory() {
		
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("javax.persistence.jdbc.driver", "org.hsqldb.jdbcDriver");
		properties.put("javax.persistence.jdbc.url", "jdbc:hsqldb:file:db/db_Simulation;shutdown=true;");
		properties.put("javax.persistence.jdbc.user", "cdUser");
		properties.put("javax.persistence.jdbc.password", "cdPass");
		properties.put("eclipselink.ddl-generation", "create-tables");
		properties.put("eclipselink.ddl-generation.output-mode", "database");
		
		
		EntityManagerFactory emf = new PersistenceProvider().createEntityManagerFactory("Simulation", null);
		EntityManager em = emf.createEntityManager();
	}

}
