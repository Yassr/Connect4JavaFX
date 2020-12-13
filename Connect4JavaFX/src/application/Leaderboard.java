package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public class Leaderboard {
	private static ArrayList<String> leaderboard = new ArrayList<String>();
	static Disc dc = new Disc(false);
	
	public static ArrayList<String> getLeaderboard() {
		return leaderboard;
	}

	
	public static void writeLeaderBoard() {
		try {
			File saveFile = new File("leaderboard.txt");
			
			if(!saveFile.exists()) {
				saveFile.createNewFile();
			}
			
			String text = EndScreen.getWinner()+" "+EndScreen.getLoser()+"\n";
			Files.write(Paths.get("leaderboard.txt"), (text).getBytes(), StandardOpenOption.APPEND);
			
			
		}catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	
	public static ArrayList<String> read(File saveFile) {
		try {
			
			if(!saveFile.exists()) {
				saveFile.createNewFile();
			}
			
			
			Scanner in = new Scanner(saveFile);
			
			while(in.hasNextLine()) {
				String cleaner = in.nextLine().replaceAll("(?m)^\\s+$", "");
				System.out.println(cleaner);
				String[] input = cleaner.split(" ");
				
				if(!dc.isDraw()) {
					Leaderboard.getLeaderboard().add("\t\tDRAW");
				}else {
					if(input[0].isEmpty() || input[1].isEmpty()) {
						continue;
					}
					if(input[0].length() < 6 ) {
						Leaderboard.getLeaderboard().add(input[0]+"\t\t\t\t"+ input[1]);
					}else if(input[0].length() >= 6){
						input[0].substring(0, 5);
						Leaderboard.getLeaderboard().add(input[0]+"\t\t\t"+ input[1]);
					}
				}
				
			}
			in.close();
			
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		return Leaderboard.getLeaderboard();
	}
	

}
