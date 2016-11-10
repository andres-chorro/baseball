package baseball;

import java.io.*;
import java.util.*;


/**
 * Helper class that creates a single serialized data file for an ArrayList of
 * Players. Call save to replace the file with the list you provide, and load
 * to retrieve that list.
 * @author Andres Chorro
 */
public class PlayerSerializer {
	private final String filePath;

	/**
	 * Initialize a PlayerSerializer that will store the list of players in a
	 * given file.
	 * @param filePath The file to store the players in
	 */
	public PlayerSerializer(String filePath) {
		this.filePath = filePath;
	}

	public void save(List<Player> players) {
		try {
			// delete the file if it exists
			File fileOut = new File(filePath);
			if (fileOut.exists() && !fileOut.isDirectory())
				fileOut.delete();
			
			FileOutputStream outFile = new FileOutputStream(filePath);
			ObjectOutputStream out = new ObjectOutputStream(outFile);
			out.writeObject(players);
			out.close();
			outFile.close();
			
		} catch (IOException i) {
			i.printStackTrace();
		}
	}
	
	public ArrayList<Player> load() {
		try {
	         FileInputStream fileIn = new FileInputStream(filePath);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         ArrayList<Player> playerList = (ArrayList<Player>) in.readObject();
	         in.close();
	         fileIn.close();
	         return playerList;
	      }catch(IOException i) {
	         i.printStackTrace();
	         return null;
	      }catch(ClassNotFoundException c) {
	         System.out.println("Player List class not found");
	         c.printStackTrace();
	         return null;
	      }
	}
	
	public static void main(String[] args) {
		//Test normal case.
		PlayerSerializer ps1 = new PlayerSerializer("test1.ser");
		List<Player> players1 = new ArrayList<>();
		players1.add(new Player("Andres", "Chorro", 73));
		players1.add(new Player("Quique", "Chorro", 16));
		ps1.save(players1);
		System.out.println(ps1.load());
		
		//Test case of loading before saving.
		PlayerSerializer ps2 = new PlayerSerializer("test2.ser");
		System.out.println(ps2.load());
		
		//Test case of loading twice in a row
		PlayerSerializer ps3 = new PlayerSerializer("test3.ser");
		List<Player> players3 = new ArrayList<>();
		players3.add(new Player("Andres", "Chorro", 73));
		ps3.save(players3);
		System.out.println(ps3.load());
		players3.add(new Player("Quique", "Chorro", 16));
		System.out.println(ps3.load());
		System.out.println(players3);
	}
}
