package test;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.GameDesign;
import application.Disc;
import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

@SuppressWarnings("static-access")
class DiscUnitTest {

	Disc disc;
	Disc disc2;
	GameDesign gd;
	boolean colour;
	
	@BeforeEach
	void setUp() throws Exception {
		JFXPanel jfxPanel = new JFXPanel();
		// Disc(boolean colour1) constructor requires that two players are created and have colours assigned.
		gd = new GameDesign();
		
		gd.createPlayer("player1", "RED");
		gd.createPlayer("player2", "BLUE");
		
		disc = new Disc(colour);
		
		// Constructor Disc() doesn't require any parameters.
		disc2 = new Disc();
		
	}
	
	
	
	@Test
	void testDisc() {
		
		// Ensure elements are not null
		assertNotNull(disc);
		assertNotNull(disc2);
		
		// Tests the elements disc and disc2 were instantiated correctly
		assertTrue(disc2 instanceof Disc);
		assertTrue(disc instanceof Disc);
	}
	


	@Test
	void testGetDisc() {
		Optional<Disc> dd = disc.getDisc(gd.getColumns(), gd.getRows());
		
		// Ensure elements are not null
		assertNotNull(dd);
		
		// Check if the return is of type Optional
		assertTrue(dd instanceof Optional<?>);
		
		// ensure that the return is optional.empty(), meaning it wont ever be null
		assertEquals(Optional.empty(), dd);
		
	}

	@Test
	void testclearAll(){
		assertFalse(disc.isDraw());
		assertTrue(disc.getMovescounter().isEmpty());
	}

}
