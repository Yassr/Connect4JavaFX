package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * 
 * @author Yassr Shaar - 14328571
 * Leaderboard class writes the results of the game into a textfile
 * and reads the contents of the textfile to pass on an ArrayList of previous game results for displaying
 *
 */
public class Leaderboard {
	// Used for storing the sentences read from the previous game results
	private static ArrayList<String> leaderboard = new ArrayList<String>();
	static Disc dc = new Disc();
	
	public static ArrayList<String> getLeaderboard() {
		return leaderboard;
	}

	/**
	 * writeLeaderBoard() tries to open the file leaderboard.txt, if it doesnt exist it creates a new one
	 * then saves the results of the game into the text file
	 */
	public static void writeLeaderBoard() {
		String winner = GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getName() : GameDesign.getPlayers().get(1).getName();
		String loser = !GameDesign.isPlayer1move() ? GameDesign.getPlayers().get(0).getName() : GameDesign.getPlayers().get(1).getName();
		
		try {
			File saveFile = new File("leaderboard.txt");
			
			if(!saveFile.exists()) {
				saveFile.createNewFile();
			}
			
			// If the game is a draw then simply write that it is a draw, otherwise write the winner and loser of the game
			if(dc.isDraw()) {
				String text = "draw draw\n";
				Files.write(Paths.get("leaderboard.txt"), (text).getBytes(), StandardOpenOption.APPEND);
			}else {
				String text = winner+" "+loser+"\n";
				Files.write(Paths.get("leaderboard.txt"), (text).getBytes(), StandardOpenOption.APPEND);
			}		
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param saveFile is retrieved to read the leaderboard.txt of previous games
	 * @return the leaderboard ArrayList which contains all of the details from leaderboard.txt
	 */
	public static ArrayList<String> read(File saveFile) {
		try {
			
			if(!saveFile.exists()) {
				saveFile.createNewFile();
			}
			
			Scanner in = new Scanner(saveFile);
			
			while(in.hasNextLine()) {
				// Remove any spaces and split the sentences 
				String cleaner = in.nextLine().replaceAll("(?m)^\\s+$", "");
				String[] input = cleaner.split(" ");
				
				// If any parts of the sentence is black, move on, no need to crash...
				// This can be necessary if the first line of the text file is blank
				if(input[0].isEmpty() || input[1].isEmpty()) {
					continue;
				}
				
				// Trying to preserve some sort of order to how text looks when displayed
				if(input[0].length() < 5 ) {
					leaderboard.add(input[0]+"\t\t\t\t"+ input[1]);
				}else if(input[0].length() >= 5){
					leaderboard.add(input[0]+"\t\t\t"+ input[1]);
				}
			}
			in.close();
			
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		return Leaderboard.getLeaderboard();
	}
	

}
