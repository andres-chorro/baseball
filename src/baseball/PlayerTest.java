package baseball;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void hitSingle() {
		Player p = new Player("John", "Dowd", 15);
		p.single(1);
		assertEquals(1, p.getSingles());
		assertEquals(1, p.getRbis());
	}

}
