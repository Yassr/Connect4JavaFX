package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.EndScreen;
import application.GameDesign;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextArea;
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
	void testEndPane() {
		GridPane ep = es.endPane();
		assertNotNull(ep);
		
		// Ensures that a GridPane is returned
		assertTrue(ep instanceof GridPane);
	}


	@Test
	void testprintLeaderTextArea() {
		
		TextArea ta = es.printLeaderTextArea();
		assertNotNull(ta);
		
		// Ensures that a GridPane is returned
		assertTrue(ta instanceof TextArea);
		
	}

}
