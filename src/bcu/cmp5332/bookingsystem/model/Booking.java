package bcu.cmp5332.bookingsystem.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Booking class represents a booking in the flight booking system.
 * <p>
 * Each booking is associated with a customer, a flight, and includes details
 * such as the booking date, price, cancellation status, and fees.
 * </p>
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
public class Booking {
    private int id;
    private Customer customer;
    private Flight flight;
    private double price;
    private LocalDate bookingDate;
    private boolean cancelled;
    private double cancellationFee;
    private double rebookFee;

    /**
     * Constructs a new Booking with the specified details.
     * 
     * @param id          the unique ID of the booking
     * @param customer    the customer who made the booking
     * @param flight      the flight associated with the booking
     * @param bookingDate the date when the booking was made
     * @param price       the price of the booking
     */
    public Booking(int id, Customer customer, Flight flight, LocalDate bookingDate, double price) {
        this.id = id;
        this.customer = customer;
        this.flight = flight;
        this.bookingDate = bookingDate;
        this.price = price;
        this.cancelled = false;
    }

    // Getters
    /**
     * Returns the unique ID of the booking.
     * 
     * @return the booking ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the customer associated with the booking.
     * 
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Sets the customer associated with the booking.
     * 
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Returns the flight associated with the booking.
     * 
     * @return the flight
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * Sets the flight associated with the booking.
     * 
     * @param flight the flight to set
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /**
     * Returns the date when the booking was made.
     * 
     * @return the booking date
     */
    public LocalDate getBookingDate() {
        return bookingDate;
    }

    /**
     * Sets the date when the booking was made.
     * 
     * @param bookingDate the booking date to set
     * @throws NullPointerException if the booking date is null
     */
    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = Objects.requireNonNull(bookingDate, "Booking date cannot be null");
    }

    /**
     * Returns the price of the booking.
     * 
     * @return the booking price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns whether the booking has been cancelled.
     * 
     * @return true if the booking is cancelled, false otherwise
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Cancels the booking. Sets the booking as cancelled, removes the customer from the flight,
     * and calculates the cancellation fee as 10% of the booking price.
     */
    public void cancelBooking() {
        if (!cancelled) {
            this.cancelled = true;
            flight.removePassenger(customer);
            this.cancellationFee = price * 0.1;
        }
    }

    /**
     * Returns the cancellation fee for the booking.
     * 
     * @return the cancellation fee
     */
    public double getCancellationFee() {
        return cancellationFee;
    }

    /**
     * Sets the cancellation fee for the booking.
     * 
     * @param cancellationFee the cancellation fee to set
     */
    public void setCancellationFee(double cancellationFee) {
        this.cancellationFee = cancellationFee;
    }

    /**
     * Returns the rebooking fee for the booking.
     * 
     * @return the rebooking fee
     */
    public double getRebookFee() {
        return rebookFee;
    }

    /**
     * Sets the rebooking fee for the booking.
     * 
     * @param rebookFee the rebooking fee to set
     */
    public void setRebookFee(double rebookFee) {
        this.rebookFee = rebookFee;
    }
}
