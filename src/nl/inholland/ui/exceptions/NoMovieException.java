package nl.inholland.ui.exceptions;

public class NoMovieException extends Exception{
    public NoMovieException() {
        super("No movie selected");
    }
}
