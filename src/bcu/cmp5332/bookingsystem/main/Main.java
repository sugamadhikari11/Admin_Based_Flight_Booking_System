package bcu.cmp5332.bookingsystem.main;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.*;
import java.net.URISyntaxException;

/**
 * Main class for the Flight Booking System application.
 * <p>
 * This class is the entry point for the application. It initializes the Flight Booking System,
 * loads data, and provides a command-line interface for interacting with the system.
 * </p>
 * <p>
 * Users can enter commands to perform various operations such as adding flights, customers, bookings,
 * and more. Entering 'help' will display a list of available commands. The program runs in a loop
 * until the user enters 'exit'.
 * </p>
 * 
 * Example usage:
 * <pre>
 * {@code
 * java -cp FlightBookingSystem.jar bcu.cmp5332.bookingsystem.main.Main
 * }
 * </pre>
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 * 
 */
public class Main {

    /**
     * Main method that serves as the entry point for the Flight Booking System application.
     *
     * @param args Command-line arguments (not used).
     * @throws IOException                  If an I/O error occurs.
     * @throws FlightBookingSystemException If there is an issue with the flight booking system operations.
     * @throws URISyntaxException           If there is a URI syntax issue when loading data.
     */
    public static void main(String[] args) throws IOException, FlightBookingSystemException, URISyntaxException {
        
        // Load the flight booking system data
        FlightBookingSystem fbs = FlightBookingSystemData.load(); 

        // Create a BufferedReader for reading user input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 

        // Display welcome message and instructions
        System.out.println("Flight Booking System");
        System.out.println("Enter 'help' to see a list of available commands.");

        // Command loop to process user input
        while (true) {
            System.out.print("> ");
            String line = br.readLine();
            
            // Exit the loop if the user enters 'exit'
            if (line.equals("exit")) {
                break;
            }

            try {
                // Parse and execute the user command
                Command command = CommandParser.parse(line, fbs);
                command.execute(fbs);
            } catch (FlightBookingSystemException ex) {
                // Display error message if an exception occurs
                System.out.println(ex.getMessage());
            }
        }

        // Store the flight booking system data before exiting
        FlightBookingSystemData.store(fbs);
        
        // Exit the application
        System.exit(0);
    }
}
