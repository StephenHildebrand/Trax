/**
 * 
 */
package net.shiild.trax.tracker;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import net.shiild.trax.client.Client;

/**
 * Test class for TrackingSystem
 * 
 * @author StephenHildebrand
 */
public class TrackingSystemTest {

    private TrackingSystem inventory;
    private Client c1, c2, c3;


    /**
     * Set up the inventory for testing.
     */
    @Before
    public void setUp() throws Exception {
        inventory = new TrackingSystem("things-short.txt");
        c1 = new Client("id1", "pw1", 1);
        c2 = new Client("id2", "pw2", 3);
        c3 = new Client("id3", "pw3", 6);


    }

    /**
     * Test method for TrackingSystem constructor .
     */
    @Test
    public void testDVDRentalSystem() {
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

    @Test
    public void showInventory() throws Exception {
        assertEquals(
                "Frozen\nGravity\nHow to Train Your Dragon 2\nSpectre (currently unavailable)\nWarcraft (currently unavailable)\n",
                inventory.showInventory());
    }

    @Test
    public void setClient() throws Exception {
    }

    @Test
    public void addToClientQueue() throws Exception {
    }

    @Test
    public void reserveMoveAheadOne() throws Exception {
    }

    @Test
    public void removeSelectedFromReserves() throws Exception {
    }

    @Test
    public void traverseReserveQueue() throws Exception {
    }

    @Test
    public void traverseAtHomeQueue() throws Exception {
        inventory.setClient(c1);
        inventory.addToClientQueue(0);
        assertTrue(c1.traverseAtHomeQueue().contains("Frozen"));
        assertEquals(inventory.traverseAtHomeQueue(), c1.traverseAtHomeQueue());
    }

    @Test
    public void returnThingToInventory() throws Exception {
    }
}
