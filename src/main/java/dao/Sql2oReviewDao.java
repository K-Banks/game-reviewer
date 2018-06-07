package dao;

import models.Review;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oReviewDao implements ReviewDao{
    private final Sql2o sql2o;

    public Sql2oReviewDao(Sql2o sql2o) { this.sql2o = sql2o; }

    @Override
    public List<Review> getAll(){
        String sql = "SELECT * FROM reviews";
        try(Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(Review.class);
        }
    }

    @Override
    public void add(Review review) {
        String sql = "INSERT into reviews (comment, rating, gameID) VALUES (:comment, :rating, :gameId)";
        try(Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(review)
                    .executeUpdate()
                    .getKey();
            review.setId(id);
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public Review findById(int id) {
        String sql = "SELECT * FROM reviews WHERE id=:id";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Review.class);
        }
    }

    @Override
    public void update(int id, String comment, int rating, int gameId) {
        String sql = "UPDATE reviews SET (comment, rating, gameId) = (:comment, :rating, :gameId) WHERE id = :id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("comment", comment)
                    .addParameter("rating", rating)
                    .addParameter("gameId", gameId)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }

    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from reviews WHERE id = :id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllReviews() {
        String sql = "DELETE from reviews";
        try(Connection con = sql2o.open()){
            con.createQuery(sql).executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Review> getReviewsByGameId(int gameId) {
        String sql = "SELECT * FROM reviews WHERE gameId = :gameId";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("gameId", gameId)
                    .executeAndFetch(Review.class);
        }
    }
}
