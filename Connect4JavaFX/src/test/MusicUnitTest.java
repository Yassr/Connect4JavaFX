package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import application.Music;
import javafx.embed.swing.JFXPanel;

class MusicUnitTest {

	Music mm;
	
	@BeforeEach
	void setUp() throws Exception {
		
		JFXPanel jfxPanel = new JFXPanel();
	
		mm = new Music("/audio/victory.wav", true);
	}

	@Test
	void testMusic() {
		
		assertNotNull(mm);
	}

}
