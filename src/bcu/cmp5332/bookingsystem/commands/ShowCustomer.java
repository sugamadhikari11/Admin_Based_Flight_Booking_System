package bcu.cmp5332.bookingsystem.commands;

import java.time.format.DateTimeFormatter;
import java.util.List;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * Implements the {@link Command} interface to show detailed information about a specific customer.
 * This command fetches and displays the customer's details, including their bookings.
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
public class ShowCustomer implements Command  {
    
    private final int customerId;

    public ShowCustomer(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public void execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
        Customer customer = fbs.getCustomerByID(customerId);
        if (customer == null) {
            throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
        }

        System.out.println("Customer ID: " + customer.getId());
        System.out.println("Name: " + customer.getName());
        System.out.println("Phone: " + customer.getPhone());
        System.out.println("Email: " + customer.getEmail());

        List<Booking> bookings = customer.getActiveBookings(); // Get only active bookings
        if (bookings.isEmpty()) {
            System.out.println("This customer has not made any bookings.");
        } else {
            System.out.println("Bookings:");
            for (Booking booking : bookings) {
                Flight flight = booking.getFlight();
                System.out.println("Booking ID: " + booking.getId());
                System.out.println("Flight Number: " + flight.getFlightNumber());
                System.out.println("Origin: " + flight.getOrigin());
                System.out.println("Destination: " + flight.getDestination());
                System.out.println("Date: " + flight.getDepartureDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                System.out.println();
            }
        }
    }
}
