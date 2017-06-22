/**
 *
 */
package net.shiild.trax.client;

/**
 * A database that maintains a list of Clients and provides the list
 * operations required to support the single-client operations described in
 * ClientAccountManager.
 * <p>
 * The client list is implemented as an array-based list, using 3 variables:
 * (1) MAX_SIZE: The maximum capacity of the list (max number of clients).
 * (2) list: An array of clients (the type is Client[]).
 * (3) size: The number of array elements that are actually clients in the database.
 *
 * Unless size is 0, the index of the last client in the database is size - 1.
 * When you add a new client to the list, make sure to insert it in the proper
 * position relative to the other clients already in the list.
 *
 * @author StephenHildebrand
 */
public class ClientDB {
    /** The max number of clients that can be added to the system */
    private static final int MAX_SIZE = 20;
    /** The current number of clients currently in the system */
    private int size;
    /** The database of clients currently in the system */
    private Client[] list;

    /**
     * Constructor for the client database which maintains a list of
     * clients.
     */
    public ClientDB() {
        // Instantiate the list with the max allowed number of clients
        list = new Client[MAX_SIZE];
        this.size = 0;
    }

    /**
     * Returns the client in the list whose id matches the first parameter and
     * password matches the second [UC2, S1], [UC2, E1].
     *
     * @param id       the entered id to compare to the stored id
     * @param password the entered password to compare to the stored password
     * @return the client in the list with a matching id and password
     * @throws IllegalArgumentException if the id or password are null, if the password is incorrect,
     *                                  or if the client is not in the database.
     */
    public Client verifyClient(String id, String password) throws IllegalArgumentException {
        // Check for null id or password
        if (id == null || password == null) {
            throw new IllegalArgumentException("The account doesn't exist.");
        }
        // Find the account's location in the client list
        int accountIndex = findMatchingAccount(id);
        if (accountIndex < 0) { // Client not in the list
            throw new IllegalArgumentException("No matching client account found.");
        }
        Client client = list[accountIndex]; // Client at that psn

        if (client.verifyPassword(password)) { // Correct password
            return client;
        } else { // Incorrect password
            throw new IllegalArgumentException("The account doesn't exist.");
        }
    }

    /**
     * Used only for testing. Returns a string of ids of clients in the list
     * in the order the clients appear in the list. Successive ids are
     * separated by newlines.
     *
     * @return an ordered string of the ids of clients in the list
     */
    public String listAccounts() {
        String clientString = "";
        for (int i = 0; i < size; i++) {
            clientString = clientString + list[i].getId() + "\n";
        }
        return clientString;
    }

    /**
     * Adds a new client to the list, where the id is the first parameter,
     * password is the second, and maximum allowed movies at home is the third
     * [UC3].
     * <p>
     * Throws an�IllegalStateException�if the database is full [UC3, E1]. Throws
     * an IllegalArgumentException�if there is whitespace in the id or password,
     * if the id or password are empty, or if there is already a client in the
     * database with the same id [UC3, E2-E3].
     *
     * @param id        string of the client to be added
     * @param password  string of the client to be added
     * @param maxAtHome max number of movies the client is allowed at home
     * @throws IllegalStateException    when the database is full
     * @throws IllegalArgumentException when the id or password contain whitespace or are empty, or
     *                                  if a client with the same id is already in the database
     */
    public void addNewClient(String id, String password, int maxAtHome)
            throws IllegalStateException, IllegalArgumentException {
        // Database full
        if (size == MAX_SIZE) {
            throw new IllegalStateException("There is no room for additional clients.");
        }
        // Invalid id or password
        if (id == null || password == null || id.isEmpty() || password.isEmpty() || id.contains(" ")
                || password.contains(" ")) {
            throw new IllegalArgumentException("Username and password must have non-whitespace characters.");
        }
        // Not a new client
        if (!isNewClient(id)) {
            throw new IllegalArgumentException("Client already has an account.");
        }
        // Add new client and increment the list size
        this.insert(new Client(id, password, maxAtHome));
    }

    /**
     * Removes the client with the given id from the list and returns any
     * movies that client has at home to the inventory [UC4, S1]. Throws
     * an�IllegalArgumentException�if the account does not exist [UC4, E1].
     *
     * @param id the id of the client to be removed from the list
     * @throws IllegalArgumentException if the account doesn't exist
     */
    public void cancelAccount(String id) throws IllegalArgumentException {
        int accountIndex = findMatchingAccount(id);
        if (accountIndex < 0) {
            throw new IllegalArgumentException();
        }
        list[accountIndex].closeAccount();

        for (int i = accountIndex; i < size - 1; i++) {
            list[i] = list[i + 1];
        }
        size--; // Decrement the size by 1 since an item was removed
    }

    /**
     * Private method used to determine a client is new
     *
     * @param id client's id
     * @return true if client is new, false if it is not
     */
    private boolean isNewClient(String id) {
        for (int j = 0; j < size; j++) {
            String thisId = list[j].getId();
            if (thisId.equals(id)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Private method used to insert a new client in order.
     *
     * @param newClient client to insert into the list.
     */
    private void insert(Client newClient) {
        // Start from the right end, shifting the items right as you look for
        //    the place to insert the item.
        int psn = size - 1;  // Index traveling from right to left on the list
        while (psn >= 0 && list[psn].compareToByName(newClient) > 0) {
            list[psn + 1] = list[psn];
            psn--;
        }
        // At this point, psn is -1 OR list[psn] <= newItem. So the newItem
        //   goes immediately to the right of psn. 
        list[psn + 1] = newClient;
        // Don't forget to change the list size. 
        size++;
    }

    /**
     * Searches through the Client list for a matching id. If a match is found
     * then the location of that Client in the array is returned. If the id is
     * not found, -1 is returned.
     *
     * @param id client's id to search for.
     * @return integer specifying the location of the target client account
     */
    private int findMatchingAccount(String id) {
        for (int i = 0; i < size; i++) {
            String thisId = list[i].getId();
            if (thisId.equals(id)) {
                return i;
            }
        }
        return -1;
    }
}
