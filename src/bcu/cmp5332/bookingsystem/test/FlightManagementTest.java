package bcu.cmp5332.bookingsystem.test;

import bcu.cmp5332.bookingsystem.commands.AddFlight;
import bcu.cmp5332.bookingsystem.commands.DeleteFlight;
import bcu.cmp5332.bookingsystem.commands.ListFlights;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for flight management in the flight booking system.
 * Tests include adding, viewing, and deleting flights.
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 * 
 */
public class FlightManagementTest {

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
     * Tests adding, viewing, and deleting flights.
     * 
     * @throws FlightBookingSystemException if an error occurs during command execution.
     */
    @Test
    public void testAddViewDeleteFlight() throws FlightBookingSystemException {
        // Add flights
        AddFlight addFlight1 = new AddFlight("BA123", "LHR", "JFK", LocalDate.of(2024, 7, 1), 200, 500.0);
        AddFlight addFlight2 = new AddFlight("AA456", "JFK", "LAX", LocalDate.of(2024, 8, 1), 150, 300.0);

        addFlight1.execute(flightBookingSystem);
        addFlight2.execute(flightBookingSystem);

        // View flights
        ListFlights listFlights = new ListFlights();
        listFlights.execute(flightBookingSystem);

        // Retrieve the list of all flights from the system
        List<Flight> flights = flightBookingSystem.getFlights();
        
        // Ensure there are exactly 2 flights added
        assertEquals(2, flights.size());

        // Capture and verify the output
        String output = outContent.toString();
        assertTrue(output.contains("BA123"));
        assertTrue(output.contains("AA456"));
        assertTrue(output.contains("2 flight(s)"));

        // Delete the first flight
        Flight flight = flights.get(0);
        int flightId = flight.getId();

        DeleteFlight deleteFlight = new DeleteFlight(flightId);
        deleteFlight.execute(flightBookingSystem);

        // Verify the flight is marked as deleted
        Flight deletedFlight = flightBookingSystem.getFlightByID(flightId);
        assertTrue(deletedFlight.isDeleted());

        // View flights again to ensure the deleted flight is not listed
        outContent.reset();
        listFlights.execute(flightBookingSystem);

        // Capture and verify the new output
        String newOutput = outContent.toString();
        assertTrue(newOutput.contains("AA456"));
    }

    /**
     * Tests attempting to delete a non-existent flight.
     * 
     * @throws FlightBookingSystemException if an error occurs during command execution.
     */
    @Test(expected = FlightBookingSystemException.class)
    public void testDeleteNonExistentFlight() throws FlightBookingSystemException {
        // Attempt to delete a flight with a non-existent ID
        DeleteFlight deleteFlight = new DeleteFlight(999);
        deleteFlight.execute(flightBookingSystem);
    }
}
