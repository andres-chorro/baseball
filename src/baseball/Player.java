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
}
