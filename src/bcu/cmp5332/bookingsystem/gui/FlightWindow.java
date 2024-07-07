package bcu.cmp5332.bookingsystem.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import bcu.cmp5332.bookingsystem.commands.DeleteFlight;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The {@code FlightWindow} class represents a GUI panel for managing flights in a flight booking system.
 * It provides functionalities to view, add, and delete flights, and allows filtering of flights based
 * on specific criteria.
 *
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 *
 * @see FlightBookingSystem
 * @see AddFlightWindow
 * @see DeleteFlight
 */

public class FlightWindow extends JPanel implements ActionListener {

    /**
     * The flight booking system instance to manage and display flight information.
     */
    private final FlightBookingSystem fbs;

    /**
     * The panel that contains the main content, including the table for displaying flight information.
     */
    private JPanel contentPanel;

    /**
     * The combo box for selecting filter options to view flights.
     */
    private JComboBox<String> viewOptions;

    /**
     * The table for displaying flight information.
     */
    private JTable table; // Define JTable instance

    /**
     * Gets the flight booking system associated with this panel.
     *
     * @return the flight booking system.
     */
    public FlightBookingSystem getFlightBookingSystem() {
        return fbs;
    }

    /**
     * Constructs a new {@code FlightWindow} with the specified flight booking system.
     * This constructor initializes the GUI components and sets up the layout.
     *
     * @param fbs the flight booking system to manage and display flight information.
     */
    public FlightWindow(FlightBookingSystem fbs) {
        this.fbs = fbs;
        initialize();
    }

    /**
     * Initializes the main layout and components of the {@code FlightWindow}.
     * Sets up the layout, title label, and various panels including the dropdown menu for filtering.
     */
    private void initialize() {
        setLayout(new BorderLayout());
        JPanel paddedPanel = createPaddedPanel();
        add(paddedPanel, BorderLayout.CENTER);

        JLabel titleLabel = createTitleLabel();
        paddedPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonAndContentPanel = new JPanel(new BorderLayout());
        paddedPanel.add(buttonAndContentPanel, BorderLayout.CENTER);

        JPanel dropdownPanel = createDropdownPanel();
        buttonAndContentPanel.add(dropdownPanel, BorderLayout.NORTH);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonAndContentPanel.add(contentPanel, BorderLayout.CENTER);

        // Set initial empty content or message
        JLabel initialMessage = new JLabel("Select a filter option to display flights.");
        initialMessage.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(initialMessage, BorderLayout.CENTER);
    }

    /**
     * Creates a panel with padding to be used as the main container.
     *
     * @return the padded panel with a border layout.
     */
    private JPanel createPaddedPanel() {
        JPanel paddedPanel = new JPanel(new BorderLayout());
        paddedPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return paddedPanel;
    }

    /**
     * Creates and returns a title label for the flight management panel.
     *
     * @return a {@code JLabel} configured as the title for the panel.
     */
    private JLabel createTitleLabel() {
        JLabel titleLabel = new JLabel("Flight Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return titleLabel;
    }

    /**
     * Creates and returns a panel containing the dropdown menu for filtering flight data,
     * and buttons for adding and deleting flights.
     *
     * @return a {@code JPanel} containing the dropdown menu and action buttons.
     */
    private JPanel createDropdownPanel() {
        JPanel dropdownPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dropdownPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton addFlightButton = createButton("Add Flight", Color.BLUE, this);
        dropdownPanel.add(addFlightButton);

        viewOptions = new JComboBox<>(new String[]{"View Flights By:", "All Flights", "Active Flights"});
        viewOptions.setPreferredSize(new Dimension(200, 35)); // Increased size for better visibility
        viewOptions.setSelectedIndex(0); // Set the default option as selected
        viewOptions.setRenderer(new ComboBoxRenderer());
        viewOptions.addActionListener(this);
        dropdownPanel.add(viewOptions);

        JButton deleteFlightButton = createButton("Delete Flight", Color.RED, this);
        dropdownPanel.add(deleteFlightButton);

        return dropdownPanel;
    }

    /**
     * Creates a button with specified label, color, and action listener.
     *
     * @param label     the text to display on the button.
     * @param color     the background color of the button.
     * @param listener  the action listener to handle button clicks.
     * @return a configured {@code JButton} ready for use.
     */
    private JButton createButton(String label, Color color, ActionListener listener) {
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(160, 35)); // Adjusted size for better visibility
        button.setBackground(color); // Set background color
        button.setForeground(Color.WHITE); // Set text color to white
        button.setFocusPainted(false); // Remove focus border
        button.setBorderPainted(false); // Remove border
        button.setOpaque(true); // Ensure button background is visible
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // Add padding
        button.addActionListener(listener);
        return button;
    }

