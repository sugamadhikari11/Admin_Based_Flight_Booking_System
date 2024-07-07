/**
 * This class represents a command to display help information about the flight booking system commands.
 * It simply prints the predefined help message to the console.
 *
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

/**
 * The {@code Help} class implements the {@link Command} interface, providing functionality
 * to display help information about the available commands in the flight booking system.
 * This command is useful for guiding users on how to interact with the system.
 *
 * @see Command
 */
public class Help implements Command {

    /**
     * Executes the command to display help information.
     * Prints the predefined help message to the console.
     *
     * @param flightBookingSystem The flight booking system instance.
     */
    @Override
    public void execute(FlightBookingSystem flightBookingSystem) {
        System.out.println(Command.HELP_MESSAGE);
    }
}
