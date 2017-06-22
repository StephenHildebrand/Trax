/**
 * 
 */
package net.shiild.trax.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.shiild.trax.inventory.Thing;
import net.shiild.trax.util.MultiPurposeList;

/**
 * Test class for MultiPurposeList.
 * 
 * @author StephenHildebrand
 */
public class MultiPurposeListTest {
	/** Declare nine test Movies */
	Thing movie1, movie2, movie3, movie4, movie5, movie6, movie7, movie8, movieNull;
	/** Declare the movies MultiPurposeList */
	MultiPurposeList<Thing> movieList, nullList;

	/**
	 * Instantiates the mList to be used for testing so that each test method
	 * has a unaltered version for testing.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Instantiate a list for Thing objects
		movieList = new MultiPurposeList<Thing>();

		// Instantiate and initialize each movie with unique data
		movie1 = new Thing("3 Frozen");
		movie2 = new Thing("2  Gravity");
		movie3 = new Thing("0 Spectre");
		movie4 = new Thing("0 Warcraft");
		movie5 = new Thing("3 How to Train Your Dragon 2");
		movie6 = new Thing("2 Selma");
		movie7 = new Thing("2 Big Hero 6");
		movie8 = new Thing("2 Maleficent");

		// Add the first five movies to movieList
		movieList.addToRear(movie1);
		movieList.addToRear(movie2);
		movieList.addToRear(movie3);
		movieList.addToRear(movie4);
		movieList.addToRear(movie5);
	}

	/**
	 * Test method for
	 * {@link MultiPurposeList#MultiPurposeList()}
	 * .
	 */
	@Test
	public void testMultiPurposeList() {
		assertFalse(movieList.isEmpty()); // is movieList empty?
		assertEquals(movie1.getName(), movieList.lookAtItemN(0).getName());
		assertEquals(movie5.getName(), movieList.lookAtItemN(4).getName());
	}

	/**
	 * Test method for both
	 * {@link MultiPurposeList#hasNext()} and
	 * {@link MultiPurposeList#next()}.
	 */
	@Test
	public void testHasNextNext() {
		movieList.resetIterator(); // Iterator at position 0
		assertTrue(movieList.hasNext());
		movieList.next(); // Position 1
		movieList.next(); // Position 2
		movieList.next(); // Position 3
		movieList.next(); // Position 4
		assertTrue(movieList.hasNext());
		movieList.next(); // Position 5 (null node)
		// movieList does not have a node at position 5
		assertFalse(movieList.hasNext());
	}

	/**
	 * Test method for
	 * {@link MultiPurposeList#addItem(int, java.lang.Object)}
	 * .
	 */
	@Test
	public void testAddItem() {
		assertEquals(5, movieList.size()); // check movieList has 5 elements

		// Test1: Add movie6 to beginning (position 0)
		movieList.addItem(0, movie6);
		// Check that movieList now has 6 elements
		assertEquals(6, movieList.size());
		// Check movie6 was added correctly
		assertEquals(movie6, movieList.lookAtItemN(0));

		// Test2: Add movieNull -- should not change the list
		movieList.addItem(2, movieNull);
		// Check that list size hasn't changed
		assertEquals(6, movieList.size());
		// Check that element at position 2 is unchanged. Position 2 contains
		// movie1 since an item was added to position0 in the above test.
		assertEquals(movie1.getName(), movieList.lookAtItemN(1).getName());

		// Test3: Add movie7 to end (current position 6)
		movieList.addItem(6, movie7);
		// Check movie6 was added correctly
		assertEquals(movie7, movieList.lookAtItemN(6));

		// Test4: Add movie8 to middle (position 3)
		movieList.addItem(3, movie8);
		// Check movie8 was added correctly
		assertEquals(movie8, movieList.lookAtItemN(3));

	}

	/**
	 * Test method for
	 * {@link MultiPurposeList#isEmpty()}.
	 */
	@Test
	public void testIsEmpty() {
		// Instantiate nullList and check it is empty
		nullList = new MultiPurposeList<Thing>();
		assertTrue(nullList.isEmpty());
		// Check that movieList is not empty
		assertFalse(movieList.isEmpty());
	}

