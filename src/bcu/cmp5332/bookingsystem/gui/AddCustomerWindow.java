package bcu.cmp5332.bookingsystem.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import bcu.cmp5332.bookingsystem.commands.AddCustomer;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * This class represents a window for adding a new customer in the flight booking system.
 * It provides a user interface for entering customer details like name, email, and phone number,
 * and adding them to the system.
 * 
 * @see FlightBookingSystem
 * @see AddCustomer
 * @see CustomerWindow
 * 
 * @see ActionListener
 * 
 * @see JFrame
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
@SuppressWarnings("serial")
public class AddCustomerWindow extends JFrame implements ActionListener {

    private final FlightBookingSystem fbs;
    private final CustomerWindow customerWindow; // Reference to the main window for refreshing purposes

    private JTextField nameField;
    private JTextField emailField;
    private JTextField phoneField;

    private JButton saveButton;
    private JButton cancelButton;

    /**
     * Constructs a new AddCustomerWindow.
     * 
     * @param customerWindow the main customer window for refreshing the customer list.
     * @param fbs the flight booking system instance used to add the customer.
     */
    public AddCustomerWindow(CustomerWindow customerWindow, FlightBookingSystem fbs) {
        this.customerWindow = customerWindow;
        this.fbs = fbs;
        initialize();
    }

    /**
     * Initializes the window settings and components.
     * Sets the title, size, layout, and default operations for the window.
     */
    private void initialize() {
        setTitle("Add Customer");
        setSize(400, 300);
        setLayout(new BorderLayout());

        JPanel formPanel = createFormPanel();
        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
    }

    /**
     * Creates and returns the form panel for customer details input.
     * Uses GridBagLayout to arrange labels and text fields for name, email, and phone number.
     * 
     * @return a JPanel containing the form layout.
     */
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);

        nameField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(emailLabel, gbc);

        emailField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        JLabel phoneLabel = new JLabel("Phone:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(phoneLabel, gbc);

        phoneField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(phoneField, gbc);

        return formPanel;
    }

    /**
     * Creates and returns the button panel for save and cancel actions.
     * Uses FlowLayout to center the buttons horizontally.
     * 
     * @return a JPanel containing the buttons.
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        saveButton = new JButton("Save");
        saveButton.addActionListener(this);
        buttonPanel.add(saveButton);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    /**
     * Handles action events for the buttons.
     * If the save button is clicked, the addCustomer method is called to add the customer.
     * If the cancel button is clicked, the window is disposed.
     * 
     * @param e the ActionEvent triggered by the button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            addCustomer();
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }

    /**
     * Adds a new customer to the flight booking system.
     * Retrieves customer details from the input fields, creates a new AddCustomer command,
     * and executes it to add the customer.
     * Displays a confirmation message on success or an error message on failure.
     */
    private void addCustomer() {
        try {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
    
            // Validate email format
            if (!email.contains("@")) {
                JOptionPane.showMessageDialog(this, "Invalid email format. Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit method if email format is invalid
            }
    
            // Validate phone number contains only digits
            if (!phone.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Phone number must contain only digits.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit method if phone number format is invalid
            }
    
            // Validate name contains only alphabetic characters
            if (!name.matches("[a-zA-Z]+")) {
                JOptionPane.showMessageDialog(this, "Name must contain only letters.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Exit method if name format is invalid
            }
    
            // Assuming AddCustomer is a command pattern to add a customer
            Command addCustomer = new AddCustomer(name, phone, email);
            addCustomer.execute(fbs); // Execute the command to add the customer
    
            // Assuming customerWindow is a reference to the main window's customer list
            customerWindow.refreshCustomersTable(); // Refresh the main window's customer list
    
            JOptionPane.showMessageDialog(this, "Customer added successfully");
            dispose(); // Close the current dialog or window after successful addition
        } catch (FlightBookingSystemException ex) {
            // Handle specific exceptions related to the flight booking system
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Optionally print the stack trace for debugging
        } catch (Exception ex) {
            // Handle any other unexpected exceptions
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(); // Optionally print the stack trace for debugging
        }
    }
    
    
    
}
