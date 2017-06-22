package net.shiild.trax.ui;

import net.shiild.trax.client.ClientAccountManager;
import net.shiild.trax.client.ClientAccountSystem;
import net.shiild.trax.tracker.TrackingSystem;
import net.shiild.trax.tracker.TrackerManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The graphical client interface (GUI) for the project. As indicated in the UML diagram,
 * the TraxGUI contains a data member of declared type ClientAccountManager
 * and a data member of declared type TrackerManager.
 * <p>
 * It bases its actions on the TrackerManager and ClientAccountSystem.
 * Its rentals data member is of type TrackerManager, and its accountManager data
 * member is of type ClientAccountSystem.
 *
 * @author StephenHildebrand
 */
public class TraxGUI extends JFrame implements ActionListener {
    /** ID number to be used for object serialization */
    private static final long serialVersionUID = 1L;
    /** Constant width of frame */
    private static final int FRAME_WIDTH = 500;
    /** Constant height of frame */
    private static final int FRAME_HEIGHT = 600;
    /** Constant width of name */
    private static final int ID_AND_PASSWORD_WIDTH = 25;
    /** Constant length of inventory */
    private static final int INVENTORY_LENGTH = 450;
    /** The length of the queue */
    private static final int QUEUE_LENGTH = 300;
    /** Constant length of checked out list */
    private static final int CHECKED_OUT_LENGTH = 120;
    /** Space to vertically separate panel items */
    private static final int VERTICAL_SPACE = 10;
    /** Padding toward the top of panel */
    private static final int UPPER_PADDING = 10;
    /** Padding for the bottom of the panel */
    private static final int BOTTOM_PADDING = 10;
    /** Padding to the left of the panel */
    private static final int LEFT_PADDING = 10;
    /** Padding to the right of the panel */
    private static final int RIGHT_PADDING = 10;

    /** Text for top of Item Inventory browse view */
    private static final String INVENTORY_TITLE = "Item Inventory";
    /** Title of the My Queue window */
    private static final String RESERVE_QUEUE_TITLE = "My Queue";
    /** Title of the checked out window */
    private static final String CHECKED_OUT_TITLE = "Movies At Home";
    /** Title of the main window */
    private static final String WINDOW_TITLE = "DVD Rental System";

    /** Button for adding a new client to the client database */
    private JButton btnAddNewClient = new JButton("Add New Client");
    /** Button for canceling a client account */
    private JButton btnCancelAccount = new JButton("Cancel Account");
    /** Button to browse available movies */
    private JButton btnBrowse = new JButton("Browse");
    /** Button to show the client's reserve queue */
    private JButton btnShowQueue = new JButton("Show My Queue");
    /** Button to add a movie to client's reserve queue */
    private JButton btnAddToQueue = new JButton("Reserve Selected Item");
    /** Button to move a movie up the queue */
    private JButton btnMove = new JButton("Move Selected Item Up");
    /** Button to remove a movie from the queue */
    private JButton btnRemove = new JButton("Remove Selected Item");
    /** Button to return a movie */
    private JButton btnReturn = new JButton("Return Selected Item");
    /** Button to login to the movie system */
    private JButton btnLogin = new JButton("Login");
    /** Button to quit the GUI */
    private JButton btnQuit = new JButton("Quit");
    /** Button to logout of the system */
    private JButton btnLogout = new JButton("Logout");

    /** Default List Model to add a scrolling list for the reserveQueue */
    private DefaultListModel<String> dlmReserveQueueModel = new DefaultListModel<String>();
    /** Default list Model to add a scrolling list for the Inventory */
    private DefaultListModel<String> dlmInventoryModel = new DefaultListModel<String>();
    /** Default List Model to add a scrolling list for the atHomeQueue */
    private DefaultListModel<String> dlmAtHomeQueueModel = new DefaultListModel<String>();

    /** Label for added to queue */
    private JLabel lblAddedToQueue = new JLabel(" ");
    /** Label for the client id text field */
    private JLabel lblUserId = new JLabel("Username: ");
    /** Label for the password text field */
    private JLabel lblPassword = new JLabel("Password: ");

    // Text fields
    private JTextField txtUserName = new JTextField(ID_AND_PASSWORD_WIDTH);
    private JPasswordField pwdPassword = new JPasswordField(ID_AND_PASSWORD_WIDTH);

