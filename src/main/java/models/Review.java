package models;

public class Review {
    private String comment;
    private int rating;
    private int gameId;
    private int id;

    public Review(String comment, Integer rating, int gameId) {
        this.comment = comment;
        this.rating = rating;
        this.gameId = gameId;
    }

    public String getComment() {
        return comment;
    }

    public int getRating() {
        return rating;
    }

    public int getGameId() {
        return gameId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
