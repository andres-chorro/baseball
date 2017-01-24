package baseball;

import java.io.*;
import java.util.List;

public class Player implements Serializable{
	private static final long serialVersionUID = 1L;
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
	
	public Player(String firstName, String lastName, int number) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.number = number;
	}
	
	public void recordStrikeOut() {
		atBats++;
		ks++;
	}
	
	public void recordPutout() {
		atBats++;
	}
	
	public void recordDoublePlay() {
		atBats++;
		gidps++;
	}
	
	public void recordTriplePlay() {
		atBats++;
		gidps++;
	}
	
	public void recordSacrifice() {
		sacs++;
	}
	
	public void recordSingle() {
		singles++;
		atBats++;
	}
	
	public void recordDouble() {
		doubles++;
		atBats++;
	}
	
	public void recordTriple() {
		triples++;
		atBats++;
	}
	
	public void recordHomerun() {
		homeruns++;
		atBats++;
	}
	
	public void recordRbis(int amount) {
		rbis += amount;
	}
	
	public void recordRun() {
		runs++;
	}
	
	public void recordWin() {
		wins++;
	}
	
	public void recordLoss() {
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
	
	public String fullName() {
		return firstName + ' ' + lastName;
	}
	
	@Override
	public String toString() {
		return fullName() + " " + number;
	}
	
	public double getBattingAverage() {
		if (atBats < 1)
			return 0;
		return (double) (singles + doubles + triples + homeruns) / atBats;
	}
	
	public double getSluggingPercentage() {
		if (atBats < 1)
			return 0;
		return (double) (singles + 2 * doubles + 3 * triples + 4 * homeruns) / atBats;
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