    /**
     * Displays the flights based on the selected filter option in the dropdown menu.
     * Retrieves flight data from the flight booking system and updates the table.
     */
    private void displayFlights() {
        String filterOption = (String) viewOptions.getSelectedItem();

        // Prevent action if the default option is selected
        if ("View Flights By:".equals(filterOption)) {
            JOptionPane.showMessageDialog(this, "Please select a valid filter option.", "Invalid Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Flight> flightsList = fbs.getFlights();
        LocalDate today = LocalDate.now();
        boolean showActiveFlights = "Active Flights".equals(filterOption);

        List<Flight> filteredFlights = flightsList.stream()
                .filter(flight -> {
                    if (showActiveFlights) {
                        return flight.hasNotDeparted(today) && !flight.isDeleted();
                    } else {
                        return !flight.isDeleted();
                    }
                })
                .collect(Collectors.toList());

        String[] columns = {"Flight ID", "Flight No", "Origin", "Destination", "Departure Date", "Number of Seats", "Price", "Booked Full Status", "Departed"};
        Object[][] data = new Object[filteredFlights.size()][columns.length];

        for (int i = 0; i < filteredFlights.size(); i++) {
            Flight flight = filteredFlights.get(i);
            populateData(data, i, flight, today);
        }

        updateTable(data, columns);
    }

    /**
     * Populates the data for a specific flight into the provided data array.
     *
     * @param data   the data array to be populated.
     * @param index  the index at which to insert the flight data.
     * @param flight the flight object containing the data.
     * @param today  the current date for price calculation.
     */
    private void populateData(Object[][] data, int index, Flight flight, LocalDate today) {
        boolean fullyBooked = flight.isFullyBooked();
        boolean departed = !flight.hasNotDeparted(today);

        String price;
        try {
            price = String.valueOf(flight.calculatePrice(today));
        } catch (FlightBookingSystemException ex) {
            price = "Price calculation error";
        }

        data[index] = new Object[]{
                flight.getId(),
                flight.getFlightNumber(),
                flight.getOrigin(),
                flight.getDestination(),
                flight.getDepartureDate(),
                flight.getNumberOfSeats(),
                price,
                fullyBooked ? "Yes" : "No",
                departed ? "Yes" : "No"
        };
    }

    /**
     * Updates the flight table with the provided data and column names.
     * Configures the table's appearance, including row height and cell padding.
     *
     * @param data    the data to display in the table.
     * @param columns the column names for the table.
     */

    private void updateTable(Object[][] data, String[] columns) {
        DefaultTableModel model = new DefaultTableModel(data, columns);
        table = new JTable(model) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                c.setFont(new Font("Arial", Font.PLAIN, 16));
                if (isCellSelected(row, column)) {
                    c.setBackground(Color.BLUE);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        };

        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30); // Increased row height for better visibility

        // Add padding to cells
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        renderer.setVerticalAlignment(JLabel.CENTER);
        renderer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Add padding
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(renderer);
        }

        // Customize the table header
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 18));
        tableHeader.setBackground(Color.LIGHT_GRAY);
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setPreferredSize(new Dimension(tableHeader.getPreferredSize().width, 40)); // Increased header height

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentPanel.removeAll();
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

       /**
     * Refreshes the flights table by displaying the updated list of flights.
     */
    public void refreshFlightsTable() {
        displayFlights();
    }

    /**
     * Handles actions performed by the user, such as selecting filter options,
     * adding a new flight, or deleting an existing flight.
     *
     * @param e the action event triggered by user interaction.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == viewOptions) {
            displayFlights();
        } else if (e.getActionCommand().equals("Add Flight")) {
            handleAddFlight();
        } else if (e.getActionCommand().equals("Delete Flight")) {
            handleDeleteFlight();
        }
    }

    /**
     * Opens the {@code AddFlightWindow} to allow the user to add a new flight.
     */
    private void handleAddFlight() {
        AddFlightWindow addFlightWindow = new AddFlightWindow(this, fbs);
        addFlightWindow.setVisible(true);
    }

    /**
     * Handles the deletion of a selected flight from the flights table.
     * Displays confirmation dialogs and updates the table after deletion.
     */
    private void handleDeleteFlight() {
        if (table != null && table.getRowCount() > 0) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int flightId = (int) table.getValueAt(selectedRow, 0);
                try {
                    new DeleteFlight(flightId).execute(fbs);
                    JOptionPane.showMessageDialog(this, "Flight deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshFlightsTable(); // Refresh the table after deletion
                } catch (FlightBookingSystemException ex) {
                    JOptionPane.showMessageDialog(this, "Failed to delete flight: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a flight to delete.", "No Flight Selected", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No flights available. Please view flights first.", "No Flights Available", JOptionPane.WARNING_MESSAGE);
        }
    }
}
