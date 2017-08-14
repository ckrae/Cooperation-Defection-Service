package i5.las2peer.services.cdService.data;

import java.util.ArrayList;
import java.util.List;

import i5.las2peer.api.exceptions.RemoteServiceException;
import i5.las2peer.api.exceptions.ServiceNotAvailableException;
import i5.las2peer.api.exceptions.ServiceNotFoundException;
import i5.las2peer.api.exceptions.StorageException;
import i5.las2peer.services.cdService.data.network.NetworkMeta;
import i5.las2peer.services.cdService.data.network.cover.AlgorithmType;
import i5.las2peer.services.cdService.data.network.cover.Cover;
import i5.las2peer.services.cdService.data.network.cover.CoverAdapter;
import i5.las2peer.services.cdService.data.network.cover.CoverOrigin;

public class CoverDataProvider {

	public CoverDataProvider() {		

	}

	public static CoverDataProvider getInstance() {
		return new CoverDataProvider();
	}

	///// Internal Cover //////
	
	public Cover getCover(long coverId) throws IllegalArgumentException {
		
		EntityHandler entityHandler = EntityHandler.getInstance();
		Cover cover = entityHandler.getCover(coverId);
		
		return cover;
	}
	
	public Cover getCover(NetworkMeta network, String algorithm) throws IllegalArgumentException {
		
		AlgorithmType type = AlgorithmType.fromString(algorithm);
		if(type == null || network == null)
			throw new IllegalArgumentException();
		
		return getCover(network, type);	
	}
	
	public Cover getCover(NetworkMeta network, AlgorithmType algorithm) {
		
		Cover cover = null;
		
		// first try to get stored cover	
		EntityHandler entityHandler = EntityHandler.getInstance();
		cover = entityHandler.getCover(network, algorithm);
		
		if(cover != null)
			return cover;
		
		// else try to get from external
		try {
			cover = getExternalCover(network, algorithm);
		} catch (ServiceNotFoundException | ServiceNotAvailableException | RemoteServiceException
				| StorageException e) {			
			e.printStackTrace();
		}
		
		return cover;		
	}
	
	public List<Cover> getCovers(NetworkMeta network) {
		
		List<Cover> covers = null;
		
		EntityHandler entityHandler = EntityHandler.getInstance();
		covers = entityHandler.getCovers(network);
		
		return covers;		
	}	

	///// External Cover /////
	
	public Cover getExternalCover(NetworkMeta network, AlgorithmType algorithm)
			throws StorageException, ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		CoverOrigin origin = CoverOrigin.UKNOWN;
		String algorithmString = "";

		switch (algorithm) {

		case SLPA:
			algorithmString = "SPEAKER_LISTENER_LABEL_PROPAGATION_ALGORITHM";
			origin = CoverOrigin.OCD_SERVICE;
			break;

		case CLIZZ:
			algorithmString = "CLIZZ_ALGORITHM ";
			origin = CoverOrigin.OCD_SERVICE;
			break;

		case DMID:
			algorithmString = "SIGNED_DMID_ALGORITHM";
			origin = CoverOrigin.OCD_SERVICE;
			break;

		case LC:
			algorithmString = "LINK_COMMUNITIES_ALGORITHM";
			origin = CoverOrigin.OCD_SERVICE;
			break;

		case SSK:
			algorithmString = "SSK_ALGORITHM";
			origin = CoverOrigin.OCD_SERVICE;
			break;

		case Spinglass:
			algorithmString = "";
			break;

		case LE:
			algorithmString = "";
			break;

		case Walktrap:
			algorithmString = "";
			break;

		case EB:
			algorithmString = "EVOLUTIONARY_ALGORITHM_BASED_ON_SIMILARITY";
			origin = CoverOrigin.OCD_SERVICE;
			break;

		case FGMod:
			algorithmString = "";
			break;

		case MLMod:
			algorithmString = "";
			break;

		case LP:
			algorithmString = "";
			break;

		case InfoMap:
			algorithmString = "";
			break;
		default:
			break;
		}

		return getExternalCover(network, algorithmString, origin);
	}

	protected Cover getExternalCover(NetworkMeta network, String algorithm, CoverOrigin origin)
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {

		CoverAdapter coverAdapter = new CoverAdapter();
		Cover cover = null;

		switch (origin) {
		case OCD_SERVICE:
			long graphId = network.getOriginId();
				cover = coverAdapter.inovkeCoverByAlgorithm(graphId, algorithm);
			break;
		default:
			break;
		}

		return cover;
	}
	
	
	///// OCD Service /////
	
	protected Cover getServiceCover(NetworkMeta network, String algorithm)
			throws StorageException, ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {
		
		CoverAdapter coverAdapter = CoverAdapter.getInstance();
		Cover cover = coverAdapter.inovkeCoverByAlgorithm(network.getNetworkId(), algorithm);
		return cover;
	}

	protected Cover getServiceCover(long graphId, long coverId)
			throws ServiceNotFoundException, ServiceNotAvailableException, RemoteServiceException {
		
		CoverAdapter coverAdapter = CoverAdapter.getInstance();
		return coverAdapter.inovkeCoverById(graphId, coverId);

	}

	public List<Cover> getServiceCovers(List<NetworkMeta> networks, String algorithm) throws StorageException {

		List<Cover> covers = new ArrayList<Cover>(networks.size());
		return covers;
	}

}
