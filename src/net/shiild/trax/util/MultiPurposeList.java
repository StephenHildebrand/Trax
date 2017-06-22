/**
 *
 */
package net.shiild.trax.util;

/**
 * A generic class that supports the underlying list operations for the movie
 * inventory, reserve queues, and at-home queues. T is determined when the class
 * is instantiated as a Item in the case of this project). Instantiation of the
 * class occurs in three places:
 * (1) ItemDB.movies
 * (2) Client.atHomeQueue
 * (3) Client.reserveQueue.
 *
 * Most of the MultiPurposeList methods are strictly position based, where the
 * position of the first element is 0, the second is 1, and so on. In the metheod
 * descriptions below, the int parameter is the given position.
 * None of the methods throw exceptions.
 *
 * MultiPurposeList uses a nested class named Node for its implementation. It
 * has two Node type data members: head, which points to the front of the list,
 * and iterator, which can traverse through the list elements. You can think of
 * an iterator as a cursor to the list, pointing to the "next" element to be
 * visited.
 * </p>
 *
 * @param <T> element list to be iterated through
 * @author StephenHildebrand
 */
public class MultiPurposeList<T> {
    /** Node that points to the front of the list */
    private Node head;
    /** Node used as a cursor to traverse through the list elements */
    private Node iterator;

    /**
     * Simple iterator definition. Node uses three iterator methods located in
     * the outer class, including resetIterator(), next(), and hasNext().
     *
     * @author StephenHildebrand
     */
    public class Node {
        /** The data stored in the node at the current pointer position */
        public T data;
        /** The next node in the list after the current position */
        private Node link;

        /**
         * Constructor for the Node object.
         *
         * @param initialData the initial data of this new node.
         * @param initialLink a reference to the node after this new node -- this
         *                    reference may be null, indicating there is no node after
         *                    this one.
         */
        public Node(T initialData, Node initialLink) {
            data = initialData;
            link = initialLink;
        }
    }

    /**
     * Constructs an empty list.
     */
    public MultiPurposeList() {
        // No tasks performed here to create empty list.
    }

    /**
     * Sets iterator to point to the first element in the list.
     */
    public void resetIterator() {
        iterator = head;
    }

    /**
     * True whenever iterator is pointing to a list element.
     *
     * @return true when the iterator is pointing to a list element, return
     * false when it does not
     */
    public boolean hasNext() {
        return iterator != null;
    }

    /**
     * Returns the element iterator is pointing to, then moves iterator to point
     * to the next element in the list. If iterator is null, null is returned.
     *
     * @return element that the iterator is pointing to prior to being moved, or
     * returns null if the iterator is null
     */
    public T next() {
        if (iterator == null) {
            return null;
        }
        T previous = iterator.data;
        iterator = iterator.link;
        return previous;
    }

    /**
     * Adds an element at the given position. If the position is negative, the
     * element is added to the front of the list. If the position is greater
     * than the length of the list, the element is added to the end of the list.
     *
     * @param psn the position at which to add the element
     * @param e   the element to be added
     */
    public void addItem(int psn, T e) {
        if (e != null) {
            if (psn <= 0) { // Add element to front of list
                head = new Node(e, head);
            } else if (psn > this.size()) { // Add element to end of list
                this.addToRear(e);
            } else if (e != null && psn > 0) { // Add element at this psn
                Node pntr = head;
                while (pntr != null && psn > 1) {
                    pntr = pntr.link;
                    psn--;
                }
                // If pointer hasn't run off the end of the list, then position
                // has decremented to 1 and pointer belongs at the next node
                // (where distance would be equal 0)
                if (pntr != null) {
                    // Create a new node just after cursor with the element and
                    // a
                    // link to the node previously at that location
                    pntr.link = new Node(e, pntr.link);
                }
            }
        }
    }

    /**
     * Returns true if the list contains no elements, false if it does contain elements.
     *
     * @return true if the list has no elements, false if it has elements
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Returns the element at the given target position, or�null�if the target
     * position is out of range.
     *
     * @param psn the position of the element to be returned
     * @return the element at the given position, null if out of range
     */
    public T lookAtItemN(int psn) {
        // Check that the list the list is not empty, and psn is within range
        if (head != null && psn >= 0 && psn <= this.size()) {
            Node pntr = head;
            while (pntr != null && psn > 0) {
                pntr = pntr.link;
                psn--;
            }
            if (pntr != null) { // pntr points to the target item
                return pntr.data;
            }
        }
        return null; // psn was out of range
    }

    /**
     * Adds an element to the rear of the list.
     *
     * @param e to be added to the end of the list
     */
    public void addToRear(T e) {
        if (e != null) { // e has been initialized
            Node cursor = head;
            Node previous = null;
            // Iterate through the list until cursor reaches the list's end
            while (cursor != null) {
                previous = cursor;
                cursor = cursor.link;
            }
            if (cursor == head) { // e is the first element to be added
                head = new Node(e, head);
            } else { // e is not the first element to be added
                previous.link = new Node(e, cursor);
            }
        }
    }

    /**
     * Removes and returns the element in the given position. Returns null�if
     * the position is out of range.
     *
     * @param psn position of the element to be removed and returned
     * @return the element at the position parameter or null if the position is
     * out of range.
     */
    public T remove(int psn) {
        T removedData = null;
        if (head != null && psn >= 0 && psn <= this.size()) {
            Node current = head;
            Node previous = null;
            // Distance remaining between cursor and target psn
            int distanceToPsn = psn;

            // Decrement distance until current reaches the end or the location
            // just before the target element while storing the previous node
            while (current != null && distanceToPsn > 0) {
                previous = current;
                current = current.link;
                distanceToPsn--;
            }
            if (current != null) { // current is at target element
                if (current == head) { // current is at head
                    removedData = current.data;
                    head = head.link;
                } else { // current is not at head
                    removedData = current.data;
                    previous.link = current.link;
                }
            }
        }
        return removedData;
    }

    /**
     * Moves the element at the given position ahead one position in the list.
     * Does nothing if the element is already at the front of the list or if the
     * position is out of range.
     *
     * @param psn of the element to be moved ahead
     */
    public void moveAheadOne(int psn) {

        if (head != null && psn > 0 && psn <= this.size()) {
            Node pntr = head;
            Node onePrevious = null;
            Node twoPrevious = null;

            if (pntr != null && psn > 0) {
                // Iterate through the list until pointer reaches target element
                while (pntr != null && psn > 0) {
                    twoPrevious = onePrevious;
                    onePrevious = pntr;
                    pntr = pntr.link;
                    psn--;
                }
                // Switches the target element with the one before it
                if (pntr != null) { // current is at the target element
                    // When onePrevious points to head, twoPrevious isn't needed
                    if (onePrevious == head) {
                        head = pntr;
                    } else {
                        // point the two previous link to target (current)
                        twoPrevious.link = pntr;
                    }
                    // point one previous link to the element after target
                    onePrevious.link = pntr.link;
                    // point target link to the one previous element
                    pntr.link = onePrevious;
                }
            }
        }
    }

    /**
     * Returns the number of elements in the list.
     *
     * @return the number of list elements
     */
    public int size() {
        int count = 0;
        Node cursor = head;

        // Iterate through the list until cursor reaches the list's end
        while (cursor != null) {
            cursor = cursor.link;
            count++; // Increment count with each iteration
        }
        return count;
    }

}
