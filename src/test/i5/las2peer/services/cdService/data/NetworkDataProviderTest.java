package i5.las2peer.services.cdService.data;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

import i5.las2peer.api.exceptions.ServiceInvocationException;
import i5.las2peer.services.cdService.data.network.Graph;

public class NetworkDataProviderTest {
	
	private static final String PERSISTENCE_UNIT_NAME = "SimulationTest";
	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	
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

		Graph resultNetwork = null;
		try {
			resultNetwork = NetworkDataProvider.getInstance().getNetwork(networkId);
		} catch (ServiceInvocationException e) {
			e.printStackTrace();
		}
		assertNotNull(resultNetwork);
		assertEquals(networkId, resultNetwork.getNetworkId());
		assertEquals(graphName, resultNetwork.getGraphName());
		
		
	}
	
}
