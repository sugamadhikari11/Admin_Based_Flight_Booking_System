package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The BookingWindow class provides a GUI for managing bookings within a flight booking system.
 * Users can view all bookings, filter by active bookings, and add or cancel bookings.
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 * 
 * @see FlightBookingSystem
 * @see Booking
 * @see Customer
 * @see JTable
 * @see JPanel
 */
@SuppressWarnings("serial")
public class BookingWindow extends JPanel {

    private final FlightBookingSystem fbs;
    private JPanel contentPanel;
    private JComboBox<String> viewOptions;

    /**
     * Constructs a BookingWindow with the specified flight booking system.
     * 
     * @param fbs the flight booking system used to manage bookings.
     */
    public BookingWindow(FlightBookingSystem fbs) {
        this.fbs = fbs;
        initialize();
    }

    /**
     * Initializes the components of the BookingWindow, setting up the layout and adding UI elements.
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

        JLabel initialMessage = new JLabel("Select a filter option to display bookings.");
        initialMessage.setHorizontalAlignment(JLabel.CENTER);
        contentPanel.add(initialMessage, BorderLayout.CENTER);
    }

    /**
     * Creates a padded panel to provide margins around the content.
     * 
     * @return a JPanel with padding around it.
     */
    private JPanel createPaddedPanel() {
        JPanel paddedPanel = new JPanel(new BorderLayout());
        paddedPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return paddedPanel;
    }

    /**
     * Creates and returns a JLabel for the title of the BookingWindow.
     * 
     * @return a JLabel for the window title.
     */
    private JLabel createTitleLabel() {
        JLabel titleLabel = new JLabel("Booking Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return titleLabel;
    }

    /**
     * Creates a panel containing a dropdown menu and buttons for adding and canceling bookings.
     * 
     * @return a JPanel with dropdown and buttons.
     */
    private JPanel createDropdownPanel() {
        JPanel dropdownPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        dropdownPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JButton addBookingButton = new JButton("Add Booking");
        styleButton(addBookingButton, Color.BLUE);
        addBookingButton.addActionListener(e -> openAddBookingWindow());
        dropdownPanel.add(addBookingButton);

        viewOptions = new JComboBox<>(new String[]{"View Booking By:", "All Bookings", "Active Bookings"});
        viewOptions.setSelectedIndex(0);
        viewOptions.setRenderer(new ComboBoxRenderer());
        viewOptions.addActionListener(e -> {
            if (viewOptions.getSelectedIndex() > 0) {
                displayBookings();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a valid filter option.", "Invalid Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
        dropdownPanel.add(viewOptions);

        JButton cancelBookingButton = new JButton("Cancel Booking");
        styleButton(cancelBookingButton, Color.ORANGE);
        cancelBookingButton.addActionListener(e -> handleCancelBooking());
        dropdownPanel.add(cancelBookingButton);

        return dropdownPanel;
    }

    /**
     * Styles a JButton with the specified background color and other visual properties.
     * 
     * @param button the JButton to style.
     * @param backgroundColor the background color to set for the button.
     */
    private void styleButton(JButton button, Color backgroundColor) {
        button.setPreferredSize(new Dimension(160, 35));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    /**
     * Displays bookings based on the selected filter option in the dropdown menu.
     * Fetches all bookings and optionally filters for active bookings.
     */
    private void displayBookings() {
        String filterOption = (String) viewOptions.getSelectedItem();

        if ("View Booking By:".equals(filterOption)) {
            return;
        }

        Collection<Customer> customers = fbs.getAllCustomers();
        List<Booking> bookingsList = customers.stream()
                .flatMap(customer -> customer.getBookings().stream())
                .collect(Collectors.toList());

        if ("Active Bookings".equals(filterOption)) {
            bookingsList = bookingsList.stream()
                    .filter(booking -> !booking.isCancelled())
                    .collect(Collectors.toList());
        }

        String[] columns = {"Booking ID", "Customer ID", "Customer Name", "Flight ID", "Booking Date", "Price", "Status"};
        Object[][] data = new Object[bookingsList.size()][columns.length];

        for (int i = 0; i < bookingsList.size(); i++) {
            Booking booking = bookingsList.get(i);
            Customer customer = booking.getCustomer();
            data[i][0] = booking.getId();
            data[i][1] = customer.getId();
            data[i][2] = customer.getName();
            data[i][3] = booking.getFlight().getId();
            data[i][4] = booking.getBookingDate();
            data[i][5] = booking.getPrice();
            data[i][6] = booking.isCancelled() ? "Cancelled" : "Active";
        }

        updateTable(data, columns);
    }

    /**
     * Updates the table with the provided data and column names.
     * Replaces the content panel with the new table and refreshes the UI.
     * 
     * @param data the data to display in the table.
     * @param columns the column names for the table.
     */
    private void updateTable(Object[][] data, String[] columns) {
        JTable table = new JTable(new DefaultTableModel(data, columns)) {
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

        customizeTableAppearance(table);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 400));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentPanel.removeAll();
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * Customizes the appearance of the table, including cell alignment, fonts, and header styling.
     * 
     * @param table the JTable to customize.
     */
    private void customizeTableAppearance(JTable table) {
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(30);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        renderer.setVerticalAlignment(JLabel.CENTER);
        renderer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(renderer);
        }

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 18));
        tableHeader.setBackground(Color.LIGHT_GRAY);
        tableHeader.setForeground(Color.BLACK);
        tableHeader.setPreferredSize(new Dimension(tableHeader.getPreferredSize().width, 40));
    }

    /**
     * Opens a new window for adding a booking.
     */
    private void openAddBookingWindow() {
        SwingUtilities.invokeLater(() -> {
            AddBookingWindow addBookingWindow = new AddBookingWindow(fbs);
            addBookingWindow.setVisible(true);
        });
    }

    /**
     * Handles the action of canceling a booking by opening the cancel booking window.
     */
    private void handleCancelBooking() {
        SwingUtilities.invokeLater(() -> {
            CancelBookingWindow cancelBookingWindow = new CancelBookingWindow(fbs, this);
            cancelBookingWindow.setVisible(true);
        });
    }

    /**
     * Refreshes the bookings table by redisplaying the bookings based on the current filter.
     */
    public void refreshBookingsTable() {
        displayBookings();
    }

    /**
     * The main method to run the BookingWindow application.
     * 
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Flight Booking System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            try {
                FlightBookingSystem fbs = FlightBookingSystemData.load();
                BookingWindow bookingWindow = new BookingWindow(fbs);
                frame.add(bookingWindow);
            } catch (IOException | FlightBookingSystemException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error loading Flight Booking System data", "Error", JOptionPane.ERROR_MESSAGE);
            }
            frame.setVisible(true);
        });
    }
}

/**
 * Custom renderer for the JComboBox to handle the display of the placeholder and regular items.
 */
class ComboBoxRenderer extends JLabel implements ListCellRenderer<String> {
    public ComboBoxRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
        if (index == 0) {
            setForeground(Color.GRAY);
        } else {
            setForeground(Color.BLACK);
        }

        setText(value);

        if (isSelected) {
            setBackground(list.getSelectionBackground());
        } else {
            setBackground(list.getBackground());
        }

        return this;
    }
}
