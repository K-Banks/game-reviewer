package models;

import java.util.Objects;

public class Game {
    private String name;
    private String genre;
    private Integer minPlayers;
    private Integer maxPlayers;
    private Integer timeToPlay;
    private Integer developerId;
    private Integer rating;
    private Integer id;

    public Game() { }

    public Game(String name, String genre, Integer minPlayers, Integer maxPlayers, Integer timeToPlay, Integer developerId) {
        this.name = name;
        this.genre = genre;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.timeToPlay = timeToPlay;
        this.developerId = developerId;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public Integer getMinPlayers() {
        return minPlayers;
    }

    public Integer getMaxPlayers() {
        return maxPlayers;
    }

    public Integer getTimeToPlay() {
        return timeToPlay;
    }

    public Integer getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(Integer developerId) {
        this.developerId = developerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(name, game.name) &&
                Objects.equals(genre, game.genre) &&
                Objects.equals(minPlayers, game.minPlayers) &&
                Objects.equals(maxPlayers, game.maxPlayers) &&
                Objects.equals(timeToPlay, game.timeToPlay) &&
                Objects.equals(developerId, game.developerId) &&
                Objects.equals(rating, game.rating) &&
                Objects.equals(id, game.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, genre, minPlayers, maxPlayers, timeToPlay, developerId, rating, id);
    }
}
