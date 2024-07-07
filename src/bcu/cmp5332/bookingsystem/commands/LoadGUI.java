package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.gui.LoginWindow;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

/**
 * Implements the {@link Command} interface to load the graphical user interface (GUI) for the flight booking system.
 * This command initializes the login window, allowing users to log in to the system.
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 */
public class LoadGUI implements Command {

    @Override
    public void execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        new LoginWindow(flightBookingSystem);
    }
}
