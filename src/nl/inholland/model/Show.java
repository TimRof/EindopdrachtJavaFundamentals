package nl.inholland.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Show {
    private final Movie movie;
    private int roomNumber;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final List<Ticket> openTickets = new ArrayList<>();
    private final List<Ticket> soldTickets = new ArrayList<>();
    public Show(Movie movie, LocalDateTime startTime){
        this.movie = movie;
        this.startTime = startTime;
        this.endTime = startTime.plus(movie.getMovieDuration());
    }
    public Show(Movie movie, LocalDateTime startTime, int roomNumber){
        this.movie = movie;
        this.startTime = startTime;
        this.endTime = startTime.plus(movie.getMovieDuration());
        addRoomNumber(roomNumber);
    }

    public void addRoomNumber(int roomNumber){
        this.roomNumber = roomNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void makeTickets(int amount){
        for (int i = 0; i < amount; i++) {
            openTickets.add(new Ticket());
        }
    }
    public Show buyTickets(int amount, String name){
        if (amount <= openTickets.size())
        {
            for (int i = 0; i < amount; i++) {
                Ticket ticket = openTickets.get(0);
                openTickets.remove(0);
                ticket.setTicketHolder(name);
                soldTickets.add(ticket);
            }
        }
        return this;
    }
    public int getAvailableTickets(){
        return openTickets.size();
    }

    public Movie getMovie() { return movie; }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
