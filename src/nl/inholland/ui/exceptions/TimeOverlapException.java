package nl.inholland.ui.exceptions;

public class TimeOverlapException extends Exception{
    public TimeOverlapException() {
        super("Time slot overlaps another within ± 15 min");
    }

}
