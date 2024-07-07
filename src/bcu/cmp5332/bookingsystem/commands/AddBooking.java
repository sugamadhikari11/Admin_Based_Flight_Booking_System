/**
 * This class represents a command to add a new booking to the flight booking system.
 * It encapsulates the logic required to create a new booking, validate the request,
 * and persist the booking details to a file.
 *
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.BufferedWriter;
import java.io.FileWriter; 
import java.io.IOException;
import java.time.LocalDate; 

/**
 * The {@code AddBooking} class implements the {@link Command} interface, providing functionality
 * to add a new booking to the flight booking system. It validates the booking request,
 * including checking if the booking is within the allowed time frame, if the customer and flight exist,
 * if the flight has not yet departed, and if there are available seats on the flight.
 * Upon successful validation, it creates a new booking object, adds it to the customer's bookings,
 * and persists the booking details to a file. 
 *
 * @see Command
 */
public class AddBooking implements Command {

    /**
     * The ID of the customer who is making the booking.
     */
    private final int customerId;

    /**
     * The ID of the flight being booked.
     */
    private final int flightId;

    /**
     * Constructs a new instance of {@code AddBooking} with the given customer ID, flight ID, and booking date.
     *
     * @param customerId The ID of the customer making the booking.
     * @param flightId   The ID of the flight being booked.
     * @param localDate  The date of the booking.
     */
    public AddBooking(int customerId, int flightId, LocalDate localDate) {
        this.customerId = customerId;
        this.flightId = flightId;
    }

    /**
     * Executes the command to add a new booking to the flight booking system.
     * Validates the booking request, creates a new booking object, and persists the booking details.
     *
     * @param fbs The flight booking system instance.
     * @throws FlightBookingSystemException If the booking request fails validation.
     */
    @Override
    public void execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
        LocalDate today = LocalDate.now();
        LocalDate twoYearsFromToday = today.plusYears(2);

        if (today.isAfter(twoYearsFromToday)) {
            throw new FlightBookingSystemException("Bookings more than 2 years in advance are not allowed.");
        }

        Customer customer = fbs.getCustomerByID(customerId);
        if (customer == null) {
            throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
        }

        Flight flight = fbs.getFlightByID(flightId);
        if (flight == null) {
            throw new FlightBookingSystemException("Flight with ID " + flightId + " not found.");
        }

        if (!flight.hasNotDeparted(today)) {
            throw new FlightBookingSystemException("Cannot book a flight that has already departed.");
        }

        if (flight.getPassengers().size() >= flight.getNumberOfSeats()) {
            throw new FlightBookingSystemException("The flight is full. Booking cannot be made.");
        }

        int price = flight.calculatePrice(today);

        int bookingId = fbs.generateBookingId();
        LocalDate bookingDate = LocalDate.now();
        Booking booking = new Booking(bookingId, customer, flight, bookingDate, price);
        customer.addBooking(booking);
        flight.addPassenger(customer);

        if (!booking.isCancelled()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("resources/data/bookings.txt", true))) {
                writer.write(booking.getId() + "::" + customer.getId() + "::" + flight.getId() + "::" + booking.getBookingDate() + "::" + booking.getPrice()+"::");
                writer.newLine();
            } catch (IOException e) {
                throw new FlightBookingSystemException("Error writing to bookings.txt: " + e.getMessage());
            }
        }

        System.out.println("Booking was issued successfully to the customer.");
    }
}
