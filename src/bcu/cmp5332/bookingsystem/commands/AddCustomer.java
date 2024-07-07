/**
 * This class represents a command to add a new customer to the flight booking system.
 * It encapsulates the logic required to create a new customer, assign them a unique ID,
 * and persist the customer details to a file.
 *
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
package bcu.cmp5332.bookingsystem.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The {@code AddCustomer} class implements the {@link Command} interface, providing functionality
 * to add a new customer to the flight booking system. It assigns a unique ID to the new customer,
 * based on the current maximum customer ID in the system, and persists the customer details to a file.
 *
 * @see Command
 */
public class AddCustomer implements Command {
    
    /**
     * The name of the customer.
     */
    private final String name;

    /**
     * The phone number of the customer.
     */
    private final String phone;

    /**
     * The email address of the customer.
     */
    private final String email;

    /**
     * Constructs a new instance of {@code AddCustomer} with the given customer details.
     *
     * @param name      The name of the customer.
     * @param phone     The phone number of the customer.
     * @param email     The email address of the customer.
     */
    public AddCustomer(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    /**
     * Executes the command to add a new customer to the flight booking system.
     * Assigns a unique ID to the new customer, adds the customer to the system, and persists the customer details.
     *
     * @param fbs The flight booking system instance.
     * @throws FlightBookingSystemException If there is an error adding the customer or writing to the file.
     */
    @Override
    public void execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
        // Get the maximum customer ID currently in the system
        int maxId = 0;
        if (!fbs.getAllCustomers().isEmpty()) {
            maxId = fbs.getAllCustomers().stream().mapToInt(Customer::getId).max().orElse(0);
        }

        // Create a new customer object with the next available ID
        Customer customer = new Customer(++maxId, name, phone, email);
        fbs.addCustomer(customer); // Add the customer to the flight booking system
        System.out.println("Customer #" + customer.getId() + " added.");

        // Write customer data to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resources/data/customers.txt", true))) {
            writer.write(customer.getId() + "::" + customer.getName() + "::" + customer.getPhone() + "::" + customer.getEmail()+ "::");
            writer.newLine();
        } catch (IOException e) {
            throw new FlightBookingSystemException("Error writing to customers.txt: " + e.getMessage());
        }
    }
}
