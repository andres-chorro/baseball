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
	private Bases bases;

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
		bases = new Bases();
	}

	public void single() {
		Player currentHitter = getCurrentBatter();
		bases.advanceRunners(1);
		bases.putRunnerOn(currentHitter, Base.FIRST);
		currentHitter.recordSingle();
		currentHitter.recordRbis(countRuns());
		incrementCurrentHitter();
	}

	public void hitDouble() {
		Player currentHitter = getCurrentBatter();
		bases.advanceRunners(2);
		bases.putRunnerOn(currentHitter, Base.SECOND);
		currentHitter.recordDouble();
		currentHitter.recordRbis(countRuns());
		incrementCurrentHitter();
	}

	public void triple() {
		Player currentHitter = getCurrentBatter();
		bases.advanceRunners(3);
		bases.putRunnerOn(currentHitter, Base.THIRD);
		currentHitter.recordTriple();
		currentHitter.recordRbis(countRuns());
		incrementCurrentHitter();
	}

	public void homerun() {
		Player currentHitter = getCurrentBatter();
		bases.advanceRunners(1);
		bases.putRunnerOn(currentHitter, Base.FIRST);
		bases.advanceRunners(3);
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
		bases.advanceRunners(1);
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
		bases.removeLeadRunner();
		incrementCurrentHitter();
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
		return bases.getNumRunnersOn();
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
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
		s.append(bases.scoreBoardString());
		// batter
		String batterLastName = getCurrentBatter().getLastName();
		int gap = (28 - batterLastName.length()) / 2;
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
		List<Player> scorers = bases.getAndCleanRunnersIn();
		int runs = scorers.size();
		for (Player p : scorers) {
			p.recordRun();
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
		bases.clearBases();
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
}
