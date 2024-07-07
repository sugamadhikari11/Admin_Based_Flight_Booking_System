/**
 * This class represents a command to delete a customer from the flight booking system.
 * It encapsulates the logic required to mark a customer as deleted and update the stored data accordingly.
 *
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.data.CustomerDataManager;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.IOException;

/**
 * The {@code DeleteCustomer} class implements the {@link Command} interface, providing functionality
 * to delete a specific customer from the flight booking system. It marks the customer as deleted
 * and updates the stored data.
 *
 * @see Command
 */
public class DeleteCustomer implements Command {

    /**
     * The ID of the customer to be deleted.
     */
    private final int customerId;

    /**
     * Constructs a {@code DeleteCustomer} object with the specified customer ID.
     *
     * @param customerId The ID of the customer to be deleted.
     */
    public DeleteCustomer(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Executes the command to delete a customer from the flight booking system.
     * Marks the customer as deleted and updates the stored data.
     *
     * @param fbs The flight booking system.
     * @throws FlightBookingSystemException If an error occurs while executing the command.
     */
    @Override
    public void execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
        Customer customer = fbs.getCustomerByID(customerId);
        if (customer == null) {
            throw new FlightBookingSystemException("Customer not found for ID: " + customerId);
        }

        // Mark the customer as deleted
        customer.setDeleted(true);

        // Store updated data
        CustomerDataManager dataManager = new CustomerDataManager();
        try {
            dataManager.storeData(fbs);
        } catch (IOException e) {
            throw new FlightBookingSystemException("Error storing customer data");
        }

        System.out.println("Customer successfully deleted for customer ID: " + customerId);
    }
}
