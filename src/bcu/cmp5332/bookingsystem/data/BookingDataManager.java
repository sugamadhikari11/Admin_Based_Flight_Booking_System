package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.*;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Manages the loading and storing of booking data for the flight booking system.
 * This class reads booking data from a file and writes booking data to a file,
 * facilitating persistence of booking information across sessions.
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
public class BookingDataManager implements DataManager {

    /** The path to the booking data file. */
    private final static String RESOURCE = "./resources/data/bookings.txt";

    /**
     * Loads booking data from the specified resource file into the given FlightBookingSystem instance.
     * This method parses each line of the file, creates Booking objects, and associates them with Customers and Flights.
     * It also updates the maximum booking ID in the FlightBookingSystem.
     *
     * @param fbs The FlightBookingSystem instance to update with loaded booking data.
     * @throws IOException If an error occurs during file reading.
     * @throws FlightBookingSystemException If a booking-related exception occurs.
     */
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
        try (Scanner scanner = new Scanner(new File(RESOURCE))) {
            int maxBookingId = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] properties = line.split(SEPARATOR);
                if (properties.length >= 5) {
                    int id = Integer.parseInt(properties[0]);
                    int customerId = Integer.parseInt(properties[1]);
                    int flightId = Integer.parseInt(properties[2]);
                    LocalDate date = LocalDate.parse(properties[3]);
                    double price = Double.parseDouble(properties[4]); 
                    boolean cancelled = properties.length > 5 && properties[5].equals("cancelled");
                    Customer customer = fbs.getCustomerByID(customerId);
                    Flight flight = fbs.getFlightByID(flightId);
                    if (customer != null && flight != null) {
                        Booking booking = new Booking(id, customer, flight, date, price);
                        if (cancelled) {
                            booking.cancelBooking();
                        }
                        customer.addBooking(booking);
                        flight.addPassenger(customer);
                        if (id > maxBookingId) {
                            maxBookingId = id;
                        }
                    }
                }
            }
            fbs.setMaxBookingId(maxBookingId);
        }
    }
    /**
     * Stores booking data from the given FlightBookingSystem instance into the specified resource file.
     * This method iterates through all customers and their bookings, writing each booking's details to the file.
     *
     * @param fbs The FlightBookingSystem instance whose data needs to be stored.
     * @throws IOException If an error occurs during file writing.
     */

    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Customer customer : fbs.getAllCustomers()) {
                for (Booking booking : customer.getBookings()) {
                    writer.print(booking.getId() + SEPARATOR
                            + customer.getId() + SEPARATOR
                            + booking.getFlight().getId() + SEPARATOR
                            + booking.getBookingDate() + SEPARATOR
                            + booking.getPrice());
                    
                    if (booking.isCancelled()) {
                        writer.print(SEPARATOR + "cancelled");
                    }
                    
                    writer.println();
                }
            }
        }
    }
}
