/**
 * 
 */
package net.shiild.trax.client;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.shiild.trax.client.Client;
import net.shiild.trax.client.ClientDB;

/**
 * Test class for ClientDB class.
 * 
 * @author StephenHildebrand
 */
public class ClientDBTest {
	/** A database maintaining a list of clients for testing */
	ClientDB cDB;
	/** Five clients for testing */
	Client c1, c2, c3, c4, c5;

	/**
	 * Set up the Client database and Client objects for use in testing.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		cDB = new ClientDB();
		// Initialize each Client with valid arguments
		c1 = new Client("a", "pw1", 5);
		c2 = new Client("b", "pw2", 3);
		c3 = new Client("c", "pw3", 4);
		c4 = new Client("d", "pw4", 2);
		c5 = new Client("e", "pw5", 1);

		// Add three clients to the client database
		cDB.addNewClient("a", "pw1", 5); // c1
		cDB.addNewClient("b", "pw2", 3); // c2
		cDB.addNewClient("c", "pw3", 4); // c3

	}

	/**
	 * Test method for
	 * {@link ClientDB#ClientDB()}.
	 */
	@Test
	public void testClientDB() {
		assertEquals("a\nb\nc\n", cDB.listAccounts());
		cDB.addNewClient("d", "pw4", 2);
		assertEquals("a\nb\nc\nd\n", cDB.listAccounts());
		cDB.cancelAccount("b");
		assertEquals("a\nc\nd\n", cDB.listAccounts());
	}

	/**
	 * Test method for
	 * {@link ClientDB#verifyClient(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testVerifyClient() {
		// Test1: null password
		try {
			cDB.verifyClient("a", null);
			fail("Invalid client password verfied.");
		} catch (IllegalArgumentException e) {
			assertEquals("a\nb\nc\n", cDB.listAccounts());
		}
		// Test2: Verify  client c4 that is not in the list
		try {
			cDB.verifyClient("d", "pw4");
			fail("Invalid client password verfied.");
		} catch (IllegalArgumentException e) {
			assertEquals("a\nb\nc\n", cDB.listAccounts());
		}
		// Test5: Incorrect password
		try {
			cDB.verifyClient("a", "pw5");
			fail("Invalid client password verfied.");
		} catch (IllegalArgumentException e) {
			assertEquals("a\nb\nc\n", cDB.listAccounts());
		}
		// Test4: Verify client c2 that is in the list
		assertEquals(c2.getId(), cDB.verifyClient("b", "pw2").getId());

	}

	/**
	 * Test method for
	 * {@link ClientDB#addNewClient(java.lang.String, java.lang.String, int)}
	 * .
	 */
	@Test
	public void testAddNewClient() {
		// Test1: empty id (invalid)
		try {
			cDB.addNewClient("", "password", 2);
			fail("Invalid client was added.");
		} catch (IllegalArgumentException e) {
			assertEquals("a\nb\nc\n", cDB.listAccounts());
		}
		// Test2: whitespace in id (invalid)
		try {
			cDB.addNewClient("i d1", "password", 2);
			fail("Invalid client was added.");
		} catch (IllegalArgumentException e) {
			assertEquals("a\nb\nc\n", cDB.listAccounts());
		}
		// Test3: Client already in database (invalid)
		try {
			cDB.addNewClient("a", "password", 5);
			fail("Invalid client was added.");
		} catch (IllegalArgumentException e) {
			assertEquals("a\nb\nc\n", cDB.listAccounts());
		}
	}
}
