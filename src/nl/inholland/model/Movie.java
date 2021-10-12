package nl.inholland.model;

import java.time.Duration;

public class Movie {
    private String title;
    private double price;
    private Duration movieDuration;

    public Movie(String title, double price, Duration movieDuration) {
        this.title = title;
        this.price = price;
        this.movieDuration = movieDuration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Duration getMovieDuration() {
        return movieDuration;
    }

    public void setMovieDuration(Duration movieDuration) {
        this.movieDuration = movieDuration;
    }
}
