package baseball;

import java.util.*;

public class Game {
	private List<Player> awayTeam;
	private List<Player> homeTeam;
	private int currentAwayHitter;
	private int currentHomeHitter;
	private int inning;
	private boolean isBottom;
	private LinkedList<Player> bases;

	public Game(List<Player> awayTeam, List<Player> homeTeam) {
		this.awayTeam = awayTeam;
		this.homeTeam = homeTeam;
		currentAwayHitter = 0;
		currentHomeHitter = 0;
		inning = 1;
		isBottom = false;
		bases = new LinkedList<>();
		for (int i = 0; i < 4; i++) {
			bases.push(null);
		}
	}

	public static void main(String[] args) {
		Game g = new Game(new ArrayList<Player>(), new ArrayList<Player>());
		System.out.println(g.bases.size());
	}

}
