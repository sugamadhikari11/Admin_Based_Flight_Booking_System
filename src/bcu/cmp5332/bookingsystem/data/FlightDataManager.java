package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Manages the loading and storing of flight data for the flight booking system.
 * This class reads flight data from a file and writes flight data to a file,
 * facilitating persistence of flight information across sessions.
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
public class FlightDataManager implements DataManager {
    
    private final String RESOURCE = "./resources/data/flights.txt";

     /**
     * Loads flight data from the specified resource file into the given FlightBookingSystem instance.
     * This method parses each line of the file, creates Flight objects, and adds them to the FlightBookingSystem.
     * It handles exceptions for invalid flight IDs and formats.
     *
     * @param fbs The FlightBookingSystem instance to update with loaded flight data.
     * @throws IOException If an error occurs during file reading.
     * @throws FlightBookingSystemException If a flight-related exception occurs, such as an invalid flight ID.
     */
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try (Scanner sc = new Scanner(new File(RESOURCE))) {
            int line_idx = 1;
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                try {
                    int id = Integer.parseInt(properties[0]);
                    String flightNumber = properties[1];
                    String origin = properties[2];
                    String destination = properties[3];
                    LocalDate departureDate = LocalDate.parse(properties[4]);
                    int numberOfSeats = Integer.parseInt(properties[5]);
                    double price = Double.parseDouble(properties[6]);
                    boolean deleted = "deleted".equalsIgnoreCase(properties[7]); // Check if the flight is marked as deleted

                    Flight flight = new Flight(id, flightNumber, origin, destination, departureDate, numberOfSeats, price, deleted);
                    fbs.addFlight(flight);
                } catch (NumberFormatException ex) {
                    throw new FlightBookingSystemException("Unable to parse flight id " + properties[0] + " on line " + line_idx
                        + "\nError: " + ex);
                }
                line_idx++;
            }
        }
    }
    
    /**
     * Stores flight data from the given FlightBookingSystem instance into the specified resource file.
     * This method iterates through all flights, writing each flight's details to the file.
     *
     * @param fbs The FlightBookingSystem instance whose flight data needs to be stored.
     * @throws IOException If an error occurs during file writing.
     */
    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Flight flight : fbs.getFlights()) {
                out.print(flight.getId() + SEPARATOR);
                out.print(flight.getFlightNumber() + SEPARATOR);
                out.print(flight.getOrigin() + SEPARATOR);
                out.print(flight.getDestination() + SEPARATOR);
                out.print(flight.getDepartureDate() + SEPARATOR);
                out.print(flight.getNumberOfSeats() + SEPARATOR);
                out.print(flight.getPrice() + SEPARATOR);
                out.print(flight.isDeleted() ? "deleted" : "" +  SEPARATOR); // Store 'deleted' status
                out.println();
            }
        }
    }
}
