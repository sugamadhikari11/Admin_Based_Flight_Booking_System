package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements the {@link Command} interface to list all flights in the flight booking system.
 * This command retrieves all flights and displays their short details, along with the total count of flights.
 * <p>
 * Optionally, it can filter flights based on departure dates that are after a given date.
 * </p>
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
public class ListFlights implements Command {

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        List<Flight> flights = flightBookingSystem.getFlights();
        for (Flight flight : flights) {
            System.out.println(flight.getDetailsShort());
        }
        System.out.println(flights.size() + " flight(s)");
    }

    /**
     * Filters flights based on whether their departure date is after a given date.
     * <p>
     * This method is currently unused but demonstrates how filtering could be implemented.
     * </p>
     * 
     * @param flights A list of flights to filter.
     * @param today The current date against which to compare flight departure dates.
     * @return A filtered list of flights departing after the given date.
     */
    @SuppressWarnings("unused")
    private List<Flight> filterFlights(List<Flight> flights, LocalDate today) {
        return flights.stream()
               .filter(flight -> flight.getDepartureDate().isAfter(today))
               .collect(Collectors.toList());
    }
}
