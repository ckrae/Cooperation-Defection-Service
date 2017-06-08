package i5.las2peer.services.cdService;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.logging.Level;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import i5.las2peer.api.Context;
import i5.las2peer.api.exceptions.StorageException;
import i5.las2peer.logging.L2pLogger;
import i5.las2peer.restMapper.RESTService;
import i5.las2peer.restMapper.annotations.ServicePath;
import i5.las2peer.security.UserAgent;
import i5.las2peer.services.cdService.data.manager.DataManager;
import i5.las2peer.services.cdService.data.simulation.SimulationContainer;
import i5.las2peer.services.cdService.data.simulation.SimulationParameters;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;
import i5.las2peer.services.cdService.network.Cover;
import i5.las2peer.services.cdService.network.NetworkAdapter;
import i5.las2peer.services.cdService.network.NetworkManager;
import i5.las2peer.services.cdService.simulation.SimulationManager;
import i5.las2peer.services.cdService.simulation.dynamics.DynamicType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import sim.field.network.Network;

/**
 * Cooperation Defection Service
 * 
 * This is a las2peer service that performs simulation of spatial evolutionary
 * cooperation defection games It depends on the OCD Service of WebOCD
 * 
 */

@ServicePath("cdservice")
public class CDService extends RESTService {

	public static final String SERVICE_PREFIX = "cdservice";

	/**
	 * If activated the service will get the graphs of another service named by
	 * GRAPH_SERVICE
	 */
	public static final boolean USE_GRAPH_RMI = false;

	/**
	 * names the service where the graphs are taken from
	 */
	public final static String GRAPH_SERVICE = "i5.las2peer.services.ocd.ServiceClass@1.0";

	/**
	 * names the service for the overlapping community detection
	 */
	public final static String OCD_SERVICE = "i5.las2peer.services.ocd.ServiceClass@1.0";

	/**
	 * If activated the service will use a database to store the simulation data
	 */
	public static final boolean USE_DATABASE = false;
	public static final boolean USE_STORAGE = true;
	public static final boolean USE_FILES = true;

	@Override
	protected void initResources() {
		getResourceConfig().register(RootResource.class);
	}

	public CDService() {
		setFieldValues();
	}

	@Api
	@SwaggerDefinition(info = @Info(title = "Cooperation Defection Service", version = "0.1", description = "A las2peer Service for spatial cooperation & defection multi agent simulations", termsOfService = "http://your-terms-of-service-url.com", contact = @Contact(name = "Christoph Kraemer", url = "provider.com", email = "john.doe@provider.com"), license = @License(name = "Apache License 2", url = "http://www.apache.org/licenses/LICENSE-2.0")))
	@Path("/")
	public static class RootResource {
		// instantiate the logger class
		private final L2pLogger logger = L2pLogger.getInstance(CDService.class.getName());

		// get access to the service class
		private final CDService service = (CDService) Context.getCurrent().getService();

		//////////////////////////////////////////////////////////////////
		///////// RMI Methods
		//////////////////////////////////////////////////////////////////

		//////////////////////////////////////////////////////////////////
		///////// REST Service Methods
		//////////////////////////////////////////////////////////////////

