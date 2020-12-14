package application;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

@SuppressWarnings("static-access")
class DiscUnitTest {

	Disc disc;
	GameDesign gd;
	boolean colour;
	
	@BeforeEach
	void setUp() throws Exception {
		gd = new GameDesign();
	}
	
	
	
	@Test
	void testDisc() {
		
		ArrayList<Player> players = gd.getPlayers();
		gd.createPlayer("player1", "BLUE");
		gd.createPlayer("player2", "Red");
		
		disc = new Disc(colour);
		assertTrue(disc instanceof Disc);
	}
//
//	@Test
//	void testDropDisc() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	void testGetDisc() {
//		fail("Not yet implemented"); // TODO
//	}

}
