package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.util.Collection;

/**
 * Implements the {@link Command} interface to list all customers in the flight booking system.
 * This command retrieves all customers and displays their short details, along with the total count of customers.
 * <p>
 * The output format for each customer's details is determined by the {@link Customer#getDetailsShort()} method.
 * </p>
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
public class ListCustomers implements Command {

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        Collection<Customer> customers = flightBookingSystem.getAllCustomers();

        System.out.println("All Customers:");
        for (Customer customer : customers) {
            System.out.println(customer.getDetailsShort());
        }
        System.out.println(customers.size() + " customer(s)");
    }
}
