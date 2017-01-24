package baseball;

import java.util.*;

public class Game {
	private final static int NUM_INNINGS = 3;
	private boolean gameOver;
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
		gameOver = false;
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
		currentHitter.recordSingle();
		currentHitter.recordRbis(countRuns());
		incrementCurrentHitter();
	}

	/**
	 * Update the game to reflect a double by the current hitter.
	 */
	public void hitDouble() {
		Player currentHitter = getCurrentBatter();
		bases.push(currentHitter);
		bases.push(null);
		currentHitter.recordDouble();
		currentHitter.recordRbis(countRuns());
		incrementCurrentHitter();
	}

	/**
	 * Update the game to reflect a triple by the current hitter.
	 */
	public void triple() {
		Player currentHitter = getCurrentBatter();
		bases.push(currentHitter);
		bases.push(null);
		bases.push(null);
		currentHitter.recordTriple();
		currentHitter.recordRbis(countRuns());
		incrementCurrentHitter();
	}

	public void homerun() {
		Player currentHitter = getCurrentBatter();
		bases.push(currentHitter);
		bases.push(null);
		bases.push(null);
		bases.push(null);
		currentHitter.recordHomerun();
		currentHitter.recordRbis(countRuns());
		incrementCurrentHitter();
	}
	
	public void strikeOut() {
		getCurrentBatter().recordStrikeOut();
		incrementCurrentHitter();
		outs++;
		changeInningIfNeeded();
	}
	
	public void putOut() {
		getCurrentBatter().recordPutout();
		incrementCurrentHitter();
		outs++;
		changeInningIfNeeded();
	}
	
	public void sacrifice() {
		if (!isSacrificeSituation()) {
			System.out.println("Error: not a sacrifice situation!");
			return;
		}
		bases.push(null);
		getCurrentBatter().recordSacrifice();
		getCurrentBatter().recordRbis(countRuns());
		incrementCurrentHitter();
		outs++;
	}

	public void doublePlay() {
		if (!isDoublePlaySituation()) {
			System.out.println("Error: not a double-play situation!");
			return;
		}
		getCurrentBatter().recordDoublePlay();
		incrementCurrentHitter();
		int leadRunnerIndex = 2;
		while (bases.get(leadRunnerIndex) == null) {
			leadRunnerIndex--;
		}
		bases.set(leadRunnerIndex, null);
		outs += 2;
		changeInningIfNeeded();
	}
	
	public void triplePlay() {
		if(!isTriplePlaySituation()) {
			System.out.println("Error: not a triple-play situation!");
			return;
		}
		getCurrentBatter().recordTriplePlay();
		incrementCurrentHitter();
		outs += 3;
		changeInningIfNeeded();
	}

	public Player getCurrentBatter() {
		return isBottom ? homeTeam.get(currentHomeHitter) : awayTeam.get(currentAwayHitter);
	}
	
	public int getAwayScore() {
		return awayScore;
	}
	
	public int getHomeScore() {
		return homeScore;
	}
	
	public int runnersOn() {
		int result = 0;
		for (Player p : bases) {
			if (p != null) {
				result++;
			}
		}
		return result;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	/**
	 * Returns true if a sacrifice is any different than a putout given the game state.
	 * Note: for my purposes, this is identical to a double play situation. For readability and
	 * maintainability, I have provided both functions.
	 */
	public boolean isSacrificeSituation() {
		return outs < 2 && runnersOn() > 0;
	}

	public boolean isDoublePlaySituation() {
		return outs < 2 && runnersOn() > 0;
	}
	
	public boolean isTriplePlaySituation() {
		return outs < 1 && runnersOn() > 1;
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
				curr.recordRun();
			}
		}
		if (!isBottom)
			awayScore += runs;
		else {
			homeScore += runs;
			checkWalkOff();
		}
		return runs;
	}

	private void checkWalkOff() {
		if (inning >= NUM_INNINGS && homeScore > awayScore)
			gameOver = true;
	}
	
	private void changeInningIfNeeded() {
		if (outs < 3) {
			return;
		}
		clearBases();
		if (isBottom) {
			inning++;
		}
		isBottom = !isBottom;
		outs = 0;
		checkGameOver();
	}

	private void checkGameOver() {
		if (inning >= NUM_INNINGS && isBottom && homeScore > awayScore) {
			gameOver = true;
		}
		if (inning > NUM_INNINGS && !isBottom && awayScore > homeScore) {
			gameOver = true;
		}
	}

	private void incrementCurrentHitter() {
		if (!isBottom) {
			currentAwayHitter = (currentAwayHitter + 1) % awayTeam.size();
		} else {
			currentHomeHitter = (currentHomeHitter + 1) % homeTeam.size();
		}
	}
	
	private void clearBases() {
		bases = new LinkedList<>();
		for (int i = 0; i < 3; i++) {
			bases.add(null);
		}
	}
}