    // Lists that have scrolling functionality
    private JList<String> listInventory = new JList<String>(dlmInventoryModel);
    private JList<String> listReserveQueue = new JList<String>(dlmReserveQueueModel);
    private JList<String> listAtHomeQueue = new JList<String>(dlmAtHomeQueueModel);
    private JScrollPane scrollInventory = new JScrollPane(listInventory);
    private JScrollPane scrollReserveQueue = new JScrollPane(listReserveQueue);
    private JScrollPane scrollAtHomeQueue = new JScrollPane(listAtHomeQueue);

    // Panel items used to help organize the window
    private JPanel pnlMovies = new JPanel();
    private JPanel pnlButtons = new JPanel(new FlowLayout());
    private JPanel pnlInventoryTop = new JPanel();
    private Box boxInventory = Box.createVerticalBox();
    private Box boxQueue = Box.createVerticalBox();
    private JPanel pnlAddButton = new JPanel(new FlowLayout());
    private JPanel pnlAddMessage = new JPanel(new FlowLayout());
    private JPanel pnlReturnButton = new JPanel(new FlowLayout());
    private JPanel pnlSeparator = new JPanel();
    private JPanel pnlQueueInstructions = new JPanel(new FlowLayout());
    private JPanel pnlQueue = new JPanel(new BorderLayout());
    private JPanel pnlAdmin = new JPanel();
    private JPanel pnlAdminButtons = new JPanel(new FlowLayout());
    private JPanel pnlLoginInfo = new JPanel(new FlowLayout());
    private JPanel pnlUserName = new JPanel(new FlowLayout());
    private JPanel pnlUserPassword = new JPanel(new FlowLayout());
    private CardLayout cardLayout = new CardLayout(1, 1);

    // Main window
    private Container mainWindow = getContentPane();

    // Backend
    private transient ClientAccountManager accountManager;
    private transient TrackerManager movieRentals;

    /**
     * Constructor for TraxGUI. Creates the TrackingSystem model and
     * the administrative client. The GUI is initialized and set as visible.
     *
     * @param fileName name of file that initializes the inventory
     * @throws FileNotFoundException if no file of the given filename exists
     */
    public TraxGUI(String fileName) throws FileNotFoundException {
        if (fileName == null) {
            String userPickName = null;
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                userPickName = fc.getSelectedFile().getName();
            }
            movieRentals = new TrackingSystem(userPickName);
        } else {
            movieRentals = new TrackingSystem(fileName);
        }
        accountManager = new ClientAccountSystem(movieRentals);

        // Use the frame constants to form the panel
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle(WINDOW_TITLE);

        // Set list content/behavior.
        loadModel(listInventory, dlmInventoryModel, movieRentals.showInventory());
        listInventory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listReserveQueue.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listAtHomeQueue.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Include all visual components.
        // Set up panel of buttons at the top.
        pnlButtons.add(btnBrowse);
        pnlButtons.add(btnShowQueue);
        pnlButtons.add(btnLogout);

        // Set the borders for scrolling lists
        Font fontTitles = new Font(mainWindow.getFont().getName(), Font.BOLD, mainWindow.getFont().getSize());
        scrollInventory.setBorder(BorderFactory.createTitledBorder(scrollInventory.getBorder(), INVENTORY_TITLE,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, fontTitles));
        scrollAtHomeQueue.setBorder(BorderFactory.createTitledBorder(scrollAtHomeQueue.getBorder(), CHECKED_OUT_TITLE,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, fontTitles));
        scrollReserveQueue.setBorder(BorderFactory.createTitledBorder(scrollReserveQueue.getBorder(),
                RESERVE_QUEUE_TITLE, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, fontTitles));
        scrollInventory.setPreferredSize(new Dimension(FRAME_WIDTH - LEFT_PADDING - RIGHT_PADDING, INVENTORY_LENGTH));
        scrollAtHomeQueue
                .setPreferredSize(new Dimension(FRAME_WIDTH - LEFT_PADDING - RIGHT_PADDING, CHECKED_OUT_LENGTH));
        scrollReserveQueue.setPreferredSize(new Dimension(FRAME_WIDTH - LEFT_PADDING - RIGHT_PADDING, QUEUE_LENGTH));

