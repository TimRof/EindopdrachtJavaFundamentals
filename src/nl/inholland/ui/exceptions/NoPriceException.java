package nl.inholland.ui.exceptions;

public class NoPriceException extends Exception{
    public NoPriceException() {
        super("No price entered");
    }

}
