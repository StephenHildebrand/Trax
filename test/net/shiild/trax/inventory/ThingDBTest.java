/**
 * 
 */
package net.shiild.trax.inventory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.shiild.trax.inventory.ThingDB;

/**
 * Test class for ThingDB.
 * 
 * @author StephenHildebrand
 */
public class ThingDBTest {
	/** A thing database to be used for testing */
	ThingDB mDB, shortDB;
	/** a null thing database */
	ThingDB nullDB;

	/**
	 * Set up before running other tests.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Instantiate/Initialize each ThingDB appropriately
		mDB = new ThingDB("test/res/things.txt");
		shortDB = new ThingDB("test/res/things-short.txt");
		nullDB = null;
	}

	/**
	 * Test method for
	 * {@link ThingDB#ThingDB(java.lang.String)}
	 * .
	 */
	@Test
	public void testThingDB() {
		// Sorted mDB order of the first 5 elements is: 12 Years a Slave, 300:
		// Rise of an Empire, About Schmidt,
		// Aloha, The Amazing Spider-man 2
		assertEquals("12 Years a Slave", mDB.findThingAt(0).getName());
		assertEquals("300: Rise of an Empire", mDB.findThingAt(1).getName());
		assertEquals("About Schmidt", mDB.findThingAt(2).getName());
		assertEquals("Aloha", mDB.findThingAt(3).getName());
		assertEquals("The Amazing Spider-man 2", mDB.findThingAt(4).getName());
	}

	/**
	 * Test method for
	 * {@link ThingDB#traverse()}.
	 */
	@Test
	public void testTraverse() {
		// Valid thing to traverse
		String expectedDB = "Frozen\nGravity\nHow to Train Your Dragon 2\nSpectre (currently unavailable)\nWarcraft (currently unavailable)\n";
		assertEquals(expectedDB, shortDB.traverse());
	}

	/**
	 * Test method for
	 * {@link ThingDB#findThingA(int)}.
	 */
	@Test
	public void testFindThingA() {
		// Check that the first Thing in mDB is correctly sorted
		assertEquals("12 Years a Slave", mDB.findThingAt(0).getName());

		try {
			mDB.findThingAt(-3);
			fail("IllegalArgumentException should have been thrown");
		} catch (IllegalArgumentException e) {
			assertEquals("12 Years a Slave", mDB.findThingAt(0).getName());
		}
	}
}
