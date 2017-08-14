package i5.las2peer.services.cdService.data.mapping;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import i5.las2peer.services.cdService.data.network.cover.AlgorithmType;
import i5.las2peer.services.cdService.data.network.cover.Community;
import i5.las2peer.services.cdService.data.network.cover.Cover;
import i5.las2peer.services.cdService.data.simulation.SimulationDataset;
import i5.las2peer.services.cdService.data.simulation.SimulationSeries;
import i5.las2peer.services.cdService.data.simulation.SimulationSeriesGroup;

@RunWith(MockitoJUnitRunner.class)
public class MappingFactoryTest {
	
	@Spy Cover cover;

	@Spy SimulationSeries simulationSeries;
	
	@Mock
	Community community1;
	@Mock
	Community community2;
	@Mock
	Community community3;
	
	@Mock SimulationSeries simulationSeries1;
	@Mock SimulationSeries simulationSeries2;
	@Mock SimulationSeries simulationSeries3;
	
	@Mock SimulationSeriesGroup simulationSeriesGroup1;
	@Mock SimulationSeriesGroup simulationSeriesGroup2;
	@Mock SimulationSeriesGroup simulationSeriesGroup3;
	
	@Mock
	SimulationDataset simulationDataset1;
	@Mock
	SimulationDataset simulationDataset2;
	@Mock
	SimulationDataset simulationDataset3;

	@Test
	public void buildCoverSimulationSeriesMapping() {
			
		Cover cover = new Cover();
		SimulationSeries simulationSeries = new SimulationSeries();	
		
		MappingFactory factory = new MappingFactory();
		CoverSimulationSeriesMapping mapping = factory.build(cover, simulationSeries);
		assertEquals(cover, mapping.getCover());
		assertEquals(simulationSeries, mapping.getSimulation());		
		
	}
	
	@Test
	public void buildCoverSimulationGroupMapping() {
		
		Cover cover = new Cover();
		cover.setAlgorithmType(AlgorithmType.DMID);
		
		SimulationSeriesGroup simulation = new SimulationSeriesGroup();
		simulation.setName("testSimulation");
		
		MappingFactory factory = new MappingFactory();
		CoverSimulationGroupMapping mapping = factory.build(cover, simulation);
		assertEquals(cover, mapping.getCover());
		assertEquals(simulation, mapping.getSimulation());		
		
	}

	


}
