package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("static-access")
class DiscUnitTest {

	
	Disc disc;
	boolean colour1 = true;
	
	@BeforeEach
	void setUp() throws Exception {
		disc = new Disc(colour1);
	}
	
	
	

	@Test
	void testIsColour1() {
		
		
		assertNull(disc.isColour1());
	}

//	@Test
//	void testGetDiscRoot() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	void testGetnamechng() {
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	void testDisc() {
//		fail("Not yet implemented"); // TODO
//	}
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
