package models;

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
}
