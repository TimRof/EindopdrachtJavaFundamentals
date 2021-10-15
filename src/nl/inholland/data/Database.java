package nl.inholland.data;

import nl.inholland.model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final List<User> users = new ArrayList<>();
    private final List<Movie> movies = new ArrayList<>();
    private final List<Show> shows = new ArrayList<>();
    private final List<Room> rooms = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public void AddUser(User user) {
        this.users.add(user);
    }

    public List<Show> getShows() {
        return shows;
    }

    public void addShow(Show show) {
        this.shows.add(show);
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public Show buyTickets(Show show, int amount, String name) {
        Show newShow = show.buyTickets(amount, name);
        shows.set(shows.indexOf(show), newShow);
        return newShow;
    }
    public void changeShow(Show oldShow, Show newShow)
    {
        int index = shows.indexOf(oldShow);
        shows.set(index, newShow);
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public Database() {
        generateUsers();
        generateMovies();
        generateShows();
        generateRooms();

        buyTestTickets();
    }
    public Room getRoomShows(int number){
        for (Room room: rooms) {
            if (room.getRoomNumber() == number)
                return room;
        }
        return null;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    private void generateUsers(){
    users.add(new User("Tim", "Roffelsen", "tim", "tim", AccessLevel.Admin));
    users.add(new User("Mark", "de Haan", "mark", "mark", AccessLevel.Admin));
    users.add(new User("Wim", "Wiltenburg", "wim", "wim", AccessLevel.Admin));

    users.add(new User("Admin", "Admin", "admin", "admin", AccessLevel.Admin));
    users.add(new User("User", "User", "user", "user", AccessLevel.User));
    }
    private void generateMovies(){
        addMovie(new Movie("Jurassic Park", 9.20, Duration.ofMinutes(127)));
        addMovie(new Movie("Back to the Future", 10.50, Duration.ofMinutes(116)));
        addMovie(new Movie("Labyrinth", 12.00, Duration.ofMinutes(101)));
    }
    private void generateShows(){
        LocalDateTime nearestTime = getNearestHour(LocalDateTime.now()).minusMinutes(15);
        Show showOne = new Show(movies.get(0), nearestTime);
        addShow(showOne);

        LocalDateTime nearestAvailable = showOne.getEndTime().plusMinutes(15);
        addShow(new Show(movies.get(1), nearestAvailable));
        addShow(new Show(movies.get(2), nearestTime));
    }
    private void generateRooms(){
        rooms.add(new Room(1, 200));
        rooms.get(0).addShow(shows.get(0));
        rooms.get(0).addShow(shows.get(1));
        rooms.add(new Room(2, 200));
        rooms.get(1).addShow(shows.get(2));
    }
    private void buyTestTickets(){
        rooms.get(0).getShows().get(0).buyTickets(178, "test");
    }
    private LocalDateTime getNearestHour(LocalDateTime localDateTime){
        LocalDateTime time = localDateTime;
        time = time.plusHours(1);
        return time.withMinute(0).withSecond(0);
    }
}
