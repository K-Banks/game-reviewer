package dao;

import models.Game;

import java.util.List;

public interface GameDao {

    //GET ALL
    List<Game> getAll();
    List<Game> findGamesByDev(int devId);

    // CREATE
    void add(Game game);

    // READ
    Game findById(int id);

    //UPDATE
    void update(int id, String name, String genre, Integer minPlayers, Integer maxPlayers, Integer timeToPlay, Integer developerId);
    void updateRating(int id, List<Integer> ratings);

    // DELETE
    void deleteById(int id);
    void clearAllGames();

}
