package nl.inholland.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import nl.inholland.model.AccessLevel;
import nl.inholland.model.Movie;
import nl.inholland.model.Ticket;
import nl.inholland.model.User;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<User> users = new ArrayList<>();
    private List<Movie> movies = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public void AddUser(User user) {
        this.users.add(user);
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
    }

    public Database() {
        addUsers();
        addMovies();
    }
    public ObservableList<Movie> getRoomMovies(int number){
        ObservableList<Movie> roomMovies = FXCollections.observableArrayList();
        for (Movie movie:movies) {
            if (movie.getRoomNumber() == number)
                roomMovies.add(movie);
        }
        return roomMovies;
    }
    private void addUsers(){
    users.add(new User("Tim", "Roffelsen", "tim01", "tim01", AccessLevel.Admin));
    users.add(new User("Mark", "de Haan", "mark01", "mark01", AccessLevel.Admin));
    users.add(new User("Bob", "Bobssen", "bob01", "bob01", AccessLevel.User));
    users.add(new User("Admin", "Admin", "admin", "admin", AccessLevel.Admin));
    users.add(new User("User", "User", "user", "user", AccessLevel.User));
    }
    private void addMovies(){
        Duration jurassicDuration = Duration.ofMinutes(127);
        LocalDateTime nearestTime = getNearestHour(LocalDateTime.now()).minusMinutes(15);
        Movie jurassicMovie = new Movie("Jurassic Park", 1,19.93, nearestTime, jurassicDuration);
        movies.add(jurassicMovie);
        Duration backDuration = Duration.ofMinutes(116);
        LocalDateTime startTime = jurassicMovie.getEndTime().plusMinutes(15);
        movies.add(new Movie("Back to the Future", 1,14.20, startTime, backDuration));
        Duration labyrinthDuration = Duration.ofMinutes(101);
        movies.add(new Movie("Labyrinth", 2, 19.86, nearestTime, labyrinthDuration));
    }
    private LocalDateTime getNearestHour(LocalDateTime localDateTime){
        LocalDateTime time = localDateTime;
        time = time.plusHours(1);
        return time.withMinute(0).withSecond(0);

    }
}
