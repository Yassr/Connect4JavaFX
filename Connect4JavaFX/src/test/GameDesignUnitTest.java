package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.GameDesign;
import javafx.geometry.Point2D;
import javafx.scene.effect.Effect;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

@SuppressWarnings("static-access")
class GameDesignUnitTest {

	GameDesign gd;
	
	@BeforeEach
	void setUp() throws Exception {
		gd = new GameDesign();
	}
	
	@Test
	void testCreatePlayer() {
		

		gd.createPlayer("Player1", "");
		gd.createPlayer("Player2   ", "BLUE");
		gd.createPlayer(" ", "RED");
		gd.createPlayer("12345678910", "RED");
		
		assertEquals("Player1", gd.getPlayers().get(0).getName());
		
		// Empty colour, defaults to RED (web value = #ff0000)
		assertEquals("#ff0000", gd.getPlayers().get(0).getColour());
		
		// Check the removal of blank spaces
		assertEquals("Player2", gd.getPlayers().get(1).getName());
		
		// Test if empty create name
		assertEquals("Player3", gd.getPlayers().get(2).getName());
		
		// Trim name longer than 8
		assertEquals("12345678", gd.getPlayers().get(3).getName());
		
		
	}


	@Test
	void testSetColumnsAndRows() {
		
		// Check the default value for Columns 
		assertEquals(7, gd.getColumns());
		
		// Check the default value for Rows 
		assertEquals(6, gd.getRows());
		
		// Set Columns to 9
		gd.setCOLUMNS(9);
		assertEquals(9, gd.getColumns());
		
		// Set Rows to 9
		gd.setROWS(9);
		assertEquals(9, gd.getRows());

	}


	@Test
	void testPlayer1move() {
		// Check that it is Player 1's move
		assertTrue(gd.isPlayer1move());
		
		// Set player 1's move to false, meaning its player 2's turn
		gd.setPlayer1Move(false);
		assertFalse(gd.isPlayer1move());
	}



	@Test
	void testMakeGrid() {
		Shape myshape = gd.makeGrid();
		
		// Check if the object is != null
		assertNotNull(myshape);
		
		// Check that the correct object is returned
		assertTrue(myshape instanceof Shape);
		
	}

	@Test
	void testLighting3D() {
		Effect myeffect = gd.lighting3D();
		
		// Check if the object is != null
		assertNotNull(myeffect);
				
		// Check that the correct object is returned
		assertTrue(myeffect instanceof Effect);
		
	}

	@Test
	void testSelection() {
		List<Rectangle> mylist = gd.selection();
		
		// Check if the object is != null
		assertNotNull(mylist);
		
		// First item from the list should be a transparent rectangle at the top left corner
		String firstItem = "Rectangle[x=0.0, y=0.0, width=80.0, height=560.0, fill=0x00000000]";
		assertEquals(firstItem, mylist.get(0).toString());
		
	}
	
	
	@Test
	void testGameEnd() {
		JFXPanel jfxPanel = new JFXPanel();
		int row = 5;
		int column = 0;
		
		boolean gameend = gd.gameEnd(column, row);
		// Check if the object is != null
		assertNotNull(gameend);
		
		List<Point2D> vertical = IntStream.rangeClosed(row - 3, row + 3).mapToObj(r -> new Point2D(column, r)).collect(Collectors.toList());
		boolean checkwin = gd.checkWin(vertical);
		// Check if the object is != null
		assertNotNull(checkwin);
		
		// Game was never started for it to end... 
		assertFalse(gameend);
		assertFalse(checkwin);

	}


}
