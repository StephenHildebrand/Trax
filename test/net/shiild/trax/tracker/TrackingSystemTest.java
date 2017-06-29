/**
 * 
 */
package net.shiild.trax.tracker;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.shiild.trax.client.Client;
import net.shiild.trax.tracker.TrackingSystem;

/**
 * Test class for TrackingSystem
 * 
 * @author StephenHildebrand
 */
public class TrackingSystemTest {
	TrackingSystem inventory;

	/**
	 * Set up the inventory for testing.
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		inventory = new TrackingSystem("things-short.txt");
	}

	/**
	 * Test method for
	 * {@link #TrackingSystem(java.lang.String)}
	 * .
	 */
	@Test
	public void testDVDRentalSystem() {
		Client c1 = new Client("id1", "pw1", 2);
		inventory.setClient(c1);
		inventory.addToClientQueue(0);
		assertTrue(c1.traverseAtHomeQueue().contains("Frozen"));
		assertEquals(inventory.traverseAtHomeQueue(), c1.traverseAtHomeQueue());
		inventory.addToClientQueue(1);
		inventory.addToClientQueue(2);
		inventory.addToClientQueue(3);
		assertEquals(inventory.traverseReserveQueue(), c1.traverseReserveQueue());
		inventory.reserveMoveAheadOne(1);
		assertEquals("Spectre\n", inventory.traverseReserveQueue());
		inventory.removeSelectedFromReserves(0);
		assertNull(inventory.traverseReserveQueue());
		inventory.returnThingToInventory(0);
		assertFalse(inventory.traverseAtHomeQueue().contains("Frozen"));

	}

	/**
	 * Test method for
	 * {@link TrackingSystem#showInventory()}
	 * .
	 */
	@Test
	public void testShowInventory() {
		assertEquals(
				"Frozen\nGravity\nHow to Train Your Dragon 2\nSpectre (currently unavailable)\nWarcraft (currently unavailable)\n",
				inventory.showInventory());
	}
}
