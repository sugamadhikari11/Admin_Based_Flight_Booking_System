package bcu.cmp5332.bookingsystem.gui;

import javax.swing.*;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * HomeWindow class represents the main panel for the Flight Booking Management System.
 * It provides a user interface with navigation cards for various features.
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
public class HomeWindow extends JPanel {

    private BufferedImage backgroundImage;
    private FlightBookingSystem fbs; 

    /**
     * Constructor for HomeWindow.
     * Initializes the GUI components and loads the background image.
     * 
     * @param fbs the FlightBookingSystem instance
     */
    public HomeWindow(FlightBookingSystem fbs) {
        this.fbs = fbs;
        try {
            // Load the background image from resources
            backgroundImage = ImageIO.read(new File("resources/images/background.jpg"));
        } catch (IOException ex) {
            // Handle exception if image loading fails
            ex.printStackTrace();
        }

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical stacking
        setBackground(Color.WHITE); // Set a white background (optional if using a background image)

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome to the Flight Booking Management System");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setForeground(Color.BLACK);

        // Logo label
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("resources/images/logo.png");
        if (logoIcon.getIconWidth() == -1) {
            logoLabel.setText("Logo not found");
        } else {
            logoLabel.setIcon(logoIcon);
        }
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add welcome and logo
        add(Box.createVerticalStrut(40)); // Add spacing at the top
        add(welcomeLabel);
        add(Box.createVerticalStrut(30)); // Add spacing between welcome and logo
        add(logoLabel);

        // Features label
        JLabel featuresLabel = new JLabel("Features shortcuts:");
        featuresLabel.setFont(new Font("Arial", Font.BOLD, 18));
        featuresLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        featuresLabel.setHorizontalAlignment(SwingConstants.CENTER);
        featuresLabel.setForeground(Color.BLACK);
        add(featuresLabel);

        // Cards panel
        JPanel cardsPanel = new JPanel();
        cardsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Flow layout for cards
        cardsPanel.setBackground(new Color(0, 0, 0, 0)); // Set transparent background
        cardsPanel.setOpaque(false); // Make panel transparent
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add some padding

        // Adding cards to the panel
        cardsPanel.add(createCard("Flights", new String[]{"Click to view, add and delete flights.", "Features Available:", "- List all flights", "- Delete Flight", "- View flight details"}, ae -> displayFlights()));
        cardsPanel.add(createCard("Bookings", new String[]{"Click to view, add and cancel bookings", "Features Available:", "- List all bookings", "- View booking details"}, ae -> displayBookings()));
        cardsPanel.add(createCard("Customers", new String[]{"Click to view, add and delete customers", "Features Available:", "- List all customers", "- View customer details", "- Delete customer"}, ae -> displayCustomers()));
     
        // Add cards panel
        add(cardsPanel);
        add(Box.createVerticalGlue()); // Add glue to push components to the top
    }

    /**
     * Custom paint method to draw the background image.
     * 
     * @param g the Graphics object
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    /**
     * Method to create a styled card with title and guidelines.
     * 
     * @param title the title of the card
     * @param features the list of features to be displayed on the card
     * @param action the action to be performed when the card is clicked
     * @return the created JPanel representing the card
     */
    private JPanel createCard(String title, String[] features, ActionListener action) {
        JPanel cardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                int width = getWidth();
                int height = getHeight();
                int arc = 20; // Adjust the arc radius for roundness
                g2.setColor(new Color(173, 216, 230)); // Light blue color
                g2.fillRoundRect(0, 0, width, height, arc, arc);
                g2.dispose();
            }
        };

        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setPreferredSize(new Dimension(250, 180)); // Set preferred size for the card

        // Add padding to the panel
        cardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the card

        // Title label for the card
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Set font for the title
        titleLabel.setForeground(Color.BLACK); // Set title text color to black
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align the text horizontally
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center-align the text

        cardPanel.add(titleLabel);

        // Add features available
        for (String feature : features) {
            JTextArea featureArea = new JTextArea(feature);
            featureArea.setFont(new Font("Arial", Font.PLAIN, 12)); // Set font for features
            featureArea.setForeground(Color.BLACK); // Set feature text color to black
            featureArea.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align the text
            featureArea.setLineWrap(true); // Enable line wrapping
            featureArea.setWrapStyleWord(true); // Wrap at word boundaries
            featureArea.setOpaque(false); // Make JTextArea background transparent
            featureArea.setEditable(false); // Make JTextArea non-editable
            featureArea.setMaximumSize(new Dimension(200, Integer.MAX_VALUE)); // Limit the width of JTextArea

            cardPanel.add(Box.createVerticalStrut(5)); // Add spacing between features
            cardPanel.add(featureArea);
        }

        // Add mouse listener for click and hover effects
        cardPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                action.actionPerformed(new ActionEvent(cardPanel, ActionEvent.ACTION_PERFORMED, null));
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cardPanel.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Change to hand cursor on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                cardPanel.setCursor(Cursor.getDefaultCursor()); // Revert to default cursor
            }
        });

        return cardPanel;
    }

    // Placeholder methods for card actions

    /**
     * Displays the Flight Management window.
     */
    private void displayFlights() {
        SwingUtilities.invokeLater(() -> {
            JFrame flightFrame = new JFrame("Flight Management");
            flightFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            flightFrame.setSize(900, 600);
            flightFrame.setLocationRelativeTo(null);

            FlightWindow flightWindow = new FlightWindow(fbs);
            flightFrame.add(flightWindow);

            flightFrame.setVisible(true);
        });
    }

    /**
     * Displays the Booking Management window.
     */
    private void displayBookings() {
        SwingUtilities.invokeLater(() -> {
            JFrame bookingFrame = new JFrame("Booking Management");
            bookingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            bookingFrame.setSize(900, 600);
            bookingFrame.setLocationRelativeTo(null);

            BookingWindow bookingWindow = new BookingWindow(fbs);
            bookingFrame.add(bookingWindow);

            bookingFrame.setVisible(true);
        });
    }

    /**
     * Displays the Customer Management window.
     */
    private void displayCustomers() {
        SwingUtilities.invokeLater(() -> {
            JFrame customerFrame = new JFrame("Customer Management");
            customerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            customerFrame.setSize(900, 600);
            customerFrame.setLocationRelativeTo(null);

            CustomerWindow customerWindow = new CustomerWindow(fbs);
            customerFrame.add(customerWindow);

            customerFrame.setVisible(true);
        });
    }

    /**
     * Main method to run the HomeWindow GUI.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Flight Booking System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            try {
                FlightBookingSystem fbs = FlightBookingSystemData.load();
                frame.add(new HomeWindow(fbs));
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (FlightBookingSystemException e) {
                e.printStackTrace();
            }
            frame.setVisible(true);
        });
    }
}
