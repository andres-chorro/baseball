package baseball;

import java.util.*;

public class Game {
	private List<Player> awayTeam;
	private List<Player> homeTeam;
	private int currentAwayHitter;
	private int currentHomeHitter;
	private int outs;
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
		outs = 0;
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
	 * Update game to reflect a strikeout by the current hitter.
	 */
	public void strikeOut() {
		getCurrentBatter().strikeOut();
		incrementHitter();
		outs++;
		checkInningOver();
	}

	/**
	 * Gets the current batter.
	 * 
	 * @return the current batter
	 */
	public Player getCurrentBatter() {
		return isBottom ? homeTeam.get(currentHomeHitter) : awayTeam.get(currentAwayHitter);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		// add headings
		s.append(String.format("Away:%3d                 %s%2d\n",
				awayScore, isBottom ? "Bot" : "Top", inning));
		s.append(String.format("Home:%3d          outs [%c] [%c]\n\n",
				homeScore, outs > 0 ? 'X' : ' ', outs > 1 ? 'X' : ' '));
		
		// add bases
		String[] baseStrings = new String[3];
		for (int i = 0; i < 3; i++) {
			if (bases.get(i) != null)
				baseStrings[i] = bases.get(i).getLastName();
			else
				baseStrings[i] = "";
		}
		// second
		int gap = (28 - baseStrings[1].length()) / 2;
		s.append(String.format("%" + gap + "s[%s]\n\n", 
				"", baseStrings[1]));
		// first and third
		gap = (26 - (baseStrings[0].length() + baseStrings[2].length()));
		s.append(String.format("[%s]%" + gap + "s[%s]\n\n", baseStrings[2], "", baseStrings[0]));
		// batter
		String batterLastName = getCurrentBatter().getLastName();
		gap = (28 - batterLastName.length()) / 2;
		s.append(String.format("%" + gap + "s[%s]\n",
				"", batterLastName));
		return s.toString();
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
	
	/**
	 * Performs the inning switch if necessary
	 */
	private void checkInningOver() {
		if (outs < 3) {
			return;
		}
		if (isBottom) {
			inning++;
		}
		isBottom = !isBottom;
		outs = 0;
	}

	public static void main(String[] args) {
		List<Player> dodgers = new ArrayList<>();
		List<Player> giants = new ArrayList<>();
		dodgers.add(new Player("Klayton", "Kershaw", 22));
		dodgers.add(new Player("Yasiel", "Puig", 66));
		giants.add(new Player("Buster", "Posey", 28));
		giants.add(new Player("Brandon", "Crawford", 35));
		Game g = new Game(dodgers, giants);

		g.single();
		System.out.println("Kershaw single:\n" + g);
		g.single();
		System.out.println("Puig single:\n" + g);
		g.hitDouble();
		System.out.println("Kershaw double:\n" + g);
		g.strikeOut();
		System.out.println("Puig strikeout:\n" + g);
		g.homerun();
		System.out.println("Kershaw homerun:\n" + g);
		g.strikeOut();
		System.out.println("Puig strikeOut:\n" + g);
		g.strikeOut();
		System.out.println("Kershaw strikeOut:\n" + g);
		System.out.println("Kershaw RBIS: " + dodgers.get(0).getRbis());
	}

}
