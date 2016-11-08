package baseball;

import java.io.*;
import java.util.*;

public class PlayerSerializer {
	private String filePath;

	public PlayerSerializer(String filePath) {
		this.filePath = filePath;
	}

	public void save(List<Player> players) {
		try {
			File fileOut = new File(filePath);
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
	
	public List<Player> load() {
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
		PlayerSerializer ps = new PlayerSerializer("test1.ser");
		List<Player> players = new ArrayList<>();
		players.add(new Player("Andres", "Chorro", 73));
		players.add(new Player("Quique", "Chorro", 16));
		ps.save(players);
		System.out.println(ps.load());
	}
}
