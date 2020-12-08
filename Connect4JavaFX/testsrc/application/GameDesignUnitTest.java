package application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameDesignUnitTest {

	GameDesign gd;
	@BeforeEach
	void setUp() throws Exception {
		gd = new GameDesign();
	}
	
	@Test
	void testCreatePlayer() {
		
		ArrayList<Player> players = gd.getPlayers();
		gd.createPlayer("TEST", "RED");
		gd.createPlayer("the name", "RED");
		gd.createPlayer(" ", "RED");
		gd.createPlayer("12345678910", "RED");
		
		assertEquals("TEST", gd.getPlayers().get(0).getName());
		assertEquals("RED", gd.getPlayers().get(0).getColour());
		
		// Check the removal of blank spaces
		assertEquals("thename", gd.getPlayers().get(1).getName());
		
		// Test if empty create name
		assertEquals("Player3", gd.getPlayers().get(2).getName());
		
		// Trim name longer than 8
		assertEquals("12345678", gd.getPlayers().get(3).getName());
	}

//	@Test
//	void testSetCOLUMNS() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetROWS() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetPlayers() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testIsPlayer1move() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSetPlayer1Move() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetTileSize() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetColumns() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetRows() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetCircle() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGameDesign() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testHandleButtonAction() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testMakeGrid() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testLighting3D() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSelection() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGameEnd() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testObject() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGetClass() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testHashCode() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testEquals() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testClone() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testToString() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testNotify() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testNotifyAll() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testWaitLong() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testWaitLongInt() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testWait() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testFinalize() {
//		fail("Not yet implemented");
//	}

}