        // Set up the browsing, queue, and admin panels.
        setUpBrowsingPanel();
        setUpQueuePanel();
        setUpAdminPanel();

        // Add browsing, queue, and admin panels to the UI.
        pnlMovies.setLayout(cardLayout);
        pnlMovies.add(pnlAdmin, "Admin");
        pnlMovies.add(boxInventory, "Browse");
        pnlMovies.add(boxQueue, "Show My Queue");
        mainWindow.add(pnlButtons, BorderLayout.NORTH);
        mainWindow.add(pnlMovies, BorderLayout.CENTER);

        // Enable buttons to respond to events.
        btnQuit.addActionListener(this);
        btnBrowse.addActionListener(this);
        btnShowQueue.addActionListener(this);
        btnLogout.addActionListener(this);
        btnAddToQueue.addActionListener(this);
        btnMove.addActionListener(this);
        btnReturn.addActionListener(this);
        btnRemove.addActionListener(this);
        btnLogin.addActionListener(this);
        btnAddNewClient.addActionListener(this);
        btnCancelAccount.addActionListener(this);

        toggleAdmin();
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                stopExecution();
            }
        });

        this.setVisible(true);
    }

    /**
     * Makes the GUI respond to button clicks.
     *
     * @param actionEvent the button click
     */
    public void actionPerformed(ActionEvent actionEvent) {

        // Browse titles button
        if (actionEvent.getSource().equals(btnBrowse)) {
            cardLayout.show(pnlMovies, "Browse");
            lblAddedToQueue.setText(" ");
            refreshInventoryList();
        }
        // Show my queue button
        if (actionEvent.getSource().equals(btnShowQueue)) {
            cardLayout.show(pnlMovies, "Show My Queue");
            this.refreshQueueAndAtHomeLists();
        }
        // Add to my queue button
        if (actionEvent.getSource().equals(btnAddToQueue)) {
            int k = listInventory.getSelectedIndex();
            if (k >= 0) {
                movieRentals.addToClientQueue(k);
                lblAddedToQueue.setText("Added: " + (String) dlmInventoryModel.get(k));
                refreshQueueAndAtHomeLists();
                refreshInventoryList();
            }
        }
        // Move to top button
        if (actionEvent.getSource().equals(btnMove)) {
            int k = listReserveQueue.getSelectedIndex();
            if (k >= 0) {
                movieRentals.reserveMoveAheadOne(k);
                refreshQueueAndAtHomeLists();
            }
        }
        // Remove selected item button
        if (actionEvent.getSource().equals(btnRemove)) {
            int k = listReserveQueue.getSelectedIndex();
            if (k >= 0)
                movieRentals.removeSelectedFromReserves(k);
            refreshQueueAndAtHomeLists();
        }
        // Return selected item button
        if (actionEvent.getSource().equals(btnReturn)) {
            int k = listAtHomeQueue.getSelectedIndex();
            if (k >= 0) {
                movieRentals.returnItemToInventory(k);
                refreshQueueAndAtHomeLists();
                refreshInventoryList();
            }
        }
        // Quit button
        if (actionEvent.getSource().equals(btnQuit)) {
            stopExecution();
        }
        // Logout button
        if (actionEvent.getSource().equals(btnLogout)) {
            cardLayout.show(pnlMovies, "Admin");
            accountManager.logout();
            toggleAdmin();
        }
        // Login button
        if (actionEvent.getSource().equals(btnLogin)) {
            try {
                accountManager.login(txtUserName.getText(), new String(pwdPassword.getPassword()));
                toggleAdmin();
                txtUserName.setText("");
                pwdPassword.setText("");
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
            }

        }

        // Add new client
        if (actionEvent.getSource().equals(btnAddNewClient)) {
            addNewClient();
        }

        // Cancel Account
        if (actionEvent.getSource().equals(btnCancelAccount)) {
            try {
                accountManager.cancelAccount(cancelUserInfo());
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
            }
        }
    }


    /**
     * Initializes the admin face
     */
    private void setUpAdminPanel() {
        // Admin subpanel
        pnlLoginInfo.setLayout(new BoxLayout(pnlLoginInfo, BoxLayout.PAGE_AXIS));
        // The login info panel has two subpanels, one for username and the
        // other for password.
        pnlUserName.setLayout(new FlowLayout());
        pnlUserPassword.setLayout(new FlowLayout());
        pnlUserName.add(lblUserId);
        pnlUserName.add(txtUserName);
        pnlUserPassword.add(lblPassword);
        pnlUserPassword.add(pwdPassword);
        pnlLoginInfo.add(Box.createVerticalStrut(40));
        pnlLoginInfo.add(pnlUserName);
        pnlLoginInfo.add(pnlUserPassword);
        pnlLoginInfo.add(btnLogin);
        pnlLoginInfo
                .setBorder(
                        BorderFactory
                                .createCompoundBorder(
                                        (EmptyBorder) BorderFactory.createEmptyBorder(UPPER_PADDING, LEFT_PADDING,
                                                BOTTOM_PADDING, RIGHT_PADDING),
                                        BorderFactory.createLineBorder(Color.red)));
        pnlAdmin.setLayout(new BorderLayout());
        pnlAdmin.add(pnlLoginInfo, BorderLayout.NORTH);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);

        pnlAdminButtons.add(btnAddNewClient);
        pnlAdminButtons.add(btnCancelAccount);
        pnlAdminButtons.add(btnQuit);

        pnlAdmin.add(pnlAdminButtons, BorderLayout.SOUTH);
    }

    /**
     * Sets up the queue face/panel.
     */
    private void setUpQueuePanel() {
        // Add components for the queue and home list.
        boxQueue.setBorder((EmptyBorder) BorderFactory.createEmptyBorder(UPPER_PADDING, LEFT_PADDING, BOTTOM_PADDING,
                RIGHT_PADDING));
        boxQueue.add(scrollAtHomeQueue);
        boxQueue.add(Box.createVerticalStrut(VERTICAL_SPACE));
        boxQueue.add(pnlReturnButton);
        boxQueue.add(pnlSeparator);
        boxQueue.add(Box.createVerticalStrut(VERTICAL_SPACE));
        boxQueue.add(scrollReserveQueue);
        boxQueue.add(pnlQueueInstructions);
        pnlQueue.setBorder((EmptyBorder) BorderFactory.createEmptyBorder(UPPER_PADDING, LEFT_PADDING, BOTTOM_PADDING,
                RIGHT_PADDING));
        pnlSeparator.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
        pnlQueueInstructions.add(btnMove);
        pnlQueueInstructions.add(btnRemove);
    }

    /**
     * Sets up the browsing face/panel.
     */
    private void setUpBrowsingPanel() {
        // Add the browsing components.
        boxInventory.setBorder((EmptyBorder) BorderFactory.createEmptyBorder(UPPER_PADDING, LEFT_PADDING,
                BOTTOM_PADDING, RIGHT_PADDING));
        pnlAddButton.add(btnAddToQueue);
        pnlAddMessage.add(lblAddedToQueue);
        pnlReturnButton.add(btnReturn);
        boxInventory.add(pnlInventoryTop);
        boxInventory.add(scrollInventory);
        boxInventory.add(Box.createVerticalStrut(VERTICAL_SPACE));
        boxInventory.add(pnlAddButton);
        boxInventory.add(Box.createVerticalStrut(VERTICAL_SPACE));
        boxInventory.add(pnlAddMessage);
        boxInventory.add(Box.createVerticalStrut(VERTICAL_SPACE * 2));
    }

    /**
     * Add new client to ClientAccountSystem.
     */
    private void addNewClient() {
        String[] info = newClientInfoDialog();
        if (info != null) {
            try {
                accountManager.addNewClient(info[0], info[1], Integer.parseInt(info[2]));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(new JFrame(), e.getMessage());
            }
        }
    }

    /**
     * Open dialog box so for id and password entry
     *
     * @return A two string array of the username and the password.
     */
    private String[] newClientInfoDialog() {
        String[] info = null;
        JPanel pnlClientInfo = new JPanel();
        JPanel pnlClientName = new JPanel(new FlowLayout());
        JPanel pnlClientPassword = new JPanel(new FlowLayout());
        JPanel pnlClientLimit = new JPanel(new FlowLayout());
        JTextField txtClientUserName = new JTextField(ID_AND_PASSWORD_WIDTH);
        JPasswordField pwdClientPassword = new JPasswordField(ID_AND_PASSWORD_WIDTH);
        String[] limits = {"1", "2", "3", "4", "5"};
        JComboBox<?> cboLimit = new JComboBox<Object>(limits);

        pnlClientInfo.setLayout(new BoxLayout(pnlClientInfo, BoxLayout.PAGE_AXIS));
        pnlClientName.add(new JLabel("Username: "));
        pnlClientName.add(txtClientUserName);
        pnlClientPassword.add(new JLabel("Password: "));
        pnlClientPassword.add(pwdClientPassword);
        pnlClientLimit.add(new JLabel("Item Limit: "));
        pnlClientLimit.add(cboLimit);
        pnlClientInfo.add(pnlClientName);
        pnlClientInfo.add(pnlClientPassword);
        pnlClientInfo.add(pnlClientLimit);
        int result = JOptionPane.showConfirmDialog(null, pnlClientInfo,
                "Enter the new client's username and password", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            info = new String[3];
            info[0] = txtClientUserName.getText();
            info[1] = new String(pwdClientPassword.getPassword());
            info[2] = (String) cboLimit.getSelectedItem();
        }
        return info;
    }

    /**
     * Open dialog for admin to cancel client accounts.
     *
     * @return username of account to cancel, or null if the client cancels out.
     */
    private String cancelUserInfo() {
        String acctResult = null;
        JPanel pnlCancel = new JPanel(new FlowLayout());
        pnlCancel.add(new JLabel("Username: "));
        JTextField txtCancelUserName = new JTextField(ID_AND_PASSWORD_WIDTH);
        pnlCancel.add(txtCancelUserName);
        int result = JOptionPane.showConfirmDialog(null, pnlCancel,
                "Enter the username for the account to be cancelled.", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION)
            acctResult = txtCancelUserName.getText().trim();
        return acctResult;
    }

    /**
     * Method to toggle admin actions.
     */
    private void toggleAdmin() {
        boolean adminLoggedIn = accountManager.isAdminLoggedIn();
        boolean clientLoggedIn = accountManager.isClientLoggedIn();
        btnQuit.setEnabled(adminLoggedIn);
        btnAddNewClient.setEnabled(adminLoggedIn);
        btnCancelAccount.setEnabled(adminLoggedIn);
        btnQuit.setEnabled(adminLoggedIn);
        btnLogin.setEnabled(!adminLoggedIn && !clientLoggedIn);
        btnLogout.setEnabled(adminLoggedIn || clientLoggedIn);

        btnBrowse.setEnabled(clientLoggedIn);
        btnShowQueue.setEnabled(clientLoggedIn);
        btnAddToQueue.setEnabled(clientLoggedIn);
        btnMove.setEnabled(clientLoggedIn);
        btnRemove.setEnabled(clientLoggedIn);
        btnReturn.setEnabled(clientLoggedIn);
    }

    /**
     * Loads model list from a string.
     *
     * @param jList     to be refreshed
     * @param modelList the default j list model
     * @param info      string used to initialize the default model
     */
    private void loadModel(JList<String> jList, DefaultListModel<String> modelList, String info) {
        Scanner s = new Scanner(info);
        while (s.hasNext()) {
            modelList.addElement(s.nextLine());
        }
        jList.ensureIndexIsVisible(0);
    }

    /**
     * Private Method - refreshes lists on the QUEUE card.
     */
    private void refreshQueueAndAtHomeLists() {
        dlmAtHomeQueueModel.clear();
        dlmReserveQueueModel.clear();
        try { // If Client logged in
            loadModel(listAtHomeQueue, dlmAtHomeQueueModel, movieRentals.traverseAtHomeQueue());
            loadModel(listReserveQueue, dlmReserveQueueModel, movieRentals.traverseReserveQueue());
        } catch (IllegalStateException e) {
            // Client  not logged in
        }
    }

    /**
     * Refresh browse list
     */
    private void refreshInventoryList() {
        dlmInventoryModel.clear();
        loadModel(listInventory, dlmInventoryModel, movieRentals.showInventory());
    }

    /**
     * Quit MovieSystem
     */
    private static void stopExecution() {
        System.exit(0);
    }

    /**
     * Starts the program.
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        try {
            if (args.length > 0)
                new TraxGUI(args[0]);
            else
                new TraxGUI(null);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Incorrect Inventory File Specified");
            stopExecution();
        }
    }
}