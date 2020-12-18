package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.MainMenu;


import javafx.embed.swing.JFXPanel;

import javafx.scene.layout.GridPane;



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
		
		// Ensures that a GridPane is returned
		assertTrue(gp instanceof GridPane);
		
	}

	@Test
	void testMusicToggle() {
		
		/*	mm.isMusic() would be true by default
		/	If we run musicToggle on it it will switch
		/	between true or false
		*/
		
//		 true --> false
		mm.musicToggle();
		assertEquals(false, mm.isMusic());
		
		
		// false --> true
		mm.musicToggle();
		assertEquals(true, mm.isMusic());
		
	}






	


	

}
