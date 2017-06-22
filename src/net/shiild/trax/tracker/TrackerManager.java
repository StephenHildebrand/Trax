package net.shiild.trax.tracker;

import net.shiild.trax.client.Client;

/**
 * Interface for a rental system where the items for rent are stored in an
 * inventory and where there are different Clients. Items can be reserved,
 * checked out for home, and returned to the inventory. Items in the the
 * inventory, reserves, and at home can be located by position.
 *
 * @author StephenHildebrand
 */
public interface TrackerManager {

    /**
     * Traverse all items in the inventory.
     *
     * @return the string representing the items in the inventory
     */
    String showInventory();

    /**
     * Set the Client for the current context to a given value.
     *
     * @param c the new current Client
     */
    void setClient(Client c);

    /**
     * Reserve the selected item for the reserve queue.
     *
     * @param position position of the selected item in the inventory
     * @throws IllegalStateException    if no Client is logged in
     * @throws IllegalArgumentException if position is out of bounds
     */
    void addToClientQueue(int position);

    /**
     * Move the item in the given position up 1 in the reserve queue.
     *
     * @param position current position of item to move up one
     * @throws IllegalStateException if no Client is logged in
     */
    void reserveMoveAheadOne(int position);

    /**
     * Remove the item in the given position from the reserve queue.
     *
     * @param position position of the item in the queue
     * @throws IllegalStateException    if no Client is logged in
     * @throws IllegalArgumentException if position is out of bounds
     */
    void removeSelectedFromReserves(int position);

    /**
     * Traverse all items in the reserve queue.
     *
     * @return string representation of items in the queue
     * @throws IllegalStateException if no Client is logged in
     */
    String traverseReserveQueue();

    /**
     * Traverse all items in the reserve queue.
     *
     * @return string representation of items at home
     * @throws IllegalStateException if no Client is logged in
     */
    String traverseAtHomeQueue();

    /**
     * Return the selected item to the inventory.
     *
     * @param position location in the list of items at home of the item to return
     * @throws IllegalStateException    if no Client is logged in
     * @throws IllegalArgumentException if position is out of bounds
     */
    void returnItemToInventory(int position);

}
