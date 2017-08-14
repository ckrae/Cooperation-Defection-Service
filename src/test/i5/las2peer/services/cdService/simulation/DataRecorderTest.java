package i5.las2peer.services.cdService.simulation;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DataRecorderTest {
	
	@Mock Simulation simulation;

	@Test
	public void testStep() {
		
		DataRecorder recorder = new DataRecorder(4);		
		double[] averageCooperation = new double[4];
		double[] averagePayoff = new double[4];
		
		int round = 0;
		averageCooperation[round] = 0.5;
		averagePayoff[round] = 3.5;		
		Mockito.when(simulation.getCooperationValue()).thenReturn(averageCooperation[round]);
		Mockito.when(simulation.getAveragePayoff()).thenReturn(averagePayoff[round]);	
		
		recorder.step(simulation);	
		assertEquals(1, recorder.getCooperationValues().size());
		assertEquals(1, recorder.getPayoffValues().size());
		assertEquals(averageCooperation[round], recorder.getCooperationValues().get(0), 0.01);
		assertEquals(averagePayoff[round], recorder.getPayoffValues().get(0), 0.01);
		
		round = 1;
		averageCooperation[round] = 1.5;
		averagePayoff[round] = 2.3;		
		Mockito.when(simulation.getCooperationValue()).thenReturn(averageCooperation[round]);
		Mockito.when(simulation.getAveragePayoff()).thenReturn(averagePayoff[round]);
		
		recorder.step(simulation);
		assertEquals(2, recorder.getCooperationValues().size());
		assertEquals(2, recorder.getPayoffValues().size());
		assertEquals(averageCooperation[0], recorder.getCooperationValues().get(0), 0.01);
		assertEquals(averagePayoff[0], recorder.getPayoffValues().get(0), 0.01);
		assertEquals(averageCooperation[1], recorder.getCooperationValues().get(1), 0.01);
		assertEquals(averagePayoff[1], recorder.getPayoffValues().get(1), 0.01);
		
	}
	
	@Test
	public void testClear() {
		
		DataRecorder recorder = new DataRecorder(10);		
		Mockito.when(simulation.getCooperationValue()).thenReturn(1.1);
		Mockito.when(simulation.getAveragePayoff()).thenReturn(1.1);
		
		recorder.step(simulation);
		recorder.step(simulation);
		recorder.step(simulation);
		recorder.step(simulation);
		recorder.step(simulation);
		assertEquals(5, recorder.getCooperationValues().size());
		assertEquals(5, recorder.getPayoffValues().size());
		
		recorder.clear();
		assertEquals(0, recorder.getCooperationValues().size());
		assertEquals(0, recorder.getPayoffValues().size());		
	}
	


}
