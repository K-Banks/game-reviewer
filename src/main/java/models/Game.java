package models;

public class Game {
    private String name;
    private String genre;
    private Integer minPlayers;
    private Integer maxPlayers;
    private Double timeToPlay;
    private Integer developerId;
    private Integer rating;
    private Integer id;

    public Game() { }

    public Game(String name, String genre, Integer minPlayers, Integer maxPlayers, Double timeToPlay) {
        this.name = name;
        this.genre = genre;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.timeToPlay = timeToPlay;
    }


}
