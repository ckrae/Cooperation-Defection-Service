package i5.las2peer.services.cdService.simulation;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import sim.engine.Schedule;
import sim.engine.Stoppable;
import sim.util.Bag;

@RunWith(MockitoJUnitRunner.class)
public class BreakConditionTest {
	
	@Spy
	BreakCondition breakCondition;
	
	@Mock
	Simulation simulation;

	@Mock
	Agent agent0;
	@Mock
	Agent agent1;
	@Mock
	Agent agent2;
	@Mock
	Agent agent3;
	
	@Mock
	Schedule schedule = new Schedule();
	
	@Spy
	Stoppable stoppable1 = schedule.scheduleRepeating(new Agent());
	@Spy
	Stoppable stoppable2 = schedule.scheduleRepeating(new Agent());

	int minIterations = 20;
	int maxIterations = 1000;

	@Before
	public void setUp() {

		Bag agentBag = new Bag();
		agentBag.add(agent0);
		agentBag.add(agent1);
		agentBag.add(agent2);
		agentBag.add(agent3);

		Mockito.when(simulation.getAgents()).thenReturn(agentBag);
	}
	
	@Test
	public void isBreakConditionFalse() {

		Mockito.when(simulation.getRound()).thenReturn(maxIterations - 2);
		
		Mockito.when(agent0.isSteady()).thenReturn(false);
		Mockito.when(agent1.isSteady()).thenReturn(true);
		Mockito.when(agent2.isSteady()).thenReturn(false);
		Mockito.when(agent3.isSteady()).thenReturn(true);
		
		BreakCondition condition = new BreakCondition();
		boolean result = condition.isFullfilled(simulation);
		assertEquals(false, result);
	}
	
	@Test
	public void isBreakConditionMaxRoundTrue() {

		Mockito.when(simulation.getRound()).thenReturn(maxIterations + 1);
		
		Mockito.when(agent0.isSteady()).thenReturn(false);
		Mockito.when(agent1.isSteady()).thenReturn(true);
		Mockito.when(agent2.isSteady()).thenReturn(false);
		Mockito.when(agent3.isSteady()).thenReturn(true);
		
		BreakCondition condition = new BreakCondition();
		boolean result = condition.isFullfilled(simulation);
		assertEquals(true, result);
	}

	@Test
	public void isBreakConditionSteadyStateTrue() {

		Mockito.when(simulation.getRound()).thenReturn(maxIterations - 2);

		Mockito.when(agent0.isSteady()).thenReturn(true);
		Mockito.when(agent1.isSteady()).thenReturn(true);
		Mockito.when(agent2.isSteady()).thenReturn(true);
		Mockito.when(agent3.isSteady()).thenReturn(true);

		BreakCondition condition = new BreakCondition();
		boolean result = condition.isFullfilled(simulation);
		assertEquals(true, result);
	}

	@Test
	public void isBreakConditionMinRoundFalse() {

		Mockito.when(simulation.getRound()).thenReturn(minIterations - 1);

		Mockito.when(agent0.isSteady()).thenReturn(true);
		Mockito.when(agent1.isSteady()).thenReturn(true);
		Mockito.when(agent2.isSteady()).thenReturn(true);
		Mockito.when(agent3.isSteady()).thenReturn(true);

		BreakCondition condition = new BreakCondition();
		boolean result = condition.isFullfilled(simulation);
		assertEquals(false, result);
	}
	
	@Test
	public void stepTestConditionFalse() {

		Mockito.when(breakCondition.isFullfilled(simulation)).thenReturn(false);		
		breakCondition.step(simulation);		
		Mockito.verify(breakCondition, Mockito.atLeastOnce()).isFullfilled(simulation);
		Mockito.verify(breakCondition, Mockito.times(0)).stopScheduledObjects();
	}
	
	@Test
	public void stepTestConditionTrue() {

		Mockito.when(breakCondition.isFullfilled(simulation)).thenReturn(true);
		breakCondition.step(simulation);	
		Mockito.verify(breakCondition, Mockito.atLeastOnce()).isFullfilled(simulation);
		Mockito.verify(breakCondition, Mockito.times(1)).stopScheduledObjects();
		
	}
	
	@Test
	public void testStop() {
		
		BreakCondition condition = new BreakCondition();
		condition.add(stoppable1);
		condition.add(stoppable2);
		
		condition.stopScheduledObjects();		
		Mockito.verify(stoppable1, Mockito.atLeastOnce()).stop();
		Mockito.verify(stoppable2, Mockito.atLeastOnce()).stop();
		
	}
	
	

}
