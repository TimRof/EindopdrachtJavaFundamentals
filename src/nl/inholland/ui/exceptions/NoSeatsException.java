package nl.inholland.ui.exceptions;

public class NoSeatsException extends Exception{
    public NoSeatsException() {
        super("No. of seats is 0");
    }
}
