package nl.inholland.ui.exceptions;

public class NoDurationException extends Exception{
    public NoDurationException() {
        super("No duration entered");
    }
}
