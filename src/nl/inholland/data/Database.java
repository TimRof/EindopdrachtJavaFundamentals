package nl.inholland.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import nl.inholland.model.AccessLevel;
import nl.inholland.model.Movie;
import nl.inholland.model.Show;
import nl.inholland.model.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<User> users = new ArrayList<>();
    private List<Movie> movies = new ArrayList<>();
    private List<Show> shows = new ArrayList<>();

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

    public void addMovie(Movie movie) {
        this.movies.add(movie);
    }

    public Database() {
        generateUsers();
        generateMovies();
        generateShows();
    }
    public ObservableList<Show> getRoomMovies(int number){
        ObservableList<Show> roomMovies = FXCollections.observableArrayList();
        for (Show movie: shows) {
            if (movie.getRoomNumber() == number)
                roomMovies.add(movie);
        }
        return roomMovies;
    }
    private void generateUsers(){
    users.add(new User("Tim", "Roffelsen", "tim01", "tim01", AccessLevel.Admin));
    users.add(new User("Mark", "de Haan", "mark01", "mark01", AccessLevel.Admin));
    users.add(new User("Bob", "Bobssen", "bob01", "bob01", AccessLevel.User));
    users.add(new User("Admin", "Admin", "admin", "admin", AccessLevel.Admin));
    users.add(new User("User", "User", "user", "user", AccessLevel.User));
    }
    private void generateMovies(){
        Duration jurassicDuration = Duration.ofMinutes(127);
        addMovie(new Movie("Jurassic Park", 19.93, Duration.ofMinutes(127)));
        Duration backDuration = Duration.ofMinutes(116);
        addMovie(new Movie("Back to the Future", 14.20, Duration.ofMinutes(116)));
        Duration labyrinthDuration = Duration.ofMinutes(101);
        addMovie(new Movie("Labyrinth", 19.86, Duration.ofMinutes(101)));
    }
    private void generateShows(){
        LocalDateTime nearestTime = getNearestHour(LocalDateTime.now()).minusMinutes(15);
        Show showOne = new Show(movies.get(0), 1, nearestTime);
        addShow(showOne);

        LocalDateTime nearestAvailable = showOne.getEndTime().plusMinutes(15);
        addShow(new Show(movies.get(1), 1, nearestAvailable));
        addShow(new Show(movies.get(2), 2, nearestTime));
    }
    private LocalDateTime getNearestHour(LocalDateTime localDateTime){
        LocalDateTime time = localDateTime;
        time = time.plusHours(1);
        return time.withMinute(0).withSecond(0);

    }
}
