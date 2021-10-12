package nl.inholland.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Show {
    private Movie movie;
    private int roomNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<Ticket> tickets = new ArrayList<>();
    public Show(Movie movie, int roomNumber, LocalDateTime startTime){
        this.movie = movie;
        this.roomNumber = roomNumber;
        this.startTime = startTime;
        this.endTime = startTime.plus(movie.getMovieDuration());
        makeTickets();
    }
    private void makeTickets(){
        for (int i = 0; i < 200; i++) {
            tickets.add(new Ticket());
        }
    }
    public int getAvailableSeats(){
        int available = 0;
        for (Ticket ticket:tickets) {
            if (ticket.getTicketStatus().equals(TicketStatus.Available))
                available++;
        }
        return available;
    }

    public Movie getMovie() { return movie; }

    public void setMovie(Movie movie) { this.movie = movie; }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

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

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
