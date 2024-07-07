package bcu.cmp5332.bookingsystem.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * MainWindow class represents the main interface for the Flight Booking Management System.
 * It provides navigation to different management sections such as Home, Flights, Customers, and Bookings.
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
public class MainWindow extends JFrame implements ActionListener {

    private JPanel sidebarPanel;
    private JPanel contentPanel;
    private JPanel headerPanel;

    private FlightBookingSystem fbs;
    private JButton homeButton;
    private JButton flightButton;
    private JButton customerButton;
    private JButton bookingButton;
    private JButton logoutButton;

    /**
     * Constructor for MainWindow.
     * Initializes the GUI components and sets up the main interface.
     * 
     * @param fbs the FlightBookingSystem instance
     */
    public MainWindow(FlightBookingSystem fbs) {
        this.fbs = fbs;

        setTitle("Flight Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Open in full screen
        setResizable(false); // Disable resizing

        initialize(); // Call the initialize method to set up GUI components

        setVisible(true);
    }

    /**
     * Method to initialize the user interface components.
     */
    private void initialize() {
        // Create the main panels
        sidebarPanel = new JPanel();
        sidebarPanel.setBackground(Color.DARK_GRAY); // Example background color
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setPreferredSize(new Dimension(250, getHeight())); // Set width of sidebar

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // No border padding

        headerPanel = new JPanel(new BorderLayout()); // Use BorderLayout for centering
        headerPanel.setBackground(Color.DARK_GRAY); // Example background color
        headerPanel.setPreferredSize(new Dimension(getWidth(), 80)); // Set height of header

        // Example components in the header panel
        JLabel titleLabel = new JLabel("Admin Panel", JLabel.CENTER); // Center text horizontally
        titleLabel.setForeground(Color.WHITE); // Example text color
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Increase font size
        headerPanel.add(titleLabel, BorderLayout.CENTER); // Add titleLabel to the center

        // Add header and sidebar to the main content panel
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(sidebarPanel, BorderLayout.WEST);

        // Initialize buttons
        homeButton = createCustomButton("Home");
        flightButton = createCustomButton("Flights");
        customerButton = createCustomButton("Customers");
        bookingButton = createCustomButton("Bookings");
        logoutButton = createCustomButton("Logout");

        // Add buttons to the sidebar
        sidebarPanel.add(Box.createVerticalStrut(30)); // Add spacing between buttons
        sidebarPanel.add(homeButton);
        sidebarPanel.add(Box.createVerticalStrut(10)); // Add spacing between buttons
        sidebarPanel.add(flightButton);
        sidebarPanel.add(Box.createVerticalStrut(10)); // Add spacing between buttons
        sidebarPanel.add(customerButton);
        sidebarPanel.add(Box.createVerticalStrut(10)); // Add spacing between buttons
        sidebarPanel.add(bookingButton);
        sidebarPanel.add(Box.createVerticalStrut(40)); // Add spacing between buttons
        sidebarPanel.add(logoutButton);

        // Add Home panel as the default content
        contentPanel.add(new HomeWindow(fbs), BorderLayout.CENTER);

        // Add content panel to the frame
        getContentPane().add(contentPanel); // Use getContentPane() to ensure no extra padding

        // Pack the frame to layout components and set visible
        pack();
    }

    /**
     * Method to create a custom JButton with specified text.
     * 
     * @param text the text to display on the button
     * @return the customized JButton
     */
    private JButton createCustomButton(String text) {
        JButton button = new JButton(text);

        // Padding inside button
        int padding = 15; // Example padding inside the button
        Border paddingBorder = BorderFactory.createEmptyBorder(padding, padding, padding, padding);

        // Blue border with margin outside
        Border lineBorder = BorderFactory.createLineBorder(Color.BLUE, 2);
        Border marginBorder = BorderFactory.createEmptyBorder(10, 20, 10, 20); // Margin outside the button

        button.setBorder(BorderFactory.createCompoundBorder(marginBorder, BorderFactory.createCompoundBorder(lineBorder, paddingBorder)));

        button.setOpaque(false); // Make the button background transparent
        button.setContentAreaFilled(false); // Prevent the button from being filled with background color
        button.setForeground(Color.WHITE);

        // Add hover effect with cursor change
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Change to hand cursor
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setContentAreaFilled(false);
                button.setCursor(Cursor.getDefaultCursor()); // Revert to default cursor
            }
        });

        button.addActionListener(this); // Add ActionListener to handle button actions
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height)); // Ensure button stretches across the panel width

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle actions from buttons or other components
        JButton source = (JButton) e.getSource();

        // Clear current content
        contentPanel.removeAll();
        contentPanel.add(headerPanel, BorderLayout.NORTH);
        contentPanel.add(sidebarPanel, BorderLayout.WEST);

        if (source == homeButton) {
            // Add Home panel to content area
            contentPanel.add(new HomeWindow(fbs), BorderLayout.CENTER);
        } else if (source == flightButton) {
            // Display message or switch to flight management view
            contentPanel.add(new FlightWindow(fbs), BorderLayout.CENTER);
        } else if (source == customerButton) {
            // Display message or switch to customer management view
            contentPanel.add(new CustomerWindow(fbs));
        } else if (source == bookingButton) {
            // Display message or switch to booking management view
            contentPanel.add(new BookingWindow(fbs));
        } else if (source == logoutButton) {
            dispose(); // Close the main window
            new LoginWindow(null); // Re-open the login window
        }

        // Refresh content panel
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    /**
     * Main method to start the Flight Booking Management System application.
     * Loads the FlightBookingSystem data and initializes the main window.
     * 
     * @param args the command line arguments
     * @throws ClassNotFoundException if the class is not found
     */
    public static void main(String[] args) throws ClassNotFoundException {
        SwingUtilities.invokeLater(() -> {
            try {
                FlightBookingSystem fbs = FlightBookingSystemData.load();
                new MainWindow(fbs);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (FlightBookingSystemException e) {
                e.printStackTrace();
            }
        });
    }
}
