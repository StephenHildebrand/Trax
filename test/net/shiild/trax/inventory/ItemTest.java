/**
 * 
 */
package net.shiild.trax.inventory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.shiild.trax.inventory.Thing;

/**
 * Test class for Thing.
 * 
 * @author StephenHildebrand
 */
public class ItemTest {
	/** Valid movie object with 1 copy in stock */
	Thing mValid;
	/** Valid movie object with negative stock */
	Thing mNegativeStock;
	/** Valid movie object with zero stock */
	Thing mZeroStock;

	/**
	 * Sets up the movie objects prior to use by the test methods.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Initialize each valid movie object with appropriate parameters
		mValid = new Thing("1 Thing Name");
		mNegativeStock = new Thing("-1 Name");
		mZeroStock = new Thing("0 Name");
	}

	/**
	 * Test method for
	 * {@link Thing#Movie(java.lang.String)}.
	 */
	@Test
	public void testMovie() {
		// Initialize each invalid movie object
		Thing mNull = null;
		Thing mNoStock = null;
		Thing mInvalidStock = null;
		Thing mNoStockNumberInTitle = null;

		// Valid movie
		assertTrue(mValid.isAvailable());
		assertEquals("Thing Name", mValid.getName());
		mValid.removeOneCopyFromInventory();
		// Remove a copy of movieValid and check that it is now unavailable
		assertFalse(mValid.isAvailable());

		// Valid movie: number at beginning of name
		Thing mNumberInName = new Thing("5 10 Angry Men");
		assertTrue(mNumberInName.isAvailable());
		assertEquals("10 Angry Men", mNumberInName.getName());

		// Valid movie: negative stock
		assertEquals("Name", mNegativeStock.getName());
		assertFalse(mNegativeStock.isAvailable());
		mNegativeStock.backToInventory();
		assertTrue(mNegativeStock.isAvailable());

		// Thing with null string parameter
		try {
			String nullString = null;
			mNull = new Thing(nullString);
			fail("Thing was created with null string");
		} catch (IllegalArgumentException e) {
			assertNull(mNull);
		}

		// Thing with empty string parameter
		try {
			String emptyString = "";
			mNull = new Thing(emptyString);
			fail("Thing was created with empty string");
		} catch (IllegalArgumentException e) {
			assertNull(mNull);
		}

		// Invalid movie: No stock in raw string
		try {
			mNoStock = new Thing("Title");
			fail("movieNoStock was created.");
		} catch (IllegalArgumentException e) {
			assertNull(mNoStock);
		}

		// Invalid movie: no stock in raw string, but a number at title start
		try {
			mInvalidStock = new Thing("10Angry Men");
			fail("movieInvalidStock was created.");
		} catch (IllegalArgumentException e) {
			assertNull(mInvalidStock);
		}

		// Invalid movie: no stock in raw string, but a number inside title
		try {
			mNoStockNumberInTitle = new Thing("The Godfather: Part 2");
			fail("movieNoStockWithNumberInsideTitle was created.");
		} catch (IllegalArgumentException e) {
			assertNull(mNoStockNumberInTitle);
		}
	}

	/**
	 * Test method for
	 * {@link Thing#getDisplayName()}.
	 */
	@Test
	public void testGetDisplayName() {
		// Available movie
		assertEquals("Thing Name", mValid.getDisplayName());
		// Unavailable movie
		assertEquals("Name (currently unavailable)", mZeroStock.getDisplayName());
	}

	/**
	 * Note: in lexical order, "a" is less than "b". Test method for
	 * {@link Thing#compareToByName(Thing)}
	 * .
	 */
	@Test
	public void testCompareToByName() {
		Thing movieAm = new Thing("1 American Sniper");
		Thing movieAm2 = new Thing("2 American Sniper");
		Thing movieAb = new Thing("1 About Time");
		Thing movieG = new Thing("1 Gone Girl");
		Thing movieTheH = new Thing("1  The Hunger Games: Mockingjay, Part 1");
		Thing movieS = new Thing("2 Selma");

		// Compare "American Sniper" to movie with same name
		assertEquals(0, movieAm.compareToByName(movieAm2));

		// Positive when "American Sniper" compared to "About Time"
		assertTrue(movieAm.compareToByName(movieAb) > 0);

		// Negative when the reverse of above is tested.
		assertTrue(movieAb.compareToByName(movieAm) < 0);

		// Positive value when "Gone Girl" is compared to "About Time"
		assertTrue(movieG.compareToByName(movieAb) > 0);

		// Check that article "The" at beginning of a movie is ignored
		assertTrue(movieTheH.compareToByName(movieS) < 0);
	}

	/**
	 * Test method for
	 * {@link Thing#removeOneCopyFromInventory()}
	 * .
	 */
	@Test
	public void testRemoveOneCopyFromInventory() {
		// Stores the original movieZeroStock movie
		Thing movieTemp = mZeroStock;

		// Check that IllegalStateException is thrown and movie is unchanged.
		try {
			mZeroStock.removeOneCopyFromInventory();
			fail("IllegalStateException should have been thrown");
		} catch (IllegalStateException e) {
			assertEquals(movieTemp, mZeroStock);
		}
	}
}
