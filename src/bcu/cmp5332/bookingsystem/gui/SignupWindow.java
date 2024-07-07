package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.UserDatabase;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * SignupWindow class represents the signup interface for the Flight Booking Management System.
 * It allows new users to create an account by providing a username and password.
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
public class SignupWindow extends JFrame {
    @SuppressWarnings("unused")
    private FlightBookingSystem flightBookingSystem;
    private UserDatabase userDatabase;

    /**
     * Constructor for SignupWindow.
     * Initializes the GUI components for the signup interface.
     * 
     * @param flightBookingSystem the FlightBookingSystem instance
     */
    public SignupWindow(FlightBookingSystem flightBookingSystem) {
        this.flightBookingSystem = flightBookingSystem;
        this.userDatabase = new UserDatabase();

        // Set title and layout
        setTitle("Flight Booking Management System - Signup");
        setSize(460, 600); // Fixed size
        setResizable(false); // Disable resizing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window

        // Main panel with GridBagLayout for precise placement
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.weightx = 1.0;
        gbcMain.weighty = 1.0;
        gbcMain.fill = GridBagConstraints.BOTH;

        // Signup label panel
        JPanel signupLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        signupLabelPanel.setBackground(Color.WHITE);
        JLabel signupLabel = new JLabel("Signup");
        signupLabel.setFont(new Font("Arial", Font.BOLD, 24));
        signupLabelPanel.add(signupLabel);
        mainPanel.add(signupLabelPanel, gbcMain);

        // Logo panel
        JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        logoPanel.setBackground(Color.WHITE);
        ImageIcon originalIcon = new ImageIcon("./resources/images/logo.png");
        Image originalImage = originalIcon.getImage();
        int newWidth = 190;
        int newHeight = 190;
        Image resizedImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JLabel logoLabel = new JLabel(resizedIcon);
        logoPanel.add(logoLabel);
        gbcMain.gridy = 1; // Move logo panel below signup label
        mainPanel.add(logoPanel, gbcMain);

        // Center panel for the form
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        GridBagConstraints gbcCenter = new GridBagConstraints();
        gbcCenter.insets = new Insets(10, 10, 10, 10);
        gbcCenter.gridx = 0;
        gbcCenter.gridy = 2;
        gbcCenter.weightx = 1.0;
        gbcCenter.fill = GridBagConstraints.HORIZONTAL;

        // Username label and input
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbcCenter.gridx = 0;
        gbcCenter.gridy = 0;
        gbcCenter.anchor = GridBagConstraints.WEST; // Align label to the left
        gbcCenter.gridwidth = 2; // Span two columns
        centerPanel.add(userLabel, gbcCenter);

        JTextField userText = new JTextField(20);
        userText.setFont(new Font("Arial", Font.PLAIN, 16));
        // Add bottom border only
        userText.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY), // Bottom border
                BorderFactory.createEmptyBorder(0, 0, 5, 0))); // Empty border for spacing
        gbcCenter.gridx = 0;
        gbcCenter.gridy = 1;
        gbcCenter.anchor = GridBagConstraints.WEST; // Align text field to the left
        gbcCenter.gridwidth = 2; // Span two columns
        centerPanel.add(userText, gbcCenter);

        // Password label and input
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbcCenter.gridx = 0;
        gbcCenter.gridy = 2;
        gbcCenter.anchor = GridBagConstraints.WEST; // Align label to the left
        gbcCenter.gridwidth = 2; // Span two columns
        centerPanel.add(passwordLabel, gbcCenter);

        JPasswordField passwordText = new JPasswordField(20);
        passwordText.setFont(new Font("Arial", Font.PLAIN, 16));
        // Add bottom border only
        passwordText.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY), // Bottom border
                BorderFactory.createEmptyBorder(0, 0, 5, 0))); // Empty border for spacing
        gbcCenter.gridx = 0;
        gbcCenter.gridy = 3;
        gbcCenter.anchor = GridBagConstraints.WEST; // Align text field to the left
        gbcCenter.gridwidth = 2; // Span two columns
        centerPanel.add(passwordText, gbcCenter);

        // Signup button
        JButton signupButton = new JButton("Signup");
        signupButton.setFont(new Font("Arial", Font.BOLD, 16));
        signupButton.setBackground(new Color(66, 103, 178));
        signupButton.setForeground(Color.WHITE);
        gbcCenter.gridx = 0;
        gbcCenter.gridy = 4;
        gbcCenter.gridwidth = 2; // Span two columns
        gbcCenter.anchor = GridBagConstraints.CENTER; // Align button to the center
        gbcCenter.insets = new Insets(20, 0, 0, 0); // Top margin for spacing
        centerPanel.add(signupButton, gbcCenter);

        // Signup message
        JLabel signupMessageLabel = new JLabel("<html><u>Already have an account? Login</u></html>");
        signupMessageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        signupMessageLabel.setForeground(Color.GRAY);
        signupMessageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signupMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbcCenter.gridx = 0;
        gbcCenter.gridy = 5;
        gbcCenter.gridwidth = 2; // Span two columns
        centerPanel.add(signupMessageLabel, gbcCenter);

        // Action listener for signup button
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());

                // Validation to ensure fields are not empty
                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(SignupWindow.this, "Username and password cannot be empty.", "Signup Failed", JOptionPane.ERROR_MESSAGE);
                } else {
                    if (userDatabase.addUser(username, password)) {
                        JOptionPane.showMessageDialog(SignupWindow.this, "Signup Successful! You can now log in.");
                        dispose();  // Close the signup window
                        new LoginWindow(flightBookingSystem);  // Open the login window
                    } else {
                        JOptionPane.showMessageDialog(SignupWindow.this, "Username already exists.", "Signup Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Mouse listener for signup message label
        signupMessageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose(); // Close the signup window
                new LoginWindow(flightBookingSystem); // Open the login window
            }
        });

        // Add center panel to main panel
        gbcMain.gridy = 2;
        mainPanel.add(centerPanel, gbcMain);

        // Add main panel to frame
        add(mainPanel);

        setVisible(true);
    }

    /**
     * Main method to start the SignupWindow application.
     * Initializes the Signup window.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Example usage in a main method
        SwingUtilities.invokeLater(() -> {
            FlightBookingSystem bookingSystem = new FlightBookingSystem(); // Example flight booking system
            new SignupWindow(bookingSystem); // Create Signup window
        });
    }
}
