/**
 * This class represents a command to list all bookings in the flight booking system.
 * It iterates over all customers and their bookings, printing detailed information about each booking.
 *
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.util.Collection;

/**
 * The {@code ListAllBookings} class implements the {@link Command} interface, providing functionality
 * to list all bookings in the flight booking system. It iterates over all customers and their bookings,
 * printing detailed information about each booking.
 *
 * @see Command
 */
public class ListAllBookings implements Command {

    /**
     * Executes the command to list all bookings in the flight booking system.
     * Iterates over all customers and their bookings, printing detailed information about each booking.
     *
     * @param flightBookingSystem The flight booking system instance.
     * @throws FlightBookingSystemException If an error occurs during the execution of the command.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        Collection<Customer> customers = flightBookingSystem.getAllCustomers();
        
        for (Customer customer : customers) {
            for (Booking booking : customer.getBookings()) {
                System.out.println("Booking ID: " + booking.getId());
                System.out.println("Customer ID: " + customer.getId());
                System.out.println("Flight ID: " + booking.getFlight().getId());
                System.out.println("Booking Date: " + booking.getBookingDate());
                System.out.println("Price: " + booking.getPrice());
                System.out.println("Status: " + (booking.isCancelled()? "Cancelled" : "Active"));
                System.out.println("----------------------------------");
            }
        }
    }
}
