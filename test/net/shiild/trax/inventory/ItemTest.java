/**
 * 
 */
package net.shiild.trax.inventory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.shiild.trax.inventory.Item;

/**
 * Test class for Item.
 * 
 * @author StephenHildebrand
 */
public class ItemTest {
	/** Valid movie object with 1 copy in stock */
	Item mValid;
	/** Valid movie object with negative stock */
	Item mNegativeStock;
	/** Valid movie object with zero stock */
	Item mZeroStock;

	/**
	 * Sets up the movie objects prior to use by the test methods.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Initialize each valid movie object with appropriate parameters
		mValid = new Item("1 Item Name");
		mNegativeStock = new Item("-1 Name");
		mZeroStock = new Item("0 Name");
	}

	/**
	 * Test method for
	 * {@link Item#Movie(java.lang.String)}.
	 */
	@Test
	public void testMovie() {
		// Initialize each invalid movie object
		Item mNull = null;
		Item mNoStock = null;
		Item mInvalidStock = null;
		Item mNoStockNumberInTitle = null;

		// Valid movie
		assertTrue(mValid.isAvailable());
		assertEquals("Item Name", mValid.getName());
		mValid.removeOneCopyFromInventory();
		// Remove a copy of movieValid and check that it is now unavailable
		assertFalse(mValid.isAvailable());

		// Valid movie: number at beginning of name
		Item mNumberInName = new Item("5 10 Angry Men");
		assertTrue(mNumberInName.isAvailable());
		assertEquals("10 Angry Men", mNumberInName.getName());

		// Valid movie: negative stock
		assertEquals("Name", mNegativeStock.getName());
		assertFalse(mNegativeStock.isAvailable());
		mNegativeStock.backToInventory();
		assertTrue(mNegativeStock.isAvailable());

		// Item with null string parameter
		try {
			String nullString = null;
			mNull = new Item(nullString);
			fail("Item was created with null string");
		} catch (IllegalArgumentException e) {
			assertNull(mNull);
		}

		// Item with empty string parameter
		try {
			String emptyString = "";
			mNull = new Item(emptyString);
			fail("Item was created with empty string");
		} catch (IllegalArgumentException e) {
			assertNull(mNull);
		}

		// Invalid movie: No stock in raw string
		try {
			mNoStock = new Item("Title");
			fail("movieNoStock was created.");
		} catch (IllegalArgumentException e) {
			assertNull(mNoStock);
		}

		// Invalid movie: no stock in raw string, but a number at title start
		try {
			mInvalidStock = new Item("10Angry Men");
			fail("movieInvalidStock was created.");
		} catch (IllegalArgumentException e) {
			assertNull(mInvalidStock);
		}

		// Invalid movie: no stock in raw string, but a number inside title
		try {
			mNoStockNumberInTitle = new Item("The Godfather: Part 2");
			fail("movieNoStockWithNumberInsideTitle was created.");
		} catch (IllegalArgumentException e) {
			assertNull(mNoStockNumberInTitle);
		}
	}

	/**
	 * Test method for
	 * {@link Item#getDisplayName()}.
	 */
	@Test
	public void testGetDisplayName() {
		// Available movie
		assertEquals("Item Name", mValid.getDisplayName());
		// Unavailable movie
		assertEquals("Name (currently unavailable)", mZeroStock.getDisplayName());
	}

	/**
	 * Note: in lexical order, "a" is less than "b". Test method for
	 * {@link Item#compareToByName(Item)}
	 * .
	 */
	@Test
	public void testCompareToByName() {
		Item movieAm = new Item("1 American Sniper");
		Item movieAm2 = new Item("2 American Sniper");
		Item movieAb = new Item("1 About Time");
		Item movieG = new Item("1 Gone Girl");
		Item movieTheH = new Item("1  The Hunger Games: Mockingjay, Part 1");
		Item movieS = new Item("2 Selma");

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
	 * {@link Item#removeOneCopyFromInventory()}
	 * .
	 */
	@Test
	public void testRemoveOneCopyFromInventory() {
		// Stores the original movieZeroStock movie
		Item movieTemp = mZeroStock;

		// Check that IllegalStateException is thrown and movie is unchanged.
		try {
			mZeroStock.removeOneCopyFromInventory();
			fail("IllegalStateException should have been thrown");
		} catch (IllegalStateException e) {
			assertEquals(movieTemp, mZeroStock);
		}
	}
}
