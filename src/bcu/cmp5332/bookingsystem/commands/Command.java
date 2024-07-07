/**
 * Defines the structure for commands in the flight booking system.
 * Each command must implement the {@link #execute(FlightBookingSystem)} method,
 * which performs the action associated with the command.
 *
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The {@code Command} interface outlines the contract for executing various operations
 * within the flight booking system. Implementations of this interface represent different
 * commands that can be executed against the system, such as listing flights, adding a new flight,
 * showing flight details, and so forth.
 *
 * <p>The interface includes a constant {@link #HELP_MESSAGE} that provides a textual representation
 * of all available commands and their descriptions. This message can be displayed to users to guide
 * them through the usage of the system.</p>
 *
 * @see FlightBookingSystem
 */
public interface Command {

    /**
     * A string representing the help message for all available commands.
     * This message is intended to be printed to the console to assist users in understanding how to interact with the system.
     */
    public static final String HELP_MESSAGE = "Commands:\n"
        + "\tlistflights                               print all flights\n"
        + "\tlistcustomers                             print all customers\n"
        + "\taddflight                                 add a new flight\n"
        + "\taddcustomer                               add a new customer\n"
        + "\tshowflight [flight id]                    show flight details\n"
        + "\tshowcustomer [customer id]                show customer details\n"
        + "\tlistallbooking                            show all bookings\n"
        + "\taddbooking                                add a new booking\n"
        + "\tcancelbooking                             cancel a booking\n"
        + "\tloadgui                                   loads the GUI version of the app\n"
        + "\thelp                                      prints this help message\n"
        + "\texit                                      exits the program";

    /**
     * Executes the command against the given flight booking system instance.
     * Commands may modify the state of the system or perform actions such as displaying information.
     *
     * @param flightBookingSystem The flight booking system instance to operate on.
     * @throws FlightBookingSystemException If the execution of the command leads to an exception.
     */
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException;
}
