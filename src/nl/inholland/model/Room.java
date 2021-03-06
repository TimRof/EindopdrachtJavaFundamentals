package nl.inholland.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Room {
    private int roomNumber;
    private int seatsAmount;
    private final List<Show> shows;

    public Room(int roomNumber, int seatsAmount) {
        this.roomNumber = roomNumber;
        this.seatsAmount = seatsAmount;
        shows = new ArrayList<>();
    }

    public ObservableList<Show> getShows() {
        ObservableList<Show> roomShows = FXCollections.observableArrayList();
        roomShows.addAll(shows);
        roomShows.sort(Comparator.comparing(Show::getStartTime));
        return roomShows;
    }
    public ObservableList<Show> getFilteredShows(String filter) {
        ObservableList<Show> roomShows = FXCollections.observableArrayList();
        for (Show s:shows)
        {
            if (!s.getMovie().getTitle().contains(filter))
                continue;
            roomShows.add(s);
        }

        roomShows.sort(Comparator.comparing(Show::getStartTime));
        return roomShows;
    }

    public void addShow(Show show) {
        show.makeTickets(seatsAmount);
        show.addRoomNumber(roomNumber);
        this.shows.add(show);
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getSeatsAmount() {
        return seatsAmount;
    }

    public void setSeatsAmount(int seatsAmount) {
        this.seatsAmount = seatsAmount;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber;
    }
}
