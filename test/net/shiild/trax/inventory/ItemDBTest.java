/**
 * 
 */
package net.shiild.trax.inventory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.shiild.trax.inventory.ItemDB;

/**
 * Test class for ItemDB.
 * 
 * @author StephenHildebrand
 */
public class ItemDBTest {
	/** A movie database to be used for testing */
	ItemDB mDB, shortDB;
	/** a null movie database */
	ItemDB nullDB;

	/**
	 * Set up before running other tests.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Instantiate/Initialize each ItemDB appropriately
		mDB = new ItemDB("movies.txt");
		shortDB = new ItemDB("movies-short.txt");
		nullDB = null;
	}

	/**
	 * Test method for
	 * {@link ItemDB#MovieDB(java.lang.String)}
	 * .
	 */
	@Test
	public void testMovieDB() {
		// Sorted mDB order of the first 5 elements is: 12 Years a Slave, 300:
		// Rise of an Empire, About Schmidt,
		// Aloha, The Amazing Spider-man 2
		assertEquals("12 Years a Slave", mDB.findItemAt(0).getName());
		assertEquals("300: Rise of an Empire", mDB.findItemAt(1).getName());
		assertEquals("About Schmidt", mDB.findItemAt(2).getName());
		assertEquals("Aloha", mDB.findItemAt(3).getName());
		assertEquals("The Amazing Spider-man 2", mDB.findItemAt(4).getName());
	}

	/**
	 * Test method for
	 * {@link ItemDB#traverse()}.
	 */
	@Test
	public void testTraverse() {
		// Valid movie to traverse
		String expectedDB = "Frozen\nGravity\nHow to Train Your Dragon 2\nSpectre (currently unavailable)\nWarcraft (currently unavailable)\n";
		assertEquals(expectedDB, shortDB.traverse());
	}

	/**
	 * Test method for
	 * {@link ItemDB#findItemA(int)}.
	 */
	@Test
	public void testFindItemA() {
		// Check that the first Item in mDB is correctly sorted
		assertEquals("12 Years a Slave", mDB.findItemAt(0).getName());

		try {
			mDB.findItemAt(-3);
			fail("IllegalArgumentException should have been thrown");
		} catch (IllegalArgumentException e) {
			assertEquals("12 Years a Slave", mDB.findItemAt(0).getName());
		}
	}
}
