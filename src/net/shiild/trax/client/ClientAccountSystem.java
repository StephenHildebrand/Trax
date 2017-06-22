/**
 *
 */
package net.shiild.trax.client;

import net.shiild.trax.tracker.TrackerManager;

/**
 * Represents the client part of the overall system and implements
 * ClientAccountManager, allowing for client management and use login.
 *
 * @author StephenHildebrand
 */
public class ClientAccountSystem implements ClientAccountManager {
    /** True iff the administrator is logged into the system */
    private boolean adminLoggedIn;
    /** True iff a client is logged into the system */
    private boolean clientLoggedIn;
    /** String constant representing the administrator's id and password */
    private static final String ADMIN = "admin";
    /** Database of clients in the system */
    private ClientDB clientList;
    /** The rental inventory associated with the system */
    private TrackerManager inventorySystem;

    /**
     * Constructor for the ClientAccountSystem.
     *
     * @param inventorySystem inventory of the overall system in the context of a single
     *                        client
     */
    public ClientAccountSystem(TrackerManager inventorySystem) {
        if (inventorySystem != null) {
            this.inventorySystem = inventorySystem;
            this.clientList = new ClientDB();
            adminLoggedIn = false;
            clientLoggedIn = false;
        }
    }

    @Override
    public void login(String id, String password) {
        // A client is already logged in
        if (adminLoggedIn || clientLoggedIn) {
            throw new IllegalStateException("The account doesn't exist.");
        }
        if (id.equals(ADMIN) && password.equals(ADMIN)) { // Login admin
            adminLoggedIn = true;
        } else { // Login client
            Client client = clientList.verifyClient(id, password);
            clientLoggedIn = true;
            client.login();
            inventorySystem.setClient(client);
        }
    }

    @Override
    public void logout() {
        if (adminLoggedIn) { // Log out admin
            adminLoggedIn = false;
        } else if (clientLoggedIn) { // Log out client
            clientLoggedIn = false;
        }
    }

    @Override
    public boolean isAdminLoggedIn() {
        return adminLoggedIn;
    }

    @Override
    public boolean isClientLoggedIn() {
        return clientLoggedIn;
    }

    @Override
    public void addNewClient(String id, String password, int num) {
        if (adminLoggedIn && clientList != null) {
            clientList.addNewClient(id, password, num);
        }
    }

    @Override
    public void cancelAccount(String id) {
        if (adminLoggedIn && clientList != null) {
            clientList.cancelAccount(id);
        }
    }

    @Override
    public String listAccounts() {
        if (clientList != null) {
            return clientList.listAccounts();
        }
        return null;
    }
}
