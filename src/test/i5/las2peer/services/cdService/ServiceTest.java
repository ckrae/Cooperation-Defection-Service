package i5.las2peer.services.cdService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import i5.las2peer.p2p.LocalNode;
import i5.las2peer.p2p.ServiceNameVersion;
import i5.las2peer.security.ServiceAgent;
import i5.las2peer.security.UserAgent;
import i5.las2peer.services.cdService.data.EntityHandler;
import i5.las2peer.services.cdService.data.network.Graph;
import i5.las2peer.testing.MockAgentFactory;
import i5.las2peer.webConnector.WebConnector;
import i5.las2peer.webConnector.client.ClientResponse;
import i5.las2peer.webConnector.client.MiniClient;

/**
 * Example Test Class demonstrating a basic JUnit test structure.
 *
 */
public class ServiceTest {

	private static final String HTTP_ADDRESS = "http://127.0.0.1";
	private static final int HTTP_PORT = WebConnector.DEFAULT_HTTP_PORT;

	private static LocalNode node;
	private static WebConnector connector;
	private static ByteArrayOutputStream logStream;

	private static UserAgent agentAdam;
	private static UserAgent agentEve;
	private static UserAgent agentAbel;
	private static final String passAdam = "adamspass";
	private static final String passEve = "evespass";
	private static final String passAbel = "abelspass";

	private static final String mainPath = "cdService/";
	
	private static final String PERSISTENCE_UNIT_NAME = "SimulationTest";
	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	private static final EntityHandler entityHandler = EntityHandler.getTestInstance();
	
	private long networkId;

	/**
	 * Called before the tests start.
	 * 
	 * Sets up the node and initializes connector and users that can be used
	 * throughout the tests.
	 * 
	 * @throws Exception
	 */
	@BeforeClass
	public static void startServer() throws Exception {

		node = LocalNode.launchNode();
		agentAdam = MockAgentFactory.getAdam();
		agentAdam.unlockPrivateKey(passAdam);
		agentEve = MockAgentFactory.getEve();
		agentEve.unlockPrivateKey(passEve);
		agentAbel = MockAgentFactory.getAbel();
		agentAbel.unlockPrivateKey(passAbel);
		node.storeAgent(agentAdam);
		node.storeAgent(agentEve);
		node.storeAgent(agentAbel);

		ServiceAgent testService = ServiceAgent
				.createServiceAgent(ServiceNameVersion.fromString(CDService.class.getName() + "@1.0"), "a pass");
		testService.unlockPrivateKey("a pass");				

		node.registerReceiver(testService);

		logStream = new ByteArrayOutputStream();

		connector = new WebConnector(true, HTTP_PORT, false, 1000);
		connector.setLogStream(new PrintStream(logStream));
		connector.start(node);
		Thread.sleep(1000);
		agentAdam = MockAgentFactory.getAdam();
		agentEve = MockAgentFactory.getEve();
		agentAbel = MockAgentFactory.getAbel();
		
		

	}
	
	@Before
	public void storeTestNetwork() {		

		Graph network = new Graph();
		network.setGraphName("TestGraph");		
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(network);
		em.flush();
		networkId = network.getNetworkId();
		System.out.println(networkId);		
		em.getTransaction().commit();
		em.close();
		
	}
	
	
	/**
	 * Called after the tests have finished. Shuts down the server and prints
	 * out the connector log file for reference.
	 * 
	 * @throws Exception
	 */
	@AfterClass
	public static void shutDownServer() throws Exception {

		connector.stop();
		node.shutDown();

		connector = null;

		node = null;

		System.out.println("Connector-Log:");
		System.out.println("--------------");

		System.out.println(logStream.toString());

	}

	@Test
	public void testPostSimulation() {
		MiniClient c = new MiniClient();
		c.setAddressPort(HTTP_ADDRESS, HTTP_PORT);
		assertTrue(true);

		try {
			c.setLogin(Long.toString(agentAdam.getId()), passAdam);
			ClientResponse result;			
			
			// No valid Payoff
			result = c.sendRequest("POST", mainPath + "simulation",	"{\"graphId\":"+ networkId + ",\"dynamic\":\"\",\"dynamicValues\":[],\"payoffValues\":[1.0,2.0,3.1,0.0],\"iterations\":20}", "application/json", "text/plain", new HashMap<String,String>());
			assertEquals(400, result.getHttpCode());
			System.out.println("Result of 'testPost': " +	result.getResponse().trim());
			assertTrue(result.getResponse().contains("payoff"));
			
			// No OCD Service accessible
			result = c.sendRequest("POST", mainPath + "simulation",	"{\"graphId\":"+ networkId + ",\"dynamic\":\"Replicator\",\"dynamicValues\":[],\"payoffValues\":[1.0,2.0,3.1,0.0],\"iterations\":20}", "application/json", "text/plain", new HashMap<String,String>());
			assertEquals(500, result.getHttpCode());
			assertTrue(result.getResponse().contains("invocation"));
			System.out.println("Result of 'testPost': " +	result.getResponse().trim());
		

			
		
		
		
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception: " + e);
		}
	}

	
	
	
}

