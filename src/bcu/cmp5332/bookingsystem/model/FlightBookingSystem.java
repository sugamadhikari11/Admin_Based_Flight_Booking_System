package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.time.LocalDate;
import java.util.*;

/**
 * The FlightBookingSystem class manages flights, customers, and bookings in the flight booking system.
 * <p>
 * It provides methods to add and retrieve flights, customers, and bookings, as well as operations
 * to delete flights and customers, update bookings, and retrieve bookings by various criteria.
 * </p>
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
public class FlightBookingSystem {
    
    private final LocalDate systemDate = LocalDate.parse("2020-11-11");

    private final Map<Integer, Customer> customers = new TreeMap<>();
    private final Map<Integer, Flight> flights = new TreeMap<>();
    private final Map<Integer, Booking> bookings = new TreeMap<>();
    private int maxBookingId;

    /**
     * Generates and returns a new unique booking ID.
     * 
     * @return the generated booking ID
     */
    public int generateBookingId() {
        return ++maxBookingId;
    }

    /**
     * Sets the maximum booking ID to the specified value.
     * 
     * @param maxBookingId the maximum booking ID to set
     */
    public void setMaxBookingId(int maxBookingId) {
        this.maxBookingId = maxBookingId;
    }

    /**
     * Returns the maximum booking ID.
     * 
     * @return the maximum booking ID
     */
    public int getMaxBookingId() {
        return maxBookingId;
    }

    /**
     * Returns the current system date.
     * 
     * @return the system date
     */
    public LocalDate getSystemDate() {
        return systemDate;
    }

    /**
     * Returns a list of all flights in the system.
     * 
     * @return an unmodifiable list of flights
     */
    public List<Flight> getFlights() {
        List<Flight> out = new ArrayList<>(flights.values());
        return Collections.unmodifiableList(out);
    }

    /**
     * Returns a collection of all customers in the system.
     * 
     * @return a collection of customers
     */
    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }

    /**
     * Returns a list of all bookings in the system.
     * 
     * @return an unmodifiable list of bookings
     */
    public List<Booking> getBookings() {
        List<Booking> out = new ArrayList<>(bookings.values());
        out.removeIf(Objects::isNull);
        return Collections.unmodifiableList(out);
    }

    /**
     * Retrieves a flight by its ID.
     * 
     * @param id the ID of the flight to retrieve
     * @return the flight with the specified ID
     * @throws FlightBookingSystemException if no flight with the specified ID exists
     */
    public Flight getFlightByID(int id) throws FlightBookingSystemException {
        if (!flights.containsKey(id)) {
            throw new FlightBookingSystemException("There is no flight with that ID.");
        }
        return flights.get(id);
    }

    /**
     * Retrieves a customer by their ID.
     * 
     * @param id the ID of the customer to retrieve
     * @return the customer with the specified ID
     * @throws FlightBookingSystemException if no customer with the specified ID exists
     */
    public Customer getCustomerByID(int id) throws FlightBookingSystemException {
        if (!customers.containsKey(id)) {
            throw new FlightBookingSystemException("There is no customer with that ID.");
        }
        return customers.get(id);
    }

    /**
     * Adds a new flight to the system.
     * 
     * @param flight the flight to add
     * @throws FlightBookingSystemException if a flight with the same ID, or with the same flight number
     *         and departure date already exists in the system
     */
    public void addFlight(Flight flight) throws FlightBookingSystemException {
        if (flights.containsKey(flight.getId())) {
            throw new IllegalArgumentException("Duplicate flight ID.");
        }
        for (Flight existing : flights.values()) {
            if (existing.getFlightNumber().equals(flight.getFlightNumber())
                    && existing.getDepartureDate().isEqual(flight.getDepartureDate())) {
                throw new FlightBookingSystemException("There is a flight with the same "
                        + "number and departure date in the system");
            }
        }
        flights.put(flight.getId(), flight);
    }

    /**
     * Adds a new customer to the system.
     * 
     * @param customer the customer to add
     * @throws FlightBookingSystemException if a customer with the same ID already exists in the system
     */
    public void addCustomer(Customer customer) throws FlightBookingSystemException {
        if (customers.containsKey(customer.getId())) {
            throw new IllegalArgumentException("Duplicate customer ID.");
        }
        customers.put(customer.getId(), customer);
    }

    /**
     * Retrieves a booking by its ID.
     * 
     * @param id the ID of the booking to retrieve
     * @return the booking with the specified ID
     * @throws FlightBookingSystemException if no booking with the specified ID exists
     */
    public Booking getBookingByID(int id) throws FlightBookingSystemException {
        if (!bookings.containsKey(id)) {
            throw new FlightBookingSystemException("There is no booking with that ID.");
        }
        return bookings.get(id);
    }

    /**
     * Adds a new booking to the system.
     * 
     * @param booking the booking to add
     * @throws FlightBookingSystemException if the booking refers to a null customer or flight,
     *         if a booking with the same ID already exists in the system, or if there are other constraints
     */
    public void addBooking(Booking booking) throws FlightBookingSystemException {
        Customer customer = booking.getCustomer();
        Flight flight = booking.getFlight();

        if (customer == null || flight == null) {
            throw new FlightBookingSystemException("Customer or Flight not found.");
        }

        if (bookings.containsKey(booking.getId())) {
            throw new IllegalArgumentException("Duplicate booking ID.");
        }

        bookings.put(booking.getId(), booking);
    }

    /**
     * Retrieves a list of bookings for a specific customer and flight.
     * 
     * @param customer the customer to retrieve bookings for
     * @param flight the flight to retrieve bookings for
     * @return a list of bookings matching the customer and flight
     */
    public List<Booking> getBookingsByCustomerAndFlight(Customer customer, Flight flight) {
        List<Booking> result = new ArrayList<>();
        
        for (Booking booking : bookings.values()) {
            if (booking.getCustomer().equals(customer) && booking.getFlight().equals(flight)) {
                result.add(booking);
            }
        }
        return result;
    }

    /**
     * Deletes a flight from the system, including all associated bookings.
     * 
     * @param flightId the ID of the flight to delete
     * @throws FlightBookingSystemException if the flight with the specified ID does not exist
     */
    public void deleteFlight(int flightId) throws FlightBookingSystemException {
        Flight flight = getFlightByID(flightId);
        if (flight == null) {
            throw new FlightBookingSystemException("Flight not found.");
        }
        List<Booking> flightBookings = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            if (booking.getFlight().equals(flight)) {
                flightBookings.add(booking);
            }
        }
        for (Booking booking : flightBookings) {
            bookings.remove(booking.getId());
        }
        flights.remove(flightId);
    }

    /**
     * Deletes a customer from the system, including all associated bookings.
     * 
     * @param customerId the ID of the customer to delete
     * @throws FlightBookingSystemException if the customer with the specified ID does not exist
     */
    public void deleteCustomer(int customerId) throws FlightBookingSystemException {
        Customer customer = getCustomerByID(customerId);
        if (customer == null) {
            throw new FlightBookingSystemException("Customer not found.");
        }
        List<Booking> customerBookings = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            if (booking.getCustomer().equals(customer)) {
                customerBookings.add(booking);
            }
        }
        for (Booking booking : customerBookings) {
            bookings.remove(booking.getId());
        }
        customers.remove(customerId);
    }

    /**
     * Retrieves a booking by customer and flight IDs.
     * 
     * @param customerId the ID of the customer
     * @param flightId the ID of the flight
     * @return the booking matching the customer and flight IDs, or null if not found
     */
    public Booking getBookingByCustomerAndFlightId(int customerId, int flightId) {
        for (Booking booking : bookings.values()) {
            if (booking.getCustomer().getId() == customerId && booking.getFlight().getId() == flightId) {
                return booking;
            }
        }
        return null;
    }

    /**
     * Retrieves a list of bookings associated with a specific flight.
     * 
     * @param flight the flight to retrieve bookings for
     * @return a list of bookings associated with the flight
     */
    public List<Booking> getBookingsByFlight(Flight flight) {
        List<Booking> bookingsByFlight = new ArrayList<>();
        for (Booking booking : bookings.values()) {
            if (booking.getFlight().getId() == flight.getId()) {
                bookingsByFlight.add(booking);
            }
        }
        return bookingsByFlight;
    }

    /**
     * Updates the flight associated with a booking.
     * 
     * @param bookingId the ID of the booking to update
     * @param newFlightId the ID of the new flight to associate with the booking
     * @throws FlightBookingSystemException if the booking is not found, is cancelled,
     *         or if the new flight ID is invalid
     */
    public void updateBookingFlight(int bookingId, int newFlightId) throws FlightBookingSystemException {
        Booking booking = getBookingByID(bookingId);
        if (booking == null) {
            throw new FlightBookingSystemException("Booking not found.");
        }

        if (booking.isCancelled()) {
            throw new FlightBookingSystemException("Cannot update a canceled booking.");
        }

        Flight newFlight = getFlightByID(newFlightId);
        if (newFlight == null) {
            throw new FlightBookingSystemException("Invalid new flight ID.");
        }

        booking.setFlight(newFlight);
    }
}
