package baseball;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BasesTest {
	
	private Bases b;
	
	@Before
	public void setup() {
		b = new Bases();
	}

	@Test
	public void testConstructor() {
		System.out.println(b);
		assertEquals(0, b.getRunnersOn());
	}
	
	@Test
	public void scoreBoardString() {
		System.out.println(b.scoreBoardString());
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
