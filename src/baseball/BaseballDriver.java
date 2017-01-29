package baseball;

import java.util.*;

public class BaseballDriver {

	private static Scanner sc = new Scanner(System.in);
	private static ArrayList<Player> league;
	private Game game;
	
	public BaseballDriver() {
		league = getLeague();
		boolean done = false;
		while (!done) {
			System.out.println(leagueMenuText());
			System.out.println("[A]dd a player to league, or [D]one");
			String in = sc.next();
			if (in.equalsIgnoreCase("d")) {
				done = true;
			}
			else if (in.equalsIgnoreCase("a")) {
				System.out.println("Enter the new player: [first name] [last name] [number]:");
				String fn = sc.next();
				String ln = sc.next();
				int n = sc.nextInt();
				league.add(new Player(fn, ln, n));
			} else {
				System.out.println("Invalid input, try again");
			}
		}
		System.out.println("Add players to the away team:");
		LinkedList<Player> awayTeam = buildTeam();
		System.out.println("Add players to the home team:");
		LinkedList<Player> homeTeam = buildTeam();
		game = new Game(awayTeam, homeTeam);
	}

	private LinkedList<Player> buildTeam() {
		LinkedList<Player> result = new LinkedList<>();
		boolean done = false;
		while (!done) {
			System.out.println(leagueMenuText());
			System.out.println("Type the index of the player to add, or [D]one:");
			String in = sc.next();
			if (in.equalsIgnoreCase("d")) {
				done = true;
				break;
			}
			int index = Integer.parseInt(in);
			if (index < 0 || index >= league.size()) {
				System.out.println("Invalid index, try again");
			} else {
				result.add(league.get(index));
			}
		}
		return result;
	}

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

	private void mainMenu() {
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
	
	private String leagueMenuText() {
		StringBuilder sb = new StringBuilder();
		sb.append("The league has the following players:\n");
		for (int i = 0; i < league.size(); i++) {
			sb.append(i + ": " + league.get(i) + "\n");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		BaseballDriver dr = new BaseballDriver();
		System.out.println(Player.hittingStats(league));
		dr.mainMenu();
		System.out.println("enter a filename to save your league");
		String fn = dr.sc.next();
		PlayerSerializer ps = new PlayerSerializer(fn);
		ps.save(dr.league);
	}
}
