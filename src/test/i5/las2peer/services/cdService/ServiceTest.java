package i5.las2peer.services.cdService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import i5.las2peer.api.Context;
import i5.las2peer.api.exceptions.StorageException;
import i5.las2peer.p2p.LocalNode;
import i5.las2peer.p2p.PastryNodeImpl;
import i5.las2peer.p2p.ServiceNameVersion;
import i5.las2peer.persistency.Envelope;
import i5.las2peer.persistency.SharedStorage.STORAGE_MODE;
import i5.las2peer.security.Agent;
import i5.las2peer.security.ServiceAgent;
import i5.las2peer.security.UserAgent;
import i5.las2peer.services.cdService.data.network.Network;
import i5.las2peer.services.cdService.data.network.NetworkContainer;
import i5.las2peer.services.cdService.data.provider.NetworkDataProvider;
import i5.las2peer.testing.MockAgentFactory;
import i5.las2peer.testing.TestSuite;
import i5.las2peer.tools.CryptoException;
import i5.las2peer.tools.SerializationException;
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

	private static ArrayList<PastryNodeImpl> nodes;
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

		//nodes = TestSuite.launchNetwork(1, STORAGE_MODE.FILESYSTEM, true);
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

		storeTestNetwork();
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
			
			
			
			result = c.sendRequest("POST", mainPath + "simulation",	"{\"graphId\":0,\"dynamic\":\"Replicator\",\"dynamicValues\":[],\"payoffValues\":[1.0,2.0,3.1,0.0],\"iterations\":20}", "application/json", "text/plain", new HashMap<String,String>());
			assertEquals(200, result.getHttpCode());
			assertTrue(result.getResponse().trim().contains("done"));
			System.out.println("Result of 'testPost': " +	result.getResponse().trim());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Exception: " + e);
		}
	}

	private static void storeTestNetwork() {

		Network network = new Network(0);

		long userId = agentAdam.getId();
		String identifier = CDService.SERVICE_PREFIX + String.valueOf(userId) + "#network" + String.valueOf(0);

		
		try {
			Envelope env = node.createEnvelope(identifier, network, agentAdam);
			node.storeEnvelope(env, agentAdam, userId);

		} catch (CryptoException | SerializationException | StorageException e) {
			e.printStackTrace();
		}
	}
	
	
}

