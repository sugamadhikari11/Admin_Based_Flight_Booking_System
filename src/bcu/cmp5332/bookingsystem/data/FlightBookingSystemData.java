package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Centralizes the management of data for the flight booking system.
 * This class utilizes instances of DataManager implementations to load and store
 * data related to flights, customers, and bookings from/to persistent storage.
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
public class FlightBookingSystemData {
    
    private static final List<DataManager> dataManagers = new ArrayList<>();
    
    // runs only once when the object gets loaded to memory
    static {
        dataManagers.add(new FlightDataManager());
        
        /* Uncomment the two lines below when the implementation of their 
        loadData() and storeData() methods is complete */
         dataManagers.add(new CustomerDataManager());
         dataManagers.add(new BookingDataManager());
    }
    
    /**
     * Loads the flight booking system data from persistent storage into a new FlightBookingSystem instance.
     * This method iterates over all registered DataManager instances, calling their loadData() method
     * to populate the FlightBookingSystem with the necessary data.
     *
     * @return A populated FlightBookingSystem instance representing the current state of the system.
     * @throws FlightBookingSystemException If an exception occurs during the loading process.
     * @throws IOException If an error occurs during file operations.
     */
    public static FlightBookingSystem load() throws FlightBookingSystemException, IOException {
        FlightBookingSystem fbs = new FlightBookingSystem();
        for (DataManager dm : dataManagers) {
            dm.loadData(fbs);
        }
        return fbs;
    }

    /**
     * Stores the current state of the FlightBookingSystem into persistent storage.
     * This method iterates over all registered DataManager instances, calling their storeData() method
     * to persist the current state of the system.
     *
     * @param fbs The FlightBookingSystem instance whose data needs to be stored.
     * @throws IOException If an error occurs during file operations.
     * @throws URISyntaxException If a URI syntax exception occurs.
     */
    public static void store(FlightBookingSystem fbs) throws IOException, URISyntaxException {
        for (DataManager dm: dataManagers ){
            dm.storeData(fbs);
        }
    }
}
