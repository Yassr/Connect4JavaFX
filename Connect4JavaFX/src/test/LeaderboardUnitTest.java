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

	@Test
	void testWriteandRead() {
		
		ld.writeLeaderBoard();
		File saveFile = new File("leaderboard.txt");
		ArrayList<String> ldstr = ld.read(saveFile);
		
		for(String win : ldstr ) {
			
			String str = win.replaceAll("(?m)^\\s+$", "");
			String[] input = str.split("\t");
			
			assertEquals("Player1", input[0]);
		}
	}


}