package baseball;

public class Player {
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
	public double battingAverage() {
		if (atBats < 1)
			return 0;
		return (double) (singles + doubles + triples + homeruns) / atBats;
	}
	
	public static void main(String[] args) {
		Player p = new Player("John", "Dowd", 25);
		System.out.println(p.fullName());
		System.out.println(p.battingAverage());
		p.single(1);
		p.strikeOut();
		p.doublePlay();
		System.out.println(p.battingAverage());
		
	}
}
