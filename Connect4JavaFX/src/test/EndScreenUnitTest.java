package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.EndScreen;
import application.GameDesign;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.GridPane;

@SuppressWarnings("static-access")
class EndScreenUnitTest {

	EndScreen es;
	GameDesign gd;
	
	@BeforeEach
	void setUp() throws Exception {
		JFXPanel jfxPanel = new JFXPanel();
		gd = new GameDesign();
		gd.createPlayer("Player1", "RED");
		gd.createPlayer("Player2", "Blue");
		
		es = new EndScreen();
		
	}

	@Test
	void testGetWinner() {
		
		// Assumes the last move made is the winner
		// By default player 1 gets the first move (and in this case last move)
		assertEquals("Player1", es.getWinner());
	}

	@Test
	void testGetLoser() {
		// Player 1 got the last move so player 2 is the loser.
		assertEquals("Player2", es.getLoser());
	}

	@Test
	void testEndPane() {
		GridPane ep = es.endPane();
		assertNotNull(ep);
		
		// Ensures that a GridPane is returned
		assertTrue(ep instanceof GridPane);
	}



}
