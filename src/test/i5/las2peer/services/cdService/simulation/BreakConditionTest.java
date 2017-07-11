package i5.las2peer.services.cdService.simulation;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BreakConditionTest {

	@Mock
	Simulation simulation;

	@Mock
	DataRecorder recorder;

	@Test
	public void isBreakConditionTest() {
		
		boolean result;
		BreakCondition condition = new BreakCondition();	
		
		Mockito.when(simulation.getRound()).thenReturn(10);
		Mockito.when(simulation.getDataRecorder()).thenReturn(recorder);
				
		Mockito.when(simulation.getMaxIterations()).thenReturn(20);		
		Mockito.when(recorder.getCooperationValue(9)).thenReturn(0.1);
		Mockito.when(recorder.getCooperationValue(8)).thenReturn(0.2);
		Mockito.when(recorder.getCooperationValue(7)).thenReturn(0.2);
		Mockito.when(recorder.getPayoffValue(9)).thenReturn(0.5);
		Mockito.when(recorder.getPayoffValue(8)).thenReturn(0.5);		
		result = condition.isBreakCondition(simulation);
		assertEquals(false, result);
		
		Mockito.when(simulation.getMaxIterations()).thenReturn(20);		
		Mockito.when(recorder.getCooperationValue(9)).thenReturn(0.2);
		Mockito.when(recorder.getCooperationValue(8)).thenReturn(0.2);
		Mockito.when(recorder.getCooperationValue(7)).thenReturn(0.2);
		Mockito.when(recorder.getPayoffValue(9)).thenReturn(0.5);
		Mockito.when(recorder.getPayoffValue(8)).thenReturn(0.4);		
		result = condition.isBreakCondition(simulation);
		assertEquals(false, result);
		
		// max iterations
		Mockito.when(simulation.getMaxIterations()).thenReturn(7);		
		Mockito.when(recorder.getCooperationValue(9)).thenReturn(0.2);
		Mockito.when(recorder.getCooperationValue(8)).thenReturn(0.2);
		Mockito.when(recorder.getCooperationValue(7)).thenReturn(0.2);
		Mockito.when(recorder.getPayoffValue(9)).thenReturn(0.5);
		Mockito.when(recorder.getPayoffValue(8)).thenReturn(0.4);
		result = condition.isBreakCondition(simulation);
		assertEquals(true, result);		
		
		// stable state
		Mockito.when(simulation.getMaxIterations()).thenReturn(20);		
		Mockito.when(recorder.getCooperationValue(9)).thenReturn(0.2);
		Mockito.when(recorder.getCooperationValue(8)).thenReturn(0.2);
		Mockito.when(recorder.getCooperationValue(7)).thenReturn(0.2);
		Mockito.when(recorder.getPayoffValue(9)).thenReturn(0.5);
		Mockito.when(recorder.getPayoffValue(8)).thenReturn(0.5);		
		result = condition.isBreakCondition(simulation);
		assertEquals(true, result);
		
		// min iterations
		Mockito.when(simulation.getMaxIterations()).thenReturn(20);		
		Mockito.when(recorder.getCooperationValue(9)).thenReturn(0.2);
		Mockito.when(recorder.getCooperationValue(8)).thenReturn(0.2);
		Mockito.when(recorder.getCooperationValue(7)).thenReturn(0.2);
		Mockito.when(recorder.getPayoffValue(9)).thenReturn(0.5);
		Mockito.when(recorder.getPayoffValue(8)).thenReturn(0.4);	
		Mockito.when(simulation.getRound()).thenReturn(4);
		

	}

}
