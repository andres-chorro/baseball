package baseball;
import java.util.*;

public class Game {
	private ArrayList<Player> awayTeam;
	private ArrayList<Player> homeTeam;
	private int inning;
	private boolean isBottom;
	private LinkedList<Player> bases;
	
	public Game(ArrayList<Player> awayTeam, ArrayList<Player> homeTeam) {
		this.awayTeam = awayTeam;
		this.homeTeam = homeTeam;
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
