package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The Flight class represents a flight in the flight booking system.
 * <p>
 * Each flight has a unique ID, flight number, origin, destination, departure date,
 * number of seats, price, list of passengers, list of bookings, and deletion status.
 * </p>
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
public class Flight {
    
    private int id;
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDate departureDate;
    private int numberOfSeats;
    private double price;
    private final Set<Customer> passengers; // Set of passengers booked on this flight
    private List<Booking> bookings = new ArrayList<>(); // List of bookings associated with this flight
    private boolean deleted; // Deletion status of the flight

    /**
     * Constructs a new Flight object with the specified details.
     * 
     * @param id             the unique ID of the flight
     * @param flightNumber   the flight number of the flight
     * @param origin         the origin of the flight
     * @param destination    the destination of the flight
     * @param departureDate  the departure date of the flight
     * @param numberOfSeats  the number of seats available on the flight
     * @param price          the price of the flight
     * @param deleted        the deletion status of the flight
     */
    public Flight(int id, String flightNumber, String origin, String destination, LocalDate departureDate, int numberOfSeats, double price, boolean deleted) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.numberOfSeats = numberOfSeats;
        this.price = price;
        this.passengers = new HashSet<>();
        this.deleted = deleted;
    }

    /**
     * Constructs a new Flight object with the specified details, with the deletion status set to false.
     * 
     * @param id             the unique ID of the flight
     * @param flightNumber   the flight number of the flight
     * @param origin         the origin of the flight
     * @param destination    the destination of the flight
     * @param departureDate  the departure date of the flight
     * @param numberOfSeats  the number of seats available on the flight
     * @param price          the price of the flight
     */
    public Flight(int id, String flightNumber, String origin, String destination, LocalDate departureDate, int numberOfSeats, double price) {
        this(id, flightNumber, origin, destination, departureDate, numberOfSeats, price, false);
    }

    // Getters and Setters

    /**
     * Returns the unique ID of the flight.
     * 
     * @return the flight ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique ID of the flight.
     * 
     * @param id the flight ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the flight number of the flight.
     * 
     * @return the flight number
     */
    public String getFlightNumber() {
        return flightNumber;
    }

    /**
     * Sets the flight number of the flight.
     * 
     * @param flightNumber the flight number to set
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    /**
     * Returns the origin of the flight.
     * 
     * @return the flight origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Sets the origin of the flight.
     * 
     * @param origin the flight origin to set
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * Returns the destination of the flight.
     * 
     * @return the flight destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the destination of the flight.
     * 
     * @param destination the flight destination to set
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Returns the departure date of the flight.
     * 
     * @return the flight departure date
     */
    public LocalDate getDepartureDate() {
        return departureDate;
    }

    /**
     * Sets the departure date of the flight.
     * 
     * @param departureDate the flight departure date to set
     */
    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    /**
     * Returns the number of seats available on the flight.
     * 
     * @return the number of seats
     */
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    /**
     * Sets the number of seats available on the flight.
     * 
     * @param numberOfSeats the number of seats to set
     */
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    /**
     * Returns the price of the flight.
     * 
     * @return the flight price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the flight.
     * 
     * @param price the flight price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns a list of passengers booked on the flight.
     * 
     * @return the list of passengers
     */
    public List<Customer> getPassengers() {
        return new ArrayList<>(passengers);
    }

    /**
     * Returns a short description of the flight details.
     * 
     * @return a formatted string with flight details
     */
    public String getDetailsShort() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return "Flight #" + id + " - " + flightNumber + " - " + origin + " to " 
                + destination + " on " + departureDate.format(dtf) + " - Seats: " + numberOfSeats + " - Price: $" + price;
    }

    /**
     * Returns a detailed description of the flight details.
     * 
     * @return a formatted string with detailed flight information
     */
    public String getDetailsLong() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        return "Flight Details:\n"
                + "Flight #: " + id + "\n"
                + "Flight Number: " + flightNumber + "\n"
                + "Origin: " + origin + "\n" 
                + "Destination: " + destination + "\n"
                + "Departure Date: " + departureDate.format(dtf) + "\n"
                + "Number of Seats: " + numberOfSeats + "\n"
                + "Price: " + price + "\n"
                + "Passengers: " + passengers.size();
    }

    /**
     * Adds a passenger to the flight.
     * 
     * @param passenger the passenger to add
     * @throws FlightBookingSystemException if the passenger is null
     */
    public void addPassenger(Customer passenger) throws FlightBookingSystemException {
        if (passenger == null) {
            throw new FlightBookingSystemException("Cannot add a null passenger.");
        }
        passengers.add(passenger);
    }

    /**
     * Removes a passenger from the flight.
     * 
     * @param customer the customer to remove as a passenger
     */
    public void removePassenger(Customer customer) {
        passengers.removeIf(passenger -> passenger.equals(customer) && !passenger.isCancelled());
    }

    /**
     * Returns whether the flight is marked as deleted.
     * 
     * @return true if the flight is deleted, false otherwise
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the deletion status of the flight.
     * 
     * @param deleted the deletion status to set
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Returns the booking associated with a specific booking ID.
     * 
     * @param bookingId the booking ID to search for
     * @return the booking associated with the ID, or null if not found
     */
    public Booking getBookingById(int bookingId) {
        for (Booking booking : bookings) {
            if (booking.getId() == bookingId) {
                return booking;
            }
        }
        return null;
    }

    /**
     * Removes a booking from the flight.
     * 
     * @param booking the booking to remove
     */
    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }

    /**
     * Adds a booking to the flight.
     * 
     * @param booking the booking to add
     */
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    /**
     * Returns an array of bookings associated with the flight (excluding cancelled bookings).
     * 
     * @return an array of bookings
     */
    public Booking[] getBookings() {
        // Return only non-cancelled bookings
        List<Booking> nonCancelledBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (!booking.isCancelled()) {
                nonCancelledBookings.add(booking);
            }
        }
        return nonCancelledBookings.toArray(new Booking[0]);
    }

    /**
     * Checks whether the flight has not departed yet based on the system date.
     * 
     * @param systemDate the current system date
     * @return true if the flight has not departed, false otherwise
     */
    public boolean hasNotDeparted(LocalDate systemDate) {
        return departureDate.isAfter(systemDate);
    }

    /**
     * Calculates the price for booking a seat on the flight based on the current date.
     * 
     * @param currentDate the current date for price calculation
     * @return the calculated price for booking a seat
     * @throws FlightBookingSystemException if no seats are available for booking
     */
    public int calculatePrice(LocalDate currentDate) throws FlightBookingSystemException {
        // Check if the flight is fully booked
        int bookedSeats = 0;
        for (Customer passenger : passengers) {
            if (!passenger.isCancelled()) {
                bookedSeats++;
            }
        }
        if (bookedSeats >= numberOfSeats) {
            return (int) price; // Return the original price
        }

        int daysLeft = (int) ChronoUnit.DAYS.between(currentDate, departureDate);
        int priceFactor;

        // Adjust the price factor based on the number of days left
        if (daysLeft >= 6) {
            priceFactor = 1; // Price factor for flights departing in more than 6 days
        } else if (daysLeft >= 1) {
            priceFactor = 2; // Price factor for flights departing in 1 to 5 days
        } else {
            priceFactor = 3; // Price factor for flights departing within 24 hours
        }

        // Calculate the final price based on the price factor
        double finalPrice = price * priceFactor;

        // Ensure that there are available seats
        int seatsLeft = numberOfSeats - bookedSeats;
        if (seatsLeft <= 0) {
            throw new FlightBookingSystemException("No seats available for booking.");
        }

        double seatsPriceIncrease = 0.0;
        if (seatsLeft <= 4) {
            seatsPriceIncrease = 50.0 * (5 - seatsLeft); // Increase price by $50 for each seat less than 5
        }

        finalPrice += seatsPriceIncrease;

        return (int) finalPrice;
    }

    /**
     * Checks whether the flight is fully booked.
     * 
     * @return true if the flight is fully booked, false otherwise
     */
    public boolean isFullyBooked() {
        int bookedSeats = 0;
        for (Customer passenger : passengers) {
            if (!passenger.isCancelled()) {
                bookedSeats++;
            }
        }
        return bookedSeats >= numberOfSeats;
    }
}
