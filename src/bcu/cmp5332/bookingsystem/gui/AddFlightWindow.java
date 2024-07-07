package bcu.cmp5332.bookingsystem.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.commands.AddFlight;
import bcu.cmp5332.bookingsystem.commands.Command;

/**
 * This class represents a window for adding a new flight to the flight booking system.
 * It provides a user interface for entering flight details like flight number, origin,
 * destination, departure date, number of seats, and price.
 * 
 * @see FlightBookingSystem
 * @see AddFlight
 * @see FlightWindow
 * @see ActionListener
 * @see JFrame
 * 
 * @see LocalDate
 * @see DateTimeFormatter
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 * 
 */
@SuppressWarnings("serial")
public class AddFlightWindow extends JFrame implements ActionListener {

    private final FlightWindow flightWindow;

    private JTextField flightNumberField;
    private JTextField originField;
    private JTextField destinationField;
    private JTextField departureDateField;
    private JTextField numberOfSeatsField;
    private JTextField priceField;

    /**
     * Constructs a new AddFlightWindow.
     * 
     * @param flightWindow the main flight window used for refreshing the flights list.
     * @param fbs the flight booking system instance to add the flight to.
     */
    public AddFlightWindow(FlightWindow flightWindow, FlightBookingSystem fbs) {
        this.flightWindow = flightWindow;
        initialize();
    }

    /**
     * Initializes the window settings and components.
     * Sets the title, size, layout, and default operations for the window.
     */
    private void initialize() {
        setTitle("Add Flight");
        setSize(600, 300);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());
        
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        flightNumberField = createInputField(inputPanel, "Flight Number");
        originField = createInputField(inputPanel, "Origin");
        destinationField = createInputField(inputPanel, "Destination");
        departureDateField = createInputField(inputPanel, "Departure Date (YYYY-MM-DD)");
        numberOfSeatsField = createInputField(inputPanel, "Number of Seats");
        priceField = createInputField(inputPanel, "Price");

        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addButton = new JButton("Add Flight");
        addButton.addActionListener(this);
        buttonPanel.add(addButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Creates and returns a JTextField for input along with a corresponding JLabel.
     * 
     * @param panel the panel to which the label and field are added.
     * @param label the text for the JLabel.
     * @return the created JTextField for user input.
     */
    private JTextField createInputField(JPanel panel, String label) {
        JLabel jLabel = new JLabel(label);
        JTextField jTextField = new JTextField();
        panel.add(jLabel);
        panel.add(jTextField);
        return jTextField;
    }

    /**
     * Handles action events for the buttons.
     * If the add button is clicked, the add flight method is called.
     * If the cancel button is clicked, the window is disposed.
     * 
     * @param e the ActionEvent triggered by the button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String flightNumber = flightNumberField.getText().trim();
        String origin = originField.getText().trim();
        String destination = destinationField.getText().trim();
        String departureDateStr = departureDateField.getText().trim();
        String numberOfSeatsStr = numberOfSeatsField.getText().trim();
        String priceStr = priceField.getText().trim();

        if (flightNumber.isEmpty() || origin.isEmpty() || destination.isEmpty() || departureDateStr.isEmpty() ||
            numberOfSeatsStr.isEmpty() || priceStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            LocalDate departureDate = LocalDate.parse(departureDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            int numberOfSeats = Integer.parseInt(numberOfSeatsStr);
            double price = Double.parseDouble(priceStr);

            Command flight = new AddFlight(flightNumber, origin, destination, departureDate, numberOfSeats, price);
            flight.execute(flightWindow.getFlightBookingSystem());
            flightWindow.refreshFlightsTable();
            JOptionPane.showMessageDialog(this, "Flight added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
