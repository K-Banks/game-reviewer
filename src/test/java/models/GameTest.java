package models;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void instantiatesNewGame_true() {
        Game newGame = setupGame();
        assertTrue(newGame instanceof Game);
    }

    @Test
    public void getName_returnsGameName() {
        Game newGame = setupGame();
        String expected = "Munchkin";
        assertEquals(expected, newGame.getName());
    }

    @Test
    public void getGenre_returnsGenreOfGame() {
        Game newGame = setupGame();
        String expected = "Card game";
        assertEquals(expected, newGame.getGenre());
    }

    @Test
    public void getMinPlayers_returnsMinPlayers() {
        Game newGame = setupGame();
        Integer expected = 3;
        assertEquals(expected, newGame.getMinPlayers());
    }

    @Test
    public void getMaxPlayers_returnsMaxPlayers() {
        Game newGame = setupGame();
        Integer expected = 6;
        assertEquals(expected, newGame.getMaxPlayers());
    }

    @Test
    public void getTimeToPlay_returnsTimeToPlay() {
        Game newGame = setupGame();
        Integer expected = 90;
        assertEquals(expected, newGame.getTimeToPlay());
    }

    @Test
    public void setDeveloperId_setsNewDeveloperIdValue() {
        Game newGame = setupGame();
        Integer expected = 1;
        newGame.setDeveloperId(expected);
        assertEquals(expected, newGame.getDeveloperId());
    }

    @Test
    public void setId_setsNewIdValue() {
        Game newGame = setupGame();
        Integer expected = 1;
        newGame.setId(expected);
        assertEquals(expected, newGame.getId());
    }

    @Test
    public void setRating_setsNewRatingValue() {
        Game newGame = setupGame();
        Integer expected = 3;
        newGame.setRating(expected);
        assertEquals(expected, newGame.getRating());
    }

    private Game setupGame() {
        return new Game ("Munchkin", "Card game", 3, 6, 90, 1);
    }
}