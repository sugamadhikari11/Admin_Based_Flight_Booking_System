package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import java.util.ArrayList;
import java.util.List;

/**
 * Customer class represents a customer in the flight booking system.
 * <p>
 * Each customer has an ID, name, phone number, email, and a list of bookings.
 * </p>
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
public class Customer {
    private int id;
    private String name;
    private String phone;
    private String email;
    private boolean deleted; 

    private final List<Booking> bookings = new ArrayList<>();

    /**
     * Constructs a new Customer with the specified details.
     * 
     * @param id       the unique ID of the customer
     * @param name     the name of the customer
     * @param phone    the phone number of the customer
     * @param email    the email of the customer
     * @param deleted  the deletion status of the customer
     */
    public Customer(int id, String name, String phone, String email, boolean deleted) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.deleted = deleted;
    }

    /**
     * Constructs a new Customer with the specified details, with the deleted status set to false.
     * 
     * @param id       the unique ID of the customer
     * @param name     the name of the customer
     * @param phone    the phone number of the customer
     * @param email    the email of the customer
     */
    public Customer(int id, String name, String phone, String email) {
        this(id, name, phone, email, false);
    }

    // Getters and Setters
    /**
     * Returns the unique ID of the customer.
     * 
     * @return the customer ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique ID of the customer.
     * 
     * @param id the customer ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the customer.
     * 
     * @return the customer name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the customer.
     * 
     * @param name the customer name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the phone number of the customer.
     * 
     * @return the customer phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number of the customer.
     * 
     * @param phone the customer phone number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the email of the customer.
     * 
     * @return the customer email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the customer.
     * 
     * @param email the customer email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the list of bookings for the customer.
     * 
     * @return the list of bookings
     */
    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Adds a booking to the customer's list of bookings.
     * 
     * @param booking the booking to add
     * @throws FlightBookingSystemException if the booking is null
     */
    public void addBooking(Booking booking) throws FlightBookingSystemException {
        if (booking == null) {
            throw new FlightBookingSystemException("Cannot add a null booking.");
        }
        bookings.add(booking);
    }

    /**
     * Returns a short description of the customer details.
     * 
     * @return a formatted string with customer details
     */
    public String getDetailsShort() {
        return String.format("Customer ID: %d, Name: %s, Phone: %s, Email: %s", id, name, phone, email);
    }

    /**
     * Returns the booking associated with a specific flight ID.
     * 
     * @param flightId the flight ID to search for
     * @return the booking associated with the flight ID, or null if not found
     */
    public Booking getBookingByFlightId(int flightId) {
        for (Booking booking : bookings) {
            if (booking.getFlight().getId() == flightId) {
                return booking;
            }
        }
        return null;
    }

    /**
     * Returns the number of bookings the customer has.
     * 
     * @return the number of bookings
     */
    public int getNumberOfBookings() {
        return bookings.size();
    }

    /**
     * Removes a booking from the customer's list of bookings.
     * 
     * @param booking the booking to remove
     */
    public void removeBooking(Booking booking) {
        bookings.remove(booking);
    }

    /**
     * Returns whether the customer is marked as deleted.
     * 
     * @return true if the customer is deleted, false otherwise
     */
    public boolean isDeleted() {
        return deleted;
    }

    /**
     * Sets the deletion status of the customer.
     * 
     * @param deleted the deletion status to set
     */
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Returns whether any of the customer's bookings are cancelled.
     * 
     * @return true if any booking is cancelled, false otherwise
     */
    public boolean isCancelled() {
        for (Booking booking : bookings) {
            if (booking.isCancelled()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a list of active (not cancelled) bookings for the customer.
     * 
     * @return the list of active bookings
     */
    public List<Booking> getActiveBookings() {
        List<Booking> activeBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (!booking.isCancelled()) {
                activeBookings.add(booking);
            }
        }
        return activeBookings;
    }

    /**
     * Updates the customer's list of bookings to only include active (not cancelled) bookings.
     */
    public void updateNumberOfBookings() {
        List<Booking> activeBookings = getActiveBookings();
        bookings.clear();
        bookings.addAll(activeBookings);
    }
}
