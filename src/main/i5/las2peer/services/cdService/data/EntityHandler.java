package i5.las2peer.services.cdService.data;

import java.util.List;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import i5.las2peer.api.Context;
import i5.las2peer.services.cdService.data.network.NetworkMeta;
import i5.las2peer.services.cdService.data.network.cover.AlgorithmType;
import i5.las2peer.services.cdService.data.network.cover.Cover;
import i5.las2peer.services.cdService.data.simulation.Parameters;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;
import i5.las2peer.services.cdService.data.simulation.SimulationSeriesGroup;

public class EntityHandler {

	private static EntityHandler databaseManager;
	private EntityManagerFactory factory;

	private EntityHandler() {

		factory = PersistenceUtil.getEntityManagerFactory();
	}

	public static synchronized EntityHandler getInstance() {
		if (databaseManager == null) {
			databaseManager = new EntityHandler();
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

	protected synchronized long storeSimulationSeries(SimulationSeries series) {

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

	protected List<SimulationSeries> getSimulationSeries(Parameters parameters) {

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

	protected List<SimulationSeries> getSimulationSeries() {

		EntityManager em = factory.createEntityManager();

		TypedQuery<SimulationSeries> query = em.createQuery("SELECT s FROM SimulationSeries s", SimulationSeries.class);
		List<SimulationSeries> seriesList = query.getResultList();
		return seriesList;
	}
	
	/////// Groups ///////
	
	protected synchronized long storeSimulation(SimulationSeriesGroup group) {
		
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(group);
		em.flush();
		em.getTransaction().commit();

		long id = group.getId();
		em.close();

		return id;
	}	

	protected SimulationSeriesGroup getSimulationSeriesGroup(long id) {

		EntityManager em = factory.createEntityManager();
		TypedQuery<SimulationSeriesGroup> query = em.createQuery("SELECT s FROM SimulationSeriesGroup AS s WHERE s.id =:id",
				SimulationSeriesGroup.class);
		query.setParameter("id", id);
		SimulationSeriesGroup simulation = query.getSingleResult();
		return simulation;
	}
	
	protected List<SimulationSeriesGroup> getSimulationSeriesGroups() {

		EntityManager em = factory.createEntityManager();

		TypedQuery<SimulationSeriesGroup> query = em.createQuery("SELECT s FROM SimulationSeriesGroup s", SimulationSeriesGroup.class);
		List<SimulationSeriesGroup> list = query.getResultList();
		return list;
	}
	

	//////////////// Network ///////////////////////

	protected NetworkMeta getNetwork(long networkId) {

		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<NetworkMeta> query = em.createQuery("SELECT n FROM Networks AS n WHERE n.networkId =:id", NetworkMeta.class);
			query.setParameter("id", networkId);
			NetworkMeta network = query.getSingleResult();
			return network;
		} catch (NoResultException e) {
			return null;
		}	
	    catch (Exception e) {
	        e.printStackTrace();
	        return null;
	     }
	}

	protected synchronized long storeNetwork(NetworkMeta network) {

		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(network);
		em.flush();
		em.getTransaction().commit();

		long networkId = network.getNetworkId();
		em.close();
		
		return networkId;
	}

	protected List<NetworkMeta> getNetworks(List<Long> networkIds) {

		EntityManager em = factory.createEntityManager();
		TypedQuery<NetworkMeta> query = em.createQuery("SELECT n FROM Networks AS n WHERE n.ocdId IN :ids", NetworkMeta.class);
		query.setParameter("ids", networkIds);
		List<NetworkMeta> networks = query.getResultList();
		return networks;
	}

	protected List<NetworkMeta> getAllNetworks() {

		EntityManager em = factory.createEntityManager();
		TypedQuery<NetworkMeta> query = em.createQuery("SELECT n FROM Networks AS n", NetworkMeta.class);
		List<NetworkMeta> networks = query.getResultList();
		return networks;
	}	


	//////////////// Cover //////////////////

	protected Cover getCover(long coverId) {

		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<Cover> query = em.createQuery("SELECT n FROM Cover AS n WHERE n.coverId =:id", Cover.class);
			query.setParameter("id", coverId);
			Cover cover = query.getSingleResult();
			return cover;
		} catch (NoResultException e) {
			return null;
		}	
	    catch (Exception e) {
	        e.printStackTrace();
	        return null;
	     }
	}
	
	protected Cover getCover(NetworkMeta network, AlgorithmType algorithm) {
		
		EntityManager em = factory.createEntityManager();
		try {
			TypedQuery<Cover> query = em.createQuery("SELECT n FROM Cover AS n WHERE n.network =:netw AND n.algorithm =:algo", Cover.class);
			query.setParameter("netw", network);
			query.setParameter("algo", algorithm);
			Cover cover = query.getSingleResult();
			return cover;
		} catch (NoResultException e) {
			return null;
		}	
	    catch (Exception e) {
	        e.printStackTrace();
	        return null;
	     }
	}
	
	protected List<Cover> getCovers(NetworkMeta network) {
		
		EntityManager em = factory.createEntityManager();
		TypedQuery<Cover> query = em.createQuery("SELECT n FROM Cover AS n WHERE n.network =:id", Cover.class);
		query.setParameter("id", network);
		List<Cover> covers = query.getResultList();
		
		return covers;
	}

	protected synchronized long storeCover(Cover cover) {

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
