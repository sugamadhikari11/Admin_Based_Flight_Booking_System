package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.CancelBooking;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;

/**
 * The CancelBookingWindow class provides a graphical interface for canceling bookings in a flight booking system.
 * It allows users to input the Booking ID and Flight ID to cancel a specific booking.
 *
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 *
 * @see FlightBookingSystem
 * @see BookingWindow
 * @see CancelBooking
 */
public class CancelBookingWindow extends JFrame {

    private final FlightBookingSystem fbs;
    private final BookingWindow bookingWindow; // Reference to BookingWindow
    private JTextField bookingIdField;
    private JTextField flightIdField;
    private JButton cancelBookingButton;
    private JButton closeButton;

    /**
     * Constructs a CancelBookingWindow with the specified flight booking system and a reference to the booking window.
     * This reference allows refreshing the bookings table upon successful booking cancellation.
     *
     * @param fbs            the flight booking system used for managing bookings.
     * @param bookingWindow  the parent booking window to refresh after cancellation.
     */
    public CancelBookingWindow(FlightBookingSystem fbs, BookingWindow bookingWindow) {
        this.fbs = fbs;
        this.bookingWindow = bookingWindow; // Initialize reference
        setTitle("Cancel Booking");
        setSize(400, 200);
        setLocationRelativeTo(null); // Center the window
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initializeComponents();
        addComponentsToFrame();

        setVisible(true);
    }

    /**
     * Initializes the GUI components including text fields and buttons.
     */
    private void initializeComponents() {
        bookingIdField = new JTextField(20);
        flightIdField = new JTextField(20);

        cancelBookingButton = new JButton("Cancel Booking");
        cancelBookingButton.addActionListener(e -> cancelBooking());

        closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
    }

    /**
     * Adds the initialized components to the main frame.
     * Organizes components using BorderLayout and GridLayout for proper alignment.
     */
    private void addComponentsToFrame() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.add(new JLabel("Customer ID:"));
        inputPanel.add(bookingIdField);
        inputPanel.add(new JLabel("Flight ID:"));
        inputPanel.add(flightIdField);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(cancelBookingButton);
        buttonPanel.add(closeButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);
    }

    /**
     * Handles the booking cancellation process.
     * Validates input fields, executes the cancellation command, and refreshes the booking table.
     */
    private void cancelBooking() {
        String bookingIdInput = bookingIdField.getText().trim();
        String flightIdInput = flightIdField.getText().trim();

        if (bookingIdInput.isEmpty() || flightIdInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Both Booking ID and Flight ID must be entered.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int bookingId = Integer.parseInt(bookingIdInput);
            int flightId = Integer.parseInt(flightIdInput);

            // Execute the CancelBooking command
            CancelBooking cancelBooking = new CancelBooking(bookingId, flightId);
            cancelBooking.execute(fbs);

            JOptionPane.showMessageDialog(this, "Booking successfully canceled.", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Clear the fields after successful cancellation
            bookingIdField.setText("");
            flightIdField.setText("");

            // Refresh the bookings table in the BookingWindow
            bookingWindow.refreshBookingsTable();

            // Close this window
            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID format. Please enter numeric values for both Booking ID and Flight ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (FlightBookingSystemException ex) {
            JOptionPane.showMessageDialog(this, "Error canceling booking: " + ex.getMessage(), "Operation Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Main method for testing the CancelBookingWindow.
     * Creates instances of FlightBookingSystem and BookingWindow, and opens the CancelBookingWindow.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            // Create or load your FlightBookingSystem instance here
            FlightBookingSystem fbs = new FlightBookingSystem();
            // Create a BookingWindow instance for demonstration purposes
            BookingWindow bookingWindow = new BookingWindow(fbs);
            new CancelBookingWindow(fbs, bookingWindow);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
