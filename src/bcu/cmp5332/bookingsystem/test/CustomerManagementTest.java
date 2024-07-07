package bcu.cmp5332.bookingsystem.test;

import bcu.cmp5332.bookingsystem.commands.AddCustomer;
import bcu.cmp5332.bookingsystem.commands.DeleteCustomer;
import bcu.cmp5332.bookingsystem.commands.ListCustomers;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Unit tests for customer management in the flight booking system.
 * Tests include adding, viewing, and deleting customers.
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 * 
 */
public class CustomerManagementTest {

    private FlightBookingSystem flightBookingSystem;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    /**
     * Sets up the test environment before each test case.
     * Initializes a new FlightBookingSystem instance and redirects System.out to capture output.
     */
    @Before
    public void setUp() {
        flightBookingSystem = new FlightBookingSystem();
        System.setOut(new PrintStream(outContent));
    }

    /**
     * Tests adding, viewing, and deleting customers.
     * 
     * @throws FlightBookingSystemException if an error occurs during command execution.
     */
    @Test
    public void testAddViewDeleteCustomer() throws FlightBookingSystemException {
        // Add customers
        AddCustomer addCustomer1 = new AddCustomer("John Doe", "1234567890", "john.doe@example.com");
        AddCustomer addCustomer2 = new AddCustomer("Jane Smith", "0987654321", "jane.smith@example.com");

        addCustomer1.execute(flightBookingSystem);
        addCustomer2.execute(flightBookingSystem);

        // View customers
        ListCustomers listCustomers = new ListCustomers();
        listCustomers.execute(flightBookingSystem);

        // Retrieve the list of all customers from the system
        Collection<Customer> customers = flightBookingSystem.getAllCustomers();
        List<Customer> customerList = customers.stream().collect(Collectors.toList());
        
        // Ensure there are exactly 2 customers added
        assertEquals(2, customerList.size());

        // Capture and verify the output
        String output = outContent.toString();
        assertTrue(output.contains("John Doe"));
        assertTrue(output.contains("Jane Smith"));
        assertTrue(output.contains("2 customer(s)"));

        // Delete the first customer
        Customer customer = customerList.get(0);
        int customerId = customer.getId();

        DeleteCustomer deleteCustomer = new DeleteCustomer(customerId);
        deleteCustomer.execute(flightBookingSystem);

        // Verify the customer is marked as deleted
        Customer deletedCustomer = flightBookingSystem.getCustomerByID(customerId);
        assertTrue(deletedCustomer.isDeleted());

        // View customers again to ensure the deleted customer is not listed
        outContent.reset();
        listCustomers.execute(flightBookingSystem);

        // Capture and verify the new output
        String newOutput = outContent.toString();
        assertTrue(newOutput.contains("Jane Smith"));
    }

    /**
     * Tests attempting to delete a non-existent customer.
     * 
     * @throws FlightBookingSystemException if an error occurs during command execution.
     */
    @Test(expected = FlightBookingSystemException.class)
    public void testDeleteNonExistentCustomer() throws FlightBookingSystemException {
        // Attempt to delete a customer with a non-existent ID
        DeleteCustomer deleteCustomer = new DeleteCustomer(999);
        deleteCustomer.execute(flightBookingSystem);
    }
}
