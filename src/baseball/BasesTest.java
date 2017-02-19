package baseball;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class BasesTest {
	
	private Bases b = new Bases();
	Player p1 = new Player("p1", "p1", 1);
	Player p2 = new Player("p2", "p2", 2);
	Player p3 = new Player("p3", "p3", 3);

	@Test
	public void testConstructor() {
		System.out.println(b);
		assertEquals(0, b.getNumRunnersOn());
	}
	
	@Test
	public void scoreBoardString() {
		System.out.println(b.scoreBoardString());
	}
	
	@Test 
	public void testNumRunnersOn() {
		assertEquals(0, b.getNumRunnersOn());
		b.putRunnerOn(p1, 1);
		assertEquals(1, b.getNumRunnersOn());
		b.putRunnerOn(p2, 2);
		assertEquals(2, b.getNumRunnersOn());
		b.putRunnerOn(p3, 3);
		assertEquals(3, b.getNumRunnersOn());
		b.advanceRunners(3);
		assertEquals(0, b.getNumRunnersOn());
	}
	
	@Test
	public void putRunnerOn() {
		b.putRunnerOn(p3, 3);
		b.putRunnerOn(p1, 1);
		b.putRunnerOn(p2, 2);
		assertEquals(p1, b.getRunnerOnFirst());
		assertEquals(p2, b.getRunnerOnSecond());
		assertEquals(p3, b.getRunnerOnThird());
	}
	
	@Test
	public void testGetAndClearBases() {
		b.putRunnerOn(p1, 3);
		b.advanceRunners(1);
		List<Player> scorers = b.getAndCleanRunnersIn();
		assertEquals(1, scorers.size());
		assertEquals(p1, scorers.get(0));
		
		scorers = b.getAndCleanRunnersIn();
		assertTrue(scorers.isEmpty());
		
		b.putRunnerOn(p1, 1);
		b.putRunnerOn(p2, 3);
		b.advanceRunners(4);
		scorers = b.getAndCleanRunnersIn();
		assertEquals(2, scorers.size());
		assertEquals(p2, scorers.get(0));
		assertEquals(p1, scorers.get(1));
	}
	
	@Test
	public void testClearBases() {
		b.putRunnerOn(p1, 1);
		b.putRunnerOn(p3, 2);
		b.clearBases();
		assertEquals(0, b.getNumRunnersOn());
		assertTrue(b.getAndCleanRunnersIn().isEmpty());
		assertEquals(Bases.NO_RUNNER, b.getRunnerOnFirst());
		assertEquals(Bases.NO_RUNNER, b.getRunnerOnSecond());
		assertEquals(Bases.NO_RUNNER, b.getRunnerOnThird());
	}
	
	@Test
	public void testRemoveLeadRunner() {
		b.putRunnerOn(p2, 2);
		b.removeLeadRunner();
		assertEquals(0, b.getNumRunnersOn());
		assertEquals(Bases.NO_RUNNER, b.getRunnerOnSecond());
		
		b.putRunnerOn(p3, 3);
		b.putRunnerOn(p1, 1);
		b.removeLeadRunner();
		assertEquals(1, b.getNumRunnersOn());
		assertEquals(Bases.NO_RUNNER, b.getRunnerOnThird());
		assertEquals(Bases.NO_RUNNER, b.getRunnerOnSecond());
		assertEquals(p1, b.getRunnerOnFirst());
	}

}
