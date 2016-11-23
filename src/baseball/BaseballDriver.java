package baseball;

import java.io.*;
import java.util.*;

public class BaseballDriver {

	private static Scanner sc = new Scanner(System.in);

	private static ArrayList<Player> getLeague() {
		System.out.println("[N]ew league, or [L]oad league");
		String input = sc.next().toLowerCase();
		if (input.equals("n")) {
			System.out.println("New empty league created");
			return new ArrayList<>();
		} else {
			System.out.println("Enter a league file Name:");
			input = sc.next();
			PlayerSerializer ps = new PlayerSerializer(input);
			return ps.load();
		}
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

	private void mainMenu(Game game) {
		boolean done = false;
		while (!done && !game.isGameOver()) {
			System.out.print(game);
			System.out.println(String.format("AVG: %.3f SLG: %.3f", game.getCurrentBatter().getBattingAverage(),
					game.getCurrentBatter().getSluggingPercentage()));
			System.out.println(mainMenuText(game));
			String input = sc.next().toLowerCase();
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
			case "sacrifice":
				if (!game.isSacrificeSituation()) {
					System.out.println("Not a sacrifice situation!");
					break;
				} else {
					game.sacrifice();
					break;
				}
			case "doubleplay":
				if (!game.isDoublePlaySituation()) {
					System.out.println("Not a double-play situation!");
					break;
				} else {
					game.doublePlay();
					break;
				}
			case "tripleplay":
				if (!game.isTriplePlaySituation()) {
					System.out.println("Not a triple-play situation");
					break;
				} else {
					game.triplePlay();
					break;
				}
			case "quit":
				done = true;
				break;
			default:
				System.out.println("Invalid input, try again!");
				break;
			}
		}
		if (game.isGameOver()) {
			System.out.println("Game Over! Away: " + game.getAwayScore() + "\nHome: " + game.getHomeScore());
		} else {
			System.out.println("Game ended early");
		}

	}

	private static String mainMenuText(Game game) {
		StringBuilder sb = new StringBuilder();
		sb.append("Choose one of the following options:\n");
		sb.append("[single] [double] [triple] [homerun]\n");
		sb.append("[putout] [strikeout]");
		if (game.isSacrificeSituation())
			sb.append(" [sacrifice]");
		if (game.isDoublePlaySituation())
			sb.append(" [doubleplay]");
		if (game.isTriplePlaySituation())
			sb.append(" [tripleplay]");
		sb.append("\n[quit]");
		return sb.toString();
	}

	public static void main(String[] args) {
		ArrayList<Player> league = getLeague();
		System.out.println(league);
	}
}
