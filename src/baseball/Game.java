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
	// Represents current players on bases: {1st, 2nd, 3rd}
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
	 * Update Game to refelct a putout by the current hitter.
	 */
	public void putOut() {
		getCurrentBatter().putout();
		incrementHitter();
		outs++;
		checkInningOver();
	}
	
	public void doublePlay() {
		if (!isDoublePlaySituation()) {
			System.out.println("Error: not a double-play situation!");
			return;
		}
		getCurrentBatter().doublePlay();
		incrementHitter();
		int leadRunnerIndex = 2;
		while (bases.get(leadRunnerIndex) == null) {
			leadRunnerIndex--;
		}
		bases.set(leadRunnerIndex, null);
		outs += 2;
		checkInningOver();
	}
	
	public void triplePlay() {
		if(!isTriplePlaySituation()) {
			System.out.println("Error: not a triple-play situation!");
			return;
		}
		getCurrentBatter().triplePlay();
		incrementHitter();
		outs += 3;
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
	
	/**
	 * Returns true if it is possible to hit a double play given the game state.
	 * @return true when a double play is possible
	 */
	public boolean isDoublePlaySituation() {
		if (outs > 1)
			return false;
		
		int runnersOn = 0;
		for (Player p: bases) {
			if (p != null)
				runnersOn++;
		}
		return runnersOn > 0;
	}
	
	/**
	 * Returns true if it is possible to hit a triple play given the game state.
	 * @return true when a triple play is possible
	 */
	public boolean isTriplePlaySituation() {
		if (outs > 0)
			return false;
		
		int runnersOn = 0;
		for (Player p: bases) {
			if (p != null)
				runnersOn++;
		}
		return runnersOn > 1;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		// add headings
		s.append(String.format("Away:%3d                 %s%2d\n",
				awayScore, isBottom ? "Bot" : "Top", inning));
		s.append(String.format("Home:%3d          Outs [%c] [%c]\n\n",
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
		
		//clear the bases
		bases = new LinkedList<>();
		for (int i = 0; i < 3; i++) {
			bases.add(null);
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

		//g.single();
		//System.out.println("Single:\n" + g);
		g.single();
		System.out.println("Single:\n" + g);
		g.triplePlay();
		System.out.println("Triple Play:\n" + g);
		
	}

}
