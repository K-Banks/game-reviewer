package dao;

import models.Review;

import java.util.List;

public interface ReviewDao {
    List<Review> getAll();

    // CREATE
    void add(Review review);

    // READ
    Review findById(int id);
    List<Review> getReviewsByGameId(int gameId);
    List<Integer> getRatingsByGameId(int gameId);

    //UPDATE
    void update(int id, String comment, int rating, int gameId);

    // DELETE
    void deleteById(int id);
    void clearAllReviews();
}
