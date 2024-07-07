package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Manages the loading and storing of customer data for the flight booking system.
 * This class reads customer data from a file and writes customer data to a file,
 * facilitating persistence of customer information across sessions.
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */

public class CustomerDataManager implements DataManager {

    private final String RESOURCE = "./resources/data/customers.txt";
   
    /**
     * Loads customer data from the specified resource file into the given FlightBookingSystem instance.
     * This method parses each line of the file, creates Customer objects, and adds them to the FlightBookingSystem.
     *
     * @param fbs The FlightBookingSystem instance to update with loaded customer data.
     * @throws IOException If an error occurs during file reading.
     * @throws FlightBookingSystemException If a customer-related exception occurs.
     */
    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException, FlightBookingSystemException {
         try (Scanner sc = new Scanner(new File(RESOURCE))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] properties = line.split(SEPARATOR, -1);
                int id = Integer.parseInt(properties[0]);
                String name = properties[1];
                String phone = properties[2];
                String email = properties[3];
                boolean deleted = "deleted".equalsIgnoreCase(properties[4]); 
                Customer customer = new Customer(id, name, phone, email, deleted);
                fbs.addCustomer(customer);
            }
        }
    }

    /**
     * Stores customer data from the given FlightBookingSystem instance into the specified resource file.
     * This method iterates through all customers, writing each customer's details to the file.
     *
     * @param fbs The FlightBookingSystem instance whose data needs to be stored.
     * @throws IOException If an error occurs during file writing.
     */
    @Override
    public void storeData(FlightBookingSystem fbs) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(RESOURCE))) {
            for (Customer customer : fbs.getAllCustomers()) {
                out.print(customer.getId() + SEPARATOR);
                out.print(customer.getName() + SEPARATOR);
                out.print(customer.getPhone() + SEPARATOR);
                out.print(customer.getEmail() + SEPARATOR);
                out.print(customer.isDeleted() ? "deleted" : "" +  SEPARATOR); // Store 'deleted' status
                out.println();
            }
        }
    }
}