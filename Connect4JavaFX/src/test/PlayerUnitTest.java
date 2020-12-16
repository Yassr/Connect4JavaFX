package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.Player;

class PlayerUnitTest {

	Player p1;
	
	@BeforeEach
	void setUp() throws Exception {
		p1 = new Player("Player1", "RED");
	}



	@Test
	void testGetName() {
		assertEquals("Player1", p1.getName());
	}

	@Test
	void testSetName() {
		
		String name = "Yassr";
		p1.setName(name);
		
		assertEquals("Yassr", p1.getName());
		
	}

	@Test
	void testGetColour() {
		
		assertEquals("RED", p1.getColour());
	}

	@Test
	void testSetColour() {
		
		String colour = "BLUE";
		p1.setColour(colour);
		
		assertEquals("BLUE", p1.getColour());
	}


}
