package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Defines the contract for managing data within the flight booking system.
 * Implementations of this interface are responsible for loading and storing data
 * related to flights, customers, bookings, etc., from/to persistent storage.
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
public interface DataManager {
    
    /**
     * The default separator used in data files for separating different pieces of data.
     * This is defined here so that implementations of this interface can easily refer to it.
     */
    public static final String SEPARATOR = "::";
    
    /**
     * Loads data from a persistent storage into the given FlightBookingSystem instance.
     * This method is expected to populate the FlightBookingSystem with relevant data
     * such as flights, customers, bookings, etc., based on the contents of the storage.
     *
     * @param fbs The FlightBookingSystem instance to update with loaded data.
     * @throws IOException If an error occurs during file operations.
     * @throws FlightBookingSystemException If a generic exception related to the flight booking system occurs.
     */
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException;
    
    /**
     * Stores data from the given FlightBookingSystem instance into a persistent storage.
     * This method is expected to write the current state of the FlightBookingSystem,
     * including flights, customers, bookings, etc., into the specified storage.
     *
     * @param fbs The FlightBookingSystem instance whose data needs to be stored.
     * @throws IOException If an error occurs during file operations.
     * @throws URISyntaxException If a URI syntax exception occurs.
     */
    public void storeData(FlightBookingSystem fbs) throws IOException, URISyntaxException;
}
