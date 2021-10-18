package nl.inholland.data;

import nl.inholland.model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final List<User> users = new ArrayList<>();
    private final List<Movie> movies = new ArrayList<>();
    private final List<Room> rooms = new ArrayList<>(); // includes list of shows

    public List<User> getUsers() {
        return users;
    }

    public void AddUser(User user) {
        this.users.add(user);
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void removeMovie(Movie movie){
        movies.remove(movie);
    }

    public Show buyTickets(Show show, int amount, String name) {
        Show newShow = show.buyTickets(amount, name);
        for (Room r:rooms){
            if (r.getRoomNumber() == show.getRoomNumber())
            {
            r.getShows().set(r.getShows().indexOf(show), newShow);
            }
        }
        return newShow;
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
        AddUser(new User("Tim", "Roffelsen", "tim", "tim", AccessLevel.Admin));
        AddUser(new User("Mark", "de Haan", "mark", "mark", AccessLevel.Admin));
        AddUser(new User("Wim", "Wiltenburg", "wim", "wim", AccessLevel.Admin));

        AddUser(new User("Admin", "Admin", "admin", "admin", AccessLevel.Admin));
        AddUser(new User("User", "User", "user", "user", AccessLevel.User));
    }
    private void generateMovies(){
        addMovie(new Movie("Jurassic Park", 9.20, Duration.ofMinutes(127)));
        addMovie(new Movie("Back to the Future", 10.50, Duration.ofMinutes(116)));
        addMovie(new Movie("Labyrinth", 12.00, Duration.ofMinutes(101)));
    }
    private List<Show> generateShows(){
        List<Show> shows = new ArrayList<>();
        LocalDateTime nearestTime = getNearestHour(LocalDateTime.now()).minusMinutes(15);
        Show showOne = new Show(movies.get(0), nearestTime); // jurassic park
        shows.add(showOne);

        LocalDateTime nearestAvailable = showOne.getEndTime().plusMinutes(15);
        shows.add(new Show(movies.get(1), nearestAvailable)); // back to the future
        shows.add(new Show(movies.get(2), nearestTime)); // labyrinth
        return shows;
    }
    private void generateRooms(){
        List<Show> shows = generateShows();
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
        return time.withMinute(0).withSecond(0).withNano(0);
    }
}
