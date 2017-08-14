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
import i5.las2peer.logging.L2pLogger;
import i5.las2peer.logging.NodeObserver.Event;
import i5.las2peer.restMapper.RESTService;
import i5.las2peer.restMapper.annotations.ServicePath;
import i5.las2peer.security.UserAgent;
import i5.las2peer.services.cdService.data.CoverDataProvider;
import i5.las2peer.services.cdService.data.MappingDataProvider;
import i5.las2peer.services.cdService.data.NetworkDataProvider;
import i5.las2peer.services.cdService.data.SimulationDataProvider;
import i5.las2peer.services.cdService.data.mapping.CoverSimulationSeriesMapping;
import i5.las2peer.services.cdService.data.network.NetworkMeta;
import i5.las2peer.services.cdService.data.network.NetworkStructure;
import i5.las2peer.services.cdService.data.network.cover.Cover;
import i5.las2peer.services.cdService.data.network.GraphAdapter;
import i5.las2peer.services.cdService.data.simulation.GroupParameters;
import i5.las2peer.services.cdService.data.simulation.MetaData;
import i5.las2peer.services.cdService.data.simulation.Parameters;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;
import i5.las2peer.services.cdService.data.simulation.SimulationSeriesGroup;
import i5.las2peer.services.cdService.simulation.SimulationBuilder;
import i5.las2peer.services.cdService.simulation.dynamic.DynamicType;
import i5.las2peer.services.cdService.simulation.game.GameType;
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
		private final CoverDataProvider coverDataProvider = CoverDataProvider.getInstance();

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

			List<SimulationSeries> series = new ArrayList<>();
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

			List<MetaData> seriesMeta = new ArrayList<>();
			try {
				seriesMeta = simulationDataProvider.getSimulationMeta(null);

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

			//// NetworkMeta
			long graphId = parameters.getGraphId();

			NetworkMeta network = networkDataProvider.getNetwork(graphId);
			if (network == null)
				return Response.status(Status.BAD_REQUEST).entity("network not found").build();

			//// Game
			if (parameters.getPayoffCC() == 0.0 && parameters.getPayoffCD() == 0.0 && parameters.getPayoffDC() == 0.0
					&& parameters.getPayoffDD() == 0.0) {

				if (parameters.getBenefit() == 0.0 && parameters.getCost() == 0.0) {
					return Response.status(Status.BAD_REQUEST).entity("invalid payoff").build();
				}

			}

			parameters.normalize();

			//// Dynamic
			if (parameters.getDynamic() == null || parameters.getDynamic() == DynamicType.UNKNOWN) {
				return Response.status(Status.BAD_REQUEST).entity("dynamic does not exist").build();
			}

			/// Start the Simulation
			SimulationSeries series = null;
			try {

				// Simulation
				SimulationBuilder simulationBuilder = new SimulationBuilder();
				simulationBuilder.setParameters(parameters);
				simulationBuilder.setNetwork(network);
				series = simulationBuilder.simulate();

			} catch (Exception e) {
				logger.log(Level.WARNING, "user: " + username, e);
				e.printStackTrace();
				return Response.serverError().entity("simulation could not be carried out\n" + e.getMessage()).build();
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
		 * Returns all available dynamics
		 * 
		 * @return HttpResponse with the returnString
		 */
		@GET
		@Path("/info/dynamics")
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "GET Dynamic", notes = "Get all available evolutionary dynamics")
		@ApiResponses(value = { @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response getDynamics() {

			return Response.status(Status.OK).entity(DynamicType.values()).build();

		}

		/**
		 * Returns all available games
		 * 
		 * @return HttpResponse with the returnString
		 */
		@GET
		@Path("/info/games")
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "GET Game", notes = "Get all available games")
		@ApiResponses(value = { @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response getGames() {

			return Response.status(Status.OK).entity(GameType.values()).build();

		}

		@GET
		@Path("/info/parameters/")
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "GET ALL SIMULATIONS", notes = "Gets a sample parameter json")
		@ApiResponses(value = {
				@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response getSimulations() {

			Parameters parameters = new Parameters();
			parameters.setGraphId(2);
			parameters.setGame(GameType.PRISONERS_DILEMMA);
			parameters.setDynamic(DynamicType.REPLICATOR);
			parameters.setPayoffCC(2);
			parameters.setPayoffCD(0);
			parameters.setPayoffDC(3);
			parameters.setPayoffDD(1);
			parameters.setIterations(200);

			return Response.ok().entity(parameters).build();

		}

		///////////////////// Simulation Group ///////////////////////////////

		/**
		 * Starts a scaling simulation series group
		 * 
		 * @param JSON
		 * 
		 * @return HttpResponse with the returnString
		 */
		@POST
		@Path("/simulation/series")
		@Produces(MediaType.TEXT_PLAIN)
		@Consumes(MediaType.APPLICATION_JSON)
		@ApiResponses(value = { @ApiResponse(code = HttpURLConnection.HTTP_OK, message = "OK"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		@ApiOperation(value = "POST SIMULATION", notes = " Starts the simulation of a evolutionary cooperation and defection game ")
		public Response postSimulationGroup(GroupParameters parameters) {
			try {
				try {
					if (!parameters.validate())
						badRequest("invalid parameters");
				} catch (Exception e) {
					e.printStackTrace();
					return badRequest("failed parse json parameters");
				}

				long graphId = parameters.getGraphId();
				NetworkStructure network = null;
				try {
					network = networkDataProvider.getNetwork(graphId).getNetworkStructure();
				} catch (Exception e) {
					e.printStackTrace();
					return internal("failed get graph");
				}

				SimulationSeriesGroup group = new SimulationSeriesGroup();
				try {
					SimulationBuilder simulationBuilder = new SimulationBuilder();
					group = simulationBuilder.simulate(parameters, network);
				} catch (Exception e) {
					logger.log(Level.WARNING, "Simulate Series Group", e);
					e.printStackTrace();
					return internal("failed to simulate");
				}

				try {
					simulationDataProvider.storeSimulationGroup(group);
				} catch (Exception e) {
					e.printStackTrace();
					return Response.serverError().entity("failed to store simulations").build();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return Response.serverError().entity("internal error").build();
			}

			return success("simulation done ");

		}

		/**
		 * Gets all the simulations performed by the user
		 * 
		 * @return HttpResponse with the returnString
		 */
		@GET
		@Path("/simulation/series")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "GET ALL SIMULATIONS", notes = "Gets all the simulations performed by the user")
		@ApiResponses(value = {
				@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response getSimulationSeriesGroup(GroupParameters parameters) {

			List<SimulationSeriesGroup> simulations = new ArrayList<>();
			try {
				simulations = simulationDataProvider.getSimulationSeriesGroups();
			} catch (Exception e) {
				L2pLogger.logEvent(this, Event.SERVICE_ERROR, "fail to get simulation series. " + e.toString());
				e.printStackTrace();
				return internal("fail to get simulation series group");
			}
			return Response.ok().entity(simulations).build();

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
		@Path("/networks/")
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "GET ALL Networks", notes = "Gets all networks reachable by this service")
		@ApiResponses(value = {
				@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response getNetworks()
				throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

			long userId = ((UserAgent) Context.getCurrent().getMainAgent()).getId();
			List<NetworkMeta> networks;
			try {
				networks = networkDataProvider.getNetworks(userId);
			} catch (Exception e) {
				logger.log(Level.WARNING, "user: " + userId, e);
				e.printStackTrace();
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("fail to get networks").build();
			}
			return Response.ok().entity(networks).build();
		}

		@GET
		@Path("/networks/external")
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "GET ALL Networks", notes = "Gets all networks reachable by this service")
		@ApiResponses(value = {
				@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response getExternalNetworks()
				throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

			long userId = ((UserAgent) Context.getCurrent().getMainAgent()).getId();
			List<NetworkMeta> networks;
			try {
				networks = networkDataProvider.getExternalNetworks(userId);
			} catch (Exception e) {
				logger.log(Level.WARNING, "user: " + userId, e);
				e.printStackTrace();
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("fail to get networks").build();
			}
			return Response.ok().entity(networks).build();
		}

		@GET
		@Path("/networks/import")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		@ApiOperation(value = "GET ALL Networks", notes = "Gets all networks reachable by this service")
		@ApiResponses(value = {
				@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response getExternalNetworks(List<Long> ocdIds)
				throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

			long userId = ((UserAgent) Context.getCurrent().getMainAgent()).getId();
			List<NetworkMeta> networks;
			try {
				ocdIds = new ArrayList<Long>();
				ocdIds.add((long) 3);
				networks = networkDataProvider.getExternalNetworks(ocdIds);
			} catch (Exception e) {
				logger.log(Level.WARNING, "user: " + userId, e);
				e.printStackTrace();
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("fail to get networks").build();
			}

			for (NetworkMeta network : networks) {
				try {
					networkDataProvider.storeNetwork(network);
				} catch (Exception e) {
					logger.log(Level.WARNING, "user: " + userId, e);
					e.printStackTrace();
				}
			}

			return success("OK");
		}

		@GET
		@Path("/network/{networkId}")
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "GET ALL Networks", notes = "Gets all networks reachable by this service")
		@ApiResponses(value = {
				@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response getNetwork(@PathParam("networkId") long networkId)
				throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

			long userId = ((UserAgent) Context.getCurrent().getMainAgent()).getId();
			NetworkMeta network;
			try {
				network = networkDataProvider.getNetwork(networkId);
			} catch (Exception e) {
				logger.log(Level.WARNING, "user: " + userId, e);
				e.printStackTrace();
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("fail to get network").build();
			}
			return Response.ok().entity(network).build();
		}

		@POST
		@Path("/network/")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.TEXT_PLAIN)
		@ApiOperation(value = "POST Network", notes = "")
		@ApiResponses(value = {
				@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response postNetwork(String content)
				throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

			long userId = ((UserAgent) Context.getCurrent().getMainAgent()).getId();
			List<NetworkMeta> networks;
			try {
				networks = NetworkDataProvider.getInstance().getNetworks(userId);
			} catch (Exception e) {
				logger.log(Level.WARNING, "user: " + userId, e);
				e.printStackTrace();
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("fail to get networks").build();
			}

			return Response.ok().entity(networks).build();

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

			NetworkMeta network;
			try {
				network = networkDataProvider.getNetwork(graphId);
			} catch (Exception e) {
				logger.log(Level.WARNING, "user: " + username, e);
				e.printStackTrace();
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("fail to get network").build();
			}

			List<Cover> covers;
			try {
				covers = coverDataProvider.getCovers(network);
			} catch (Exception e) {
				logger.log(Level.WARNING, "user: " + username, e);
				e.printStackTrace();
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("fail to get covers").build();
			}

			return Response.ok().entity(covers).build();

		}

		@GET
		@Path("/cover/{graphId}/{algorithm}")
		@Produces(MediaType.APPLICATION_JSON)
		@ApiOperation(value = "GET ALL SIMULATIONS", notes = "Gets all networks registred by the cdservice")
		@ApiResponses(value = {
				@ApiResponse(code = HttpURLConnection.HTTP_OK, message = "REPLACE THIS WITH YOUR OK MESSAGE"),
				@ApiResponse(code = HttpURLConnection.HTTP_UNAUTHORIZED, message = "Unauthorized") })
		public Response getCover(@PathParam("graphId") long graphId, @PathParam("algorithm") String algorithm) {

			String username = ((UserAgent) Context.getCurrent().getMainAgent()).getLoginName();

			NetworkMeta network;
			try {
				network = networkDataProvider.getNetwork(graphId);
			} catch (Exception e) {
				logger.log(Level.WARNING, "user: " + username, e);
				e.printStackTrace();
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("fail to get network").build();
			}

			Cover cover = null;
			try {
				cover = coverDataProvider.getCover(network, algorithm);
			} catch (Exception e) {
				logger.log(Level.WARNING, "user: " + username, e);
				e.printStackTrace();
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("fail to get cover").build();
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

			List<CoverSimulationSeriesMapping> mappings;
			try {
				mappings = mappingDataProvider.getCoverSimulationSeriesMappings(seriesId);
			} catch (Exception e) {
				logger.log(Level.WARNING, "", e);
				e.printStackTrace();
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity("fail to get mappings").build();
			}

			return Response.ok().entity(mappings).build();
		}

		/////// Response Utility ///////

		public Response badRequest(String message) {
			return Response.status(Status.BAD_REQUEST).entity(message).build();
		}

		public Response internal(String message) {
			return Response.serverError().entity(message).build();
		}

		public Response success(String message) {
			return Response.ok().entity(message).build();
		}

	}

}
