package bcu.cmp5332.bookingsystem.main;

import bcu.cmp5332.bookingsystem.commands.*;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * CommandParser class parses the user input commands and executes the corresponding actions
 * in the Flight Booking Management System.
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 * 
 */
public class CommandParser {
    private static final LocalDate LocalDate = null;
    
    /**
     * Parses the user input command and returns the corresponding Command object.
     * 
     * @param line the user input command as a string
     * @param fbs the FlightBookingSystem instance
     * @return the Command object corresponding to the user input
     * @throws IOException if an I/O error occurs
     * @throws FlightBookingSystemException if an error occurs while processing the command
     */
    public static Command parse(String line, FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try {
            String[] parts = line.split(" ", 2);
            String cmd = parts[0];

            if (cmd.equals("addflight")) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Flight Number: ");
                String flightNumber = reader.readLine();
                System.out.print("Origin: ");
                String origin = reader.readLine();
                System.out.print("Destination: ");
                String destination = reader.readLine();

                LocalDate departureDate = parseDateWithAttempts(reader);
                System.out.print("Number of Seats: ");
                int numberOfSeats = Integer.parseInt(reader.readLine());
                System.out.print("Price: ");
                int price = Integer.parseInt(reader.readLine());

                return new AddFlight(flightNumber, origin, destination, departureDate, numberOfSeats, price);
            } else if (cmd.equals("addcustomer")) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Customer Name: ");
                String name = reader.readLine();
                System.out.print("Customer Phone: ");
                String phone = reader.readLine();
                System.out.print("Customer Email: ");
                String email = reader.readLine();

                return new AddCustomer(name, phone, email);

            } else if (cmd.equals("loadgui")) {
                return new LoadGUI();
            } else if (parts.length == 1) {
                if (line.equals("listflights")) {
                    return new ListFlights();
                } else if (line.equals("listcustomers")) {
                    return new ListCustomers();
                } else if (line.equals("listallbookings")) {
                    return new ListAllBookings();
                } else if (cmd.equals("addbooking")) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    System.out.print("Customer ID: ");
                    int customerId = Integer.parseInt(reader.readLine());
                    System.out.print("Flight ID: ");
                    int flightId = Integer.parseInt(reader.readLine());

                    Flight flight = fbs.getFlightByID(flightId);
                    if (flight != null && flight.getPassengers().size() < flight.getNumberOfSeats()) {
                        return new AddBooking(customerId, flightId, LocalDate);
                    } else {
                        System.out.println("The flight is full. Booking cannot be made.");
                        return null;
                    }
                } else if (cmd.equals("cancelbooking")) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    System.out.print("Customer ID: ");
                    int customerId = Integer.parseInt(reader.readLine());
                    System.out.print("Flight ID: ");
                    int flightId = Integer.parseInt(reader.readLine());

                    return new CancelBooking(customerId, flightId);
                } else if (line.equals("help")) {
                    return new Help();
                }
            } else if (parts.length == 2) {
                int id = Integer.parseInt(parts[1]);

                if (cmd.equals("showflight")) {
                    return new ShowFlights(id);
                } else if (cmd.equals("showcustomer")) {
                    return new ShowCustomer(id);
                }
            }
        } catch (NumberFormatException ex) {
            // Handle number format exceptions
        }

        throw new FlightBookingSystemException("Invalid command.");
    }
    
    /**
     * Parses a date from user input with a specified number of attempts.
     * 
     * @param br the BufferedReader instance for reading user input
     * @param attempts the number of attempts allowed for date parsing
     * @return the parsed LocalDate
     * @throws IOException if an I/O error occurs
     * @throws FlightBookingSystemException if the date parsing fails
     */
    private static LocalDate parseDateWithAttempts(BufferedReader br, int attempts) throws IOException, FlightBookingSystemException {
        if (attempts < 1) {
            throw new IllegalArgumentException("Number of attempts should be higher than 0");
        }
        while (attempts > 0) {
            attempts--;
            System.out.print("Departure Date (\"YYYY-MM-DD\" format): ");
            try {
                @SuppressWarnings("static-access")
                LocalDate departureDate = LocalDate.parse(br.readLine());
                return departureDate;
            } catch (DateTimeParseException dtpe) {
                System.out.println("Date must be in YYYY-MM-DD format. " + attempts + " attempts remaining...");
            }
        }

        throw new FlightBookingSystemException("Incorrect departure date provided. Cannot create flight.");
    }
    
    /**
     * Parses a date from user input with a default number of 3 attempts.
     * 
     * @param br the BufferedReader instance for reading user input
     * @return the parsed LocalDate
     * @throws IOException if an I/O error occurs
     * @throws FlightBookingSystemException if the date parsing fails
     */
    private static LocalDate parseDateWithAttempts(BufferedReader br) throws IOException, FlightBookingSystemException {
        return parseDateWithAttempts(br, 3);
    }
}
