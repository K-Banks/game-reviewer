package dao;

import models.Developer;
import models.Game;
import models.Review;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class Sql2oGameDaoTest {
    private Sql2oGameDao gameDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        gameDao = new Sql2oGameDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void getAll_returnsEmptyIfNoGamesExist() {
        assertEquals(0, gameDao.getAll().size());
    }

    @Test
    public void add_addsNewGameToDatabase() {
        Game newGame = setupGame();
        gameDao.add(newGame);
        assertTrue(gameDao.getAll().size()==1);
    }

    @Test
    public void getAll_returnsAllGamesIncludesGameJustAdded() {
        Game newGame = setupGame();
        gameDao.add(newGame);
        assertEquals(1, gameDao.getAll().size());
    }

    @Test
    public void findById_returnsGameIdentifiedById() {
        Game newGame = setupGame();
        gameDao.add(newGame);
        assertTrue(gameDao.findById(newGame.getId()).getName().equals(newGame.getName()));
    }

    @Test
    public void updateChangesGameContent() {
        Game newGame = setupGame();
        gameDao.add(newGame);
        String initialName = gameDao.findById(newGame.getId()).getName();
        String initialGenre = gameDao.findById(newGame.getId()).getGenre();
        gameDao.update(newGame.getId(), "Small World", "board Game", 3, 6, 90, 1);
        assertFalse(initialName.equals(gameDao.findById(newGame.getId()).getName()));
        assertFalse(initialGenre.equals(gameDao.findById(newGame.getId()).getGenre()));
    }

    @Test
    public void deleteById_deletesGameByProvidedId() {
        Game newGame = setupGame();
        Game game2 = setupGame();
        gameDao.add(newGame);
        gameDao.add(game2);
        gameDao.deleteById(newGame.getId());
        assertTrue(gameDao.getAll().size()==1);
        assertFalse(gameDao.getAll().contains(newGame));
        assertTrue(gameDao.getAll().get(0).getName().equals(game2.getName()));
    }

    @Test
    public void deleteAllGames() {
        Game newGame = setupGame();
        Game game2 = setupGame();
        Game game3 = setupGame();
        gameDao.add(newGame);
        gameDao.add(game2);
        gameDao.add(game3);
        assertTrue(gameDao.getAll().size()==3);
        gameDao.clearAllGames();
        assertTrue(gameDao.getAll().size()==0);
    }

    @Test
    public void getAllGamesByDev() {
        Game game1 = setupGame();
        Game game2 = new Game ("Munchkin", "Card game", 3, 6, 90, 2);
        Game game3 = setupGame();
        gameDao.add(game1);
        gameDao.add(game2);
        gameDao.add(game3);
        List<Game> games = gameDao.findGamesByDev(1);
        assertEquals(2, games.size());
    }

    @Test
    public void updateRating_calculatesAndUpdatesRating() {
        Game game = setupGame();
        gameDao.add(game);
        List<Integer> ratings = new ArrayList<Integer>();
        ratings.add(3);
        ratings.add(4);
        gameDao.updateRating(game.getId(), ratings);
        int newRating = gameDao.findById(1).getRating();
        assertNotEquals(null, newRating);
        assertEquals(3, newRating);
    }

    @Test
    public void sort_returnsAllGamesSorted() {
        Game game1 = setupGame();
        Game game2 = new Game ("Ascension", "Card game", 2, 4, 90, 2);
        gameDao.add(game1);
        gameDao.add(game2);
        List<Game> games = gameDao.sort("name");
        assertEquals(2, games.size());
        assertEquals("Ascension", games.get(0).getName());
    }

    private Game setupGame() {
        return new Game ("Munchkin", "Card game", 3, 6, 90, 1);
    }
}