		///////////////////// Simulations ///////////////////////////////
		/**
		 * Gets all the simulations performed by the user
		 * 
		 * @return HttpResponse with the returnString
		 */
		@GET
		@Path("/simulation/")
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "GET ALL SIMULATIONS", notes = "Gets all the simulations performed by the user")
		@ApiResponses(value = {
				@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response getAllSimulations() {

			String username = ((UserAgent) Context.getCurrent().getMainAgent()).getLoginName();
			TreeSet<Long> seriesIndexes = new TreeSet<Long>();
			SimulationContainer container;
			try {
				seriesIndexes = DataManager.getSimulationSeriesIndices();
				container = DataManager.getSimulationContainer();
			} catch (Exception e) {
				logger.log(Level.WARNING, "user: " + username, e);
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("fail to get simulation series").build();
			}

			return Response.ok().entity(container).build();

		}

		/**
		 * Gets the results of a performed simulation series on a network
		 * 
		 * @return HttpResponse with the returnString
		 */
		@GET
		@Path("/simulation/{seriesId}")
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "GET SIMULATION", notes = "Gets the results of a performed simulation")
		@ApiResponses(value = {
				@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response getSimulation(@PathParam("seriesId") long seriesId) {

			String username = ((UserAgent) Context.getCurrent().getMainAgent()).getLoginName();
			SimulationSeries series = null;

			try {
				series = DataManager.getSimulationSeries(seriesId);
			} catch (StorageException e) {
				logger.log(Level.WARNING, "user: " + username, e);
				e.printStackTrace();
				return Response.status(Status.BAD_REQUEST).entity("no simulation with id " + seriesId + " found")
						.build();
			}

			return Response.ok().entity(series).build();
		}

		/**
		 * Gets the parameters of a simulation
		 * 
		 * @return HttpResponse with the returnString
		 */
		@GET
		@Path("/simulation/{seriesId}/parameters")
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "GET SIMULATION PARAMETERS", notes = "Gets the parameters of a simulation")
		@ApiResponses(value = {
				@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response getSimulationParameters(@PathParam("seriesId") long seriesId) {

			String username = ((UserAgent) Context.getCurrent().getMainAgent()).getLoginName();
			SimulationParameters parameters = null;

			try {
				parameters = DataManager.getSimulationParameters(seriesId);
			} catch (Exception e) {
				logger.log(Level.WARNING, "user: " + username, e);
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("fail to get simulation series parameters")
						.build();
			}

			return Response.ok().entity(parameters).build();
		}

		/**
		 * Deletes a performed simulation series on a network
		 * 
		 * @return HttpResponse with the returnString
		 */
		@DELETE
		@Path("/simulation/{seriesId}")
		@Produces(MediaType.TEXT_PLAIN)
		@ApiOperation(value = "DELETE SIMULATION", notes = "Deletes a performed simulation")
		@ApiResponses(value = {
				@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response deleteSimulation() {

			return Response.status(Status.NOT_IMPLEMENTED).entity("").build();

		}

		/**
		 * Starts the simulation of a evolutionary cooperation and defection
		 * game
		 * 
		 * @param JSON
		 * 
		 * @return HttpResponse with the returnString
		 */
		@POST
		@Path("/simulation")
		@Produces(MediaType.TEXT_PLAIN)
		@Consumes(MediaType.APPLICATION_JSON)
		@ApiResponses(value = { @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		@ApiOperation(value = "POST SIMULATION", notes = " Starts the simulation of a evolutionary cooperation and defection game ")
		public Response postSimulation(SimulationParameters parameters) {

			String username = ((UserAgent) Context.getCurrent().getMainAgent()).getLoginName();

			/// Validate JSON parameters

			//// Graph
			long graphId = parameters.getGraphId();
			Network network = null;
			try {
				network = NetworkManager.getNetwork(graphId);
			} catch (StorageException e) {
				return Response.status(Status.BAD_REQUEST).entity("Graph not found").build();
			}

			if (network.equals(null)) {
				return Response.status(Status.BAD_REQUEST).entity("Graph not found").build();
			}

			//// Game
			double[] payoffValues = parameters.getPayoffValues();
			if (payoffValues.length != 4 && payoffValues.length != 2) {

				return Response.status(Status.BAD_REQUEST).entity("Invalid payoff").build();
			}

			//// Dynamic
			String dynamicStr = parameters.getDynamic();
			if (!DynamicType.TypeExists(dynamicStr)) {
				return Response.status(Status.BAD_REQUEST).entity("Dynamic does not exist").build();
			}

			/// Start the Simulation
			SimulationSeries series = null;
			try {

				// Simulation
				series = SimulationManager.simulate(parameters, network);

			} catch (Exception e) {
				logger.log(Level.WARNING, "user: " + username, e);
				return Response.serverError().entity("Simulation could not be carried out").build();
			}

			boolean result = DataManager.storeSimulationSeries(series);

			if (result) {
				return Response.ok().entity("simulation done").build();
			}
			return Response.serverError().entity("simulation not stored").build();

		}

		////////// //////////////////

		/**
		 * Returns all available evolutionary dynamics
		 * 
		 * @return HttpResponse with the returnString
		 */
		@GET
		@Path("/dynamics")
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "GET Dynamic", notes = "Get all available evolutionary dynamics")
		@ApiResponses(value = { @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response getSimulation() {

			return Response.status(Status.NOT_IMPLEMENTED).entity(DynamicType.values()).build();

		}

		///////////////////// Networks ///////////////////////////////
		/**
		 * Gets all the simulations performed by the user
		 * 
		 * @return HttpResponse with the returnString
		 */
		@GET
		@Path("/network/")
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "GET ALL SIMULATIONS", notes = "Gets all networks registred by the cdservice")
		@ApiResponses(value = {
				@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response getNetworks() {

			String username = ((UserAgent) Context.getCurrent().getMainAgent()).getLoginName();
			TreeSet<Long> networkIndices = new TreeSet<Long>();
			try {
				networkIndices = NetworkManager.getNetworkIndices();
			} catch (Exception e) {
				logger.log(Level.WARNING, "user: " + username, e);
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("fail to get networks").build();
			}

			return Response.ok().entity(networkIndices).build();

		}

		///////////////////// Covers ///////////////////////////////

		@GET
		@Path("/cover/{graphId}")
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "GET ALL SIMULATIONS", notes = "Gets all networks registred by the cdservice")
		@ApiResponses(value = {
				@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response getCover(@PathParam("graphId") long graphId) {

			String username = ((UserAgent) Context.getCurrent().getMainAgent()).getLoginName();

			ArrayList<Integer> covers = null;
			try {
				covers = NetworkAdapter.inovkeCovers(graphId);
			} catch (Exception e) {
				logger.log(Level.WARNING, "user: " + username, e);
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("fail to get networks").build();
			}

			return Response.ok().entity(covers).build();

		}

		@GET
		@Path("/cover/{graphId}/{coverId}")
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "GET ALL SIMULATIONS", notes = "Gets all networks registred by the cdservice")
		@ApiResponses(value = {
				@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response getCover(@PathParam("graphId") long graphId, @PathParam("coverId") long coverId) {

			String username = ((UserAgent) Context.getCurrent().getMainAgent()).getLoginName();

			Cover cover = null;
			try {
				cover = NetworkAdapter.inovkeCoverById(graphId, coverId);
			} catch (Exception e) {
				logger.log(Level.WARNING, "user: " + username, e);
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("fail to get networks").build();
			}

			return Response.ok().entity(cover).build();

		}

		///////////////////// Mapping ///////////////////////////////

		@GET
		@Path("/simulation/mapping/")
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "GET SIMULATION NETWORK COVER MAPPING", notes = "Get the ")
		@ApiResponses(value = {
				@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response getNetworks1() {

			String username = ((UserAgent) Context.getCurrent().getMainAgent()).getLoginName();

			return Response.status(Status.NOT_IMPLEMENTED).build();

		}

	}

}
