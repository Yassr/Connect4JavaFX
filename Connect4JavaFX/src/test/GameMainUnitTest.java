package test;

import static org.junit.jupiter.api.Assertions.*;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.GameMain;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

class GameMainUnitTest{

	GameMain gm;
	@BeforeEach
	void setUp() throws Exception {
		JFXPanel jfxPanel = new JFXPanel();
		gm = new GameMain();
	}
	
//	@Test
//	void testStartGame() {
//
//		assertThrows(gm.start(gm.getStage()));
//
//	}


	


}
