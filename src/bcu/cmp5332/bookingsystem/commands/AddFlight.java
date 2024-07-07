/**
 * This class represents a command to add a new flight to the flight booking system.
 * It encapsulates the logic required to create a new flight, assign it a unique ID,
 * and persist the flight details to a file.
 *
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The {@code AddFlight} class implements the {@link Command} interface, providing functionality
 * to add a new flight to the flight booking system. It assigns a unique ID to the new flight,
 * based on the current maximum flight ID in the system, and persists the flight details to a file.
 *
 * @see Command
 */
public class AddFlight implements Command {

    /**
     * The flight number of the flight.
     */
    private final String flightNumber;

    /**
     * The origin airport of the flight.
     */
    private final String origin;

    /**
     * The destination airport of the flight.
     */
    private final String destination;

    /**
     * The departure date of the flight.
     */
    private final LocalDate departureDate;

    /**
     * The total number of seats available on the flight.
     */
    private final int numberOfSeats;

    /**
     * The price per seat for the flight.
     */
    private final double price;

    /**
     * Constructs a new instance of {@code AddFlight} with the given flight details.
     *
     * @param flightNumber The flight number of the flight.
     * @param origin       The origin airport of the flight.
     * @param destination  The destination airport of the flight.
     * @param departureDate The departure date of the flight.
     * @param numberOfSeats The total number of seats available on the flight.
     * @param price         The price per seat for the flight.
     */
    public AddFlight(String flightNumber, String origin, String destination, LocalDate departureDate, int numberOfSeats, double price) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.numberOfSeats = numberOfSeats;
        this.price = price;
    }

    /**
     * Executes the command to add a new flight to the flight booking system.
     * Assigns a unique ID to the new flight, adds the flight to the system, and persists the flight details.
     *
     * @param flightBookingSystem The flight booking system instance.
     * @throws FlightBookingSystemException If there is an error adding the flight or writing to the file.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        int maxId = 0; 
        if (flightBookingSystem.getFlights().size() > 0) {
            int lastIndex = flightBookingSystem.getFlights().size() - 1;
            maxId = flightBookingSystem.getFlights().get(lastIndex).getId();
        }
        
        Flight flight = new Flight(++maxId, flightNumber, origin, destination, departureDate, numberOfSeats, price);
        flightBookingSystem.addFlight(flight);
        System.out.println("Flight #" + flight.getId() + " added.");

        // Write the new flight data to the flights.txt file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resources/data/flights.txt", true))) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = departureDate.format(dtf);
            writer.write(flight.getId() + "::" + flight.getFlightNumber() + "::" + flight.getOrigin() + "::" + flight.getDestination() + "::" + formattedDate + "::" + flight.getNumberOfSeats() + "::" + flight.getPrice()+ "::");
            writer.newLine();
        } catch (IOException ex) {
            throw new FlightBookingSystemException("Error writing to flights.txt: " + ex.getMessage());
        }
    }
}
