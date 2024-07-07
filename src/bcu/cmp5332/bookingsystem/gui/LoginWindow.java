package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.UserDatabase;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * LoginWindow class represents the login interface for the Flight Booking Management System.
 * It allows users to log in using their credentials and provides a link to the signup page.
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
public class LoginWindow extends JFrame {

    private UserDatabase userDatabase;
    private FlightBookingSystem flightBookingSystem;

    private JTextField userText;
    private JPasswordField passwordText;

    /**
     * Constructor for LoginWindow.
     * Initializes the GUI components and sets up the login interface.
     * 
     * @param flightBookingSystem the FlightBookingSystem instance
     */
    public LoginWindow(FlightBookingSystem flightBookingSystem) {
        this.flightBookingSystem = flightBookingSystem;
        this.userDatabase = new UserDatabase();

        initializeUI();
    }

    /**
     * Method to initialize the user interface components.
     */
    private void initializeUI() {
        setTitle("Flight Booking Management System - Login");
        setSize(460, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel with GridBagLayout for precise placement
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.WHITE); // Set background color explicitly
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.weightx = 1.0;
        gbcMain.weighty = 1.0;
        gbcMain.fill = GridBagConstraints.BOTH;

        // Login label
        JLabel loginLabel = new JLabel("Login");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 24));
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbcMain.insets = new Insets(0, 0, 20, 0); // Top margin for spacing
        loginLabel.setForeground(Color.BLACK);
        mainPanel.add(loginLabel, gbcMain);

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

        // Reset insets for logo panel
        gbcMain.insets = new Insets(0, 0, 0, 0);
        gbcMain.gridy = 1; // Move down after login label
        mainPanel.add(logoPanel, gbcMain);

        // Center panel for the form
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);
        GridBagConstraints gbcCenter = new GridBagConstraints();
        gbcCenter.insets = new Insets(10, 10, 10, 10);
        gbcCenter.gridx = 0;
        gbcCenter.gridy = 2; // Start below logo panel
        gbcCenter.weightx = 1.0;
        gbcCenter.fill = GridBagConstraints.HORIZONTAL;

        // Username label and input
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbcCenter.gridx = 0;
        gbcCenter.gridy = 0;
        gbcCenter.anchor = GridBagConstraints.CENTER; // Align label to the center horizontally
        gbcCenter.gridwidth = 2; // Span two columns
        userLabel.setForeground(Color.BLACK);
        centerPanel.add(userLabel, gbcCenter);

        userText = new JTextField(20); // Initialize userText
        userText.setFont(new Font("Arial", Font.PLAIN, 16));
        // Set custom border for text field
        userText.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY), // Bottom border
            BorderFactory.createEmptyBorder(0, 0, 5, 0))); // Empty border for spacing
        gbcCenter.gridx = 0;
        gbcCenter.gridy = 1;
        gbcCenter.anchor = GridBagConstraints.CENTER; // Align text field to the center horizontally
        gbcCenter.gridwidth = 2; // Span two columns
        userText.setBackground(Color.WHITE);
        centerPanel.add(userText, gbcCenter);

        // Password label and input
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbcCenter.gridx = 0;
        gbcCenter.gridy = 2;
        gbcCenter.anchor = GridBagConstraints.CENTER; // Align label to the center horizontally
        gbcCenter.gridwidth = 2; // Span two columns
        passwordLabel.setForeground(Color.BLACK);
        centerPanel.add(passwordLabel, gbcCenter);

        passwordText = new JPasswordField(20); // Initialize passwordText
        passwordText.setFont(new Font("Arial", Font.PLAIN, 16));
        // Set custom border for password field
        passwordText.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY), // Bottom border
            BorderFactory.createEmptyBorder(0, 0, 5, 0))); // Empty border for spacing
        gbcCenter.gridx = 0;
        gbcCenter.gridy = 3;
        gbcCenter.anchor = GridBagConstraints.CENTER; // Align text field to the center horizontally
        gbcCenter.gridwidth = 2; // Span two columns
        passwordText.setBackground(Color.WHITE);
        centerPanel.add(passwordText, gbcCenter);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        buttonsPanel.setBackground(Color.WHITE);
        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.gridx = 0;
        gbcButtons.gridy = 3; // Start below center panel
        gbcButtons.weightx = 1.0;
        gbcButtons.fill = GridBagConstraints.HORIZONTAL;

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(66, 103, 178));
        loginButton.setForeground(Color.WHITE);
        gbcButtons.gridx = 0;
        gbcButtons.gridy = 0;
        gbcButtons.insets = new Insets(0, 0, 10, 0); // Bottom margin for spacing
        buttonsPanel.add(loginButton, gbcButtons);

        // Signup message
        JLabel signupMessageLabel = new JLabel("<html><u>Don't have an account? Signup</u></html>");
        signupMessageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        signupMessageLabel.setForeground(Color.GRAY);
        signupMessageLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signupMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbcButtons.gridx = 0;
        gbcButtons.gridy = 1;
        buttonsPanel.add(signupMessageLabel, gbcButtons);

        // Add center panel to main panel
        gbcMain.gridy = 2; // Start below login label and logo panel
        mainPanel.add(centerPanel, gbcMain);

        // Add buttons panel to main panel
        gbcMain.gridy = 3; // Start below center panel
        mainPanel.add(buttonsPanel, gbcMain);

        // Add main panel to frame
        add(mainPanel);

        // Action listener for login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });

        // Mouse listener for signup message label
        signupMessageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();  // Close the login window
                new SignupWindow(flightBookingSystem);  // Open the signup window
            }
        });

        setVisible(true);
    }

    /**
     * Method to handle the login process.
     */
    private void handleLogin() {
        String username = userText.getText();
        char[] password = passwordText.getPassword();
        if (userDatabase.authenticate(username, new String(password))) {
            dispose();  // Close login window
            SwingUtilities.invokeLater(() -> {
                MainWindow mainWindow = new MainWindow(flightBookingSystem);
                mainWindow.setVisible(true);  // Open main window
            });
        } else {
            JOptionPane.showMessageDialog(null, "Invalid username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
