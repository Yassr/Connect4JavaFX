package test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.MainMenu;

import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


@SuppressWarnings("static-access")
class MainMenuUnitTest{
	
	MainMenu mm;
	 
	@BeforeEach
	void setUp() throws Exception {
		/* JFXPanel is required to allow for the testing of Classes
		 *  that depend on "internal graphics initalization"
		 */
		
		JFXPanel jfxPanel = new JFXPanel();

        mm = new MainMenu();
	}

	
	@Test
	void testPreference() {
		
		GridPane gp = mm.preference();
		assertNotNull(gp);
		
		//
		assertTrue(gp instanceof GridPane);
		
	}
//
//	@Test
//	void testMusicToggle() {
//		fail("Not yet implemented"); // TODO
//	}






	


	

}
