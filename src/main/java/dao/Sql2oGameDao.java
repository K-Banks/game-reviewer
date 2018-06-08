package dao;

import models.Game;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.sql.SQLDataException;
import java.util.List;

public class Sql2oGameDao implements GameDao {
    private final Sql2o sql2o;

    public Sql2oGameDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public List<Game> getAll() {
        String sql = "SELECT * FROM games";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(Game.class);
        }
    }

    @Override
    public void add(Game game) {
        String sql = "INSERT INTO games (name, genre, minPlayers, maxPlayers, timeToPlay, developerId) VALUES (:name, :genre, :minPlayers, :maxPlayers, :timeToPlay, :developerId)";
        try(Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(game)
                    .executeUpdate()
                    .getKey();
            game.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public Game findById(int id) {
        String sql = "SELECT * FROM games WHERE id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Game.class);
        }
    }

    @Override
    public void update(int id, String name, String genre, Integer minPlayers, Integer maxPlayers, Integer timeToPlay, Integer developerId) {
        String sql = "UPDATE games SET (name, genre, minPlayers, maxPlayers, timeToPlay, developerId) = (:name, :genre, :minPlayers, :maxPlayers, :timeToPlay, :developerId) WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .addParameter("name", name)
                    .addParameter("genre", genre)
                    .addParameter("minPlayers", minPlayers)
                    .addParameter("maxPlayers", maxPlayers)
                    .addParameter("timeToPlay", timeToPlay)
                    .addParameter("developerId", developerId)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM games WHERE id = :id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllGames() {
        String sql = "DELETE FROM games";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Game> findGamesByDev(int developerId){
        String sql = "SELECT * FROM games WHERE developerId = :developerId";
        try(Connection con = sql2o.open()){
            return con.createQuery(sql)
                    .addParameter("developerId", developerId)
                    .executeAndFetch(Game.class);
        }
    }

    @Override
    public void updateRating(int id, List<Integer> ratings) {
        Integer overallRating = 0;
        if(ratings.size()>0){
            Integer ratingSum = 0;
            for(Integer rating: ratings){
                ratingSum += rating;
            }
            overallRating = (int) Math.floor(ratingSum / ratings.size());
        }

        String sql = "UPDATE games SET (rating) = (:rating) WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("rating", overallRating)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Game> sort(String sort) {
        String sql = "SELECT * FROM games ORDER BY " + sort;
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .executeAndFetch(Game.class);
        }
    }

    @Override
    public List<Game> searchGames(String searchTerm) {
//        String secondTerm = searchTerm + "%";
//        String thirdTerm = "%" + searchTerm;
        String sql = "SELECT * FROM games WHERE name LIKE :searchTerm";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sql)
                    .addParameter("searchTerm", searchTerm)
//                    .addParameter("secondTerm", secondTerm)
                    .executeAndFetch(Game.class);
        }
    }
}
