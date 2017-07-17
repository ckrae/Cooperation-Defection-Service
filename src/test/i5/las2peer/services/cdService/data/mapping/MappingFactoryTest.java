package i5.las2peer.services.cdService.data.mapping;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import i5.las2peer.services.cdService.data.network.Community;
import i5.las2peer.services.cdService.data.simulation.AgentData;
import i5.las2peer.services.cdService.data.simulation.SimulationDataset;

@RunWith(MockitoJUnitRunner.class)
public class MappingFactoryTest {

	@Mock
	AgentData agentData1;
	@Mock
	AgentData agentData2;
	@Mock
	AgentData agentData3;

	@Mock
	Community community1;
	@Mock
	Community community2;
	@Mock
	Community community3;

	@Mock
	SimulationDataset simulationDataset1;
	@Mock
	SimulationDataset simulationDataset2;
	@Mock
	SimulationDataset simulationDataset3;

	List<Integer> memberList;
	List<AgentData> agentDataList;

	List<Double> coopValList1;
	List<Double> coopValList2;
	List<Double> coopValList3;

	List<Boolean> strategies1;
	List<Boolean> strategies2;
	List<Boolean> strategies3;

	@Before
	public void mockSetUp() {

		strategies1 = new ArrayList<>();
		strategies2 = new ArrayList<>();
		strategies3 = new ArrayList<>();

		memberList = new ArrayList<>();
		agentDataList = new ArrayList<>();
		agentDataList.add(agentData1);
		agentDataList.add(agentData2);
		agentDataList.add(agentData3);

		coopValList1 = new ArrayList<>();
		coopValList2 = new ArrayList<>();
		coopValList3 = new ArrayList<>();

		Mockito.when(agentData1.getStrategies()).thenReturn(strategies1);
		Mockito.when(agentData2.getStrategies()).thenReturn(strategies2);
		Mockito.when(agentData3.getStrategies()).thenReturn(strategies3);

		Mockito.when(simulationDataset1.getAgentData()).thenReturn(agentDataList);

		Mockito.when(simulationDataset1.getCooperationValues()).thenReturn(coopValList1);
		Mockito.when(simulationDataset2.getCooperationValues()).thenReturn(coopValList2);
		Mockito.when(simulationDataset3.getCooperationValues()).thenReturn(coopValList3);

		Mockito.when(community1.getMembers()).thenReturn(memberList);

	}

	@Test
	public void getCommunityDatasetMapping() {

		MappingFactory factory = new MappingFactory();
		CommunityDataSetMapping mapping;
		List<Double> coopList;
		double coopVal;

		strategies1.clear();
		strategies1.addAll(Arrays.asList(true, true, true));
		strategies2.clear();
		strategies2.addAll(Arrays.asList(true, true, false));
		strategies3.clear();
		strategies3.addAll(Arrays.asList(true, false, false));
		memberList.clear();
		memberList.addAll(Arrays.asList(0, 1, 2));

		mapping = factory.getCommunitySimulationDatasetMapping(community1, simulationDataset1);
		assertNotNull(mapping);
		coopList = mapping.getCooperationValues();
		assertNotNull(coopList);
		assertEquals(3, coopList.size(), 0.01);
		assertEquals(1.0, coopList.get(0), 0.01);
		assertEquals(0.66, coopList.get(1), 0.01);
		assertEquals(0.33, coopList.get(2), 0.01);
		coopVal = mapping.getCooperationValue();
		assertEquals(0.33, coopVal, 0.01);
	}

	@Test
	public void getCommunityCooperationValues() {

		MappingFactory factory = new MappingFactory();
		List<Double> coopList;

		strategies1.clear();
		strategies1.addAll(Arrays.asList(true, true, false, false));
		strategies2.clear();
		strategies2.addAll(Arrays.asList(true, true, false, false));
		strategies3.clear();
		strategies3.addAll(Arrays.asList(true, false, true, false));
		memberList.clear();
		memberList.addAll(Arrays.asList(0, 1, 2));

		coopList = factory.getCommunityCooperationValues(agentDataList, memberList);
		assertNotNull(coopList);
		assertEquals(4, coopList.size());
		assertEquals(1.0, coopList.get(0), 0.01);
		assertEquals(0.66, coopList.get(1), 0.01);
		assertEquals(0.33, coopList.get(2), 0.01);
		assertEquals(0.0, coopList.get(3), 0.01);

		memberList.clear();
		memberList.addAll(Arrays.asList(0, 2));

		coopList = factory.getCommunityCooperationValues(agentDataList, memberList);
		assertNotNull(coopList);
		assertEquals(4, coopList.size());
		assertEquals(1.0, coopList.get(0), 0.01);
		assertEquals(0.5, coopList.get(1), 0.01);
		assertEquals(0.5, coopList.get(2), 0.01);
		assertEquals(0.0, coopList.get(3), 0.01);

		memberList.clear();
		memberList.addAll(Arrays.asList(1));

		coopList = factory.getCommunityCooperationValues(agentDataList, memberList);
		assertNotNull(coopList);
		assertEquals(4, coopList.size());
		assertEquals(1.0, coopList.get(0), 0.01);

		memberList.clear();

		coopList = factory.getCommunityCooperationValues(agentDataList, memberList);
		assertNotNull(coopList);
		assertEquals(0, coopList.size());

		memberList = null;

		coopList = factory.getCommunityCooperationValues(agentDataList, memberList);
		assertNotNull(coopList);
		assertEquals(0, coopList.size());

	}

	@Test
	public void getCommunityCooperationValue() {

		MappingFactory factory = new MappingFactory();
		double value;

		strategies1.clear();
		strategies1.addAll(Arrays.asList(true, true, true));
		strategies2.clear();
		strategies2.addAll(Arrays.asList(true, true, false));
		strategies3.clear();
		strategies3.addAll(Arrays.asList(true, false, false));
		memberList.clear();
		memberList.addAll(Arrays.asList(0, 1, 2));

		value = factory.getCooperationValue(memberList, agentDataList, 0);
		assertEquals(1.0, value, 0.01);

		value = factory.getCooperationValue(memberList, agentDataList, 1);
		assertEquals(0.66, value, 0.01);

		value = factory.getCooperationValue(memberList, agentDataList, 2);
		assertEquals(0.33, value, 0.01);

	}

}
