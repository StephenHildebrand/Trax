/**
 * 
 */
package net.shiild.trax.client;

import static org.junit.Assert.*;

import net.shiild.trax.client.Client;
import net.shiild.trax.inventory.Thing;
import net.shiild.trax.util.MultiPurposeList;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the Client.
 * 
 * @author StephenHildebrand
 */
public class ClientTest {
	/** Three clients for testing */
	Client c1, c2, c3;
	/** Two reserve queues for testing */
	MultiPurposeList<Thing> reserve1, reserve2;
	/** Two at home queues for testing */
	MultiPurposeList<Thing> atHome1, atHome2;
	/** Five Thing objects for testing */
	Thing thing1, thing2, thing3, thing4, thing5;

	/**
	 * Set up a Client for testing.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Initialize each Client with valid arguments
		c1 = new Client("id1", "pw1", 5);
		c2 = new Client("id2", "pw2", 3);
		c3 = new Client("id3", "pw3", 1);
		// Initialize each thing with valid arguments
		thing1 = new Thing("3 Frozen");
		thing2 = new Thing("2  Gravity");
		thing3 = new Thing("0 Spectre");
		thing4 = new Thing("0 Warcraft");
		thing5 = new Thing("3 How to Train Your Dragon 2");
		// Put things1-3 in c1's reserve queue
		c1.reserve(thing1);
		c1.reserve(thing2);
		c1.reserve(thing3);

	}

	/**
	 * Test method for
	 * {@link Client#Client(java.lang.String, java.lang.String, int)}
	 * .
	 */
	@Test
	public void testClient() {
		// Test1: c1 is a valid Client
		assertEquals("id1", c1.getId());
		assertTrue(c1.verifyPassword("pw1"));
		// Verify current c1 reserve queue
		assertEquals("Spectre\n", c1.traverseReserveQueue());
		// Verify c1 atHomeQueue contents
		assertEquals("Frozen\nGravity\n", c1.traverseAtHomeQueue());

		// Test2: null id
		Client cNullId = null;
		try {
			cNullId = new Client(null, "pw", 1);
			fail("A Client with a null id was created");
		} catch (IllegalArgumentException e) {
			assertNull(cNullId);
		}

		// Test3: empty password
		Client cEmptyPassword = null;
		try {
			cEmptyPassword = new Client("id", "", 1);
			fail("A Client with an empty password was created");
		} catch (IllegalArgumentException e) {
			assertNull(cEmptyPassword);
		}
		// // Test4: negative maxAtHome
		// Client negMaxAtHome = new Client("id", "pw", -3);
		// assertEquals(0, negMaxAtHome.maxAtHome);
	}

	/**
	 * Test method for
	 * {@link Client#compareToByName(Client)}
	 * .
	 */
	@Test
	public void testCompareToByName() {
		assertTrue(c1.compareToByName(c2) < 0);
		assertTrue(c2.compareToByName(c1) > 0);
		assertTrue(c1.compareToByName(c1) == 0);
	}

	/**
	 * Test method for {@link Client#login()}.
	 */
	@Test
	public void testLogin() {
		// c2 reserves last copy of thing2 (c1 already reserved copy 1)
		c2.reserve(thing2);
		// Check that thing2 is no long available
		assertFalse(thing2.isAvailable());
		// c3 reserves thing2 -- currently unavailable
		c3.reserve(thing2);
		assertEquals("Gravity\n", c3.traverseReserveQueue());
		assertNull(c3.traverseAtHomeQueue());
		// c1 returns a copy of thing2 (their atHomeQueue position 1)
		c1.returnDVD(1);
		// c3 logs in, and a copy of thing2 is automatically reserved for them
		c3.login();
		assertEquals("Gravity\n", c3.traverseAtHomeQueue());
		assertNull(c3.traverseReserveQueue());
	}

//	/**
//	 * Test method for
//	 * {@link Client#closeAccount()}.
//	 */
//	@Test
//	public void testCloseAccount() {
//		c1.closeAccount();
//		assertEquals("", c1.traverseAtHomeQueue());
//	}

	/**
	 * Test method for
	 * {@link Client#returnDVD(int)}.
	 */
	@Test
	public void testReturnDVD() {
		// Test1: Return c1's atHomeQueue position 1 thing
		c1.returnDVD(0);
		assertEquals("Gravity\n", c1.traverseAtHomeQueue());
		// Remove the other thing and check the atHomeQueue
		c1.returnDVD(0);
		assertNull(c1.traverseAtHomeQueue());
		// Try to remove another thing -- nothing should happen
		c1.returnDVD(0);
		assertNull(c1.traverseAtHomeQueue());
	}

	/**
	 * Test method for
	 * {@link Client#moveAheadOneInReserves(int)}
	 * .
	 */
	@Test
	public void testMoveAheadOneInReserves() {
		// Test1: Move ahead thing1 at position 0 -- shouldn't change the list
		c1.moveAheadOneInReserves(0);
		// Check that reserveQueue is unchanged
		assertEquals("Spectre\n", c1.traverseReserveQueue());

		// Test2: Move ahead thing at invalid position -- throws exception
		try {
			c1.moveAheadOneInReserves(5);
			fail("Invalid position");
		} catch (IllegalArgumentException e) {
			assertEquals("Spectre\n", c1.traverseReserveQueue());
			// Check that reserveQueue is unchanged
		}

		// Test3: Add thing4, which goes to reserveQueue since it is
		// unavailable. Then move ahead thing4 (position 1).
		c1.reserve(thing4);
		c1.moveAheadOneInReserves(1);
		// Check that reserveQueue is correctly changed
		assertEquals("Warcraft\nSpectre\n", c1.traverseReserveQueue());
	}

	/**
	 * Test method for
	 * {@link Client#unReserve(int)}.
	 */
	@Test
	public void testUnReserve() {
		try {
			c1.unReserve(5);
			fail("Position out of bounds.");
		} catch (IllegalArgumentException e) {
			// Exception thrown if attempt to unreserve a thing outside
			// reserveQueue bounds
		}
	}

	/**
	 * Test method for
	 * {@link Client#reserve(Thing)}
	 * .
	 */
	@Test
	public void testReserve() {
		// Test1: c1 reserve thing4
		c1.reserve(thing4);
		// Check that reserveQueue is correctly updated.
		assertEquals("Spectre\nWarcraft\n", c1.traverseReserveQueue());
		// Test2: Reserve a null thing
		try {
			c1.reserve(null);
			fail("Null thing cannot be added.");
		} catch (IllegalArgumentException e) {
			// Exception should be thrown if a null thing is reserved
		}
	}
}
