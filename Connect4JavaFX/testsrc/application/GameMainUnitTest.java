package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

class GameMainUnitTest{

	GameMain gc;
	@BeforeEach
	void setUp() throws Exception {
		gc = new GameMain();
	}
	
	@Test
	void testStartGame() {
		
//		assertNotNull(gc.startGame());
//		assertTrue(gc.startGame() instanceof Pane);
	}

	


}
