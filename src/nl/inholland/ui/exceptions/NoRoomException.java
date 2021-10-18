package nl.inholland.ui.exceptions;

public class NoRoomException extends Exception{
    public NoRoomException() {
        super("No room selected");
    }
}
