package baseball;

import java.io.*;
import java.util.List;

public class Player implements Serializable{
	private String firstName;
	private String lastName;
	private int number;
	private int atBats;
	private int singles;
	private int doubles;
	private int triples;
	private int homeruns;
	private int runs;
	private int rbis;
	private int sacs;
	private int ks;
	private int gidps;
	private int wins;
	private int losses;
	
	/**
	 * Initializes a Player with a given name and number, and an empty stat sheet.
	 * @param firstName player's first name
	 * @param lastName player's last name
	 * @param number player's number
	 */
	public Player(String firstName, String lastName, int number) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.number = number;
	}
	
	/**
	 * Update stats after a strikeout
	 */
	public void strikeOut() {
		atBats++;
		ks++;
	}
	
	/**
	 * Update stats after a putout
	 */
	public void putout() {
		atBats++;
	}
	
	/**
	 * Update stats after a double play
	 */
	public void doublePlay() {
		atBats++;
		gidps++;
	}
	
	/**
	 * Update stats after a triple play
	 */
	public void triplePlay() {
		atBats++;
		gidps++;
	}
	
	/**
	 * Update stats after a sacrifice
	 * @param rbis number of runs on the sacrifice (0 or 1)
	 */
	public void sacrifice(int rbis) {
		if (rbis < 0 || rbis > 1) {
			System.out.println("Invalid number of RBIs on sacrifice");
			return;
		}
		
		this.rbis += rbis;
		sacs++;
	}
	
	/**
	 * Update stats after a single.
	 * @param rbis number of runs driven in on the single
	 */
	public void single(int rbis) {
		if (rbis < 0 || rbis > 1) {
			System.out.println("Invalid number of RBIs on single");
			return;
		}
		
		this.rbis += rbis;
		singles++;
		atBats++;
	}
	
	/**
	 * Update stats after a double.
	 * @param rbis number of runs driven in on the double
	 */
	public void hitDouble(int rbis) {
		if (rbis < 0 || rbis > 2) {
			System.out.println("Invalid number of RBIs on double");
			return;
		}
		
		this.rbis += rbis;
		doubles++;
		atBats++;
	}
	
	/**
	 * Update stats after a triple.
	 * @param rbis number of runs driven in on the triple
	 */
	public void triple(int rbis) {
		if (rbis < 0 || rbis > 3) {
			System.out.println("Invalid number of RBIs on triple");
			return;
		}
		
		this.rbis += rbis;
		triples++;
		atBats++;
	}
	
	/**
	 * Update stats after a homerun.
	 * @param rbis number of runs driven in on the homerun
	 */
	public void homerun(int rbis) {
		if (rbis < 1 || rbis > 4) {
			System.out.println("Invalid number of RBIs on homerun");
			return;
		}
		
		this.rbis += rbis;
		homeruns++;
		atBats++;
	}
	
	/**
	 * Update stats for a run scored.
	 */
	public void addRun() {
		runs++;
	}
	
	/**
	 * Update stats after a win.
	 */
	public void win() {
		wins++;
	}
	
	/**
	 * Update stats after a loss.
	 */
	public void loss() {
		losses++;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public int getNumber(){
		return number;
	}
	
	public int getAtBats() {
		return atBats;
	}
	
	public int getHits() {
		return singles + doubles + triples + homeruns;
	}
	
	public int getSingles() {
		return singles;
	}
	
	public int getDoubles() {
		return doubles;
	}
	
	public int getTriples() {
		return triples;
	}
	
	public int getHomeruns() {
		return homeruns;
	}
	
	public int getRuns() {
		return runs;
	}
	
	public int getRbis() {
		return rbis;
	}

	public int getSacs() {
		return sacs;
	}
	
	public int getKs() {
		return ks;
	}
	
	public int getGidps() {
		return gidps;
	}
	
	public int getWins() {
		return wins;
	}
	
	public int getLosses() {
		return losses;
	}
	
	/**
	 * Returns a players full name.
	 * @return full name
	 */
	public String fullName() {
		return firstName + ' ' + lastName;
	}
	
	@Override
	public String toString() {
		return fullName() + " " + number;
	}
	
	/**
	 * Return the player's batting average
	 * @return the player's batting average
	 */
	public double getBattingAverage() {
		if (atBats < 1)
			return 0;
		return (double) (singles + doubles + triples + homeruns) / atBats;
	}
	
	/**
	 * Converts a double representing an average (avg, slg, etc.)
	 * into a string with commonly used baseball format.
	 * @param avg
	 * @return
	 */
	public static String baseballPercentage(double avg) {
		if (avg >= 10 || avg < 0) 
			throw new IllegalArgumentException("Averages must be between 0 and 10");
		String result = String.format("%.3f", avg);
		if (avg < 1) {
			result = result.substring(1);
		}
		return result;
	}
	
	/**
	 * Returns the player's slugging percentage
	 * @return slugging percentage
	 */
	public double getSluggingPercentage() {
		if (atBats < 1)
			return 0;
		return (double) (singles + 2 * doubles + 3 * triples + 4 * homeruns) / atBats;
	}
	
	public static String hittingStats(List<Player> list) {
		StringBuilder res = new StringBuilder();
		String bar = "--------------------------------------------------------------------------------\n";
		res.append(bar);
		res.append("|Name                          |   AB| RBI|   H|  1B|  2B|  3B|  HR|  AVG|  SLG|\n");
		res.append(bar);
		for (Player p: list) {
			res.append(String.format("|%-30s|%5d|%4d|%4d|%4d|%4d|%4d|%4d|%5s|%5s|\n", 
					p.getLastName() + ", " + p.getFirstName(), p.getAtBats(), p.getRbis(),
					p.getHits(), p.getSingles(), p.getDoubles(), p.getTriples(), p.getHomeruns(),
					baseballPercentage(p.getBattingAverage()), baseballPercentage(p.getSluggingPercentage())));
			res.append(bar);
		}
		return res.toString();
		
	}
	
	public static void main(String[] args) {
		System.out.println(baseballPercentage(1.534543));
		System.out.println(baseballPercentage(0.110232));
		System.out.println(baseballPercentage(0));
		System.out.println(baseballPercentage(-3.53));
		System.out.println(baseballPercentage(10.53435));
		
	}
}
