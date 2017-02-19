package baseball;

import static org.junit.Assert.*;

import org.junit.Before;
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
		Player p1 = new Player("Andres", "Chorro", 7);
		Player p2 = new Player("Quique", "Dahlgren", 13);
		Player p3 = new Player("Erik", "Jennings", 15);
		b.putRunnerOn(p3, 3);
		b.putRunnerOn(p1, 1);
		b.putRunnerOn(p2, 2);
		assertEquals(p1, b.getRunnerOnFirst());
		assertEquals(p2, b.getRunnerOnSecond());
		assertEquals(p3, b.getRunnerOnThird());
	}

}
