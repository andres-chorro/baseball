package baseball;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
	
	private Player p;
	
	@Before
	public void setupPlayer() {
		p = new Player("John", "Dowd", 15);
	}

	@Test
	public void recordSingle() {
		p.recordSingle();
		assertEquals(1, p.getSingles());
	}
	
	@Test
	public void recordDouble() {
		p.recordDouble();
		p.recordDouble();
		assertEquals(2, p.getDoubles());
	}
	
	@Test
	public void recordTriple() {
		for (int i = 0; i < 68; i++) {
			p.recordTriple();
		}
		assertEquals(68, p.getTriples());
	}
	
	@Test
	public void recordHomeRun() {
		assertEquals(0, p.getHomeruns());
		p.recordHomerun();
		assertEquals(1, p.getHomeruns());
	}
	
	@Test
	public void recordRbis() {
		p.recordRbis(3);
		assertEquals(3, p.getRbis());
	}

}
