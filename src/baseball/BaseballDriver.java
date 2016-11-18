package baseball;

import java.util.*;

public class BaseballDriver {
	private Scanner sc = new Scanner(System.in);
	private Game game;

	public BaseballDriver(Game game) {
		this.game = game;
	}

	private static Game testGame() {
		List<Player> dodgers = new ArrayList<>();
		List<Player> giants = new ArrayList<>();
		dodgers.add(new Player("Klayton", "Kershaw", 22));
		dodgers.add(new Player("Yasiel", "Puig", 66));
		giants.add(new Player("Buster", "Posey", 28));
		giants.add(new Player("Brandon", "Crawford", 35));
		return new Game(dodgers, giants);
	}

	private void mainMenu() {
		boolean done = false;
		while (!done) {
			System.out.println(game);
			System.out.println(mainMenuText());
			String input = sc.next().toLowerCase();
			//TODO: finish cases.
			switch (input) {
			case "single":
				game.single();
				break;
			case "double":
				game.hitDouble();
				break;
			case "triple":
				game.triple();
				break;
			case "homerun":
				game.homerun();
				break;
			case "putout":
				game.putOut();
				break;
			case "strikeout":
				game.strikeOut();
				break;
			case "quit":
				done = true;
				break;
			default:
				System.out.println("Invalid input, try again!");
				break;
			}
		}
		
	}
	
	private String mainMenuText() {
		StringBuilder sb = new StringBuilder();
		sb.append("Choose one of the following options:\n");
		sb.append("[single] [double] [triple] [homerun]\n");
		sb.append("[putout] [strikeout]");
		if (game.isDoublePlaySituation())
			sb.append(" [doubleplay]");
		// TODO: add triple play option.
		sb.append("\n[quit]");
		return sb.toString();
	}

	public static void main(String[] args) {
		BaseballDriver driver = new BaseballDriver(testGame());
		driver.mainMenu();
	}
}
