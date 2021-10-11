package nl.inholland.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String movieTitle;
    private int roomNumber;
    private double price;
    private LocalDateTime startTime;
    private Duration movieDuration;
    private LocalDateTime endTime;
    private List<Ticket> tickets = new ArrayList<>();
    public Movie(String movieTitle, int roomNumber, double price, LocalDateTime startTime, Duration movieDuration){
        this.movieTitle = movieTitle;
        this.roomNumber = roomNumber;
        this.price = price;
        this.startTime = startTime;
        this.movieDuration = movieDuration;
        this.endTime = startTime.plus(movieDuration);
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

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Duration getMovieDuration() {
        return movieDuration;
    }

    public void setMovieDuration(Duration movieDuration) {
        this.movieDuration = movieDuration;
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
