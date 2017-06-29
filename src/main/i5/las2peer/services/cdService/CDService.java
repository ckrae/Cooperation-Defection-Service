package i5.las2peer.services.cdService;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
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
import i5.las2peer.api.exceptions.RemoteServiceException;
import i5.las2peer.api.exceptions.ServiceNotAvailableException;
import i5.las2peer.api.exceptions.ServiceNotFoundException;
import i5.las2peer.api.exceptions.StorageException;
import i5.las2peer.logging.L2pLogger;
import i5.las2peer.logging.NodeObserver.Event;
import i5.las2peer.restMapper.RESTService;
import i5.las2peer.restMapper.annotations.ServicePath;
import i5.las2peer.security.UserAgent;
import i5.las2peer.services.cdService.data.MappingDataProvider;
import i5.las2peer.services.cdService.data.NetworkDataProvider;
import i5.las2peer.services.cdService.data.SimulationDataProvider;
import i5.las2peer.services.cdService.data.mapping.CoverSimulationSeriesMapping;
import i5.las2peer.services.cdService.data.network.Cover;
import i5.las2peer.services.cdService.data.network.Network;
import i5.las2peer.services.cdService.data.network.NetworkAdapter;
import i5.las2peer.services.cdService.data.simulation.SimulationMeta;
import i5.las2peer.services.cdService.data.simulation.Parameters;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;
import i5.las2peer.services.cdService.simulation.SimulationManager;
import i5.las2peer.services.cdService.simulation.dynamic.DynamicType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;

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
	public static final boolean USE_DATABASE = true;

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

		private final SimulationDataProvider simulationDataProvider = SimulationDataProvider.getInstance();
		private final NetworkDataProvider networkDataProvider = NetworkDataProvider.getInstance();
		private final MappingDataProvider mappingDataProvider = MappingDataProvider.getInstance();

		//////////////////////////////////////////////////////////////////
		///////// RMI Methods
		//////////////////////////////////////////////////////////////////

		//////////////////////////////////////////////////////////////////
		///////// REST Service Methods
		//////////////////////////////////////////////////////////////////

		///////////////////// Simulation ///////////////////////////////
		/**
		 * Gets all the simulations performed by the user
		 * 
		 * @return HttpResponse with the returnString
		 */
		@GET
		@Path("/simulation/")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "GET ALL SIMULATIONS", notes = "Gets all the simulations performed by the user")
		@ApiResponses(value = {
				@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response getSimulations(Parameters parameters) {

			List<SimulationSeries> series = new ArrayList<SimulationSeries>();
			try {
				if (parameters == null) {
					series = simulationDataProvider.getSimulationSeries();
				} else {
					series = simulationDataProvider.getSimulationSeries(parameters);
				}
			} catch (Exception e) {
				L2pLogger.logEvent(this, Event.SERVICE_ERROR, "fail to get simulation series. " + e.toString());
				e.printStackTrace();
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("fail to get simulation series").build();
			}

			return Response.ok().entity(series).build();

		}

		@GET
		@Path("/simulation/meta")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "GET ALL SIMULATIONS", notes = "Gets all the simulations performed by the user")
		@ApiResponses(value = {
				@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response getSimulationMeta(Parameters parameters) {

			if (parameters == null) {
				parameters = new Parameters();
			}

			List<SimulationMeta> seriesMeta = new ArrayList<SimulationMeta>();
			try {
				seriesMeta = simulationDataProvider.getSimulationMeta(parameters);

			} catch (Exception e) {
				L2pLogger.logEvent(this, Event.SERVICE_ERROR, "fail to get simulation series. " + e.toString());
				e.printStackTrace();
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("fail to get simulation series").build();
			}

			return Response.ok().entity(seriesMeta).build();

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
				series = simulationDataProvider.getSimulationSeries(seriesId);
			} catch (Exception e) {
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

			Parameters parameters = null;
			try {
				parameters = simulationDataProvider.getSimulationParameters(seriesId);
			} catch (Exception e) {
				logger.log(Level.WARNING, "fail to get simulation series parameters");
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
		public Response postSimulation(Parameters parameters) {

			String username = ((UserAgent) Context.getCurrent().getMainAgent()).getLoginName();

			/// Validate JSON parameters

			//// Graph
			long graphId = parameters.getGraphId();			
			Network network = networkDataProvider.getNetwork(graphId);
			if (network == null) {
				return Response.status(Status.BAD_REQUEST).entity("Graph not found").build();
			}

			//// Game
			if (parameters.getPayoffValues() == null) {
				return Response.status(Status.BAD_REQUEST).entity("Invalid payoff").build();
			}

			double[] payoffValues = parameters.getPayoffValues();
			if (payoffValues.length != 4 && payoffValues.length != 2) {

				return Response.status(Status.BAD_REQUEST).entity("Invalid payoff").build();
			}

			parameters.normalize();

			//// Dynamic
			if (parameters.getDynamic() == null) {
				return Response.status(Status.BAD_REQUEST).entity("Dynamic does not exist").build();
			}

			/// Start the Simulation
			SimulationSeries series = null;
			try {

				// Simulation
				series = SimulationManager.simulate(parameters, network);

			} catch (Exception e) {
				logger.log(Level.WARNING, "user: " + username, e);
				e.printStackTrace();
				return Response.serverError().entity("Simulation could not be carried out\n" + e.getMessage()).build();
			}

			long result;
			try {
				result = simulationDataProvider.storeSimulationSeries(series);

			} catch (Exception e) {
				e.printStackTrace();
				return Response.serverError().entity("simulation not stored").build();
			}

			return Response.ok().entity("simulation done" + result).build();

		}

		////////////// Information //////////////////

		/**
		 * Returns all available evolutionary dynamics
		 * 
		 * @return HttpResponse with the returnString
		 */
		@GET
		@Path("/info/dynamics")
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "GET Dynamic", notes = "Get all available evolutionary dynamics")
		@ApiResponses(value = { @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response getSimulation() {

			return Response.status(Status.NOT_IMPLEMENTED).entity(DynamicType.values()).build();

		}

		@GET
		@Path("/info/parameters/")
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "GET ALL SIMULATIONS", notes = "Gets all the simulations performed by the user")
		@ApiResponses(value = {
				@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response getSimulations() {

			Parameters paramters = new Parameters();

			return Response.ok().entity(paramters).build();

		}

		///////////////////// Network ///////////////////////////////
		/**
		 * Gets all networks reachable by this service
		 * 
		 * @return HttpResponse with the returnString
		 * @throws RemoteServiceException
		 * @throws ServiceNotAvailableException
		 * @throws ServiceNotFoundException
		 */
		@GET
		@Path("/network/")
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "GET ALL SIMULATIONS", notes = "Gets all networks reachable by this service")
		@ApiResponses(value = {
				@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response getNetworks()
				throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

			String username = ((UserAgent) Context.getCurrent().getMainAgent()).getLoginName();
			ArrayList<Long> networkIds = new ArrayList<Long>();
			try {
				networkIds = NetworkAdapter.invokeGraphIds();
			} catch (Exception e) {
				logger.log(Level.WARNING, "user: " + username, e);
				e.printStackTrace();
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("fail to get networks").build();
			}

			return Response.ok().entity(networkIds).build();

		}

		///////////////////// Cover ///////////////////////////////

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
				e.printStackTrace();
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
				e.printStackTrace();
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("fail to get networks").build();
			}

			return Response.ok().entity(cover).build();

		}

		///////////////////// Mapping ///////////////////////////////

		@GET
		@Path("/mapping/simulation/{seriesId}")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "GET SIMULATION NETWORK COVER MAPPING", notes = "Get the ")
		@ApiResponses(value = {
				@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response getSimulationMapping(@PathParam("seriesId") long seriesId) {

			SimulationSeries series;
			try {
				series = simulationDataProvider.getSimulationSeries(seriesId);
			} catch (Exception e) {
				logger.log(Level.WARNING, "", e);
				e.printStackTrace();
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("fail to get simulation series").build();
			}

			ArrayList<Cover> covers = new ArrayList<Cover>();
			try {
				ArrayList<Integer> coverIds = NetworkAdapter.inovkeCovers(series.getParameters().getGraphId());

				for (Integer coverId : coverIds) {
					covers.add(NetworkAdapter.inovkeCoverById(series.getParameters().getGraphId(), coverId));
				}
			} catch (Exception e) {
				logger.log(Level.WARNING, "", e);
				e.printStackTrace();
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("fail to get covers").build();
			}

			ArrayList<SimulationSeries> seriesList = new ArrayList<SimulationSeries>(1);
			seriesList.add(series);
			ArrayList<CoverSimulationSeriesMapping> mappings = null;
			try {
				mappings = mappingDataProvider.getCoverSimulationSeriesMappings(covers, seriesList);
			} catch (Exception e) {
				logger.log(Level.WARNING, "", e);
				e.printStackTrace();
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("fail to get mappings").build();
			}

			return Response.ok().entity(mappings).build();
		}
	}

}
