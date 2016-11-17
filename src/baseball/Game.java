package baseball;

import java.util.*;

public class Game {
	private List<Player> awayTeam;
	private List<Player> homeTeam;
	private int currentAwayHitter;
	private int currentHomeHitter;
	private int inning;
	private boolean isBottom;
	private int awayScore;
	private int homeScore;
	private LinkedList<Player> bases;

	public Game(List<Player> awayTeam, List<Player> homeTeam) {
		this.awayTeam = awayTeam;
		this.homeTeam = homeTeam;
		currentAwayHitter = 0;
		currentHomeHitter = 0;
		inning = 1;
		isBottom = false;
		awayScore = 0;
		homeScore = 0;
		bases = new LinkedList<>();
		for (int i = 0; i < 3; i++) {
			bases.push(null);
		}
	}

	/**
	 * Update the game to reflect a single by the current hitter.
	 */
	public void single() {
		Player currentHitter = getCurrentBatter();
		bases.push(currentHitter);
		currentHitter.single(countRuns());
		incrementHitter();
	}

	/**
	 * Gets the current batter.
	 * 
	 * @return the current batter
	 */
	public Player getCurrentBatter() {
		return isBottom ? homeTeam.get(currentHomeHitter) : awayTeam.get(currentAwayHitter);
	}

	/**
	 * Checks how many runners scored on the last hit, and clears the bases
	 * structure to keep only the current base runners. Adjusts score
	 * accordingly.
	 * 
	 * @return number of runs scored
	 */
	private int countRuns() {
		int runs = 0;
		while (bases.size() > 3) {
			Player curr = bases.removeLast();
			if (curr != null) {
				runs++;
				curr.addRun();
			}
		}
		if (!isBottom)
			awayScore += runs;
		else
			homeScore += runs;
		return runs;
	}

	/**
	 * Updates the current hitter variables to reflect the end of the at-bat.
	 */
	private void incrementHitter() {
		if (!isBottom) {
			currentAwayHitter = (currentAwayHitter + 1) % awayTeam.size();
		} else {
			currentHomeHitter = (currentHomeHitter + 1) % homeTeam.size();
		}
	}

	public static void main(String[] args) {
		List<Player> dodgers = new ArrayList<>();
		List<Player> giants = new ArrayList<>();
		dodgers.add(new Player("Klayton", "Keyshaw", 22));
		giants.add(new Player("Buster", "Posey", 28));
		Game g = new Game(dodgers, giants);
		System.out.println(g.bases.size());
		g.single();
		System.out.println(g.bases);
		g.single();
		g.single();
		System.out.println(g.bases);
		g.single();
		System.out.println(g.awayScore);
		System.out.println(dodgers.get(0).getRuns());
		System.out.println(dodgers.get(0).getRbis());
		System.out.println(dodgers.get(0).getBattingAverage());
	}

}
