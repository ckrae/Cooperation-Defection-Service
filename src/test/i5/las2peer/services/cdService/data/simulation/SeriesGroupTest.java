package i5.las2peer.services.cdService.data.simulation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class SeriesGroupTest {
	
	@Mock SimulationSeries series1;
	@Mock SimulationSeries series2;
	
	@Mock Parameters para1;
	@Mock Parameters para2;
	
	@Mock Evaluation eval1;
	@Mock Evaluation eval2;
	
	@Test
	public void getSeriesMetaTest() {
		
		Mockito.when(series1.getSeriesId()).thenReturn((long) 1);
		Mockito.when(series2.getSeriesId()).thenReturn((long) 2);
		Mockito.when(series1.getParameters()).thenReturn(para1);
		Mockito.when(series2.getParameters()).thenReturn(para2);
		Mockito.when(series1.getEvaluation()).thenReturn(eval1);
		Mockito.when(series2.getEvaluation()).thenReturn(eval2);
		
		ArrayList<SimulationSeries> seriesList = new ArrayList<SimulationSeries>();
		seriesList.add(series1);
		seriesList.add(series2);
		
		SeriesGroup group = new SeriesGroup(seriesList);
		assertNotNull(group);
		assertEquals(seriesList, group.getSimulationSeries());
		
		List<SimulationMeta> metaList = group.getSimulationMeta();
		assertNotNull(metaList);
		assertEquals(2, metaList.size());
		assertEquals(1, metaList.get(0).getSeriesId());
		assertEquals(2, metaList.get(1).getSeriesId());
		assertEquals(para1, metaList.get(0).getParameters());
		assertEquals(para2, metaList.get(1).getParameters());
		assertEquals(eval1, metaList.get(0).getEvaluation());
		assertEquals(eval2, metaList.get(1).getEvaluation());
		
	}
	
	
}
