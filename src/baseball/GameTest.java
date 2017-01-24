package baseball;

import java.util.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class GameTest {
	
	private Game g;
	@Before 
	public void startGame() {
		List<Player> dodgers = new ArrayList<>();
		List<Player> giants = new ArrayList<>();
		dodgers.add(new Player("Klayton", "Kershaw", 22));
		dodgers.add(new Player("Yasiel", "Puig", 66));
		giants.add(new Player("Buster", "Posey", 28));
		giants.add(new Player("Brandon", "Crawford", 35));
		Game g = new Game(dodgers, giants);
		g = new Game(dodgers, giants);
	}
	
	@Test
	public void clearBases() {
		
	}
}
