package bcu.cmp5332.bookingsystem.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import bcu.cmp5332.bookingsystem.commands.DeleteCustomer;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.model.Booking;

/**
 * The {@code CustomerWindow} class represents a graphical user interface (GUI) panel for managing
 * customer information in a flight booking system. It provides functionalities to view, add,
 * and delete customers, and allows filtering of customer data based on specific criteria.
 *
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 *
 * @see FlightBookingSystem
 * @see AddCustomerWindow
 * @see DeleteCustomer
 */

public class CustomerWindow extends JPanel {

    private final FlightBookingSystem fbs;
    private JPanel contentPanel;
    private JComboBox<String> viewOptions;
    private JTable table;

    /**
     * Constructs a new {@code CustomerWindow} with the specified flight booking system.
     * This constructor initializes the GUI components and sets up the layout.
     *
     * @param fbs the flight booking system to manage and display customer information.
     */
    public CustomerWindow(FlightBookingSystem fbs) {
        this.fbs = fbs;
        initialize();
    }

    /**
     * Initializes the main layout and components of the {@code CustomerWindow}.
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
        JLabel initialMessage = new JLabel("Select a filter option to display customers.");
        initialMessage.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(initialMessage, BorderLayout.CENTER);

        displayCustomers();
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
     * Creates and returns a title label for the customer management panel.
     *
     * @return a {@code JLabel} configured as the title for the panel.
     */
    private JLabel createTitleLabel() {
        JLabel titleLabel = new JLabel("Customer Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return titleLabel;
    }

    /**
     * Creates and returns a panel containing the dropdown menu for filtering customer data,
     * and buttons for adding and deleting customers.
     *
     * @return a {@code JPanel} containing the dropdown menu and action buttons.
     */
    private JPanel createDropdownPanel() {
        JPanel dropdownPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dropdownPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton addCustomerButton = createButton("Add Customer", Color.BLUE, this::openAddCustomerWindow);
        dropdownPanel.add(addCustomerButton);

        // Add a default option to the dropdown and set custom renderer
        viewOptions = new JComboBox<>(new String[]{"View Customer By:", "All Customers", "Active Customers", "Customers with No Bookings"});
        viewOptions.setSelectedIndex(0);
        viewOptions.setRenderer(new ComboBoxRenderer());
        viewOptions.addActionListener(e -> {
            if (viewOptions.getSelectedIndex() > 0) {
                displayCustomers();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a valid filter option.", "Invalid Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
        dropdownPanel.add(viewOptions);

        JButton deleteCustomerButton = createButton("Delete Customer", Color.RED, this::handleDeleteCustomer);
        dropdownPanel.add(deleteCustomerButton);

        return dropdownPanel;
    }

    /**
     * Creates a {@code JButton} with the specified label, background color, and action listener.
     *
     * @param label      the text to display on the button.
     * @param color      the background color of the button.
     * @param action     the action listener to handle button clicks.
     * @return a configured {@code JButton} ready for use.
     */
    private JButton createButton(String label, Color color, Runnable action) {
        JButton button = new JButton(label);
        button.setPreferredSize(new Dimension(160, 35));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(5, 5, 4, 15));
        button.addActionListener(e -> action.run());
        return button;
    }

    /**
     * Displays the customers based on the selected filter option in the dropdown menu.
     * Retrieves customer data from the flight booking system and updates the table.
     */
    private void displayCustomers() {
        String filterOption = (String) viewOptions.getSelectedItem();

        // Prevent action if the default option is selected
        if ("View Customer By:".equals(filterOption)) {
            return;
        }

        Collection<Customer> customersList = fbs.getAllCustomers();
        List<Customer> filteredCustomers;

        switch (filterOption) {
            case "Active Customers":
                filteredCustomers = customersList.stream()
                        .filter(customer -> !customer.isDeleted() && !customer.getActiveBookings().isEmpty())
                        .collect(Collectors.toList());
                break;
            case "Customers with No Bookings":
                filteredCustomers = customersList.stream()
                        .filter(customer -> !customer.isDeleted() && customer.getActiveBookings().isEmpty())
                        .collect(Collectors.toList());
                break;
            default:
                filteredCustomers = customersList.stream()
                        .filter(customer -> !customer.isDeleted())
                        .collect(Collectors.toList());
                break;
        }

        String[] columns = {"Customer ID", "Name", "Email", "Phone", "Active Bookings Count"};
        Object[][] data = new Object[filteredCustomers.size()][columns.length];

        int i = 0;
        for (Customer customer : filteredCustomers) {
            List<Booking> activeBookings = customer.getActiveBookings();
            int activeBookingsCount = activeBookings.size();
            data[i][0] = customer.getId();
            data[i][1] = customer.getName();
            data[i][2] = customer.getEmail();
            data[i][3] = customer.getPhone();
            data[i][4] = activeBookingsCount;
            i++;
        }

        updateTable(data, columns);
    }

    /**
     * Updates the customer table with the provided data and column names.
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
        table.setRowHeight(30);

        // Add padding
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        renderer.setVerticalAlignment(JLabel.CENTER);
        renderer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(renderer);
        }

        // Customize the table header
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 18));
        tableHeader.setBackground(Color.LIGHT_GRAY);
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setPreferredSize(new Dimension(tableHeader.getPreferredSize().width, 40));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentPanel.removeAll();
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * Opens a new window for adding a customer. This method invokes the {@link AddCustomerWindow}
     * class to allow the user to input and add new customer details.
     */
    private void openAddCustomerWindow() {
        SwingUtilities.invokeLater(() -> {
            AddCustomerWindow addCustomerWindow = new AddCustomerWindow(this, fbs);
            addCustomerWindow.setVisible(true);
        });
    }

    /**
     * Refreshes the customer table by re-fetching and displaying the latest customer data.
     */
    public void refreshCustomersTable() {
        displayCustomers();
    }

    /**
     * Handles the deletion of a selected customer. It retrieves the selected customer ID from the table,
     * deletes the customer from the flight booking system, and refreshes the table.
     */
    private void handleDeleteCustomer() {
        if (table != null && table.getRowCount() > 0) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                int customerId = (int) table.getValueAt(selectedRow, 0);
                try {
                    new DeleteCustomer(customerId).execute(fbs);
                    JOptionPane.showMessageDialog(this, "Customer deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    displayCustomers();
                } catch (FlightBookingSystemException e) {
                    JOptionPane.showMessageDialog(this, "Failed to delete customer: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a customer to delete.", "No Customer Selected", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No customers to delete.", "Empty Table", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Custom renderer for the {@code JComboBox}, used to set the display text of the combo box items.
     */
    private static class ComboBoxRenderer extends DefaultListCellRenderer {
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value != null) {
                setText(value.toString());
            }
            return this;
        }
    }
}