	/**
	 * Test method for
	 * {@link MultiPurposeList#lookAtItemN(int)}
	 * .
	 */
	@Test
	public void testLookAtItemN() {
		// Test beginning
		assertEquals(movie1.getName(), movieList.lookAtItemN(0).getName());
		// Test middle
		assertEquals(movie2.getName(), movieList.lookAtItemN(1).getName());
		// Test end
		assertEquals(movie5.getName(), movieList.lookAtItemN(4).getName());
		// Test out of range (too big)
		assertNull(movieList.lookAtItemN(5));
		// Test out of range (negative position)
		assertNull(movieList.lookAtItemN(-1));
	}

	/**
	 * Test method for
	 * {@link MultiPurposeList#addToRear(java.lang.Object)}
	 * .
	 */
	@Test
	public void testAddToRear() {
		assertEquals(5, movieList.size()); // movieList has 5 elements
		movieList.addToRear(movie6); // Add a movie to movieList
		assertEquals(6, movieList.size()); // movieList now has 6 elements
		// Check that movie6 was added correctly
		assertEquals(movie6, movieList.lookAtItemN(5));
		// Test null movie
		movieList.addToRear(movieNull);
		assertNull(movieList.lookAtItemN(6));
	}

	/**
	 * Test method for
	 * {@link MultiPurposeList#remove(int)}.
	 */
	@Test
	public void testRemove() {
		// Test1: Remove movie1 at beginning (position 0)
		movieList.remove(0);
		// Check that movieList now has 4 elements
		assertEquals(4, movieList.size());
		// Check that position 0 now contains movie2
		assertEquals(movie2, movieList.lookAtItemN(0));

		// Test2: Remove movie at invalid position -- shouldn't change the list
		movieList.remove(8);
		// Check that list size hasn't changed
		assertEquals(4, movieList.size());

		// Test3: Remove movie5 at end (current position 3)
		movieList.remove(3);
		// Check movie5 was removed correctly
		assertNull(movieList.lookAtItemN(3));

		// Test4: Remove movie3 from middle (current position 1)
		movieList.remove(1);
		// Check movie3 was removed correctly
		assertEquals(movie4, movieList.lookAtItemN(1));
	}

	/**
	 * Test method for
	 * {@link MultiPurposeList#moveAheadOne(int)}
	 * .
	 */
	@Test
	public void testMoveAheadOne() {
		// Test1: Move ahead movie1 at position 0 -- shouldn't change the list
		movieList.moveAheadOne(0);
		// Check that movieList still has 5 elements
		assertEquals(5, movieList.size());
		// Check that position 0 still contains movie1
		assertEquals(movie1.getName(), movieList.lookAtItemN(0).getName());

		// Test2: Move ahead movie at invalid position -- shouldn't change list
		movieList.moveAheadOne(8);
		// Check that list size hasn't changed
		assertEquals(5, movieList.size());

		// Test3: Move ahead movie5 from current position 4 (end) -- moves
		// movie4 from position 4 to position 5
		movieList.moveAheadOne(4);
		// Check movie5 is now in position 3
		assertEquals(movie5.getName(), movieList.lookAtItemN(3).getName());
		// Check movie4 is now in position 4
		assertEquals(movie4.getName(), movieList.lookAtItemN(4).getName());

		// Test4: Move ahead movie2 from position 1 (middle) -- moves movie1
		// from position 0 to position 1
		movieList.moveAheadOne(1);
		// Check movie2 is now in position 0
		assertEquals(movie2.getName(), movieList.lookAtItemN(0).getName());
		// Check movie1 is now in position 1
		assertEquals(movie1.getName(), movieList.lookAtItemN(1).getName());
	}

	/**
	 * Test method for
	 * {@link MultiPurposeList#size()}.
	 */
	@Test
	public void testSize() {
		// Check that movieList starts with 5 elements
		assertEquals(5, movieList.size());
		// Add movie6 to end of list
		movieList.addToRear(movie6);
		// Check that movieList now has 6 elements
		assertEquals(6, movieList.size());
	}
}
