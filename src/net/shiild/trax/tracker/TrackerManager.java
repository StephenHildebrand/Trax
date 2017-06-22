package net.shiild.trax.tracker;

import net.shiild.trax.client.Client;

/**
 * Interface for a rental system where the things for rent are stored in an
 * inventory and where there are different Clients. Things can be reserved,
 * checked out for home, and returned to the inventory. Things in the the
 * inventory, reserves, and at home can be located by position.
 *
 * @author StephenHildebrand
 */
public interface TrackerManager {

    /**
     * Traverse all things in the inventory.
     *
     * @return the string representing the things in the inventory
     */
    String showInventory();

    /**
     * Set the Client for the current context to a given value.
     *
     * @param c the new current Client
     */
    void setClient(Client c);

    /**
     * Reserve the selected thing for the reserve queue.
     *
     * @param position position of the selected thing in the inventory
     * @throws IllegalStateException    if no Client is logged in
     * @throws IllegalArgumentException if position is out of bounds
     */
    void addToClientQueue(int position);

    /**
     * Move the thing in the given position up 1 in the reserve queue.
     *
     * @param position current position of thing to move up one
     * @throws IllegalStateException if no Client is logged in
     */
    void reserveMoveAheadOne(int position);

    /**
     * Remove the thing in the given position from the reserve queue.
     *
     * @param position position of the thing in the queue
     * @throws IllegalStateException    if no Client is logged in
     * @throws IllegalArgumentException if position is out of bounds
     */
    void removeSelectedFromReserves(int position);

    /**
     * Traverse all things in the reserve queue.
     *
     * @return string representation of things in the queue
     * @throws IllegalStateException if no Client is logged in
     */
    String traverseReserveQueue();

    /**
     * Traverse all things in the reserve queue.
     *
     * @return string representation of things at home
     * @throws IllegalStateException if no Client is logged in
     */
    String traverseAtHomeQueue();

    /**
     * Return the selected thing to the inventory.
     *
     * @param position location in the list of things at home of the thing to return
     * @throws IllegalStateException    if no Client is logged in
     * @throws IllegalArgumentException if position is out of bounds
     */
    void returnThingToInventory(int position);

}
