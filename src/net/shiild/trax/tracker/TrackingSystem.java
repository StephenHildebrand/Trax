/**
 *
 */
package net.shiild.trax.tracker;

import net.shiild.trax.client.Client;
import net.shiild.trax.inventory.Item;
import net.shiild.trax.inventory.ItemDB;

/**
 * Represents the inventory part of the overall system in the context of a
 * single Client and implements the TrackerManager interface.
 *
 * @author StephenHildebrand
 */
public class TrackingSystem implements TrackerManager {
    /** The Client currently logged into the system */
    private Client currentClient;
    /** The database of movies in the system */
    private ItemDB inventory;

    /**
     * Constructor for TrackingSystem, representing the system inventory
     *
     * @param fileName name of the movie inventory file
     */
    public TrackingSystem(String fileName) {
        if (fileName != null && !fileName.equals("")) {
            inventory = new ItemDB(fileName);
        }
    }

    @Override
    public String showInventory() {
        if (inventory != null) {
            return inventory.traverse();
        }
        return null;
    }

    @Override
    public void setClient(Client c) {
        currentClient = c;
    }

    @Override
    public void addToClientQueue(int position) {
        if (currentClient == null) {
            throw new IllegalStateException();
        }
        Item movie = inventory.findItemAt(position);
        currentClient.reserve(movie);
    }

    @Override
    public void reserveMoveAheadOne(int position) {
        if (currentClient == null) {
            throw new IllegalStateException();
        }
        currentClient.moveAheadOneInReserves(position);
    }

    @Override
    public void removeSelectedFromReserves(int position) {
        if (currentClient == null) {
            throw new IllegalStateException();
        }
        currentClient.unReserve(position);
    }

    @Override
    public String traverseReserveQueue() {
        if (currentClient == null) {
            throw new IllegalStateException();
        }
        return currentClient.traverseReserveQueue();
    }

    @Override
    public String traverseAtHomeQueue() {
        if (currentClient == null) {
            throw new IllegalStateException();
        }
        return currentClient.traverseAtHomeQueue();
    }

    /**
     * Return a movie from the client's at home queue to inventory.
     */
    @Override
    public void returnItemToInventory(int position) {
        if (currentClient == null) {
            throw new IllegalStateException();
        }
        currentClient.returnDVD(position);
    }
}
