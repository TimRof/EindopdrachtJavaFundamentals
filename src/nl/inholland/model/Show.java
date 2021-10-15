package nl.inholland.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Show {
    private Movie movie;
    private int roomNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Ticket> openTickets = new ArrayList<>();
    private List<Ticket> soldTickets = new ArrayList<>();
    public Show(Movie movie, LocalDateTime startTime){
        this.movie = movie;
        this.startTime = startTime;
        this.endTime = startTime.plus(movie.getMovieDuration());
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

    public void setMovie(Movie movie) { this.movie = movie; }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public List<Ticket> getOpenTickets() {
        return openTickets;
    }

    public void setOpenTickets(List<Ticket> openTickets) {
        this.openTickets = openTickets;
    }
}
