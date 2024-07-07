package bcu.cmp5332.bookingsystem.main;

/**
 * FlightBookingSystemException is a custom exception that extends the {@link Exception} class.
 * This exception is used to notify the user about errors or invalid commands in the Flight Booking System.
 * <p>
 * This exception can be thrown when:
 * <ul>
 *     <li>Invalid input is provided by the user.</li>
 *     <li>Commands fail to execute due to incorrect parameters.</li>
 *     <li>Any other system-specific errors occur that need to be communicated to the user.</li>
 * </ul>
 * <p>
 * Example usage:
 * <pre>
 * {@code
 * if (input.isInvalid()) {
 *     throw new FlightBookingSystemException("Invalid input provided.");
 * }
 * }
 * </pre>
 * 
 * @author Sugam Adhikari
 * @author Prasansha Tamang
 * 
 */
public class FlightBookingSystemException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new FlightBookingSystemException with the specified detail message.
     * 
     * @param message the detail message, saved for later retrieval by the {@link #getMessage()} method.
     */
    public FlightBookingSystemException(String message) {
        super(message);
    }
}
