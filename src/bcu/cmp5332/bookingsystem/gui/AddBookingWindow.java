package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddBooking;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * This class represents a window for adding a booking in the flight booking system.
 * It provides a user interface for entering customer and flight information and
 * adding a new booking.
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
@SuppressWarnings("serial")
public class AddBookingWindow extends JFrame {

    private FlightBookingSystem fbs;
    private JTextField customerIDField;
    private JTextField flightIDField;
    private JButton addButton;
    private JButton cancelButton;

    /**
     * Constructs a new AddBookingWindow.
     * 
     * @param fbs the flight booking system instance used to add the booking.
     */
    public AddBookingWindow(FlightBookingSystem fbs) {
        this.fbs = fbs;
        setTitle("Add Booking");
        setSize(400, 200);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initializeComponents();
        addComponentsToFrame();

        setVisible(true);
    }

    /**
     * Initializes the components of the window.
     * Sets up the layout and adds the input fields and buttons.
     */
    private void initializeComponents() {
        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel customerLabel = new JLabel("Customer ID:");
        customerIDField = new JTextField();

        JLabel flightLabel = new JLabel("Flight ID:");
        flightIDField = new JTextField();

        addButton = new JButton("Add");
        addButton.addActionListener(e -> addBooking());

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());

        mainPanel.add(customerLabel);
        mainPanel.add(customerIDField);
        mainPanel.add(flightLabel);
        mainPanel.add(flightIDField);
        mainPanel.add(addButton);
        mainPanel.add(cancelButton);

        setContentPane(mainPanel);
    }

    /**
     * Handles the action of adding a booking.
     * Retrieves the customer ID and flight ID from the input fields,
     * creates a new booking, and adds it to the flight booking system.
     * Displays a confirmation message upon successful booking or
     * an error message in case of failure.
     */
    private void addBooking() {
        try {
            int customerId = Integer.parseInt(customerIDField.getText());
            int flightId = Integer.parseInt(flightIDField.getText());
            LocalDate bookingDate = LocalDate.now(); // Use the current date as booking date

            Command addBooking = new AddBooking(customerId, flightId, bookingDate);
            addBooking.execute(fbs);
            JOptionPane.showMessageDialog(this, "Booking added successfully.");
            dispose(); // Close the window after successful booking
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Unexpected error occurred.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Adds the components to the frame.
     * This method can be used to add any additional components to the frame.
     */
    private void addComponentsToFrame() {
        // Method to add components to the frame
    }

    /**
     * The main method to test the AddBookingWindow.
     * Creates a new instance of the flight booking system and opens the add booking window.
     * 
     * @param args the command line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            // Test the AddBookingWindow
            FlightBookingSystem fbs = new FlightBookingSystem(); // Create or load FlightBookingSystem instance
            SwingUtilities.invokeLater(() -> new AddBookingWindow(fbs));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
