package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.GameDesign;
import application.Leaderboard;
import javafx.embed.swing.JFXPanel;

@SuppressWarnings("static-access")
class LeaderboardUnitTest {

	Leaderboard ld;
	GameDesign gd;
	ArrayList<String> str;
	
	@BeforeEach
	void setUp() throws Exception {
		JFXPanel jfxPanel = new JFXPanel();
		
		ld = new Leaderboard();
		str= new ArrayList<String>();
		
		gd = new GameDesign();
		gd.createPlayer("Player1", "RED");
		gd.createPlayer("Player2", "BLUE");
	}

	@Test
	void testGetLeaderboard() {
		
		assertEquals(str, ld.getLeaderboard());
		
	}

	// Clear the Leaderboard.txt file before running this test
	@Test
	void testWriteandRead() {
		
		ld.writeLeaderBoard();
		File saveFile = new File("leaderboard.txt");
		ArrayList<String> ldstr = ld.readFile(saveFile);
		
		for(String win : ldstr ) {
			
			String str = win.replaceAll("(?m)^\\s+$", "");
			str = win.replaceAll("\t", "");
			String[] input = str.split("");
			
			assertEquals("Player2Player1", str);
		}
	}


}
