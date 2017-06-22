/**
 * 
 */
package net.shiild.trax.client;

import org.junit.Before;

import net.shiild.trax.client.ClientAccountSystem;
import net.shiild.trax.tracker.TrackerManager;

/**
 * Test class for ClientAccountSystem
 * 
 * @author StephenHildebrand
 */
public class ClientAccountSystemTest {
	ClientAccountSystem system;
	TrackerManager inventory;

	/**
	 * Set up objects for testing.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		system = new ClientAccountSystem(inventory);
	}

//	/**
//	 * Test method for
//	 * {@link ClientAccountSystem#ClientAccountSystem(RentalManager)}
//	 * .
//	 */
//	@Test
//	public void testMovieClientAccountSystem() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for
//	 * {@link ClientAccountSystem#login(java.lang.String, java.lang.String)}
//	 * .
//	 */
//	@Test
//	public void testLogin() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for
//	 * {@link ClientAccountSystem#logout()}
//	 * .
//	 */
//	@Test
//	public void testLogout() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for
//	 * {@link ClientAccountSystem#isAdminLoggedIn()}
//	 * .
//	 */
//	@Test
//	public void testIsAdminLoggedIn() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for
//	 * {@link ClientAccountSystem#isClientLoggedIn()}
//	 * .
//	 */
//	@Test
//	public void testIsClientLoggedIn() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for
//	 * {@link ClientAccountSystem#addNewClient(java.lang.String, java.lang.String, int)}
//	 * .
//	 */
//	@Test
//	public void testAddNewClient() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for
//	 * {@link ClientAccountSystem#cancelAccount(java.lang.String)}
//	 * .
//	 */
//	@Test
//	public void testCancelAccount() {
//		fail("Not yet implemented");
//	}
//
//	/**
//	 * Test method for
//	 * {@link ClientAccountSystem#listAccounts()}
//	 * .
//	 */
//	@Test
//	public void testListAccounts() {
//		fail("Not yet implemented");
//	}

}
