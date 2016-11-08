package baseball;

import java.io.*;

public class Player implements Serializable{
	private String firstName;
	private String lastName;
	private int number;
	private int atBats;
	private int singles;
	private int doubles;
	private int triples;
	private int homeruns;
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
	 * Returns the player's slugging percentage
	 * @return slugging percentage
	 */
	public double getSluggingPercentage() {
		if (atBats < 1)
			return 0;
		return (double) (singles + 2 * doubles + 3 * triples + 4 * homeruns) / atBats;
	}
	
	public static void main(String[] args) {
		Player p = null;
		try {
			FileInputStream fileIn = new FileInputStream("players.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			p = (Player) in.readObject();
			in.close();
			fileIn.close();
		} catch(IOException i){
			i.printStackTrace();
			p = new Player("John", "Dowd", 25);
		} catch(ClassNotFoundException c) {
			System.out.println("Player class not found");
			c.printStackTrace();
			p = new Player("John", "Dowd", 25);
		}
		System.out.println(p.fullName());
		System.out.println(p.getBattingAverage());
		p.single(1);
		p.homerun(3);
		p.strikeOut();
		p.doublePlay();
		System.out.println(p.getBattingAverage());
		System.out.println(p.getSluggingPercentage());
		System.out.println(p.getAtBats());
		try {
			FileOutputStream fileOut = new FileOutputStream("players.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(p);
			out.close();
			fileOut.close();
			System.out.println("Serialized player is stored in players.ser");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
