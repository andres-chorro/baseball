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
	 * Update the game to reflect a double by the current hitter.
	 */
	public void hitDouble() {
		Player currentHitter = getCurrentBatter();
		bases.push(currentHitter);
		bases.push(null);
		currentHitter.hitDouble(countRuns());
		incrementHitter();
	}
	
	/**
	 * Update the game to reflect a triple by the current hitter.
	 */
	public void triple() {
		Player currentHitter = getCurrentBatter();
		bases.push(currentHitter);
		bases.push(null);
		bases.push(null);
		currentHitter.triple(countRuns());
		incrementHitter();
	}
	
	/**
	 * Update the game to reflect a homerun by the current hitter.
	 */
	public void homerun() {
		Player currentHitter = getCurrentBatter();
		bases.push(currentHitter);
		bases.push(null);
		bases.push(null);
		bases.push(null);
		currentHitter.homerun(countRuns());
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
		dodgers.add(new Player("Yasiel", "Puig", 66));
		giants.add(new Player("Buster", "Posey", 28));
		giants.add(new Player("Brandon", "Crawford", 35));
		Game g = new Game(dodgers, giants);
		
		g.single();
		System.out.println("Kershaw single: " + g.bases);
		g.single();
		System.out.println("Puig single: " + g.bases);
		g.hitDouble();
		System.out.println("Kershaw double: " + g.bases);
		g.triple();
		System.out.println("Puig triple: " + g.bases);
		g.homerun();
		System.out.println("Kershaw homerun " + g.bases);
		System.out.println(g.awayScore);
		System.out.println("Kershaw RBIS: " + dodgers.get(0).getRbis());
	}

}
