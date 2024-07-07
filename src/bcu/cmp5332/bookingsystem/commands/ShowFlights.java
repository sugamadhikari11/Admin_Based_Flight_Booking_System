package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.util.List;

/**
 * Implements the {@link Command} interface to show detailed information about a specific flight.
 * This command fetches and displays the flight's long details, including its passengers.
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
public class ShowFlights implements Command {

    private final int flightId;
    
    public ShowFlights(int flightId) {
        this.flightId = flightId;
    }

    @Override
    public void execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
        Flight flight = fbs.getFlightByID(flightId);
        if (flight == null) {
            throw new FlightBookingSystemException("Flight with ID " + flightId + " not found.");
        }
        
        System.out.println(flight.getDetailsLong());

        List<Customer> passengers = flight.getPassengers();
        if (passengers.isEmpty()) {
            System.out.println("No passengers booked for this flight.");
        } else {
            System.out.println("Passengers:");
            for (Customer passenger : passengers) {
                System.out.println("Name: " + passenger.getName());
                System.out.println("Phone Number: " + passenger.getPhone());
                System.out.println();
            }
        }
    }
}
