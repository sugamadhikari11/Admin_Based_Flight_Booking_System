/**
 * This class represents a command to cancel a booking in the flight booking system.
 * It encapsulates the logic required to find a specific booking associated with a customer and a flight,
 * cancel the booking, and update the stored data accordingly.
 *
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import java.io.IOException;

import bcu.cmp5332.bookingsystem.data.BookingDataManager;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The {@code CancelBooking} class implements the {@link Command} interface, providing functionality
 * to cancel a specific booking in the flight booking system. It searches for the booking associated
 * with a given customer ID and flight ID, cancels the booking, and updates the stored data.
 *
 * @see Command
 */
public class CancelBooking implements Command {

    /**
     * The ID of the customer whose booking is to be canceled.
     */
    private final int customerId;

    /**
     * The ID of the flight associated with the booking to be canceled.
     */
    private final int flightId;

    /**
     * Constructs a new instance of {@code CancelBooking} with the given customer ID and flight ID.
     *
     * @param customerId The ID of the customer whose booking is to be canceled.
     * @param flightId   The ID of the flight associated with the booking to be canceled.
     */
    public CancelBooking(int customerId, int flightId) {
        this.customerId = customerId;
        this.flightId = flightId;
    }

    /**
     * Executes the command to cancel a booking in the flight booking system.
     * Finds the booking associated with the given customer ID and flight ID, cancels it, and updates the stored data.
     *
     * @param fbs The flight booking system instance.
     * @throws FlightBookingSystemException If the customer, flight, or booking is not found.
     */
    @Override
    public void execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
        Customer customer = fbs.getCustomerByID(customerId);
        if (customer == null) {
            throw new FlightBookingSystemException("Customer not found for ID: " + customerId);
        }

        Flight flight = fbs.getFlightByID(flightId);
        if (flight == null) {
            throw new FlightBookingSystemException("Flight not found for ID: " + flightId);
        }

        Booking booking = null;
        for (Booking b : customer.getBookings()) {
            if (b.getFlight().getId() == flightId) {
                booking = b;
                break;
            }
        }

        if (booking == null) {
            throw new FlightBookingSystemException("No booking found for customer ID: " + customerId + " and flight ID: " + flightId);
        }

        // Cancel the booking
        booking.cancelBooking();

        // Store updated data
        BookingDataManager dataManager = new BookingDataManager();
        try {
            dataManager.storeData(fbs);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Booking successfully canceled for customer ID: " + customerId + " and flight ID: " + flightId);
    }
}
