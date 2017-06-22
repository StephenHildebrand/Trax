package net.shiild.trax.client;

/**
 * Describes behaviors of a client management system that permits client login.
 * The management system has an administrator that may log in via the same interface.
 *
 * @author StephenHildebrand
 */
public interface ClientAccountManager {

    /**
     * Logs a client into the system.
     *
     * @param username id/username of the client
     * @param password client's password
     * @throws IllegalStateException    if a client or the administrator is already logged in
     * @throws IllegalArgumentException if the client account does not exist
     */
    void login(String username, String password);

    /**
     * Logs the current client or administrator out of the system.
     */
    void logout();

    /**
     * Is an administrator logged into the system?
     *
     * @return true if yes, false if no
     */
    boolean isAdminLoggedIn();

    /**
     * Is a client logged into the system?
     *
     * @return true if yes, false if no
     */
    boolean isClientLoggedIn();

    /**
     * Add a new client to the client database. The administrator must be
     * logged in.
     *
     * @param id       id/email for new client
     * @param password new client's password
     * @param num      number associated with this client
     * @throws IllegalStateException    if the database is full or the administrator is not logged in
     * @throws IllegalArgumentException if client with given id is already in the database
     */
    void addNewClient(String id, String password, int num);

    /**
     * Cancel a client account.
     *
     * @param id id/username of the client to cancel
     * @throws IllegalStateException    if the administrator is not logged in
     * @throws IllegalArgumentException if no matching account is found
     */
    void cancelAccount(String id);

    /**
     * List all client accounts.
     *
     * @return string of client usernames separated by newlines
     */
    String listAccounts();

}
