/**
 * This class represents a command to delete a flight from the flight booking system.
 * It encapsulates the logic required to mark a flight as deleted and update the stored data accordingly.
 *
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.data.FlightDataManager;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.IOException;

/**
 * The {@code DeleteFlight} class implements the {@link Command} interface, providing functionality
 * to delete a specific flight from the flight booking system. It marks the flight as deleted
 * and updates the stored data.
 *
 * @see Command
 */
public class DeleteFlight implements Command {

    /**
     * The ID of the flight to be deleted.
     */
    private final int flightId;

    /**
     * Constructs a {@code DeleteFlight} object with the specified flight ID.
     *
     * @param flightId The ID of the flight to be deleted.
     */
    public DeleteFlight(int flightId) {
        this.flightId = flightId;
    }

    /**
     * Executes the command to delete a flight from the flight booking system.
     * Marks the flight as deleted and updates the stored data.
     *
     * @param fbs The flight booking system.
     * @throws FlightBookingSystemException If an error occurs while executing the command.
     */
    @Override
    public void execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
        Flight flight = fbs.getFlightByID(flightId);
        if (flight == null) {
            throw new FlightBookingSystemException("Flight not found for ID: " + flightId);
        }

        // Delete the flight
        flight.setDeleted(true);

        // Store updated data
        FlightDataManager dataManager = new FlightDataManager();
        try {
            dataManager.storeData(fbs);
        } catch (IOException e) {
            throw new FlightBookingSystemException("Error storing flight data");
        }

        System.out.println("Flight successfully deleted for flight ID: " + flightId);
    }
}
